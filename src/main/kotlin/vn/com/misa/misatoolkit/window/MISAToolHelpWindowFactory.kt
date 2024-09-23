package vn.com.misa.misatoolkit.window

import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.components.JBPanel
import org.json.JSONObject
import java.awt.*
import javax.swing.*
import javax.swing.event.TreeSelectionEvent
import javax.swing.tree.DefaultMutableTreeNode
import javax.swing.tree.DefaultTreeCellRenderer

class MISAToolHelpWindowFactory : ToolWindowFactory, DumbAware {

    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val panel = JBPanel<JBPanel<*>>()
        panel.layout = BorderLayout()
        panel.preferredSize = Dimension(1600, panel.preferredSize.height)

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
        treeView.border = BorderFactory.createTitledBorder("Help Topics")

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
                val helpContent = selectedNode.userObject as? HelpContent
                when (helpContent?.id) {
                    "details" -> {
                        detailArea.text = helpContent.content
                        detailView.setViewportView(detailArea)
                    }
                    "video_example" -> {
                        val videoIcon = loadAndScaleImageIcon(helpContent.path ?: "", 400, 300)
                        detailArea.text = ""
                        detailView.setViewportView(JLabel(videoIcon))
                    }
                    "gif_example" -> {
                        val gifIcon = loadAndScaleImageIcon(helpContent.path ?: "", 400, 300)
                        if (gifIcon != null) {
                            detailArea.text = ""
                            detailView.setViewportView(JLabel(gifIcon))
                        } else {
                            detailArea.text = "Image not found: ${helpContent.path}"
                            detailView.setViewportView(detailArea)
                        }
                    }
                    else -> {
                        detailArea.text = helpContent?.content ?: ""
                        detailView.setViewportView(detailArea)
                    }
                }
            }
        }

        // Automatically select the first node and load its content
        tree.setSelectionRow(0)

        // Layout
        val splitPane = JSplitPane(JSplitPane.VERTICAL_SPLIT, treeView, detailView)
        splitPane.dividerLocation = toolWindow.component.height / 2
        panel.add(splitPane, BorderLayout.CENTER)

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
        val path = jsonObject.optString("path", null)
        val children = mutableListOf<HelpContent>()
        if (jsonObject.has("children")) {
            val childrenArray = jsonObject.getJSONArray("children")
            for (i in 0 until childrenArray.length()) {
                children.add(parseJsonObject(childrenArray.getJSONObject(i)))
            }
        }
        return HelpContent(id, title, level, parentId, content, path, children)
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

    private fun loadAndScaleImageIcon(path: String, width: Int, height: Int): ImageIcon? {
        val resource = this::class.java.classLoader.getResource(path)
        return resource?.let {
            ImageIcon(it)
        } ?: run {
            println("Image not found: $path")
            null
        }
    }
}