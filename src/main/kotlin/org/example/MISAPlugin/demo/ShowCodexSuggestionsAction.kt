package org.example.MISAPlugin.demo

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.ui.Messages
import org.example.MISAPlugin.common.CodexService

class ShowCodexSuggestionsAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return
        val codexService = project.getService(CodexService::class.java)
        val editor = e.getData(com.intellij.openapi.actionSystem.CommonDataKeys.EDITOR) ?: return
        val selectionModel = editor.selectionModel
        val selectedText = selectionModel.selectedText ?: return
        val suggestions = codexService.getSuggestions(selectedText)
        Messages.showMessageDialog(project, suggestions.joinToString("\n"), "Codex Suggestions", Messages.getInformationIcon())
    }
}