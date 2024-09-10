package org.example.MISAPlugin.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class GenerateCommentAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        // Logic cho Hỗ trợ sinh comment
        println("Hỗ trợ sinh comment clicked")
    }
}