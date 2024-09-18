package vn.com.misa.misatoolkit.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.ui.InputValidator
import com.intellij.openapi.ui.MessageMultilineInputDialog
import com.intellij.openapi.ui.Messages
import vn.com.misa.misatoolkit.copilot.referenceFileInChat
import vn.com.misa.misatoolkit.copilot.replaceTextInChat

class MSConvertJsonToObjectAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val dialog = MessageMultilineInputDialog(
            e.project,
            "Input your json here",
            "Json to Object",
            Messages.getInformationIcon(),
            "",
            object : InputValidator {
                override fun checkInput(str: String?) = !str.isNullOrEmpty() && str.isJson()

                override fun canClose(p0: String?) = true
            },
            arrayOf(
                Messages.getOkButton(),
            ), 0
        )
        if (dialog.showAndGet()) {
            e.referenceFileInChat()
            e.replaceTextInChat("Convert the following JSON to an object using the project's format. Provide only the final class, not the steps to achieve it: \n${dialog.inputString}")
        }
    }
}

private fun String.isJson(): Boolean {
    return try {
        this.trim().let {
            it.startsWith("{") && it.endsWith("}") || it.startsWith("[") && it.endsWith("]")
        }
    } catch (e: Exception) {
        false
    }
}