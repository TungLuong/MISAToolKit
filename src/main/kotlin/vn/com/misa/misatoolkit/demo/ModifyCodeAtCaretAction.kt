package vn.com.misa.misatoolkit.demo

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.editor.CaretModel
import com.intellij.openapi.command.WriteCommandAction

class ModifyCodeAtCaretAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return
        val editor = e.getData(com.intellij.openapi.actionSystem.CommonDataKeys.EDITOR) ?: return
        val document = editor.document
        val caretModel: CaretModel = editor.caretModel
        val caretOffset = caretModel.offset

        // Example modification: Insert text at the current caret position
        val runnable = Runnable {
            document.insertString(caretOffset, "// Inserted text at caret position\n")
        }
        WriteCommandAction.runWriteCommandAction(project, runnable)
    }
}