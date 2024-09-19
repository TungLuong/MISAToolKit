package vn.com.misa.misatoolkit.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import vn.com.misa.misatoolkit.copilot.referenceFileInChat
import vn.com.misa.misatoolkit.copilot.replaceTextInChat

class MSConvertCodeToDartAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        e.referenceFileInChat()
        e.replaceTextInChat(
            """
            Convert this code to Dart, ensuring the following criteria:
            1. Idiomatic Dart: Ensure the converted code uses idiomatic Dart features and best practices.
            2. Null Safety: Utilize Dart's null safety features to avoid null pointer exceptions.
            3. Flutter Integration: If the project involves Flutter, ensure the code is compatible with Flutter widgets and architecture.
            4. Asynchronous Programming: Use Dart's async and await for asynchronous operations.
            5. Type Safety: Leverage Dart's strong type system to ensure type safety.
            6. Packages and Dependencies: Ensure all necessary packages and dependencies are correctly imported and used.
            7. Error Handling: Properly handle exceptions and errors using Dart's error handling mechanisms.
            8. Performance: Ensure the performance of the Dart code is comparable to the original code.
            9. Testing: Write or update tests to ensure the Dart code works as expected.
            10. Documentation: Include comments and documentation to explain the Dart code, especially if it differs significantly from the original version.
            """.trimIndent()
        )
    }
}