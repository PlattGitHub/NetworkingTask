package com.example.networkingtask.api

import com.example.networkingtask.model.JobGitHub
import com.example.networkingtask.utils.DEFAULT_LANGUAGE
import com.example.networkingtask.utils.DEFAULT_LOCATION

/**
 * Singleton class that has a method [getJobs] to fetch data from GitHub Api.
 *
 * @author Alexander Gorin
 */
class Repository private constructor() {

    private val jobsApi: JobsApi = JobsApi.retrofit

    suspend fun getJobs(
        language: String = DEFAULT_LANGUAGE,
        location: String = DEFAULT_LOCATION
    ): List<JobGitHub> {
        val webResponse = jobsApi.getJobsAsync(language, location).await()
        return when {
            webResponse.isSuccessful -> webResponse.body() ?: emptyList()
            else -> emptyList()
        }
    }

    companion object {
        @Volatile
        private var instance: Repository? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: Repository().also { instance = it }
            }
    }
}