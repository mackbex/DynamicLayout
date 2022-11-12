package com.musinsa.shopping.ui.home.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.musinsa.shopping.R
import com.musinsa.shopping.databinding.ItemHomeBannerBinding
import com.musinsa.shopping.domain.model.remote.Home
import com.musinsa.shopping.util.getDataBinding


class BannerAdapter :
    ListAdapter<Home.HomeContents.BannersContents.Banner, BannerAdapter.ViewHolder>(
        ItemDiffCallback()
    ) {

    private var clickListener: BannerListener? = null

    interface BannerListener {
        fun onClick(link: String)
    }

    fun setBannerListener(listener: BannerListener) {
        this.clickListener = listener
    }

    inner class ViewHolder(private val binding: ItemHomeBannerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: Home.HomeContents.BannersContents.Banner) {
            binding.model = model
            binding.clickListener = clickListener
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(getDataBinding(parent, R.layout.item_home_banner))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).hashCode().toLong()
    }

    private class ItemDiffCallback : DiffUtil.ItemCallback<Home.HomeContents.BannersContents.Banner>() {
        override fun areItemsTheSame(
            oldItem: Home.HomeContents.BannersContents.Banner,
            newItem: Home.HomeContents.BannersContents.Banner
        ): Boolean {
            return oldItem.linkURL == newItem.linkURL
                    && oldItem.thumbnailURL == newItem.thumbnailURL
                    && oldItem.keyword == newItem.keyword
                    && oldItem.title == newItem.title
                    && oldItem.description == newItem.description
        }

        override fun areContentsTheSame(
            oldItem: Home.HomeContents.BannersContents.Banner,
            newItem: Home.HomeContents.BannersContents.Banner
        ): Boolean {
            return oldItem == newItem
        }

    }
}