package org.example.democopycodeplugin

import ai.grazie.utils.applyIf
import com.intellij.execution.ExecutionManager
import com.intellij.execution.executors.DefaultRunExecutor
import com.intellij.execution.filters.TextConsoleBuilderFactory
import com.intellij.execution.ui.ConsoleView
import com.intellij.execution.ui.ConsoleViewContentType
import com.intellij.execution.ui.ExecutionConsole
import com.intellij.execution.ui.RunContentDescriptor
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.progress.ProgressIndicator
import com.intellij.openapi.progress.ProgressManager
import com.intellij.openapi.progress.Task
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages
import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.io.InputStreamReader

object ScriptUtils {

    const val SCRIPT_NAME = "copy_components.sh"
    const val SCRIPT_URL = "https://alm-github.systems.uk.hsbc/api/v3/repos/mobile/platform-common-ui-components-set-1-android-lib/contents/$SCRIPT_NAME"

    fun runScript(
        project: Project?,
        targetPath: String,
        packageName: String,
        isAll: Boolean,
        components: List<String> = emptyList()
    ) {
        project?.basePath?.let { basePath ->

            // Create a console view to show logs
            val consoleView: ConsoleView = createConsoleView(project)
            showConsole(project, consoleView)

            val propertiesService: ProjectPropertiesService = project.getService(ProjectPropertiesService::class.java)
            val nexusUser = propertiesService.getNexusUser() ?: "nexusUser properties is not found in gradle.properties, please check."
            val nexusPassword = propertiesService.getNexusPassword() ?: "nexusPassword properties is not found in gradle.properties, please check."

            val script = "curl -u '$nexusUser:$nexusPassword' -H 'Accept: application/vnd.github.v3.raw' -LO $SCRIPT_URL"

            log(consoleView, "packageName: $packageName")
            log(consoleView, "targetPath: $targetPath")
            log(consoleView, "Selected components: ${if (isAll) "All" else components}")

            // Use ProgressManager to show a progress indicator
            ProgressManager.getInstance().run(object : Task.Backgroundable(project, "Downloading Script") {
                override fun run(indicator: ProgressIndicator) {
                    indicator.text = "Copying components..."
                    indicator.isIndeterminate = true

                    try {
                        log(consoleView, "Starting download of script...")

                        // Download the script using curl
                        val downloadProcessBuilder = ProcessBuilder("bash", "-c", script)
//                        val downloadProcessBuilder = ProcessBuilder("echo", components.toString())
                        downloadProcessBuilder.directory(File(basePath))
                        val downloadProcess = downloadProcessBuilder.start()
                        val downloadExitCode = downloadProcess.waitFor()

                        if (downloadExitCode != 0) {
                            log(consoleView, "Failed to download the script with exit code: $downloadExitCode")
                            ApplicationManager.getApplication().invokeLater {
                                Messages.showErrorDialog(
                                    "Failed to download the script with exit code: $downloadExitCode",
                                    "Download Error"
                                )
                            }
                            return
                        }

                        log(consoleView, "Script downloaded successfully as $SCRIPT_NAME")

                        // Optionally, you can make the downloaded script executable
                        val scriptPath = "$basePath/$SCRIPT_NAME"
                        val chmodProcessBuilder = ProcessBuilder("chmod", "+x", scriptPath)
                        chmodProcessBuilder.directory(File(basePath))
                        val chmodProcess = chmodProcessBuilder.start()
                        val chmodExitCode = chmodProcess.waitFor()
                        if (chmodExitCode != 0) {
                            log(consoleView, "Failed to make the script executable with exit code: $chmodExitCode")
                            ApplicationManager.getApplication().invokeLater {
                                Messages.showErrorDialog(
                                    "Failed to make the script executable with exit code: $chmodExitCode",
                                    "Chmod Error"
                                )
                            }
                            return
                        }

                        log(consoleView, "Script is now executable.")
                        // Run the script
                        val commandList = mutableListOf("bash", scriptPath, targetPath, packageName).applyIf(!isAll) {
                            addAll(components)
                        }
                        // Pass commandList to ProcessBuilder
                        val executeProcessBuilder = ProcessBuilder(commandList)
//                        val executeProcessBuilder = ProcessBuilder("bash", testScriptPath)
                        log(consoleView, "Script executing : ${executeProcessBuilder.command()}")
                        executeProcessBuilder.directory(File(basePath))
                        val executeProcess = executeProcessBuilder.start()

                        // Capture and log script output
                        val reader = BufferedReader(InputStreamReader(executeProcess.inputStream))
                        val errorReader = BufferedReader(InputStreamReader(executeProcess.errorStream))
                        var line: String?
                        while (reader.readLine().also { line = it } != null) {
                            log(consoleView, line!!)
                        }

                        while (errorReader.readLine().also { line = it } != null) {
                            log(consoleView, line!!, ConsoleViewContentType.ERROR_OUTPUT)
                        }

                        val executeExitCode = executeProcess.waitFor()
                        if (executeExitCode == 0) {
                            log(consoleView, "Script executed successfully.")
                            ApplicationManager.getApplication().invokeLater {
                                Messages.showInfoMessage("Script executed successfully.", "Execution Complete")
                            }
                        } else {
                            log(
                                consoleView,
                                "Script execution failed with exit code: $executeExitCode",
                                contentType =ConsoleViewContentType.ERROR_OUTPUT
                            )
                            ApplicationManager.getApplication().invokeLater {
                                Messages.showErrorDialog(
                                    "Script execution failed with exit code: $executeExitCode",
                                    "Execution Error"
                                )
                            }
                        }
                        indicator.isIndeterminate = false
                    } catch (ex: IOException) {
                        log(consoleView, "Error downloading script: ${ex.message}")
                        ex.printStackTrace()
                        ApplicationManager.getApplication().invokeLater {
                            Messages.showErrorDialog("Error downloading script: ${ex.message}", "Download Error")
                        }
                    } catch (ex: InterruptedException) {
                        log(consoleView, "Script download interrupted: ${ex.message}")
                        ex.printStackTrace()
                        ApplicationManager.getApplication().invokeLater {
                            Messages.showErrorDialog("Script download interrupted: ${ex.message}", "Download Error")
                        }
                    }
                }
            })
        }
    }

    private fun createConsoleView(project: Project): ConsoleView {
        return TextConsoleBuilderFactory.getInstance().createBuilder(project).console
    }

    private fun showConsole(project: Project, consoleView: ConsoleView) {
        val runContentDescriptor =
            RunContentDescriptor(consoleView as ExecutionConsole, null, consoleView.component, "Copy Components")
        ExecutionManager.getInstance(project).getContentManager()
            .showRunContent(DefaultRunExecutor.getRunExecutorInstance(), runContentDescriptor)
    }

    private fun log(
        consoleView: ConsoleView,
        message: String,
        contentType: ConsoleViewContentType = ConsoleViewContentType.NORMAL_OUTPUT,
    ) {
        ApplicationManager.getApplication().invokeLater {
            consoleView.print("$message\n", contentType)
        }
    }
}