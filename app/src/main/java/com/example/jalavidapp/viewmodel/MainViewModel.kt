package com.example.jalavidapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.jalavidapp.api.ApiConfig
import com.example.jalavidapp.model.ListDataItem
import com.example.jalavidapp.model.ResponseData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    private val _additionalData = MutableLiveData<ResponseData>()
    val additionalData: LiveData<ResponseData> = _additionalData

    private val _covidProvince = MutableLiveData<List<ListDataItem>>()
    val covidProvince: LiveData<List<ListDataItem>> = _covidProvince

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean> = _isSuccess

    companion object {
        private const val TAG = "MainViewModel"
    }

    fun findCovidProvince() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getCovidProvince()
        client.enqueue(object : Callback<ResponseData> {
            override fun onResponse(
                call: Call<ResponseData>,
                response: Response<ResponseData>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _additionalData.value = responseBody!!
                        _covidProvince.value = responseBody.listData
                        _isSuccess.value = true
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                    _isSuccess.value = false
                }
            }

            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
                _isSuccess.value = false
            }
        })
    }

}