package org.example.MISAPlugin.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.wm.ToolWindowManager
import org.example.MISAPlugin.dialog.ContextInputDialog
import javax.swing.JComponent
import javax.swing.JPanel
import javax.swing.JTextField
import com.intellij.ui.layout.panel

class BuildCreateClassPromptAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        // Show the context input dialog
        val dialog = ContextInputDialog()
        if (dialog.showAndGet()) {
            // Logic to generate XML file with context
            val project = e.project ?: return
            val toolWindow = ToolWindowManager.getInstance(project).getToolWindow("GitHub Copilot Chat")
            toolWindow?.show(null)

            val contentManager = toolWindow?.contentManager
            val contents = contentManager?.contents

            if (contents != null) {
                for (content in contents) {
                    val component = content.component
                    if (component is JPanel) {
                        for (comp in component.components) {
                            if (comp is JTextField) {
                                //comp.text = "Hello, Copilot! Context: $context"
                                return
                            }
                        }
                    }
                }
            }
        }
    }

    private fun buildCreateClassPrompt(desc: String): String {
        return "create a class for \"$desc\""
    }

    class ContextInputDialog : DialogWrapper(true) {
        private val featureField = JTextField()
        private val requirementField = JTextField()

        init {
            init()
            title = "Input Context"
        }

        override fun createCenterPanel(): JComponent {
            return panel {
                row("Feature:") { featureField() }
                row("Requirement:") { requirementField() }
            }
        }

        fun getFeature(): String {
            return featureField.text
        }

        fun getRequirement(): String {
            return requirementField.text
        }
    }
}

