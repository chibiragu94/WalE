package com.chibi.waie.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chibi.waie.common.Constants
import com.chibi.waie.common.DateUtils
import com.chibi.waie.common.Status
import com.chibi.waie.data.local.repository.AstronomyLocalRepository
import com.chibi.waie.data.remote.repository.BaseApiRepositoryImpl
import com.chibi.waie.model.Astronomy
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AstronomyViewModel @Inject constructor(
    private val repository: BaseApiRepositoryImpl?,
    private val localRepository: AstronomyLocalRepository
) : ViewModel(){

    // Variables
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage : LiveData<String> = _errorMessage

    private val _astronomyDetails = MutableLiveData<Astronomy>()
    val astronomyDetails : LiveData<Astronomy> = _astronomyDetails

    private var getAstronomyDetailsJob : Job? = null


    // override Methods
    /**
     * Will be called when activity is destroyed, so we can cancel all the Running Job's
     */
    override fun onCleared() {
        super.onCleared()
        getAstronomyDetailsJob?.cancel()
    }

    // Public Methods
    /**
     * Get details from the API
     */
    fun getAstronomyDetailRemote(){
        getAstronomyDetailsJob = viewModelScope.launch(Dispatchers.Main) {

            _isLoading.value = true

            repository
                ?.getAstronomyRepositoryImplInstance()
                ?.getAstronomyDetails()
                ?.catch {
                        error ->
                    _errorMessage.value = error.localizedMessage
                    _isLoading.value = false
                }
                ?.collect { response ->
                    _isLoading.value = false

                    response.let {

                        if (response.status == Status.SUCCESS) {
                            response.data?.let { data ->
                                _astronomyDetails.value = data

                                saveAstronomyInLocalRepository(data)
                            }
                        }else {
                            response.message?.let { message -> _errorMessage.value = message }
                        }
                    }
                }
        }
    }

    /**
     * Check the local data with current day, month and year
     * or else - results the last inserted item in the Local data
     */
    fun getAstronomyFromLocal(){
        viewModelScope.launch {
            localRepository.getAstronomyByDate().collect { astronomy->
                if(null != astronomy){
                    _astronomyDetails.value = astronomy
                }else{
                    getAllAstronomyFromLocal()
                }
            }
        }
    }

    // Private Methods
    /**
     * Saves astronomy data locally
     */
    private fun saveAstronomyInLocalRepository(astronomy: Astronomy){
        viewModelScope.launch {

            //add inserted day, month, year
            astronomy.apply {
                insertedDay = DateUtils.getCurrentDay()
                insertedMonth = DateUtils.getCurrentMonth()
                insertedYear = DateUtils.getCurrentYear()
            }

            localRepository.insert(astronomy)
        }
    }

    /**
     * Get All Astronomy in Desc order by Id
     */
    private fun getAllAstronomyFromLocal(){
        viewModelScope.launch {

          localRepository.getAllAstronomy()
              .collect { astronomyList ->
              astronomyList.let {

                  val result = it

                  if (result.isNotEmpty()){
                      _astronomyDetails.value = result[0]

                      _errorMessage.value = Constants.NETWORK_FAILURE_ERROR
                  }
              }
          }
        }
    }



}