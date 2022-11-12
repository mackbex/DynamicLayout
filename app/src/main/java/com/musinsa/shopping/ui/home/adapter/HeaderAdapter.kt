package com.musinsa.shopping.ui.home.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.musinsa.shopping.R
import com.musinsa.shopping.databinding.ItemHomeHeaderBinding
import com.musinsa.shopping.domain.model.remote.HomeContents
import com.musinsa.shopping.util.getDataBinding


class HeaderAdapter(
    private val data: HomeContents.Header
) : RecyclerView.Adapter<HeaderAdapter.ViewHolder>() {

    private var clickListener: HeaderListener? = null

    interface HeaderListener {
        fun onClick(link: String?)
    }

    fun setHeaderClickListener(listener: HeaderListener) {
        this.clickListener = listener
    }

    inner class ViewHolder(private val binding: ItemHomeHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: HomeContents.Header) {
            binding.model = model
            binding.clickListener = clickListener
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(getDataBinding(parent, R.layout.item_home_header))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data)
    }

    override fun getItemCount(): Int = 1
}