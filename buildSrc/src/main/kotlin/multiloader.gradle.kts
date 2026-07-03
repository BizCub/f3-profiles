plugins {
    id("me.modmuss50.mod-publish-plugin")
    id("dev.kikugie.fletching-table")
    id("com.bizcub.multiloader")
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

    if (isFabric) {
        addDependency(
            dependency = "net.fabricmc:fabric-loader:${getDep("fabric")}"
        )
        addDependency(
            dependency = "net.fabricmc.fabric-api:fabric-api:${getDep("fabric-api")}",
            isPublishDepEnabled = true,
            publishRequirement = "requires"
        )
    }
}
