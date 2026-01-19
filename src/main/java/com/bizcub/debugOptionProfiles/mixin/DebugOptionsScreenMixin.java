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

    @Unique Button doneButton;
    @Unique EditBox searchBox;

    protected DebugOptionsScreenMixin(Component component) {
        super(component);
    }

    @Inject(method = "init", at = @At("TAIL"))
    private void init(CallbackInfo ci) {
        if (!Utils.isMyScreenOpen) {
            this.addRenderableWidget(Button
                    .builder(Component.literal("presets"),
                            (button) -> this.minecraft.setScreen(new ProfilesScreen(this)))
                    .bounds(8, 8, 50, 20)
                    .build());
        }
    }

    @Inject(method = "renderBlurredBackground", at = @At("HEAD"))
    private void editBoxError(CallbackInfo ci) {
        if (this.searchBox != null) {
            ArrayList<String> prohibitedSymbols = new ArrayList<>(List.of("\\", "/", ":", "*", "?", "\"", "<", ">", "|"));
            ArrayList<String> profiles = Utils.getExistedProfileNames();
            profiles.remove(Utils.profileName + Utils.configFormat);

            doneButton.active = true;
            searchBox.setTextColor(0xffffffff);

            for (String symbol : prohibitedSymbols) {
                if (searchBox.getValue().contains(symbol)) {
                    setFieldTextRed();
                }
            }
            for (String profile : profiles) {
                String name = profile.substring(0, profile.lastIndexOf(Utils.configFormat));
                if (searchBox.getValue().equals(name)) {
                    setFieldTextRed();
                }
            }
        }
    }

    @Unique
    private void setFieldTextRed() {
        doneButton.active = false;
        searchBox.setTextColor(0xffff5555);
    }

    @Redirect(method = "init", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/Button$Builder;build()Lnet/minecraft/client/gui/components/Button;"))
    private Button doneButtonInject(Button.Builder instance) {
        if (Utils.isMyScreenOpen) {
            searchBox = new EditBox(minecraft.font, width / 2 - 145, 8, 100, 20, Component.empty());
            searchBox.setValue(Utils.profileName);
            addRenderableWidget(searchBox);

            doneButton = Button.builder(
                    CommonComponents.GUI_DONE,
                    (button) -> {
                        minecraft.debugEntries.save();

                        Utils.editedProfileName = searchBox.getValue();
                        Path path1 = getConfigFile(Utils.profileName);
                        Path path2 = getConfigFile(Utils.editedProfileName);
                        path1.toFile().renameTo(path2.toFile());

                        minecraft.setScreen(new ProfilesScreen(this));
                    }
            ).width(60).build();
            return doneButton;
        }
        return instance.build();
    }

    @Unique
    private static Path getConfigFile(String path) {
        return Utils.getConfigPath().resolve(path + ".json");
    }
}
