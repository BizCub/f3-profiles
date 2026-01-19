package com.bizcub.debugOptionProfiles.gui;

import com.bizcub.debugOptionProfiles.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.network.chat.Component;

import java.io.File;

public class ProfilesList extends ContainerObjectSelectionList<ProfilesEntry> {

    public ProfilesList(ProfilesScreen screen, Minecraft minecraft) {
        super(minecraft, screen.width, screen.layout.getContentHeight(), screen.layout.getHeaderHeight(), 20);
        refreshEntries();
    }

    public void refreshEntries() {
        this.clearEntries();

        File[] files = Utils.getConfigPath().toFile().listFiles();
        String jsonFormat = ".json";

        if (files != null) {
            for (File file : files) {
                if (file.getName().endsWith(jsonFormat)) {
                    String name = file.getName().substring(0, file.getName().lastIndexOf(jsonFormat));
                    addEntry(new ProfilesEntry(this, Component.literal(name)));
                }
            }
        }
    }

    public int scrollBarX() {
        return super.scrollBarX() + 15;
    }

    public int getRowWidth() {
        return 200;
    }
}
