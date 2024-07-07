package org.example.democopycodeplugin

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.components.Service
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.project.Project
import java.io.File
import java.util.*

class MyDemoAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return
        val propertiesService: ProjectPropertiesService = project.getService(ProjectPropertiesService::class.java)

        val nexusUser = propertiesService.getNexusUser() ?: "nexusUser properties is not found in gradle.properties"
        val nexusPassword = propertiesService.getNexusPassword() ?: "nexusUser properties is not found in gradle.properties"

        val message = """
            Nexus User: $nexusUser
            Nexus Password: $nexusPassword
        """.trimIndent()

        Messages.showMessageDialog(project, message, "Nexus Properties", Messages.getInformationIcon())
    }

}

@Service(Service.Level.PROJECT)
class ProjectPropertiesService(private val project: Project) {
    private val properties: Properties = Properties()

    init {
        loadProjectProperties()
        loadUserProperties()
    }

    private fun loadProjectProperties() {
        // Check gradle.properties in the project root directory
        val rootPropertiesFile = File(project.basePath, "gradle.properties")
        if (rootPropertiesFile.exists()) {
            properties.load(rootPropertiesFile.inputStream())
        }
    }

    private fun loadUserProperties() {
        // Check gradle.properties in the user's home .gradle directory
        val userHome = System.getProperty("user.home")
        val userGradlePropertiesFile = File(userHome, ".gradle/gradle.properties")
        if (userGradlePropertiesFile.exists()) {
            properties.load(userGradlePropertiesFile.inputStream())
        }
    }

    fun getNexusUser(): String? {
        return properties.getProperty("nexusUser")
    }

    fun getNexusPassword(): String? {
        return properties.getProperty("nexusPassword")
    }

    fun getAllProperties(): Properties {
        return properties
    }
}