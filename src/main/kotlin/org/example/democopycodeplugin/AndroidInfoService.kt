package org.example.democopycodeplugin

import com.android.tools.idea.gradle.model.IdeAndroidProject
import com.android.tools.idea.gradle.project.model.GradleAndroidModel
import com.intellij.openapi.components.Service
import com.intellij.openapi.module.Module
import org.jetbrains.android.facet.AndroidFacet

@Service
class AndroidInfoService {
    fun getAndroidExtension(module: Module): IdeAndroidProject? {
        val facet = AndroidFacet.getInstance(module)
        return GradleAndroidModel.get(facet!!)?.androidProject
    }

//    fun getApplicationId(module: Module): String? {
//        val androidProject = getAndroidExtension(module)
//        return androidProject?.defaultConfig?.productFlavor?.applicationId
//    }

    fun getApplicationId(module: Module): String? {
        val facet = AndroidFacet.getInstance(module)
        val gradleAndroidModel = GradleAndroidModel.get(facet!!)
        return gradleAndroidModel?.applicationId
    }

    fun getNamespace(module: Module): String? {
        val androidProject = getAndroidExtension(module)
        return androidProject?.namespace
    }


    private fun isAndroidLibraryModule(module: Module): Boolean {
        val facet = AndroidFacet.getInstance(module)
        return facet != null && facet.configuration.isLibraryProject
    }

    private fun isAppModule(module: Module): Boolean {
        val facet = AndroidFacet.getInstance(module)
        return facet != null && facet.configuration.isAppProject
    }

    fun getModuleInfo(module: Module): String? {
        if (isAppModule(module)) {
            return getApplicationId(module)
        } else if (isAndroidLibraryModule(module)) {
            return getNamespace(module)
        } else {
            return "Unsupported module, please check if it is a android module"
        }
    }
}
