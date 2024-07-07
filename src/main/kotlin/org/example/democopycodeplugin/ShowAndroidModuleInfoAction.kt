package org.example.democopycodeplugin

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.module.ModuleUtilCore
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages

class ShowAndroidModuleInfoAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val project: Project? = e.project
        val module = ModuleUtilCore.findModuleForFile(e.getData(CommonDataKeys.VIRTUAL_FILE)!!, project!!)

        if (module != null) {
            val service = project.getService(AndroidInfoService::class.java)
            val info = service.getModuleInfo(module)

            Messages.showMessageDialog(project, info, "Android Module Info", Messages.getInformationIcon())
        }
    }
}
