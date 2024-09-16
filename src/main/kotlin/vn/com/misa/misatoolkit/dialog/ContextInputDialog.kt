package vn.com.misa.misatoolkit.dialog

import com.intellij.openapi.ui.DialogWrapper
import javax.swing.JComponent
import javax.swing.JTextField
import com.intellij.ui.dsl.builder.panel
import java.awt.Dimension

class ContextInputDialog : DialogWrapper(true) {
    private val contextField = JTextField().apply {
        preferredSize = Dimension(500, 100) // Set preferred size
    }

    private val context2Field = JTextField().apply {
        preferredSize = Dimension(400, 100) // Set preferred size
    }

    init {
        init()
        title = "Enter Context Information"
    }

    override fun createCenterPanel(): JComponent {
        return panel {
            row("Feature:") { cell(contextField) }
            row("Requirement:") { cell(context2Field) }
        }
    }

    fun getContext(): String {
        return contextField.text
    }
}