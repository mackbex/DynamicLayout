package com.musinsa.shopping.ui.home.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.musinsa.shopping.R
import com.musinsa.shopping.databinding.ItemHomeScrollBinding
import com.musinsa.shopping.domain.model.remote.HomeContents
import com.musinsa.shopping.util.getDataBinding


class ScrollAdapter :
    ListAdapter<HomeContents.HomeItem.ScrollContents.ScrollGoods, ScrollAdapter.ViewHolder>(
        ItemDiffCallback()
) {

    private var clickListener: ScrollGoodsListener? = null

    interface ScrollGoodsListener {
        fun onClick(link: String)
    }

    fun setScrollClickListener(listener: ScrollGoodsListener) {
        this.clickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScrollAdapter.ViewHolder {
        return ViewHolder(getDataBinding(parent, R.layout.item_home_scroll))
    }

    override fun onBindViewHolder(holder: ScrollAdapter.ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemHomeScrollBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: HomeContents.HomeItem.ScrollContents.ScrollGoods) {
            binding.model = model
            binding.clickListener = clickListener
        }
    }

    private class ItemDiffCallback : DiffUtil.ItemCallback<HomeContents.HomeItem.ScrollContents.ScrollGoods>() {
        override fun areItemsTheSame(
            oldItem: HomeContents.HomeItem.ScrollContents.ScrollGoods,
            newItem: HomeContents.HomeItem.ScrollContents.ScrollGoods
        ): Boolean {
            return oldItem.linkURL == newItem.linkURL
                    && oldItem.thumbnailURL == newItem.thumbnailURL
                    && oldItem.brandName == newItem.brandName
        }

        override fun areContentsTheSame(
            oldItem: HomeContents.HomeItem.ScrollContents.ScrollGoods,
            newItem: HomeContents.HomeItem.ScrollContents.ScrollGoods
        ): Boolean {
            return oldItem == newItem
        }

    }
}