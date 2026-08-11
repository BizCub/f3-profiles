package io.github.bizcub.debugOptionProfiles.mixin;

import io.github.bizcub.debugOptionProfiles.Utils;
import net.minecraft.client.gui.components.debug.DebugScreenEntryList;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;

@Mixin(DebugScreenEntryList.class)
public abstract class DebugScreenEntryListMixin {

    @Mutable @Shadow @Final private File debugProfileFile;

    @Inject(method = "load", at = @At("HEAD"))
    private void changeProfileFile(CallbackInfo ci) {
        if (Utils.isMyScreenOpen) {
            debugProfileFile = Utils.getConfigPath().resolve(Utils.profileName + Utils.configFormat).toFile();
        }
    }
}
