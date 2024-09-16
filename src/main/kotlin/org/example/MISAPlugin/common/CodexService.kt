package org.example.MISAPlugin.common

import com.intellij.openapi.components.Service
import com.intellij.openapi.project.Project
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

@Service(Service.Level.PROJECT)
class CodexService(private val project: Project) {

    val OPENAI_API_KEY = "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"

    fun getSuggestions(query: String): List<String> {
        val client = OkHttpClient()
        val url = "https://api.openai.com/v1/chat/completions"

        val json = JSONObject()
        json.put("model", "gpt-3.5-turbo")
        json.put("messages", listOf(
            mapOf("role" to "system", "content" to "You are a helpful assistant."),
            mapOf("role" to "user", "content" to query)
        ))

        val requestBody = json.toString().toRequestBody("application/json".toMediaTypeOrNull())
        val request = Request.Builder()
            .url(url)
            .header("Content-Type", "application/json")
            .header("Authorization", "Bearer $OPENAI_API_KEY")
            .post(requestBody)
            .build()

        client.newCall(request).execute().use { response ->
            return when (response.code) {
                200 -> response.body?.string()?.let { listOf(it) } ?: listOf("No response")
                404 -> listOf("Error 404: Not Found - The requested resource could not be found.")
                401 -> listOf("Error 401: Unauthorized - Check your API key.")
                429 -> listOf("Error 429: Too Many Requests - You have sent too many requests in a given amount of time.")
                500 -> listOf("Error 500: Internal Server Error - Try again later.")
                else -> listOf("Failed to call Codex API with response code: ${response.code}")
            }
        }
    }
}