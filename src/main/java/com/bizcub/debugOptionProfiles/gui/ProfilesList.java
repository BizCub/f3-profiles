package com.bizcub.debugOptionProfiles.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.network.chat.Component;

public class ProfilesList extends ContainerObjectSelectionList<ProfilesEntry> {

    public ProfilesList(ProfilesScreen screen, Minecraft minecraft) {
        super(minecraft, screen.width, screen.layout.getContentHeight(), screen.layout.getHeaderHeight(), 20);
        refreshEntries();
    }

    public void refreshEntries() {
        this.clearEntries();
        addEntry(new ProfilesEntry(this, Component.literal("oioi")));
        addEntry(new ProfilesEntry(this, Component.literal("42")));
        addEntry(new ProfilesEntry(this, Component.literal("gg")));
    }

    public int scrollBarX() {
        return super.scrollBarX() + 15;
    }

    public int getRowWidth() {
        return 340;
    }
}
