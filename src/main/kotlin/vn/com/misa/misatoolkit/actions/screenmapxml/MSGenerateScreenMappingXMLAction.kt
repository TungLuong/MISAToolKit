package vn.com.misa.misatoolkit.actions.screenmapxml

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.vfs.newvfs.impl.VirtualFileImpl
import vn.com.misa.misatoolkit.copilot.referenceFileInChat
import vn.com.misa.misatoolkit.copilot.replaceTextInChat

class MSGenerateScreenMappingXMLAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val dialog = ScreenMappingXMLContextDialog(
            prompt = ScreenMappingXMLPrompt(
                fileName = (e.dataContext.getData("virtualFile") as VirtualFileImpl).name,
                type = ScreenMappingXMLPrompt.ScreenType.ACTIVITY,
            )
        )
        if (dialog.showAndGet()) {
            e.referenceFileInChat()
            e.replaceTextInChat(dialog.buildPrompt())
        }
    }
}

