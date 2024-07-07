package org.example.democopycodeplugin

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages
import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.io.InputStreamReader


//fun getApplicationId(project: Project): String? {
//    val moduleManager = ModuleManager.getInstance(project)
//    val modules = moduleManager.modules
//
//    for (module in modules) {
//        val androidModel = GradleAndroidModel.get(module)
//        if (androidModel != null) {
//            return androidModel.applicationId
//        }
//    }
//
//    return null
//}

class DialogAction: AnAction() {
    override fun actionPerformed(p0: AnActionEvent) {
        val project: Project? = p0.project

//        val taskManager: ExternalSystemTaskManager<*> = ExternalSystemTaskManager.getInstance(project)
//        val settingsManager: ExternalSystemSettingsManager = ExternalSystemSettingsManager.getInstance()
//        retrieveAndDisplayProperties(project)
        project?.basePath?.let { basePath ->
            val scriptName = "script.sh"
            val scriptPath = "$basePath/$scriptName"

            try {
                // Create ProcessBuilder
                val builder = ProcessBuilder("bash", scriptPath)

                // Set working directory to project root
                builder.directory(File(basePath))

                // Start the process
                val process = builder.start()

                // Capture script output
                val reader = BufferedReader(InputStreamReader(process.inputStream))
                val output = StringBuilder()
                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    output.append(line).append("\n")
                }

                // Wait for the script to complete
                val exitCode = process.waitFor()

                // Display output to user
                if (exitCode == 0) {
                    Messages.showInfoMessage("Script executed successfully:\n$output", "Script Output")
                } else {
                    Messages.showErrorDialog("Script execution failed with code: $exitCode", "Script Execution Error")
                }

            } catch (ex: IOException) {
                ex.printStackTrace()
                Messages.showErrorDialog("Error executing script: ${ex.message}", "Script Execution Error")
            } catch (ex: InterruptedException) {
                ex.printStackTrace()
                Messages.showErrorDialog("Script execution interrupted: ${ex.message}", "Script Execution Error")
            }
        }
    }
}