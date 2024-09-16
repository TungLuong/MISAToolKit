package vn.com.misa.misatoolkit.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.ui.Messages

class BuildCreateEmptyFunctionPromptAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        // Example description
        val description = "yêu cầu của BA"
        val prompt = buildCreateEmptyFunctionPrompt(description)
        Messages.showMessageDialog(
            e.project,
            prompt,
            "Build Create Empty Function Prompt",
            Messages.getInformationIcon()
        )
    }

    private fun buildCreateEmptyFunctionPrompt(desc: String): String {
        return "create an empty function for \"$desc\""
    }
}