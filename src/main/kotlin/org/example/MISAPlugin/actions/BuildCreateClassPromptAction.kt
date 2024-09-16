package org.example.MISAPlugin.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.ui.DialogWrapper
import org.example.MISAPlugin.common.CopilotHelper
import java.awt.Dimension
import javax.swing.JComponent
import javax.swing.JTextField

class BuildCreateClassPromptAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        // Show the context input dialog
        val dialog = ContextInputDialog()
        if (dialog.showAndGet()) {
            // Logic to generate XML file with context
            CopilotHelper.sendTextToCopilot(buildCreateClassPrompt(dialog.getContext()), e.project!!)
        }
    }

    private fun buildCreateClassPrompt(desc: String): String {
        return "create a class for \"$desc\""
    }

    class ContextInputDialog : DialogWrapper(true) {
        private val contextField = JTextField().apply {
            preferredSize = Dimension(500, 100) // Set preferred size
        }

        private val context2Field = JTextField().apply {
            preferredSize = Dimension(400, 100) // Set preferred size
        }

        init {
            init()
            title = "Enter Context Information"
        }

        override fun createCenterPanel(): JComponent {
            return com.intellij.ui.dsl.builder.panel {
                row("Feature:") { cell(contextField) }
                row("Requirement:") { cell(context2Field) }
            }
        }

        fun getContext(): String {
            return contextField.text
        }
    }
}

