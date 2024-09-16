package vn.com.misa.misatoolkit.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.wm.ToolWindowManager
import vn.com.misa.misatoolkit.copilot.referenceFileInChat
import vn.com.misa.misatoolkit.copilot.replaceTextInChat
import vn.com.misa.misatoolkit.dialog.ContextInputDialog
import javax.swing.JPanel
import javax.swing.JTextField

class MSGenerateScreenMappingXMLAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
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
        e.referenceFileInChat()
        e.replaceTextInChat("Long test \n long test")
        // Show the context input dialog

    }
}