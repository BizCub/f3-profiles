package io.github.bizcub.debugOptionProfiles;

import net.minecraft.network.chat.Component;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;

public class Utils {
    public static boolean isMyScreenOpen = false;
    public static String profileName;
    public static String editedProfileName;
    public static Component activeButton = Component.empty();
    public static String configFormat = ".json";
    public static String mainFileName = "debug-profile.json";

    public static Path getConfigPath() {
        Path path = Path.of("config/debug-option-profiles");
        path.toFile().mkdirs();
        return path;
    }

    public static ArrayList<String> getExistedProfileNames() {
        File[] files = Utils.getConfigPath().toFile().listFiles();
        ArrayList<String> profiles = new ArrayList<>();
        if (files != null) {
            for (File file : files) {
                profiles.add(file.getName());
            }
        }
        return profiles;
    }

    public static Component getTranslateComponent(String key) {
        return Component.translatable("gui.debug-option-profiles." + key);
    }
}
