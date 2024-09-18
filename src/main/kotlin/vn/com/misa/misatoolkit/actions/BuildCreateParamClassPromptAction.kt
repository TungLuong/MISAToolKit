package vn.com.misa.misatoolkit.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.openapi.ui.Messages
import vn.com.misa.misatoolkit.common.setHint
import vn.com.misa.misatoolkit.copilot.replaceTextInChat
import java.awt.Dimension
import javax.swing.BorderFactory
import javax.swing.JComponent
import javax.swing.JScrollPane
import javax.swing.JTextArea

class BuildCreateParamClassPromptAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val dialog = ContextInputDialog()
        if (dialog.showAndGet()) {
            e.replaceTextInChat(dialog.buildCreateEnumPrompt())
        }
    }


    private class ContextInputDialog : DialogWrapper(true) {
        private val inputFeatureField = JTextArea().apply {
            lineWrap = true
            wrapStyleWord = true
            border = BorderFactory.createEmptyBorder(10, 10, 10, 10)
            setHint("VD: Kiểm tra thông tin trước khi đăng nhập")
        }

        private val inputRequirementField = JTextArea().apply {
            lineWrap = true
            wrapStyleWord = true
            border = BorderFactory.createEmptyBorder(10, 10, 10, 10)
            setHint(
                "VD:" +
                        "\n+ Nếu tên miền để trống: Hiển thị cảnh báo \"Tên miền không được để trống\"" +
                        "\n+ Nếu tên miền không đúng định dạng: Hiển thị cảnh báo \"Tên miền không đúng định dạng\"" +
                        "\n+ Nếu tên đăng nhập để trống: Hiển thị cảnh báo \"Tên đăng nhập không được để trống\"" +
                        "\n+ Nếu mật khẩu để trống: Hiển thị cảnh báo \"Mật khẩu không được không được để trống\""
            )
        }

        init {
            init()
            title = "Nhập thông tin param class cần tạo"
        }

        override fun createCenterPanel(): JComponent {
            return com.intellij.ui.dsl.builder.panel {
                row("Feature:") {}
                row {
                    cell(JScrollPane(inputFeatureField).apply {
                        preferredSize = Dimension(800, 50)
                    })
                }
                row("Requirement:") {}
                row {
                    cell(JScrollPane(inputRequirementField).apply {
                        preferredSize = Dimension(800, 200)
                    })
                }
            }
        }

        fun buildCreateEnumPrompt(): String {
            val feature: String = inputFeatureField.text
            val requirement: String = inputRequirementField.text
            val className: String = ""
            if (className.isEmpty()) {
                return "- feature: $feature \n- requirement: $requirement \n=> create all class represent input of function need for feature from requirement like this file"
            }
            return "- feature: $feature \n- requirement: $requirement \n=> create class $className represent input of function need for feature from requirement like this file"

        }
    }
}