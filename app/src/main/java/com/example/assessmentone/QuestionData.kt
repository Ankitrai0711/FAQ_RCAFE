package com.example.assessmentone


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class QuestionData(
    @SerializedName("faq")
    @Expose
    var faq: List<Faq?>?,
    @SerializedName("faqCategoryId")
    @Expose
    var faqCategoryId: Int?,
    @SerializedName("faqCategoryTitle")
    @Expose
    var faqCategoryTitle: String?,
    @SerializedName("timeStamp")
    @Expose
    var timeStamp: Long?
) {
    data class Faq(
        @SerializedName("answer")
        @Expose
        var answer: String?,
        @SerializedName("faqId")
        @Expose
        var faqId: Int?,
        @SerializedName("question")
        @Expose
        var question: String?,
        @SerializedName("status")
        @Expose
        var status: String?
    )
}