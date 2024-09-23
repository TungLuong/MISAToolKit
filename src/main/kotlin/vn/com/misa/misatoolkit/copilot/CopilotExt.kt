package vn.com.misa.misatoolkit.copilot

import com.github.copilot.chat.input.SendStopActionButtonPanel
import com.intellij.openapi.actionSystem.ActionGroup
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.DefaultActionGroup
import com.intellij.openapi.actionSystem.impl.ActionButton
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowManager
import com.intellij.ui.components.JBTextArea
import com.intellij.util.ui.JBUI
import com.jetbrains.rd.swing.mouseClicked
import vn.com.misa.misatoolkit.common.getComponentsByType
import javax.swing.JPanel

private const val EDITOR_POPUP_MENU_ACTION = "EditorPopupMenu"
private const val GITHUB_COPILOT_GROUP_ACTION = "GitHub Copilot"
private const val REFERENCE_FILE_IN_CHAT_ACTION = "Reference File in Chat"
private const val GITHUB_COPILOT_CHAT = "GitHub Copilot Chat"

val Project.copilotChat: ToolWindow
    get() {
        return ToolWindowManager
            .getInstance(this)
            .getToolWindow(GITHUB_COPILOT_CHAT)
            ?: throw GithubCopilotException.CopilotNotInstalled()
    }

@Throws(GithubCopilotException::class)
fun AnActionEvent.replaceTextInChat(content: String) {
    val toolWindow = project?.copilotChat ?: throw GithubCopilotException.CopilotNotInstalled()
    toolWindow.show {
        toolWindow.component.getComponentsByType(JBTextArea::class).lastOrNull()?.let { chatTextArea ->
            chatTextArea.text = content.trim()
        }
        // send data
        try {
            ((((((toolWindow.contentManager.contents.first().component.components[2] as JPanel).components[2] as JPanel).components[1] as JPanel).components[2] as JPanel).components[0] as JPanel).components[0] as? ActionButton)?.click()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

@Throws(GithubCopilotException::class)
fun AnActionEvent.referenceFileInChat() {
    val editorPopupMenu = actionManager.getAction(EDITOR_POPUP_MENU_ACTION) as? DefaultActionGroup
    val copilotActionGroup = (
            editorPopupMenu
                ?.getChildren(this, actionManager)
                ?.firstOrNull { it.templateText == GITHUB_COPILOT_GROUP_ACTION } as? ActionGroup
            ) ?: throw GithubCopilotException.CopilotNotInstalled()

    copilotActionGroup
        .getChildren(null, actionManager)
        .firstOrNull { it.templateText == REFERENCE_FILE_IN_CHAT_ACTION }
        ?.actionPerformed(this)
        ?: throw GithubCopilotException.CopilotNotInstalled()
}