package org.example.MISAPlugin.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.wm.ToolWindowManager
import org.example.MISAPlugin.dialog.ContextInputDialog
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
}