package org.example.MISAPlugin.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.ui.Messages

class BuildCreateParamClassPromptAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        // Example description
        val description = "yêu cầu của BA"
        val prompt = buildCreateParamClassPrompt(description)
        Messages.showMessageDialog(
            e.project,
            prompt,
            "Build Create Param Class Prompt",
            Messages.getInformationIcon()
        )
    }

    private fun buildCreateParamClassPrompt(desc: String): String {
        return "create a parameter class for \"$desc\""
    }
}