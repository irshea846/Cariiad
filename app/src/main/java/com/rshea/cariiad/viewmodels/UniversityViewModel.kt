package com.rshea.cariiad.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.rshea.cariiad.datastore.local.CacheMapper
import com.rshea.cariiad.datastore.remote.NetworkMapper
import com.rshea.cariiad.di.RepositoryModule
import com.rshea.cariiad.di.RetrofitModule
import com.rshea.cariiad.di.RoomModule
import com.rshea.cariiad.models.University
import com.rshea.cariiad.repository.UniversityRepository
import com.rshea.cariiad.utils.DataState
import com.rshea.cariiad.utils.LiveNetworkMonitor
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class UniversityViewModel (
    private val application: Application,
    private val universityRepository: UniversityRepository
) : ViewModel() {
    /**
     * A list of countries that can be shown on the screen. This is private to avoid exposing a
     * way to set this value to observers.
     */
    private val _uiState: MutableLiveData<DataState<List<University>>> = MutableLiveData()

    /**
     * A list of countries that can be shown on the screen. Views should use this to get access
     * to the data.
     */
    val uiState: LiveData<DataState<List<University>>>
        get() = _uiState

    /**
     * init{} is called immediately when this ViewModel is created.
     */
    init {
        fetchUniversities()
    }

    private var fetchJob: Job? = null

    /**
     * Refresh data from the repository. Use a coroutine launch to run in a
     * background thread.
     */
    private fun fetchUniversities() {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            universityRepository.fetchUniInfoList()
                .onEach { uiState -> _uiState.value = uiState }
                .launchIn(viewModelScope)
        }
    }


    /**
     * Factory for constructing UniViewModel with parameter
     */
    class Factory(private val app: Application) : ViewModelProvider.Factory {

        private val countryRepositoryModule = RepositoryModule.provideUniversityRepository(
            RoomModule.provideUniversityDao(RoomModule.provideUniDB(app)),
            RetrofitModule.provideUniversityService(RetrofitModule.provideRetrofit(
                LiveNetworkMonitor(app),
                UniversityRepository.baseUrl,
                RetrofitModule.provideGsonBuilder()
            )),
            CacheMapper(),
            NetworkMapper(),
        )
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(UniversityViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return UniversityViewModel(app, countryRepositoryModule) as T
            }
            throw IllegalArgumentException("Unknown View Model Class")
        }
    }
}