package com.bizcub.debugOptionProfiles.gui;

import com.bizcub.debugOptionProfiles.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;

public class ProfilesList extends ContainerObjectSelectionList<ProfilesEntry> {

    public ProfilesList(ProfilesScreen screen) {
        super(Minecraft.getInstance(), screen.width, screen.layout.getContentHeight(), screen.layout.getHeaderHeight(), 20);
        refreshEntries();
    }

    public void refreshEntries() {
        this.clearEntries();

        ArrayList<String> profiles = Utils.getExistedProfileNames();
        for (String profile : profiles) {
            if (profile.endsWith(Utils.configFormat)) {
                String name = profile.substring(0, profile.lastIndexOf(Utils.configFormat));
                addEntry(new ProfilesEntry(this, Component.literal(name)));
            }
        }
    }

    public int scrollBarX() {
        return super.scrollBarX() + 20;
    }

    public int getRowWidth() {
        return 200;
    }
}
