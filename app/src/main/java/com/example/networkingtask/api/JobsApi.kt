package com.example.networkingtask.api

import com.example.networkingtask.model.JobGitHub
import com.example.networkingtask.utils.DEFAULT_LANGUAGE
import com.example.networkingtask.utils.DEFAULT_LOCATION
import com.example.networkingtask.utils.baseURL
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface JobsApi {

    @GET("positions.json")
    fun getJobsAsync(
        @Query("description") language: String = DEFAULT_LANGUAGE,
        @Query("location") location: String = DEFAULT_LOCATION
    ): Deferred<Response<List<JobGitHub>>>

    companion object {
        val retrofit: JobsApi = Retrofit.Builder()
            .client(
                OkHttpClient().newBuilder()
                    .addInterceptor(HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    })
                    .build()
            )
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
            .create(JobsApi::class.java)
    }
}
