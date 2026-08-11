package io.github.bizcub.debugOptionProfiles.gui;

import io.github.bizcub.debugOptionProfiles.Utils;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.debug.DebugOptionsScreen;
import net.minecraft.network.chat.Component;

public class ProfileEditScreen extends DebugOptionsScreen {
    public static Screen parent;
    private final boolean close;

    public ProfileEditScreen(Screen screen, Component entryName, boolean closeImmediately) {
        parent = screen;
        this.close = closeImmediately;
        Utils.profileName = entryName.getString();
    }

    public void init() {
        super.init();
        minecraft.debugEntries.load();
        if (close) onClose();
    }

    @Override
    public void onClose() {
        super.onClose();
        this.minecraft.gui.setScreen(new ProfilesScreen(this));
    }
}
