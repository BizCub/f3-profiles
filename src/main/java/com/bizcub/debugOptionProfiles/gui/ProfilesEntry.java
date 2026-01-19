package com.bizcub.debugOptionProfiles.gui;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.network.chat.Component;

import java.util.List;

public class ProfilesEntry extends ContainerObjectSelectionList.Entry<ProfilesEntry> {
    private Minecraft minecraft = Minecraft.getInstance();
    private final Component name;
    private final Button editButton;
    private final Button loadButton;
    private final ProfilesList profilesList;

    public ProfilesEntry(ProfilesList profilesList, Component name) {
        this.name = name;
        this.profilesList = profilesList;

        this.editButton = Button.builder(
                        Component.literal("edit"),
                        (button) -> minecraft.setScreen(new ProfileEditScreen(minecraft.screen, name, false)))
                .width(75)
                .build();

        this.loadButton = Button.builder(
                        Component.literal("load"),
                        (button) -> {
                            button.active = false;
                            minecraft.setScreen(new ProfileEditScreen(minecraft.screen, name, true));
                        })
                .width(75)
                .build();
    }

    @Override
    public void renderContent(GuiGraphics guiGraphics, int mouseX, int mouseY, boolean bl, float tickDelta) {
        int posX = this.profilesList.scrollBarX() - this.loadButton.getWidth() - 10;
        int posY = this.getContentY() - 2;

        this.editButton.setPosition(posX - this.editButton.getWidth(), posY);
        this.editButton.render(guiGraphics, mouseX, mouseY, tickDelta);

        this.loadButton.setPosition(posX, posY);
        this.loadButton.render(guiGraphics, mouseX, mouseY, tickDelta);

        guiGraphics.drawString(minecraft.font, this.name, this.getContentX(), this.getContentYMiddle() - 4, -1);
    }

    @Override
    public List<? extends NarratableEntry> narratables() {
        return ImmutableList.of(this.editButton, this.loadButton);
    }

    @Override
    public List<? extends GuiEventListener> children() {
        return ImmutableList.of(this.editButton, this.loadButton);
    }
}
