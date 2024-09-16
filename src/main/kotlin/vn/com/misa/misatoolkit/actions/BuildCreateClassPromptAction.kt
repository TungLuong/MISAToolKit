package vn.com.misa.misatoolkit.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.impl.ActionButton
import com.intellij.openapi.wm.ToolWindowManager
import com.intellij.ui.components.JBTextArea
import vn.com.misa.misatoolkit.dialog.ContextInputDialog
import javax.swing.JPanel

class BuildCreateClassPromptAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        // Show the context input dialog
        val dialog = ContextInputDialog()
        if (dialog.showAndGet()) {
            // Logic to generate XML file with context
            val project = e.project ?: return
            val toolWindow = ToolWindowManager.getInstance(project).getToolWindow("GitHub Copilot Chat")
            toolWindow?.show(null)

            toolWindow?.let {
                val jbTextArea = (((((toolWindow.contentManager.contents.first().component.components[2] as JPanel).components[2] as JPanel).components[1] as JPanel).components[0] as JPanel).components[0] as JBTextArea)
                jbTextArea.text = "Hello, Copilot! Context:"
                // reference
                // file:///Users/tungluongngoc/StudioProjects/Cukcuk-lite/app/src/main/java/com/example/cukcuklitedemo/reactproduct/ReactProductActivity.java

                // (((((toolWindow.contentManager.contents.first().component.components[2] as JPanel).components[2] as JPanel).components[1] as JPanel).components.get(1) as JPanel).components.get(0) as Container)
                // send data
                ((((((toolWindow.contentManager.contents.first().component.components[2] as JPanel).components[2] as JPanel).components[1] as JPanel).components[2] as JPanel).components.get(0) as JPanel).components[0] as ActionButton).click()

            }
        }
    }

    private fun buildCreateClassPrompt(desc: String): String {
        return "create a class for \"$desc\""
    }
}

