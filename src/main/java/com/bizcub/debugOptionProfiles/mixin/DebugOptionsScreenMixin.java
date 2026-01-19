package com.bizcub.debugOptionProfiles.mixin;

import com.bizcub.debugOptionProfiles.Utils;
import com.bizcub.debugOptionProfiles.gui.ProfilesScreen;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.debug.DebugOptionsScreen;
import net.minecraft.network.chat.Component;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.nio.file.Path;

@Mixin(DebugOptionsScreen.class)
public abstract class DebugOptionsScreenMixin extends Screen {

    @Shadow public abstract DebugOptionsScreen.@Nullable OptionList getOptionList();

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

    @Redirect(method = "init", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/Button;builder(Lnet/minecraft/network/chat/Component;Lnet/minecraft/client/gui/components/Button$OnPress;)Lnet/minecraft/client/gui/components/Button$Builder;"))
    private Button.Builder done(Component component, Button.OnPress onPress) {
        if (Utils.isMyScreenOpen) {
            EditBox searchBox = new EditBox(minecraft.font, width / 2 - 145, 8, 100, 20, Component.empty());
            searchBox.setValue(Utils.profileName);
            addRenderableWidget(searchBox);

            return Button.builder(component, button -> {
                this.minecraft.debugEntries.save();

                Utils.editedProfileName = searchBox.getValue();
                Path path1 = getConfigFile(Utils.profileName);
                Path path2 = getConfigFile(Utils.editedProfileName);
                path1.toFile().renameTo(path2.toFile());

                this.minecraft.setScreen(new ProfilesScreen(this));
            });
        }
        return Button.builder(component, onPress);
    }

    @Inject(method = "renderBlurredBackground", at = @At("HEAD"))
    private void test(CallbackInfo ci) {
        getOptionList().updateSearch("");
    }

    @Unique
    private static Path getConfigFile(String path) {
        return Utils.getConfigPath().resolve(path + ".json");
    }
}
