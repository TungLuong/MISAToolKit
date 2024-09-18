package vn.com.misa.misatoolkit.window

import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.components.JBPanel
import org.json.JSONObject
import java.awt.Component
import java.awt.Dimension
import javax.swing.*
import javax.swing.event.TreeSelectionEvent
import javax.swing.tree.DefaultMutableTreeNode
import javax.swing.tree.DefaultTreeCellRenderer

class MISAToolHelpWindowFactory : ToolWindowFactory, DumbAware {

    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val panel = JBPanel<JBPanel<*>>()
        panel.layout = BoxLayout(panel, BoxLayout.Y_AXIS)

        // Read and parse JSON file
        val jsonContent = readJsonFile("asset/help_content.json")
        val helpContent = parseJsonContent(jsonContent)

        // Build tree from JSON
        val root = buildTreeFromJson(helpContent)

        // Create JTree
        val tree = JTree(root)
        tree.cellRenderer = HelpContentTreeCellRenderer()
        tree.isRootVisible = true
        val treeView = JScrollPane(tree)

        // Panel to display details
        val detailArea = JTextArea()
        detailArea.isEditable = false
        detailArea.lineWrap = true
        detailArea.wrapStyleWord = true
        val detailView = JScrollPane(detailArea)
        detailView.preferredSize = Dimension(400, 300)
        detailView.border = BorderFactory.createTitledBorder("Detail View")

        // Tree selection listener
        tree.addTreeSelectionListener { event: TreeSelectionEvent? ->
            val selectedNode = tree.lastSelectedPathComponent as DefaultMutableTreeNode
            if (selectedNode.isLeaf) {
                detailArea.text = (selectedNode.userObject as? HelpContent)?.content ?: ""
            }
        }

        // Layout
        val splitPane = JSplitPane(JSplitPane.VERTICAL_SPLIT, treeView, detailView)
        splitPane.dividerLocation = toolWindow.component.height / 2
        panel.add(splitPane)

        toolWindow.component.add(panel)
    }

    private fun parseJsonContent(jsonContent: String): HelpContent {
        val jsonObject = JSONObject(jsonContent)
        return parseJsonObject(jsonObject)
    }

    private fun parseJsonObject(jsonObject: JSONObject): HelpContent {
        val id = jsonObject.getString("id")
        val title = jsonObject.getString("title")
        val level = jsonObject.getInt("level")
        val parentId = jsonObject.optString("parentId", null)
        val content = jsonObject.optString("content", null)
        val children = mutableListOf<HelpContent>()
        if (jsonObject.has("children")) {
            val childrenArray = jsonObject.getJSONArray("children")
            for (i in 0 until childrenArray.length()) {
                children.add(parseJsonObject(childrenArray.getJSONObject(i)))
            }
        }
        return HelpContent(id, title, level, parentId, content, children)
    }

    private fun createTreeNode(helpContent: HelpContent): DefaultMutableTreeNode {
        val node = DefaultMutableTreeNode(helpContent.title)
        node.userObject = helpContent
        for (child in helpContent.children) {
            node.add(createTreeNode(child))
        }
        return node
    }

    private fun buildTreeFromJson(helpContent: HelpContent): DefaultMutableTreeNode {
        return createTreeNode(helpContent)
    }

    private fun readJsonFile(filePath: String): String {
        return try {
            val resource = this::class.java.classLoader.getResource(filePath)
            resource?.readText() ?: throw java.nio.file.NoSuchFileException(filePath)
        } catch (e: NoSuchFileException) {
            println("File not found: $filePath")
            "{}" // Return an empty JSON object as a fallback
        }
    }

}

data class HelpContent(
    val id: String,
    val title: String,
    val level: Int,
    val parentId: String?,
    val content: String?,
    val children: List<HelpContent> = emptyList()
)

class HelpContentTreeCellRenderer : DefaultTreeCellRenderer() {
    override fun getTreeCellRendererComponent(
        tree: JTree?,
        value: Any?,
        selected: Boolean,
        expanded: Boolean,
        leaf: Boolean,
        row: Int,
        hasFocus: Boolean
    ): Component {
        val component = super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus)
        if (value is DefaultMutableTreeNode) {
            val userObject = value.userObject
            if (userObject is HelpContent) {
                text = userObject.title
            }
        }
        return component
    }
}