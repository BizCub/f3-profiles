package com.bizcub.debugOptionProfiles;

import net.minecraft.network.chat.Component;

import java.nio.file.Path;

public class Utils {
    public static boolean isMyScreenOpen = false;
    public static String profileName;
    public static String editedProfileName;
    public static Component activeButton = Component.empty();

    public static Path getConfigPath() {
        Path path = Path.of("config/debug-option-profiles");
        path.toFile().mkdirs();
        return path;
    }
}
