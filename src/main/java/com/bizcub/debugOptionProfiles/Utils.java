package com.bizcub.debugOptionProfiles;

import com.bizcub.debugOptionProfiles.gui.ProfileEditScreen;
import com.bizcub.debugOptionProfiles.gui.ProfilesScreen;

import java.nio.file.Path;

public class Utils {
    public static boolean isMyScreenOpen = false;
    public static String profileName;
    public static String editedProfileName;

    public static ProfilesScreen profilesScreen;
    public static ProfileEditScreen profileEditScreen;

    public static Path getConfigPath() {
        Path path = Path.of("config/debug-option-profiles");
        path.toFile().mkdirs();
        return path;
    }
}
