package com.example.jalavidapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.jalavidapp.api.ApiConfig
import com.example.jalavidapp.model.RumahSakitItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RumahSakitRujukanViewModel : ViewModel() {
    private val _rsDetail = MutableLiveData<List<RumahSakitItem>>()
    val rsDetail: LiveData<List<RumahSakitItem>> = _rsDetail

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean> = _isSuccess
    companion object {
        private const val TAG = "MainViewModel"
    }

    fun findRS(){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getHospital()
        client.enqueue(object : Callback<List<RumahSakitItem>> {
            override fun onResponse(
                call: Call<List<RumahSakitItem>>,
                response: Response<List<RumahSakitItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _isSuccess.value = true
                        _rsDetail.value = responseBody!!
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                    _isSuccess.value = false
                }
            }

            override fun onFailure(call: Call<List<RumahSakitItem>>, t: Throwable) {
                _isLoading.value = false
                _isSuccess.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

}