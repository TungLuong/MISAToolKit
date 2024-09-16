package org.example.MISAPlugin.common

import com.intellij.openapi.actionSystem.impl.ActionButton
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindowManager
import com.intellij.ui.components.JBTextArea
import javax.swing.JPanel

object CopilotHelper {
    // function gui text den copilot window
    fun sendTextToCopilot(text: String, project: Project) {
        // Logic cho action cha (nếu cần)
        val toolWindow = ToolWindowManager.getInstance(project).getToolWindow("GitHub Copilot Chat")
        toolWindow?.show(null)
        toolWindow?.let {
            val jbTextArea =
                (((((toolWindow.contentManager.contents.first().component.components[2] as JPanel).components[2] as JPanel).components[1] as JPanel).components[0] as JPanel).components[0] as JBTextArea)
            jbTextArea.text = "Hello, Copilot! Context:"
            // reference
            // file:///Users/tungluongngoc/StudioProjects/Cukcuk-lite/app/src/main/java/com/example/cukcuklitedemo/reactproduct/ReactProductActivity.java

            // (((((toolWindow.contentManager.contents.first().component.components[2] as JPanel).components[2] as JPanel).components[1] as JPanel).components.get(1) as JPanel).components.get(0) as Container)
            // send data
            ((((((toolWindow.contentManager.contents.first().component.components[2] as JPanel).components[2] as JPanel).components[1] as JPanel).components[2] as JPanel).components[0] as JPanel).components[0] as ActionButton).click()
        }
    }
}