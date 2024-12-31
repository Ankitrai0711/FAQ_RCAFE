package com.example.assessmentone


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class DataClass(
    @SerializedName("faqCategory")
    @Expose
    var faqCategory: List<FaqCategory?>?,
    @SerializedName("timestamp")
    @Expose
    var timestamp: Long?
) {
    data class FaqCategory(
        @SerializedName("faqCategoryId")
        @Expose
        var faqCategoryId: Int?,
        @SerializedName("faqCategoryTitle")
        @Expose
        var faqCategoryTitle: String?,
        @SerializedName("imagePath")
        @Expose
        var imagePath: String?,
        @SerializedName("status")
        @Expose
        var status: String?
    )
}