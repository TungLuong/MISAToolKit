package vn.com.misa.misatoolkit.actions.screenmapxml

import com.intellij.openapi.ui.ComboBox
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.util.ui.JBUI
import java.awt.Dimension
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import javax.swing.DefaultComboBoxModel
import javax.swing.JComponent
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JTextArea

class ScreenMappingXMLContextDialog(
    private val prompt: ScreenMappingXMLPrompt
) : DialogWrapper(true) {

    private val userRequirementField = JTextArea().apply {
        preferredSize = Dimension(500, 100) // Set preferred size
        margin = JBUI.insets(8)
    }

    private val exampleField = JTextArea().apply {
        preferredSize = Dimension(500, 100) // Set preferred size
        margin = JBUI.insets(8)
    }

    private val screentTypeComboBoxModel = DefaultComboBoxModel<ScreenType>().apply {
        addAll(ScreenType.values().toList())
        setSelectedItem(prompt.type)
    }

    init {
        init()
        title = "Provide Context Information"
    }

    override fun createCenterPanel(): JComponent {
        return JPanel(GridBagLayout()).apply {
            var gridyI = 0

            // Add drop down list component
            add(JLabel("Select Screen Type"), GridBagConstraints().apply {
                fill = GridBagConstraints.BOTH
                weightx = 1.0
                gridy = gridyI++
                insets = JBUI.insets(10, 5, 5, 5)
            })
            add(ComboBox(screentTypeComboBoxModel), GridBagConstraints().apply {
                fill = GridBagConstraints.BOTH
                weightx = 1.0
                gridy = gridyI++
                insets = JBUI.insetsBottom(10)
            })

            add(JLabel("Requirement (Optional)"), GridBagConstraints().apply {
                fill = GridBagConstraints.BOTH
                weightx = 1.0
                gridy = gridyI++
                insets = JBUI.insets(10, 5, 5, 5)
            })
            add(userRequirementField, GridBagConstraints().apply {
                fill = GridBagConstraints.BOTH
                weightx = 1.0
                weighty = 0.4
                gridy = gridyI++
                insets = JBUI.insets(0, 5, 20, 5)
            })

            add(
                JLabel("Example (Optional)"),
                GridBagConstraints().apply {
                    fill = GridBagConstraints.BOTH
                    weightx = 1.0
                    gridy = gridyI++
                    insets = JBUI.insets(10, 5, 5, 5)
                })
            add(exampleField, GridBagConstraints().apply {
                fill = GridBagConstraints.BOTH
                weightx = 1.0
                weighty = 0.4
                gridy = gridyI++
                insets = JBUI.insets(0, 5, 10, 5)
            })
            add(
                JLabel("Please reference all example class file in Github Copilot Chat for the best result!"),
                GridBagConstraints().apply {
                    fill = GridBagConstraints.BOTH
                    weightx = 1.0
                    gridy = gridyI++
                    insets = JBUI.insets(10, 5, 15, 5)
                })
        }
    }

    fun buildPrompt(): String {
        prompt.userRequirement = userRequirementField.text
        prompt.example = exampleField.text
        prompt.type = (screentTypeComboBoxModel.selectedItem as? ScreenType) ?: ScreenType.ACTIVITY

        return prompt.buildPrompt()
    }
}