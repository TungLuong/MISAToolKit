package org.example.MISAPlugin.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.ui.Messages

class BuildCreateResultClassPromptAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        // Example description
        val description = "yêu cầu của BA"
        val prompt = buildCreateResultClassPrompt(description)
        Messages.showMessageDialog(
            e.project,
            prompt,
            "Build Create Result Class Prompt",
            Messages.getInformationIcon()
        )
    }

    private fun buildCreateResultClassPrompt(desc: String): String {
        return "create a result class for \"$desc\""
    }
}