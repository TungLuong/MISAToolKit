package vn.com.misa.misatoolkit.common

import java.awt.Component
import java.awt.Container
import javax.swing.JComponent
import kotlin.reflect.KClass
import kotlin.reflect.safeCast

/**
 * Extension function for `JPanel` to filter and retrieve all components of a specified type.
 *
 * @param T The type of components to filter.
 * @param clazz The `KClass` of the type `T`.
 * @return A list of components of type `T` found within the `JPanel`.
 */
fun <T : Any> JComponent.getComponentsByType(clazz: KClass<T>): List<T> {
    val result = mutableListOf<T>()
    components.forEach { component ->
        if (clazz.isInstance(component)) {
            clazz.safeCast(component)?.let { result.add(it) }
        } else if (component is JComponent) {
            result.addAll(component.getComponentsByType(clazz))
        }
    }
    return result
}

/**
 * Class representing a tree structure of components.
 *
 * @property root The root component of the tree.
 * @property nodes The list of child component trees.
 */
class ComponentTree(val root: Component, val nodes: List<ComponentTree>) {
    override fun toString(): String {
        val rootName = "${root.javaClass.simpleName} (${root.javaClass.superclass.simpleName}) (${root.javaClass.packageName})"
        return if (nodes.isEmpty()) {
            "\"$rootName\""
        } else {
            """{"$rootName": [${nodes.joinToString(",") { it.toString() }}]}""".trimIndent()
        }
    }
}

/**
 * Extension property to get the component tree of a `Component`.
 *
 * @return The `ComponentTree` of the component.
 */
val Component.tree: ComponentTree
    get() {
        return ComponentTree(
            root = this,
            nodes = if (this is Container) {
                components.map { it.tree }
            } else {
                emptyList()
            }
        )
    }

