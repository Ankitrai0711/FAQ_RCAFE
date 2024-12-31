package com.example.assessmentone
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.assessmentone.databinding.CategoryItemBinding

class CategoryAdapter(
    private val context: Context,
    private val data: DataClass,
    private val userClick: UserClick
) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {
    private var selectedPosition = 0
    init {
        val sharedPreferences = context.getSharedPreferences("category_prefs", Context.MODE_PRIVATE)
        val lastClickedCategoryId = sharedPreferences.getInt("last_clicked_category_id", -1)
        selectedPosition = data.faqCategory!!.indexOfFirst { it?.faqCategoryId==lastClickedCategoryId}
        if (selectedPosition==-1)
            selectedPosition=0
         }



    inner class ViewHolder(val binding: CategoryItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            CategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.faqCategory?.size ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = data.faqCategory!![position]
        holder.binding.apply {
            category.text = currentItem?.faqCategoryTitle

            if (position == selectedPosition) {
                category.setBackgroundColor(ContextCompat.getColor(context, R.color.saddleBrown))
                category.setTextColor(Color.WHITE)
            } else {
                category.setBackgroundColor(ContextCompat.getColor(context, R.color.munsel))
                category.setTextColor(ContextCompat.getColor(context, R.color.dimGray))
            }

            cardView.setOnClickListener {
                if (selectedPosition != holder.adapterPosition) {
                    selectedPosition = holder.adapterPosition
                    notifyDataSetChanged()
                    currentItem?.faqCategoryId?.let { it1 -> userClick.onClicked(it1) }
                }
            }
        }
    }
}
