package org.example.democopycodeplugin

import com.android.tools.idea.gradle.project.model.GradleAndroidModel
import com.android.tools.idea.insights.androidAppId
import com.android.tools.idea.util.toIoFile
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.module.Module
import com.intellij.openapi.vfs.VirtualFile
import org.jetbrains.android.facet.AndroidFacet
import org.jetbrains.android.facet.AndroidRootUtil
import java.io.File
import javax.xml.parsers.DocumentBuilderFactory

class CopyComponentsAction : AnAction() {
    override fun actionPerformed(anActionEvent: AnActionEvent) {
        val project = anActionEvent.project ?: return
        println("project name: ${project.name}")
        val packageName = getPackageName(anActionEvent)
        val virtualFile = anActionEvent.getData(CommonDataKeys.VIRTUAL_FILE)
        println("selected virtualFile: ${virtualFile?.path}")
        // Get the relative path of the virtual file
        MyDialogWrapper(project, packageName?:"", virtualFile?.path?:"").show()
    }

    private fun isAndroidLibraryModule(module: Module): Boolean {
        val facet = AndroidFacet.getInstance(module)
        return facet != null && facet.configuration.isLibraryProject
    }

    private fun isAppModule(module: Module): Boolean {
        val facet = AndroidFacet.getInstance(module)
        return facet != null && facet.configuration.isAppProject
    }

    private fun getPackageName(event: AnActionEvent): String? {
        val module: Module? = event.getData(PlatformDataKeys.MODULE)
        val virtualFile: VirtualFile? = event.getData(PlatformDataKeys.VIRTUAL_FILE)

        if (module == null) return null

        if (isAppModule(module)) {
            println("==========isAppModule=== ${module.name}")
            val androidModuleModel = GradleAndroidModel.get(module)
            return module.androidAppId?:androidModuleModel?.applicationId
        }

        if (isAndroidLibraryModule(module)) {
            println("==========isAndroidLibraryModule=== ${module.name}")
            // Try to get package name from AndroidManifest.xml
            val facet = AndroidFacet.getInstance(module)
            if (facet != null) {
                val manifestFile = AndroidRootUtil.getPrimaryManifestFile(facet)
                if (manifestFile != null) {
                    val packageName = parsePackageNameFromManifest(manifestFile.toIoFile())
                    if (packageName.isNullOrEmpty().not()) {
                        return packageName
                    }
                }
            }

            // Fallback: Try to get package name from build.gradle or build.gradle.kts
            if (virtualFile != null) {
                val buildFile = findBuildFile(virtualFile)
                println("==========buildFile=== $buildFile")
                return buildFile?.let { parsePackageNameFromBuildFile(it) }
            }
        }

        return null
    }

    private fun findBuildFile(file: VirtualFile): File? {
        var currentDir: VirtualFile? = if (file.isDirectory) file else file.parent
        while (currentDir != null) {
            val buildGradle = currentDir.findChild("build.gradle")
            if (buildGradle != null && buildGradle.exists()) {
                return File(buildGradle.path)
            }
            val buildGradleKts = currentDir.findChild("build.gradle.kts")
            if (buildGradleKts != null && buildGradleKts.exists()) {
                return File(buildGradleKts.path)
            }
            currentDir = currentDir.parent
        }
        return null
    }

    private fun parsePackageNameFromManifest(manifestFile: File): String? {
        try {
            val documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder()
            val document = documentBuilder.parse(manifestFile)
            val manifestElement = document.documentElement
            return manifestElement.getAttribute("package")
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    private fun parsePackageNameFromBuildFile(buildFile: File): String? {
        try {
            buildFile.bufferedReader().useLines { lines ->
                for (line in lines) {
                    val trimmedLine = line.trim()
                    if (trimmedLine.startsWith("namespace")) {
                        val namespaceValue = trimmedLine.substringAfter("namespace").trim().trim('=', ' ', '"', '\'')
                        if (namespaceValue.isNotEmpty()) {
                            return namespaceValue
                        }
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
}
