package vn.com.misa.misatoolkit.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import vn.com.misa.misatoolkit.copilot.referenceFileInChat
import vn.com.misa.misatoolkit.copilot.replaceTextInChat

class MSConvertCodeToSwiftAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        e.referenceFileInChat()
        e.replaceTextInChat(
            """
            Convert this code to Swift, ensuring the following criteria:
            1. Idiomatic Swift: Ensure the converted code uses idiomatic Swift features and best practices.
            2. Optionals: Utilize Swift's optionals to handle nullability and avoid runtime crashes.
            3. SwiftUI Integration: If the project involves SwiftUI, ensure the code is compatible with SwiftUI views and architecture.
            4. Asynchronous Programming: Use Swift's async and await for asynchronous operations.
            5. Type Safety: Leverage Swift's strong type system to ensure type safety.
            6. Error Handling: Properly handle exceptions and errors using Swift's error handling mechanisms.
            7. Performance: Ensure the performance of the Swift code is comparable to the original code.
            8. Memory Management: Utilize Swift's automatic reference counting (ARC) for memory management.
            9. Testing: Write or update tests to ensure the Swift code works as expected.
            10. Documentation: Include comments and documentation to explain the Swift code, especially if it differs significantly from the original version.
            """.trimIndent()
        )
    }
}