import com.bizcub.multiloader.MultiLoader
import dev.kikugie.stonecutter.build.StonecutterBuildExtension
import me.modmuss50.mpp.ModPublishExtension

val stonecutter = project.extensions.getByType(StonecutterBuildExtension::class.java)

project.extensions.configure<MultiLoader>("multiloader") {
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
        addDependency("implementation", "net.fabricmc:fabric-loader:latest.release")
    }

    if (isNeoForge) {
        addRepository("https://maven.neoforged.net/releases")
    }

    project.extensions.configure<ModPublishExtension>("publishMods") {

    }
}
