package vn.com.misa.misatoolkit.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import vn.com.misa.misatoolkit.copilot.referenceFileInChat
import vn.com.misa.misatoolkit.copilot.replaceTextInChat

class MSConvertCodeToKotlinAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        e.referenceFileInChat()
        e.replaceTextInChat(
            """
            Convert this code to Kotlin, ensuring the following criteria:
            1. Idiomatic Kotlin: Ensure the converted code uses idiomatic Kotlin features and best practices.
            2. Null Safety: Utilize Kotlin's null safety features to avoid null pointer exceptions.
            3. Extension Functions: Where applicable, use extension functions to simplify code.
            4. Data Classes: Convert Java POJOs to Kotlin data classes for better readability and functionality.
            5. Coroutines: If the original code in Java and uses threading, consider converting it to use Kotlin coroutines for better concurrency management.
            6. Interoperability: Ensure the Kotlin code is interoperable with existing Java code if both languages are used in the project.
            7. Type Inference: Use Kotlin's type inference to reduce boilerplate code.
            8. Functional Programming: Leverage Kotlin's support for functional programming where appropriate.
            8. Testing: Update or write new tests to ensure the Kotlin code works as expected.
            10. Documentation: Include comments and documentation to explain the Kotlin code, especially if it differs significantly from the original version.
            """.trimIndent()
        )
    }
}