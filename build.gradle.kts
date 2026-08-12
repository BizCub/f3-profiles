plugins {
    id("io.github.bizcub.multiloader")
}

multiloader {
    sc.replacements {
        string(scp >= "26.2") {
            replace(".setScreen(", ".gui.setScreen(")
        }
        string(scp >= "26.1") {
            replace(".render(", ".extractRenderState(")
            replace(".drawString(", ".text(")
            replace("renderContent", "extractContent")
            replace("GuiGraphics", "GuiGraphicsExtractor")
        }
    }

    setMREnvironment(mrEnvs.clientOnly)
    setCFEnvironment(cfEnvs.client)

    versionRange("26.2", to = "latest")
    versionRange("1.21.10", to = "1.21.11")

    if (isFabric) {
        addDependency(
            dependency = "net.fabricmc:fabric-loader:${getDep("fabric")}"
        )
        addDependency(
            dependency = "net.fabricmc.fabric-api:fabric-api:${getDep("fabric-api")}",
            isPublishDepEnabled = true,
            isPublishDepRequired = true
        )
    }
}
