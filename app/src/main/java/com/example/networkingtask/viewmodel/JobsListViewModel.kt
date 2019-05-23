package com.example.networkingtask.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.networkingtask.api.Repository
import com.example.networkingtask.model.JobGitHub
import com.example.networkingtask.utils.DEFAULT_LANGUAGE
import com.example.networkingtask.utils.DEFAULT_LOCATION
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * ViewModel that is used in JobsListFragment.
 *
 * @author Alexander Gorin
 */
class JobsListViewModel(app: Application) : AndroidViewModel(app) {

    private val repository = Repository.getInstance()
    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.IO + job)
    private val connectivityManager: ConnectivityManager =
        app.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val isLoading = MutableLiveData<Boolean>().apply {
        value = false
    }
    val isConnected = MutableLiveData<Boolean>()

    fun loadData(
        language: String = DEFAULT_LANGUAGE,
        location: String = DEFAULT_LOCATION
    ): LiveData<List<JobGitHub>> {
        val jobsList = MutableLiveData<List<JobGitHub>>()
        if (isOnline()) {
            scope.launch {
                isLoading.postValue(true)
                try {
                    jobsList.postValue(repository.getJobs(language, location))
                } catch (e: Exception) {
                    isConnected.postValue(false)
                }
                isLoading.postValue(false)
            }
        } else {
            isConnected.value = false
        }
        return jobsList

    }

    private fun isOnline(): Boolean {
        val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
        isConnected.value = networkInfo?.isConnected == true
        return networkInfo?.isConnected == true
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}