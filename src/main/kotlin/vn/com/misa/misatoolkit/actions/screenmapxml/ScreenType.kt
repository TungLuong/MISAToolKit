package vn.com.misa.misatoolkit.actions.screenmapxml

enum class ScreenType(val stringValue: String) {
    ACTIVITY("Activity"),
    FRAGMENT("Fragment"),
    DIALOG("Dialog"),
    POPUP("Popup"),
    DIALOG_FRAGMENT("DialogFragment");

    override fun toString(): String {
        return stringValue
    }

    companion object {
        fun suggest(fileName: String): ScreenType {
            for (type in values()) {
                if (fileName.contains(type.stringValue, ignoreCase = true)) {
                    return type
                }
            }

            return ACTIVITY
        }
    }
}