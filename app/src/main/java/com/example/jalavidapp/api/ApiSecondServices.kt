package com.example.jalavidapp.api
import com.example.jalavidapp.model.Faskes
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiSecondServices {
    @GET("get-faskes-vaksinasi")
    fun getFaskes(
        @Query("province") province : String
    ): Call<Faskes>
}