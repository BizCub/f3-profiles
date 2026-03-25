plugins {
    id("multiloader-common")
    id("me.modmuss50.mod-publish-plugin")
}

reps.clear()
deps.clear()

if (isFabric) {
    deps.add(Dependency("net.fabricmc:fabric-loader:latest.release", "implementation"))
    deps.add(Dependency("net.fabricmc.fabric-api:fabric-api:${getProp("fabric_api")}", "implementation"))
}

if (isNeoForge) {
    reps.add(Repository("https://maven.neoforged.net/releases"))
}

sc.replacements {
    string(scp >= "26.1") {
        replace(".render(", ".extractRenderState(")
        replace(".drawString(", ".text(")
        replace("renderContent", "extractContent")
        replace("GuiGraphics", "GuiGraphicsExtractor")
    }
}
