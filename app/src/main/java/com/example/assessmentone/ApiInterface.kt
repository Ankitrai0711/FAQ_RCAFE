package com.example.assessmentone

import com.example.assessmentone.DataClass
import com.example.assessmentone.QuestionData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiInterface {

        @GET("/api/ns/faq/category/v1/get")
        fun getData(): Call<DataClass>

        @GET("/api/ns/faq/v1/get")
        fun getQuestions(@Query("faqCategoryId") faqCategoryId: Int): Call<QuestionData>

}