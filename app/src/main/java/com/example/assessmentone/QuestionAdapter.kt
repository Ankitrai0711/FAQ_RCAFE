package com.example.assessmentone

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.assessmentone.DataClass
import com.example.assessmentone.QuestionData
import com.example.assessmentone.databinding.QuestionEachItemBinding
import com.google.gson.Gson

class QuestionAdapter(private val context: Context, private val data: QuestionData,val userClick: UserClick) :
    RecyclerView.Adapter<QuestionAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: QuestionEachItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = QuestionEachItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.faq?.size ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = data.faq?.get(position)

        holder.binding.apply {
            if (currentItem != null) {
                question.text= currentItem.question
            }
            parent.setOnClickListener{
                val dataAnswer = data.faq?.get(position)?.answer
                val dataQuestion = data.faq?.get(position)?.question
                if (dataQuestion != null) {
                    if (dataAnswer != null) {
                        userClick.onClickAnswer(dataAnswer,dataQuestion)
                    }
                }
                    }
                }
            }

        }

