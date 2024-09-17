package vn.com.misa.misatoolkit.copilot

sealed class GithubCopilotException : Exception() {
    class CopilotNotInstalled : GithubCopilotException()
}