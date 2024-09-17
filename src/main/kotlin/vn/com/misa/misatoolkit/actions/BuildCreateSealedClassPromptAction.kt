package vn.com.misa.misatoolkit.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.ui.Messages

class BuildCreateSealedClassPromptAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        // Example description
        val description = "yêu cầu của BA"
        val prompt = buildCreateSealedClassPrompt(description)
        Messages.showMessageDialog(
            e.project,
            prompt,
            "Build Create Sealed Class Prompt",
            Messages.getInformationIcon()
        )
    }

    private fun buildCreateSealedClassPrompt(desc: String): String {
        return "create a sealed class for \"$desc\""
    }
}