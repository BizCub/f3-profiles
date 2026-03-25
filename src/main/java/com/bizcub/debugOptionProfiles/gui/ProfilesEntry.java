package com.bizcub.debugOptionProfiles.gui;

import com.bizcub.debugOptionProfiles.Utils;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.network.chat.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class ProfilesEntry extends ContainerObjectSelectionList.Entry<ProfilesEntry> {
    private final Minecraft minecraft = Minecraft.getInstance();
    private final Component name;
    private final Button removebutton;
    private final Button editButton;
    private final Button loadButton;
    private final ProfilesList profilesList;

    public ProfilesEntry(ProfilesList profilesList, Component name) {
        this.name = name;
        this.profilesList = profilesList;

        this.loadButton = Button.builder(
                        Component.literal("✔"),
                        (button) -> {
                            Utils.activeButton = name;
                            rewriteMainFile();
                            minecraft.setScreen(new ProfileEditScreen(minecraft.screen, name, true));
                        })
                .width(20)
                .build();

        this.editButton = Button.builder(
                        Component.literal("✎"),
                        (button) -> {
                            Utils.activeButton = name;
                            rewriteMainFile();
                            minecraft.setScreen(new ProfileEditScreen(minecraft.screen, name, false));
                        })
                .width(20)
                .build();

        this.removebutton = Button.builder(
                        Component.literal("❌"),
                        (button) -> {
                            Utils.getConfigPath().resolve(name.getString() + Utils.configFormat).toFile().delete();
                            minecraft.setScreen(new ProfilesScreen(minecraft.screen));
                        })
                .width(20)
                .build();

        if (Utils.activeButton.equals(name)) {
            loadButton.active = false;
        }
    }

    @Override
    public void extractContent(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, boolean bl, float tickDelta) {
        int posX = this.profilesList.scrollBarX() - this.removebutton.getWidth() - 30;
        int posY = this.getContentY() - 2;

        this.removebutton.setPosition(posX, posY);
        this.removebutton.extractRenderState(guiGraphics, mouseX, mouseY, tickDelta);

        this.editButton.setPosition(posX - this.editButton.getWidth(), posY);
        this.editButton.extractRenderState(guiGraphics, mouseX, mouseY, tickDelta);

        this.loadButton.setPosition(posX - this.loadButton.getWidth() * 2, posY);
        this.loadButton.extractRenderState(guiGraphics, mouseX, mouseY, tickDelta);

        guiGraphics.text(minecraft.font, this.name, this.getContentX(), this.getContentYMiddle() - 4, -1);
    }

    private ImmutableList getButtons() {
        return ImmutableList.of(this.editButton, this.loadButton, this.removebutton);
    }

    @Override
    public List<? extends NarratableEntry> narratables() {
        return getButtons();
    }

    @Override
    public List<? extends GuiEventListener> children() {
        return getButtons();
    }

    private void rewriteMainFile() {
        Path path1 = Utils.getConfigPath().resolve(name.getString() + Utils.configFormat);
        Path path2 = Path.of(Utils.mainFileName);
        try {
            Files.copy(path1, path2, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
