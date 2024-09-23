package vn.com.misa.misatoolkit.actions

import com.intellij.openapi.actionSystem.*
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.openapi.vfs.VirtualFile
import vn.com.misa.misatoolkit.copilot.referenceFileInChat
import vn.com.misa.misatoolkit.copilot.replaceTextInChat

class MSGenerateScreenMappingXMLAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        e.referenceFileInChat()
        e.replaceTextInChat("Long test \n long test")

        getAnActionEventFromFilePath(
            "/Users/tungluongngoc/StudioProjects/Cukcuk-lite/app/src/main/java/com/example/cukcuklitedemo/reactproduct/ReactProductActivity.java",
            e.project!!
        )?.referenceFileInChat()

        e.dataContext
    }

    fun getAnActionEventFromFilePath(filePath: String, project: Project): AnActionEvent? {
        val virtualFile: VirtualFile? = LocalFileSystem.getInstance().findFileByPath(filePath)
        if (virtualFile != null) {
            val dataContext: DataContext = DataContext { dataId ->
                when (dataId) {
                    CommonDataKeys.VIRTUAL_FILE.name -> virtualFile
                    CommonDataKeys.PROJECT.name -> project
                    else -> null
                }
            }
            val presentation = Presentation()
            return AnActionEvent.createFromDataContext("place", presentation, dataContext)
        }
        return null
    }

}