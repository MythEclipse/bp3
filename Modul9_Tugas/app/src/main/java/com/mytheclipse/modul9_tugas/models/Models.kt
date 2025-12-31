package com.mytheclipse.modul9_tugas.models

import com.google.gson.annotations.SerializedName

// Generic API Response wrapper
data class ApiResponse<T>(
    @SerializedName("status") val status: Boolean?,
    @SerializedName("message") val message: String?,
    @SerializedName("data") val data: T?
)

// AI Chat Response
data class AiResponse(
    @SerializedName("success") val success: Boolean?,
    @SerializedName("result") val result: String?,
    @SerializedName("session") val session: String?
)

// Video Downloader Response
data class DownloadResponse(
    @SerializedName("title") val title: String?,
    @SerializedName("thumbnail") val thumbnail: String?,
    @SerializedName("url") val url: String?,
    @SerializedName("quality") val quality: String?,
    @SerializedName("download") val download: List<DownloadLink>?
)

data class DownloadLink(
    @SerializedName("quality") val quality: String?,
    @SerializedName("url") val url: String?
)

// Image Generator Response
data class ImageResponse(
    @SerializedName("url") val url: String?,
    @SerializedName("image") val image: String?
)

// Search Response
data class SearchResponse(
    @SerializedName("results") val results: List<SearchResult>?
)

data class SearchResult(
    @SerializedName("title") val title: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("url") val url: String?
)

// GitHub Profile Response
data class GitHubProfile(
    @SerializedName("login") val login: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("bio") val bio: String?,
    @SerializedName("avatar_url") val avatarUrl: String?,
    @SerializedName("followers") val followers: Int?,
    @SerializedName("following") val following: Int?,
    @SerializedName("public_repos") val publicRepos: Int?
)

// Chat Message for UI
data class ChatMessage(
    val message: String,
    val isUser: Boolean,
    val timestamp: Long = System.currentTimeMillis()
)
