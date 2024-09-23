package vn.com.misa.misatoolkit.window


data class HelpContent(
    val id: String,
    val title: String,
    val level: Int,
    val parentId: String?,
    val content: String?,
    val path: String?,
    val children: List<HelpContent> = emptyList()
)