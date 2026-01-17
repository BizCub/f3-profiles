package com.bizcub.debugOptionProfiles.gui;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.layouts.LinearLayout;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.options.OptionsSubScreen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

public class ProfilesScreen extends OptionsSubScreen {
    private ProfilesList list;
    protected final Screen parent;

    public ProfilesScreen(Screen parent) {
        super(parent, null, Component.literal("MYS"));
        this.parent = parent;
    }

    protected void addContents() {
        this.layout.setHeaderHeight(33);
        this.list = this.layout.addToContents(new ProfilesList(this, this.minecraft));
    }

    protected void addFooter() {
        LinearLayout linearLayout = this.layout.addToFooter(LinearLayout.horizontal().spacing(8));
        linearLayout.addChild(
                Button.builder(
                                CommonComponents.GUI_DONE,
                                (button -> this.onClose()))
                        .build()
        );
    }

    @Override
    protected void addOptions() {

    }
}
