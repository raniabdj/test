package com.example.interviewprep.network

class DataRepository constructor(private val retrofitService:RetrofitService) {
   fun getAllCharacters()=retrofitService.getAllCharacters()
}