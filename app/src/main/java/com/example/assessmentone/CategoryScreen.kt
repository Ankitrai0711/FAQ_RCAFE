package com.example.assessmentone
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assessmentone.databinding.FragmentCategoryScreenBinding
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryScreen : Fragment(), UserClick {

    private lateinit var mcontaxt: Context
    private val PREF_NAME = "category_prefs"
    private val KEY_CATEGORY_ID = "last_clicked_category_id"
    var  isFragmentInitialized = false


    override fun onAttach(context: Context) {
        mcontaxt = context
        super.onAttach(context)
    }

    private lateinit var binding: FragmentCategoryScreenBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if(::binding.isInitialized){
            isFragmentInitialized = true
            return binding.root
        }
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_category_screen, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(!isFragmentInitialized){
            callCategryApi()
            loadLastClickedCategory()
            clickOnBack()
        }


    }

    fun clickOnBack(){
        binding.backButton.backButton.setOnClickListener{

                requireActivity().finish()
        }
    }

    private fun loadLastClickedCategory() {
        val sharedPreferences = requireContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val lastClickedCategoryId = sharedPreferences.getInt(KEY_CATEGORY_ID, -1)
        if (lastClickedCategoryId != -1) {
            callQuestionApi(lastClickedCategoryId)
        }
    }

    private fun saveLastClickedCategory(faqCategoryId: Int) {
        val sharedPreferences = requireContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt(KEY_CATEGORY_ID, faqCategoryId)
        editor.apply()
    }

    fun setCategoryAdapter(myData: DataClass) {
        binding.categoryRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.categoryRecyclerView.adapter = CategoryAdapter(mcontaxt, myData, this@CategoryScreen)
    }

    fun setQuestionAdapter(myData: QuestionData) {
        binding.questionRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.questionRecyclerView.adapter = QuestionAdapter(this.mcontaxt, myData,this@CategoryScreen)
    }

    override fun onClicked(faqCategoryId: Int) {
        saveLastClickedCategory(faqCategoryId)
        callQuestionApi(faqCategoryId)
    }

    override fun onClickAnswer(data: String,data2:String) {
        val bundle = Bundle()
        bundle.putString("store", data)
        bundle.putString("stores", data2)
        var frag = AnswerScreen()
        frag.arguments = bundle
        requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer,frag).addToBackStack(null).commit()
    }

    fun callQuestionApi(id: Int) {
        binding.shimmerContainerQuestion.visibility = View.VISIBLE
        binding.shimmerContainerQuestion.startShimmer()
        binding.questionRecyclerView.visibility=View.GONE
        val retrofit = ServiceBuilder.buildServices(ApiInterface::class.java)
        val retroData = retrofit.getQuestions(id)
        retroData.enqueue(object : Callback<QuestionData> {
            override fun onResponse(call: Call<QuestionData>, response: Response<QuestionData>) {
                val myData = response.body()
                if (response.isSuccessful) {
                    binding.shimmerContainerQuestion.stopShimmer()
                    binding.shimmerContainerQuestion.visibility = View.GONE
                    binding.questionRecyclerView.visibility=View.VISIBLE
                    if (response.code() == 200) {
                        if (myData != null) {
                            setQuestionAdapter(myData)
                        }
                    }
                    Log.e("getData", myData.toString())
                }
                else{
                    handleApiError(response)
                }
            }
            fun showError(message: String): Unit? {
                return view?.let { Snackbar.make(it,message, Snackbar.LENGTH_SHORT).show() }
            }
            override fun onFailure(call: Call<QuestionData>, t: Throwable) {
                Log.e("getData", "Failure: ${t.message}")
                view?.let { Snackbar.make(it,"Api Error",Snackbar.LENGTH_SHORT).show() }
                binding.shimmerContainerQuestion.stopShimmer()
                binding.shimmerContainerQuestion.visibility = View.GONE
            }
            fun handleApiError(response: Response<*>) {
                when (response.code()) {
                    400 -> showError("Bad Request. Please check your input.")
                    404 -> showError("Not Found. The requested resource doesn't exist.")
                    500 -> showError("Server Error. Please try again later.")
                    503 -> showError("Error: Service unavailable. Please try again after some time.")
                    401 -> showError("Unauthorized access.")
                    405 -> showError("Error: Method not allowed. Please contact support.")
                    else -> showError("Unexpected error occurred. Code: ${response.code()}")
                }
            }
        }
        )
    }

    fun callCategryApi() {
        binding.shimmerContainer.visibility = View.VISIBLE
        binding.shimmerContainer.startShimmer()
        val retrofit = ServiceBuilder.buildServices(ApiInterface::class.java)
        val retroData = retrofit.getData()
        retroData.enqueue(object : Callback<DataClass> {
            override fun onResponse(call: Call<DataClass>, response: Response<DataClass>) {
                val myData = response.body()
                if (response.isSuccessful) {
                    binding.shimmerContainer.stopShimmer()
                    binding.shimmerContainer.visibility = View.GONE
                    if (response.code() == 200) {
                        if (myData != null) {
                            val sharedPreferences = requireContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
                            if (!sharedPreferences.contains(KEY_CATEGORY_ID)){
                                myData.faqCategory?.get(0)?.faqCategoryId?.let { callQuestionApi(it) }
                                myData.faqCategory?.get(0)?.faqCategoryId?.let {
                                    sharedPreferences.edit().putInt("category_prefs", it)
                                }?.apply()
                            }
                            setCategoryAdapter(myData)
                        }
                    }
                }
                else{
                    handleApiError(response)
                }
            }
            fun showError(message: String): Unit? {
                return view?.let { Snackbar.make(it,message, Snackbar.LENGTH_SHORT).show() }
            }

            override fun onFailure(call: Call<DataClass>, t: Throwable) {
                Log.e("getData", "Failure: ${t.message}")
                binding.shimmerContainer.stopShimmer()
                binding.shimmerContainer.visibility = View.GONE
            }
            fun handleApiError(response: Response<*>) {
                when (response.code()) {
                    400 -> showError("Bad Request. Please check your input.")
                    404 -> showError("Not Found. The requested resource doesn't exist.")
                    500 -> showError("Server Error. Please try again later.")
                    401 -> showError("Authentication Issue")
                    503 -> showError("ll")
                    405 -> showError("ll")
                    else -> showError("Unexpected error occurred. Code: ${response.code()}")
                }
            }
        }
        )
    }
}