package com.example.jalavidapp.api

import com.example.jalavidapp.model.ResponseData
import com.example.jalavidapp.model.RumahSakitItem
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("public/api/prov.json")
    fun getCovidProvince(): Call<ResponseData>
    @GET("public/api/rs.json")
    fun getHospital(): Call<List<RumahSakitItem>>
}