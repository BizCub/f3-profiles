package com.bizcub.debugOptionProfiles.gui;

import com.bizcub.debugOptionProfiles.Utils;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.layouts.LinearLayout;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.debug.DebugOptionsScreen;
import net.minecraft.client.gui.screens.options.OptionsSubScreen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

public class ProfilesScreen extends OptionsSubScreen {
    private ProfilesList list;

    public ProfilesScreen(Screen parent) {
        super(parent, null, Component.literal("ProfilesScreen"));
        Utils.isMyScreenOpen = true;
    }

    @Override
    protected void addOptions() {
    }

    protected void addContents() {
        this.layout.setHeaderHeight(33);
        this.list = this.layout.addToContents(new ProfilesList(this, this.minecraft));
        repositionElements();
    }

    protected void addFooter() {
        LinearLayout linearLayout = this.layout.addToFooter(LinearLayout.horizontal().spacing(8));
        linearLayout.addChild(
                Button.builder(
                        Component.literal("add"),
                        (button) -> onClose()
                ).build()
        );
        linearLayout.addChild(
                Button.builder(
                        CommonComponents.GUI_DONE,
                        (button) -> onClose()
                ).build()
        );
    }

    public void onClose() {
        super.onClose();
        Utils.isMyScreenOpen = false;
        minecraft.setScreen(new DebugOptionsScreen());
    }

    protected void repositionElements() {
        this.layout.arrangeElements();
        this.list.updateSize(this.width, this.layout);
    }
}
