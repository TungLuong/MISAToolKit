package org.example.democopycodeplugin

import com.android.tools.idea.gradle.project.model.GradleAndroidModel
import com.intellij.openapi.module.ModuleManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.StartupActivity

class MyStartupActivity : StartupActivity {
    override fun runActivity(project: Project) {
        val moduleManager = ModuleManager.getInstance(project)
        for (module in moduleManager.modules) {
            val androidModuleModel = GradleAndroidModel.get(module)
            if (androidModuleModel != null) {
                // Do something with AndroidModuleModel
//                 val androidModel = GradleAndroidModel.get(module
                println("Module: ${module.name}")
                println("AndroidModuleModel: ${androidModuleModel.applicationId}")
                println("namespacing: ${androidModuleModel.namespacing.name}")
            }
        }
    }
}
