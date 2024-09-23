package vn.com.misa.misatoolkit.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.editor.CaretModel

class EnterKeyAction : AnAction() {

    val KEY_VARIABLE = "var:"

    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return
        val editor = e.getData(com.intellij.openapi.actionSystem.CommonDataKeys.EDITOR) ?: return
        val document = editor.document
        val caretModel: CaretModel = editor.caretModel
        val caretOffset = caretModel.offset

        // Find the nearest "var:" keyword before the caret position
        val textBeforeCaret = document.text.substring(0, caretOffset)
        val keywordIndex = textBeforeCaret.lastIndexOf(KEY_VARIABLE)

        if (keywordIndex != -1) {
            val extractedText = textBeforeCaret.substring(keywordIndex + KEY_VARIABLE.length, caretOffset).trim()
            // Example modification: Insert text at the current caret position
            val runnable = Runnable {
                val insertText = "create a variable for \"$extractedText\" by english"
                val newOffset = keywordIndex
                document.deleteString(newOffset, caretOffset)
                document.insertString(newOffset, insertText)
                caretModel.moveToOffset(newOffset + insertText.length)
            }
            WriteCommandAction.runWriteCommandAction(project, runnable)
        }



    }
}