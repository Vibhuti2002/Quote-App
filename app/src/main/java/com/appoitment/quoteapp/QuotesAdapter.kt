package com.appoitment.quoteapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.appoitment.quoteapp.databinding.ItemQuotesBinding

class QuotesAdapter : RecyclerView.Adapter<QuotesAdapter.QuotesViewHolder>() {
    inner class QuotesViewHolder(val binding: ItemQuotesBinding) : RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<Result>(){
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem._id== newItem._id
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)
    var quotes : List<Result>
    get() = differ.currentList
    set(value){differ.submitList(value)}

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): QuotesAdapter.QuotesViewHolder {
        return QuotesViewHolder(ItemQuotesBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: QuotesAdapter.QuotesViewHolder, position: Int) {
         holder.binding.apply { val quote = quotes[position]
         TVtitle.text = quote.content
         }
    }

    override fun getItemCount() = quotes.size

}