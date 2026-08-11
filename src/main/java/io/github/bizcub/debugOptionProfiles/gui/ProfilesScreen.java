package io.github.bizcub.debugOptionProfiles.gui;

import io.github.bizcub.debugOptionProfiles.Utils;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.layouts.LinearLayout;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.debug.DebugOptionsScreen;
import net.minecraft.client.gui.screens.options.OptionsSubScreen;
import net.minecraft.network.chat.CommonComponents;

import java.io.File;
import java.io.IOException;

public class ProfilesScreen extends OptionsSubScreen {
    private ProfilesList list;

    public ProfilesScreen(Screen parent) {
        super(parent, null, Utils.getTranslateComponent("profiles-screen"));
        Utils.isMyScreenOpen = true;
    }

    @Override
    protected void addOptions() {
    }

    protected void addContents() {
        this.layout.setHeaderHeight(33);
        this.list = this.layout.addToContents(new ProfilesList(this));
    }

    protected void addFooter() {
        LinearLayout linearLayout = this.layout.addToFooter(LinearLayout.horizontal().spacing(8));
        linearLayout.addChild(
                Button.builder(
                        Utils.getTranslateComponent("profiles-screen.add"),
                        (button) -> {
                            for (int i = 1; i > 0; i++) {
                                File file = Utils.getConfigPath().resolve(Utils.getTranslateComponent("profile").getString() + " " + i + Utils.configFormat).toFile();
                                if (!file.exists()) {
                                    try {
                                        file.createNewFile();
                                        onClose();
                                        minecraft.gui.setScreen(new ProfilesScreen(this));
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }
                                    break;
                                }
                            }
                        }
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
        minecraft.gui.setScreen(new DebugOptionsScreen());
    }

    protected void repositionElements() {
        this.layout.arrangeElements();
        this.list.updateSize(this.width, this.layout);
    }
}
