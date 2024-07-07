package org.example.democopycodeplugin

import com.intellij.openapi.fileChooser.FileChooserDescriptor
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.openapi.ui.TextFieldWithBrowseButton
import com.intellij.ui.TitledSeparator
import com.intellij.ui.dsl.builder.Align
import com.intellij.ui.dsl.builder.AlignX
import com.intellij.ui.dsl.builder.RightGap
import com.intellij.ui.dsl.builder.panel
import com.intellij.util.ui.ThreeStateCheckBox
import com.intellij.util.ui.ThreeStateCheckBox.State
import java.awt.Dimension
import java.awt.GridLayout
import javax.swing.*


class MyDialogWrapper(
    private val project: Project,
    packageName: String,
    targetPath:String? = ""
) : DialogWrapper(project) {

    private val packageNameField = JTextField(packageName)
    private val targetPathField = TextFieldWithBrowseButton().apply {
        textField.name = "Target path"
        textField.text = targetPath
        addBrowseFolderListener(
            "Select Target Path",
            "Choose the target path where you want to copy the component.",
            project,
            FileChooserDescriptor(false, true, false, false, false, false)
        )
    }
    private val components = listOf(
        "Button", "InlineMessage", "InputField", "CheckBox",
        "TopNavBar", "RadioButton","SnackBar","Banner",
        "Dialog","TabBar","ProgressTracker","Spinner","BaseTile").sortedDescending()
    private val componentCheckBoxes = components.map { JCheckBox(it).apply { } }
    private val checkAllCheckBox = ThreeStateCheckBox("Check All", ThreeStateCheckBox.State.NOT_SELECTED)

    init {
        init()
        title = "Copy Components"
        setupCheckAllCheckBox()
        updateOkButtonState()
    }

    override fun createCenterPanel(): JComponent? {
        return panel {
            row {
                cell(TitledSeparator("Configs"))
                    .align(Align.FILL)
            }
            indent {
                row("Package Name:") {
                    cell(packageNameField)
                        .align(Align.FILL)
                }
                row("Target Path:") {
                    cell(targetPathField)
                        .align(Align.FILL)
                }
            }

            row {
                cell(TitledSeparator("Components: ")).align(AlignX.FILL).gap(RightGap.SMALL)
                cell(checkAllCheckBox).align(AlignX.LEFT)
            }

            indent {
                val componentsPanel = JPanel(GridLayout(0, 3, 10, 10)) // 3 columns, 10px horizontal and vertical gaps
                componentCheckBoxes.forEach { checkBox ->
                    componentsPanel.add(checkBox)
                }
                row {
                    cell(componentsPanel).align(Align.FILL)
                }
            }

        }.apply {
            minimumSize = Dimension(600, 340)
            preferredSize = Dimension(600, 340)
        }
    }

    override fun doOKAction() {
        super.doOKAction()
        if (validateInputs()) {
            val packageName = packageNameField.text
            val targetPath = targetPathField.text
            println("Package Name: $packageName")
            println("Target Path: $targetPath")


            // Get the selected components
            val selectedComponents = componentCheckBoxes.filter { it.isSelected }.map { it.text }
            println("Selected Components: ${selectedComponents.joinToString(", ")}")
            val isAll = checkAllCheckBox.isSelected
            println("is allChecked: $isAll")

            // Here you can run the shell command with the provided package name and target path
            // For example:
            ScriptUtils.runScript(project,targetPath,packageName, isAll,selectedComponents)
        }
    }

    private fun updateOkButtonState() {
        val anySelected = componentCheckBoxes.any { it.isSelected }
        okAction.isEnabled = anySelected
    }


    private fun setupCheckAllCheckBox() {
        componentCheckBoxes.forEach { checkBox ->
            checkBox.addActionListener {
                updateTriCheckAllState()
                updateOkButtonState()
            }
        }

        checkAllCheckBox.addActionListener {
            val isSelected = checkAllCheckBox.isSelected
            componentCheckBoxes.forEach { it.isSelected = isSelected }
            updateOkButtonState()
        }

    }

    private fun updateTriCheckAllState() {
        val allSelected = componentCheckBoxes.all { it.isSelected }
        val allDeselected = componentCheckBoxes.none { it.isSelected }

        checkAllCheckBox.state = when {
            allSelected -> State.SELECTED
            allDeselected -> State.NOT_SELECTED
            else -> State.DONT_CARE
        }
    }

    private fun validateInputs(): Boolean {
        if (packageNameField.text.isBlank()) {
            showError("Package name cannot be empty")
            return false
        }
        if (targetPathField.text.isBlank()) {
            showError("Target path cannot be empty")
            return false
        }
        return true
    }

    private fun showError(message: String) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE)
    }

}