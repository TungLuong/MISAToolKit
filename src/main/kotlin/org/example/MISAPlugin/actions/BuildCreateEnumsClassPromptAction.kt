package org.example.MISAPlugin.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.ui.Messages

class BuildCreateEnumsClassPromptAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        // Example description
        val description = "yêu cầu của BA"
        val prompt = buildCreateEnumsClassPrompt(description)
        Messages.showMessageDialog(
            e.project,
            prompt,
            "Build Create Enums Class Prompt",
            Messages.getInformationIcon()
        )
    }

    private fun buildCreateEnumsClassPrompt(desc: String): String {
        return "create an enum class for \"$desc\""
    }
}