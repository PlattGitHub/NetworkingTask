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

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _isConnected = MutableLiveData<Boolean>()
    val isConnected: LiveData<Boolean>
        get() = _isConnected

    fun loadData(
        language: String = DEFAULT_LANGUAGE,
        location: String = DEFAULT_LOCATION
    ): LiveData<List<JobGitHub>> {
        val jobsList = MutableLiveData<List<JobGitHub>>()
        if (isOnline()) {
            scope.launch {
                _isLoading.postValue(true)
                try {
                    jobsList.postValue(repository.getJobs(language, location))
                } catch (e: Exception) {
                    _isConnected.postValue(false)
                }
                _isLoading.postValue(false)
            }
        } else {
            _isConnected.value = false
        }
        return jobsList

    }

    private fun isOnline(): Boolean {
        val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
        _isConnected.value = networkInfo?.isConnected == true
        return networkInfo?.isConnected == true
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}