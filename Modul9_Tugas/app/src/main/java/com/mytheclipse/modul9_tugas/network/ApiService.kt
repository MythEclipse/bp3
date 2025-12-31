package com.mytheclipse.modul9_tugas.network

import com.mytheclipse.modul9_tugas.models.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.Multipart
import retrofit2.http.Part
import okhttp3.MultipartBody

interface ApiService {
    
    // AI Chat Endpoints
    @GET("api/ai/chatgpt")
    suspend fun chatGpt(
        @Query("text") text: String,
        @Query("prompt") prompt: String? = null,
        @Query("session") session: String? = null
    ): Response<AiResponse>
    
    @GET("api/ai/gemini")
    suspend fun gemini(
        @Query("text") text: String
    ): Response<AiResponse>
    
    // Video Downloader Endpoints
    @GET("api/downloader/ytmp4")
    suspend fun youtubeDownload(
        @Query("url") url: String
    ): Response<DownloadResponse>
    
    @GET("api/downloader/fbdl")
    suspend fun facebookDownload(
        @Query("url") url: String
    ): Response<DownloadResponse>
    
    @GET("api/downloader/tiktok")
    suspend fun tiktokDownload(
        @Query("url") url: String
    ): Response<DownloadResponse>
    
    // Image Generator Endpoints
    @GET("api/image/brat")
    suspend fun bratImage(
        @Query("text") text: String
    ): Response<ImageResponse>
    
    @GET("api/image/quotly")
    suspend fun quotlyImage(
        @Query("text") text: String,
        @Query("username") username: String? = null
    ): Response<ImageResponse>
    
    // Search Endpoints
    @GET("api/search/google")
    suspend fun googleSearch(
        @Query("query") query: String
    ): Response<SearchResponse>
    
    @GET("api/stalk/github")
    suspend fun githubStalk(
        @Query("username") username: String
    ): Response<GitHubProfile>
    
    // Uploader Endpoint
    @Multipart
    @POST("api/uploader/ryzencdn")
    suspend fun uploadFile(
        @Part file: MultipartBody.Part
    ): Response<ImageResponse>
}
