package com.bizcub.debugOptionProfiles.mixin;

import com.bizcub.debugOptionProfiles.Utils;
import com.bizcub.debugOptionProfiles.gui.ProfilesScreen;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.debug.DebugOptionsScreen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Mixin(DebugOptionsScreen.class)
public abstract class DebugOptionsScreenMixin extends Screen {

    @Unique Button debugScreenOptionProfiles$doneButton;
    @Unique EditBox debugScreenOptionProfiles$searchBox;

    protected DebugOptionsScreenMixin(Component component) {
        super(component);
    }

    @Inject(method = "init", at = @At("TAIL"))
    private void addPresetsButton(CallbackInfo ci) {
        if (!Utils.isMyScreenOpen) {
            this.addRenderableWidget(Button
                    .builder(Utils.getTranslateComponent("profile-edit-screen.presets"),
                            (button) -> this.minecraft.setScreen(new ProfilesScreen(this)))
                    .bounds(8, 8, 50, 20)
                    .build());
        }
    }

    //~ if >=26.1 'renderBlurredBackground' -> 'extractBlurredBackground'
    @Inject(method = "extractBlurredBackground", at = @At("HEAD"))
    private void editBoxError(CallbackInfo ci) {
        if (this.debugScreenOptionProfiles$searchBox != null) {
            ArrayList<String> prohibitedSymbols = new ArrayList<>(List.of("\\", "/", ":", "*", "?", "\"", "<", ">", "|"));
            ArrayList<String> profiles = Utils.getExistedProfileNames();
            profiles.remove(Utils.profileName + Utils.configFormat);

            if (debugScreenOptionProfiles$doneButton != null) debugScreenOptionProfiles$doneButton.active = true;
            debugScreenOptionProfiles$searchBox.setTextColor(0xffffffff);

            for (String symbol : prohibitedSymbols) {
                if (debugScreenOptionProfiles$searchBox.getValue().contains(symbol)) {
                    debugScreenOptionProfiles$setFieldTextRed();
                }
            }
            for (String profile : profiles) {
                String name = profile.substring(0, profile.lastIndexOf(Utils.configFormat));
                if (debugScreenOptionProfiles$searchBox.getValue().equals(name)) {
                    debugScreenOptionProfiles$setFieldTextRed();
                }
            }
        }
    }

    @Unique
    private void debugScreenOptionProfiles$setFieldTextRed() {
        debugScreenOptionProfiles$doneButton.active = false;
        debugScreenOptionProfiles$searchBox.setTextColor(0xffff5555);
    }

    @Redirect(method = "init", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/Button$Builder;build()Lnet/minecraft/client/gui/components/Button;"))
    private Button doneButtonInject(Button.Builder instance) {
        if (Utils.isMyScreenOpen) {
            debugScreenOptionProfiles$searchBox = new EditBox(minecraft.font, width / 2 - 145, 8, 100, 20, Component.empty());
            debugScreenOptionProfiles$searchBox.setValue(Utils.profileName);
            addRenderableWidget(debugScreenOptionProfiles$searchBox);

            debugScreenOptionProfiles$doneButton = Button.builder(
                    CommonComponents.GUI_DONE,
                    (button) -> {
                        minecraft.debugEntries.save();

                        Utils.editedProfileName = debugScreenOptionProfiles$searchBox.getValue();
                        Path path1 = debugScreenOptionProfiles$getConfigFile(Utils.profileName);
                        Path path2 = debugScreenOptionProfiles$getConfigFile(Utils.editedProfileName);
                        path1.toFile().renameTo(path2.toFile());

                        minecraft.setScreen(new ProfilesScreen(this));
                    }
            ).width(60).build();
            return debugScreenOptionProfiles$doneButton;
        }
        return instance.build();
    }

    @Unique
    private static Path debugScreenOptionProfiles$getConfigFile(String path) {
        return Utils.getConfigPath().resolve(path + Utils.configFormat);
    }
}
