package com.example.jalavidapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.jalavidapp.api.ApiSecondConfig
import com.example.jalavidapp.model.DataItemFaskes
import com.example.jalavidapp.model.Faskes
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FaskesViewModel : ViewModel() {

    private val _faskesDetail = MutableLiveData<List<DataItemFaskes>>()
    val faskesDetail: LiveData<List<DataItemFaskes>> = _faskesDetail

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean> = _isSuccess
    companion object {
        private const val TAG = "MainViewModel"
    }

    fun findFaskes(){
        _isLoading.value = true
        val client = ApiSecondConfig.getApiService().getFaskes("jakarta")
        client.enqueue(object : Callback<Faskes> {
            override fun onResponse(
                call: Call<Faskes>,
                response: Response<Faskes>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _faskesDetail.value = responseBody.data
                        _isSuccess.value = true
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                    _isSuccess.value = false
                }
            }

            override fun onFailure(call: Call<Faskes>, t: Throwable) {
                _isLoading.value = false
                _isSuccess.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

}