package com.bizcub.debugOptionProfiles.mixin;

import com.bizcub.debugOptionProfiles.gui.ProfilesScreen;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TitleScreen.class)
public class HomeScreenMixin extends Screen {

    protected HomeScreenMixin(Component component) {
        super(component);
    }

    @Inject(method = "createNormalMenuOptions", at = @At("RETURN"))
    private void addModsButton(CallbackInfoReturnable<Integer> cir) {
        this.addRenderableWidget(Button
                .builder(Component.literal("Debug Screen"), (button) -> this.minecraft.setScreen(new ProfilesScreen(this)))
                .bounds(this.width / 2 - 100, this.height / 2 - 36, 200, 20)
                .build());
    }
}
