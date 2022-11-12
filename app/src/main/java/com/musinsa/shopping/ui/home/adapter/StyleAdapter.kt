package com.musinsa.shopping.ui.home.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.musinsa.shopping.R
import com.musinsa.shopping.databinding.ItemHomeStyleBinding
import com.musinsa.shopping.domain.model.remote.HomeContents
import com.musinsa.shopping.util.getDataBinding


class StyleAdapter :
    ListAdapter<HomeContents.HomeItem.StyleContents.Styles, StyleAdapter.ViewHolder>(
        ItemDiffCallback()
    ) {

    private var clickListener: StyleListener? = null

    interface StyleListener {
        fun onClick(link: String)
    }

    fun setStyleClickListener(listener: StyleListener) {
        this.clickListener = listener
    }

    inner class ViewHolder(private val binding: ItemHomeStyleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: HomeContents.HomeItem.StyleContents.Styles) {
            binding.model = model
            binding.clickListener = clickListener
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(getDataBinding(parent, R.layout.item_home_style))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private class ItemDiffCallback : DiffUtil.ItemCallback<HomeContents.HomeItem.StyleContents.Styles>() {
        override fun areItemsTheSame(
            oldItem: HomeContents.HomeItem.StyleContents.Styles,
            newItem: HomeContents.HomeItem.StyleContents.Styles
        ): Boolean {
            return oldItem.linkURL == newItem.linkURL
                    && oldItem.thumbnailURL == newItem.thumbnailURL
        }

        override fun areContentsTheSame(
            oldItem: HomeContents.HomeItem.StyleContents.Styles,
            newItem: HomeContents.HomeItem.StyleContents.Styles
        ): Boolean {
            return oldItem == newItem
        }

    }
}