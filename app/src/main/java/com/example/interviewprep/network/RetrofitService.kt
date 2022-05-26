package com.example.interviewprep.network

import com.example.interviewprep.models.ComicsData
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface RetrofitService {
@GET("characters?ts=1&apikey=678c0bb6d6aea2fbde954e9c8e170586&hash=6c5502e0089e46828d91a0d16149b185")
fun getAllCharacters():Observable<ComicsData>

companion object {
    var retrofitService: RetrofitService? = null
    fun getInstance(): RetrofitService {
        if (retrofitService == null) {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://gateway.marvel.com/v1/public/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
            retrofitService = retrofit.create(RetrofitService::class.java)

        }
        return retrofitService!!
    }
}}
