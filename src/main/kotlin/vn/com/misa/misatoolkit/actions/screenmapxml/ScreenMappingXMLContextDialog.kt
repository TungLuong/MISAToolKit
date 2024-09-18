package vn.com.misa.misatoolkit.actions.screenmapxml

import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.dsl.builder.panel
import java.awt.Dimension
import javax.swing.JComponent
import javax.swing.JTextField

class ScreenMappingXMLContextDialog(
    private val prompt: ScreenMappingXMLPrompt
) : DialogWrapper(true) {
    private val contextField = JTextField().apply {
        preferredSize = Dimension(500, 100) // Set preferred size
    }

    init {
        init()
        title = "Enter Context Information"
    }

    override fun createCenterPanel(): JComponent {
        return panel {
            row("Feature:") { cell(contextField) }
        }
    }

    fun buildPrompt(): String = prompt.buildPrompt()
}