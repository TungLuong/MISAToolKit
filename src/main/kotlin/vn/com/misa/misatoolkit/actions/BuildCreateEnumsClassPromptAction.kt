package vn.com.misa.misatoolkit.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.ui.DialogWrapper
import vn.com.misa.misatoolkit.common.setHint
import vn.com.misa.misatoolkit.copilot.replaceTextInChat
import java.awt.Dimension
import javax.swing.BorderFactory
import javax.swing.JComponent
import javax.swing.JScrollPane
import javax.swing.JTextArea

class BuildCreateEnumsClassPromptAction : AnAction() {
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
            setHint("VD: Các chức năng có thể thực hiện được với hóa đơn.")
        }

        private val inputRequirementField = JTextArea().apply {
            lineWrap = true
            wrapStyleWord = true
            border = BorderFactory.createEmptyBorder(10, 10, 10, 10)
            setHint(
                "VD:\n" +
                        "- Với hoá đơn có trạng thái\n" +
                        "   + Chưa phát hành: Phát hành hóa đơn, Xem hóa đơn, Sửa, Xóa\n" +
                        "   + Đang phát hành: Xem hóa đơn\n" +
                        "   + Phát hành lỗi: Phát hành hóa đơn, Xem hóa đơn, Sửa, Xóa\n"
            )
        }

        init {
            init()
            title = "Nhập thông tin enum cần tạo"
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
            val enumName: String = ""

            if (enumName.isEmpty()) {
                return "- feature: $feature \n- requirement: $requirement \n=> create all enum need for feature from requirement like this file"
            }
            return "- feature: $feature \n- requirement: $requirement \n=> create enum $enumName need for feature from requirement like this file"
        }
    }
}