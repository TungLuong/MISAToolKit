package vn.com.misa.misatoolkit.actions
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.ui.Messages

class BuildCreateFunctionPromptAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        // Example description
        val description = "yêu cầu của BA"
        val prompt = buildCreateFunctionPrompt(description)
        Messages.showMessageDialog(
            e.project,
            prompt,
            "Build Create Function Prompt",
            Messages.getInformationIcon()
        )
    }

    private fun buildCreateFunctionPrompt(desc: String): String {
        return "create a function for \"$desc\""
    }
}