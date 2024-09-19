package vn.com.misa.misatoolkit.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import vn.com.misa.misatoolkit.copilot.referenceFileInChat
import vn.com.misa.misatoolkit.copilot.replaceTextInChat

class MSConvertCodeToJavaAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        e.referenceFileInChat()
        e.replaceTextInChat(
            """
            Convert this code to Java, ensuring the following criteria:
            1. Maintaining Functionality: Ensure that the converted Java code maintains the same functionality as the original code.
            2. Code Style: Follow Java coding conventions and style guides.
            3. Dependencies: Ensure that all dependencies and imports are correctly translated and available in Java.
            4. Error Handling: Properly handle any exceptions or errors that might arise in the Java version.
            5. Performance: Ensure that the performance of the Java code is comparable to the existing code.
            6. Compatibility: Ensure that the Java code is compatible with the existing project setup, including Gradle configurations.
            7. Testing: Write or update tests to ensure the Java code works as expected.
            8. Documentation: Include comments and documentation to explain the Java code, especially if it differs significantly from the original version.
            """.trimIndent()
        )
    }
}