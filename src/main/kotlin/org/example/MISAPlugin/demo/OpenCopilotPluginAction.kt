package org.example.MISAPlugin.demo

import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import java.awt.event.InputEvent

class OpenCopilotPluginAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
//        val project = e.project ?: return
//        val toolWindow = ToolWindowManager.getInstance(project).getToolWindow("GitHub Copilot")
//        toolWindow?.show(null)

        val inst = ActionManager.getInstance()
        //(ActionManager.getInstance() as ActionManagerImpl).actionIds.filter { it.contains("copilot") }
        val other = inst.getAction("copilot.chat.show") // another plugin's action

        val ie: InputEvent? = e.inputEvent

        inst.tryToExecute(other, ie, ie?.component, null, true)
    }
}