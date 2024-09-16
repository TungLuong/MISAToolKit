package org.example.MISAPlugin.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.openapi.wm.ToolWindowManager
import com.intellij.ui.dsl.builder.panel
import java.awt.Dimension
import javax.swing.JComponent
import javax.swing.JPanel
import javax.swing.JTextField

class GenerateScreenMappingXMLAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        // Show the context input dialog
        val dialog = ContextInputDialog()
        if (dialog.showAndGet()) {
            val context = dialog.getContext()
            println("Context: $context")

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
                                comp.text = "Hello, Copilot! Context: $context"
                                return
                            }
                        }
                    }
                }
            }
        }
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
            return panel {
                row("Feature:") { cell(contextField) }
                row("Requirement:") { cell(context2Field) }
            }
        }

        fun getContext(): String {
            return contextField.text
        }
    }

}