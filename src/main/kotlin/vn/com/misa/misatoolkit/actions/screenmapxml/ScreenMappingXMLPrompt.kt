package vn.com.misa.misatoolkit.actions.screenmapxml

data class ScreenMappingXMLPrompt(
    private val fileName: String,
    var type: ScreenType,
    var userRequirement: String? = null,
    var example: String? = null
) {
    private fun buildContext(): String {
        return """
            I am working on an Android application and need a/an ${type.stringValue} a Kotlin/Java class that inflates a XML layout excerpt from file "$fileName" and sets up the necessary UI components.
        """.trimIndent()
    }

    private fun buildUserRequirement(): String {
        return if (userRequirement.isNullOrBlank()) {
            ""
        } else {
            "\n" + """
                The class must prioritize satisfying this requirements: $userRequirement.
            """.trimIndent()
        }
    }

    private fun buildSystemRequirement(): String {
        return "\n" + """
            The class should include:
               Initialization of UI components (e.g., Button, TextView, etc.) with private variables
               Setting up any required listeners (e.g., onClickListener)
               Any additional setup required for the specific type of class ${type.stringValue}
               Any additional setup code (e.g., setting up a RecyclerView, ViewPager, etc.)
               Any other necessary code to make the UI functional and interactive
               Any necessary lifecycle methods (e.g., onCreate, onDestroy, etc.)
               Error handling and input validation where necessary
            The class should be:
                Well-structured and easy to read/understand
                Have comments to explain the purpose of each section of code
                Easy to modify and extend (e.g., adding new UI components, listeners, etc.).
        """.trimIndent()
    }

    private fun buildExample(): String {
        return if (example.isNullOrBlank()) {
            ""
        } else {
            "\n" + """
                Consider the following examples: $example.
            """.trimIndent()
        }
    }

    fun buildPrompt(): String {
        return buildContext() + buildUserRequirement() + buildSystemRequirement() + buildExample()
    }

    enum class ScreenType(val stringValue: String) {
        ACTIVITY("Activity"),
        FRAGMENT("Fragment"),
        DIALOG("Dialog"),
        POPUP("Popup"),
        DIALOG_("PopupFragment"),
    }
}