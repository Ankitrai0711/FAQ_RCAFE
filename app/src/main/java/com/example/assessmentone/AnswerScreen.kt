package com.example.assessmentone

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.assessmentone.databinding.FragmentAnswerScreenBinding
import com.google.gson.Gson

class AnswerScreen : Fragment() {

    private lateinit var binding : FragmentAnswerScreenBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_answer_screen, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val answer = arguments?.getString("store")
        val question = arguments?.getString("stores")
        binding.answer.text = answer
        binding.question.text = question

        binding.yes.setOnClickListener {
            binding.yes.backgroundTintList = binding.yes.context.getColorStateList(R.color.green)
            binding.no.backgroundTintList = binding.no.context.getColorStateList(R.color.battleShipGray)
        }

        binding.no.setOnClickListener{
            binding.yes.backgroundTintList = binding.yes.context.getColorStateList(R.color.battleShipGray)
            binding.no.backgroundTintList = binding.no.context.getColorStateList(R.color.red)
        }

        binding.view.backButton.setOnClickListener{
            requireActivity().supportFragmentManager.popBackStackImmediate()
        }
        super.onViewCreated(view, savedInstanceState)
    }


}