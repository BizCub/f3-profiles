package com.bizcub.debugOptionProfiles.gui;

import com.bizcub.debugOptionProfiles.Utils;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.debug.DebugScreenProfile;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
import net.minecraft.client.gui.layouts.LinearLayout;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.debug.DebugOptionsScreen;
import net.minecraft.network.chat.CommonComponents;

public class ProfileEditScreen extends DebugOptionsScreen {
    private final Screen parent;

    public ProfileEditScreen(Screen parent) {
        this.parent = parent;
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
        super.render(graphics, mouseX, mouseY, delta);
    }

    public void init() {
        super.init();

        for(GuiEventListener guiEventListener : this.children()) {
            if (guiEventListener instanceof AbstractWidget abstractWidget) {
                if (Utils.getTranslationKey(abstractWidget.getMessage().toString()).equals(DebugScreenProfile.DEFAULT.translationKey()) ||
                Utils.getTranslationKey(abstractWidget.getMessage().toString()).equals(DebugScreenProfile.PERFORMANCE.translationKey()) ||
                Utils.getTranslationKey(abstractWidget.getMessage().toString()).equals(Utils.getTranslationKey(CommonComponents.GUI_DONE.getContents().toString()))) {
                    abstractWidget.visible = false;
                }
            }
        }

        HeaderAndFooterLayout layout = new HeaderAndFooterLayout(this, 61, 33);
        LinearLayout linearLayout = layout.addToFooter(LinearLayout.horizontal().spacing(8));
        linearLayout.addChild(
                Button.builder(
                                CommonComponents.GUI_DONE,
                                (button -> this.minecraft.setScreen(parent)))
                        .build()
        );
        linearLayout.addChild(
                Button.builder(
                                CommonComponents.GUI_CANCEL,
                                (button -> this.minecraft.setScreen(parent)))
                        .build()
        );
        layout.arrangeElements();
        layout.visitWidgets(this::addRenderableWidget);
    }
}
