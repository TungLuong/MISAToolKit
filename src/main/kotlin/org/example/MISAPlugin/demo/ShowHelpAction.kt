package org.example.MISAPlugin.demo

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import javax.swing.*
import javax.swing.event.TreeSelectionEvent
import javax.swing.tree.DefaultMutableTreeNode


class ShowHelpAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        // Tạo cửa sổ hiển thị trợ giúp
        val frame = JFrame("GitHub Copilot Help")
        frame.setSize(600, 600)
        frame.defaultCloseOperation = JFrame.DISPOSE_ON_CLOSE

        // Tạo root node
        val root = DefaultMutableTreeNode("GitHub Copilot Help")

        // Tạo các nhánh chính
        val suggestionNode = DefaultMutableTreeNode("Cách nhận đề xuất mã")
        val caseStudyNode = DefaultMutableTreeNode("Các case study sử dụng Github Copilot")

        // Tạo các nhánh con chi tiết cho suggestionNode
        val chatNode = DefaultMutableTreeNode("1. Hỏi trực tiếp tại Chat")
        chatNode.add(HelpNode("Mở Copilot Chat"))
        chatNode.add(HelpNode("Sử dụng command"))
        chatNode.add(HelpNode("Lợi ích của Copilot Chat"))

        val commentNode = DefaultMutableTreeNode("2. Comment yêu cầu và đợi Copilot gợi ý")
        commentNode.add(HelpNode("Cách gõ comment"))
        commentNode.add(HelpNode("Ưu nhược điểm"))

        val inlineChatNode = DefaultMutableTreeNode("3. Sử dụng Inline chat")
        inlineChatNode.add(HelpNode("Mở Inline chat"))
        inlineChatNode.add(HelpNode("Khi nào sử dụng"))

        suggestionNode.add(chatNode)
        suggestionNode.add(commentNode)
        suggestionNode.add(inlineChatNode)

        // Tạo các nhánh con chi tiết cho caseStudyNode
        val frontendNode = DefaultMutableTreeNode("1. Sử dụng Copilot để vẽ giao diện Front-end")
        frontendNode.add(HelpNode("Prompt vẽ giao diện"))
        frontendNode.add(HelpNode("Bổ sung CSS/SCSS"))

        val classNode = DefaultMutableTreeNode("2. Thêm Class, Interface, SQL Script từ mô tả")
        classNode.add(HelpNode("Tạo Class/Interface tại Front-end"))
        classNode.add(HelpNode("Tạo SQL Script từ mô tả"))

        caseStudyNode.add(frontendNode)
        caseStudyNode.add(classNode)

        // Thêm các nhánh vào root
        root.add(suggestionNode)
        root.add(caseStudyNode)

        // Tạo cây từ root node
        val tree = JTree(root)
        tree.isRootVisible = true
        val treeView = JScrollPane(tree)

        // Panel hiển thị chi tiết nội dung
        val detailArea = JTextArea()
        detailArea.isEditable = false
        detailArea.lineWrap = true
        detailArea.wrapStyleWord = true
        val detailView = JScrollPane(detailArea)

        // Lắng nghe sự kiện chọn node trong cây
        tree.addTreeSelectionListener { event: TreeSelectionEvent? ->
            val selectedNode =
                tree.lastSelectedPathComponent as DefaultMutableTreeNode
            if (selectedNode.isLeaf) {
                detailArea.text = getHelpContent(selectedNode.toString())
            }
        }

        // Bố trí giao diện
        val splitPane = JSplitPane(JSplitPane.HORIZONTAL_SPLIT, treeView, detailView)
        splitPane.dividerLocation = 200
        frame.add(splitPane)

        // Hiển thị cửa sổ
        frame.isVisible = true
    }

    // Nội dung chi tiết cho từng mục
    private fun getHelpContent(nodeTitle: String): String {
        return when (nodeTitle) {
            "Mở Copilot Chat" -> ("""
     ### Cách mở Copilot Chat
     
     1. **Visual Studio Code**: Chọn biểu tượng Extension trên thanh công cụ, sau đó chọn `GitHub Copilot Chat`.
     2. **Visual Studio 2022**: Chọn `View` > `GitHub Copilot Chat` từ menu.
     
     Copilot Chat là công cụ hữu ích để tìm hiểu về các vấn đề lập trình, thay thế việc tìm kiếm trên Google.
     """.trimIndent())

            "Sử dụng command" -> ("""
     ### Sử dụng Command trong Copilot Chat
     
     Bạn có thể sử dụng các command trong Copilot Chat để tương tác hiệu quả hơn:
     - **/help**: Hiển thị danh sách các lệnh có sẵn.
     - **#terminalLastCommand**: Tự động lấy câu lệnh cuối cùng tại Terminal và giải thích lỗi nếu có.
     
     Ví dụ: `#terminalLastCommand giải thích giúp tôi lỗi đang thông báo tại Terminal`.
     """.trimIndent())

            "Lợi ích của Copilot Chat" -> ("""
     ### Lợi ích của Copilot Chat
     
     Copilot Chat ghi nhận lịch sử đoạn hội thoại, giúp bạn dễ dàng tiếp tục cuộc trò chuyện mà không cần lặp lại context.
     Điều này giúp tiết kiệm thời gian và tăng hiệu quả làm việc.
     
     Nếu Copilot trả lời chưa chính xác, bạn có thể điều chỉnh câu hỏi để cải thiện kết quả.
     """.trimIndent())

            "Cách gõ comment" -> ("""
     ### Gõ Comment để Nhận Gợi Ý
     
     Bạn có thể yêu cầu Copilot thực hiện các nhiệm vụ cụ thể bằng cách gõ comment trực tiếp vào editor:
     ```java
     // Hãy tạo hàm kiểm tra số nguyên tố
     ```
     Copilot sẽ gợi ý mã nguồn dựa trên comment này.
     
     Cách này nhanh chóng và thích hợp cho các tác vụ nhỏ.
     """.trimIndent())

            "Ưu nhược điểm" -> ("""
     ### Ưu Nhược Điểm của Comment to Code
     
     **Ưu điểm**:
     - Nhanh chóng và dễ sử dụng.
     - Copilot hiểu rõ context và gợi ý chính xác hơn.
     
     **Nhược điểm**:
     - Không lưu lại lịch sử comment.
     - Không phù hợp cho các tác vụ lớn cần nhiều dòng code.
     """.trimIndent())

            "Mở Inline chat" -> ("""
     ### Cách Mở Inline Chat
     
     Inline Chat cho phép bạn tương tác nhanh với Copilot trong Visual Studio Code và Visual Studio 2022:
     - **VS Code**: Sử dụng phím tắt `Ctrl + I`.
     - **Visual Studio 2022**: Sử dụng phím tắt `Alt + /` hoặc chuột phải > `Ask Copilot`.
     """.trimIndent())

            "Khi nào sử dụng" -> ("""
     ### Khi Nào Nên Sử Dụng Inline Chat
     
     Inline Chat phù hợp cho các thao tác đơn giản và dùng một lần, ví dụ:
     - Sinh comment cho hàm.
     - Giải thích một đoạn mã.
     
     Nếu cần thực hiện các tác vụ phức tạp hơn, hãy sử dụng Copilot Chat để có lịch sử hội thoại.
     """.trimIndent())

            "Prompt vẽ giao diện" -> ("""
     ### Prompt Để Vẽ Giao Diện Front-end
     
     Sử dụng các prompt chi tiết để Copilot sinh HTML, JS, CSS cho giao diện Front-end:
     - **Bước 1**: Đặt con trỏ vào vị trí cần generate HTML.
     - **Bước 2**: Sử dụng Inline chat với prompt: `; <Mô tả layout>`.
     
     Lưu ý: Nên chia prompt thành nhiều đoạn ngắn để đạt hiệu quả cao hơn.
     """.trimIndent())

            "Bổ sung CSS/SCSS" -> ("""
     ### Bổ Sung CSS/SCSS
     
     Sau khi sinh HTML, bạn có thể bổ sung CSS hoặc SCSS bằng Copilot Chat:
     - **Bước 1**: Sử dụng prompt: `Thêm code <Ngôn ngữ> <Mô tả thuộc tính các class>`.
     - **Bước 2**: Điều chỉnh theo ý muốn dựa trên gợi ý của Copilot.
     """.trimIndent())

            "Tạo Class/Interface tại Front-end" -> ("""
     ### Tạo Class/Interface tại Front-end
     
     Sử dụng Copilot Chat để tạo Class hoặc Interface từ mô tả của bạn:
     - **Prompt**: `Hãy tạo Class/Interface sử dụng <Ngôn ngữ> từ mô tả dưới đây...`.
     - **Ví dụ**: Tạo TypeScript Interface từ một object JSON với comment tiếng Việt cho từng thuộc tính.
     """.trimIndent())

            "Tạo SQL Script từ mô tả" -> ("""
     ### Tạo SQL Script từ Mô Tả
     
     Copilot có thể giúp bạn tạo SQL Script dựa trên mô tả chi tiết:
     - **Prompt**: `Hãy tạo MySQL Script để tạo bảng từ đoạn mô tả dưới đây...`.
     - **Lưu ý**: Bạn có thể yêu cầu thêm comment cho các cột trong bảng bằng tiếng Việt.
     """.trimIndent())

            else -> "No detailed help available for this section."
        }
    }
}

internal class HelpNode(title: String?) : DefaultMutableTreeNode(title)