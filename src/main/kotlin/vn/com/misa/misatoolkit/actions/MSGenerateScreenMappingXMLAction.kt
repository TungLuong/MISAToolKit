package vn.com.misa.misatoolkit.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import vn.com.misa.misatoolkit.copilot.referenceFileInChat
import vn.com.misa.misatoolkit.copilot.replaceTextInChat

class MSGenerateScreenMappingXMLAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        e.referenceFileInChat()
        e.replaceTextInChat("Long test \n long test")
    }
}