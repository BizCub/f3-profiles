import com.bizcub.multiloader.MultiLoader
import dev.kikugie.stonecutter.build.StonecutterBuildExtension

apply(plugin = "dev.kikugie.fletching-table")

val stonecutter = project.extensions.getByType(StonecutterBuildExtension::class.java)

project.extensions.configure<MultiLoader>("multiloader") {
    access()

    project.afterEvaluate {
        stonecutter.let { sc ->
            sc.replacements {
                string(scp >= "26.1") {
                    replace(".render(", ".extractRenderState(")
                    replace(".drawString(", ".text(")
                    replace("renderContent", "extractContent")
                    replace("GuiGraphics", "GuiGraphicsExtractor")
                }
            }
        }
    }

    if (isFabric) {
        addDependency("implementation", "net.fabricmc:fabric-loader:${getDep("fabric")}")
    }

    if (isFabric) addPublishDep("requires", "fabric-api")
}
