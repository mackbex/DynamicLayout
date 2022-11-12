package com.musinsa.shopping.ui.home.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.musinsa.shopping.R
import com.musinsa.shopping.databinding.ItemHomeGridBinding
import com.musinsa.shopping.domain.model.remote.HomeContents
import com.musinsa.shopping.util.getDataBinding


class GridAdapter :
    ListAdapter<HomeContents.HomeItem.GridContents.GridGoods, GridAdapter.ViewHolder>(
        ItemDiffCallback()
    ) {

    private var clickListener: GridGoodsListener? = null

    interface GridGoodsListener {
        fun onClick(link: String)
    }

    fun setGridClickListener(listener: GridGoodsListener) {
        this.clickListener = listener
    }

    inner class ViewHolder(private val binding: ItemHomeGridBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: HomeContents.HomeItem.GridContents.GridGoods) {
            binding.model = model
            binding.clickListener = clickListener
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(getDataBinding(parent, R.layout.item_home_grid))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private class ItemDiffCallback : DiffUtil.ItemCallback<HomeContents.HomeItem.GridContents.GridGoods>() {
        override fun areItemsTheSame(
            oldItem: HomeContents.HomeItem.GridContents.GridGoods,
            newItem: HomeContents.HomeItem.GridContents.GridGoods
        ): Boolean {
            return oldItem.linkURL == newItem.linkURL
                    && oldItem.thumbnailURL == newItem.thumbnailURL
                    && oldItem.brandName == newItem.brandName
        }

        override fun areContentsTheSame(
            oldItem: HomeContents.HomeItem.GridContents.GridGoods,
            newItem: HomeContents.HomeItem.GridContents.GridGoods
        ): Boolean {
            return oldItem == newItem
        }

    }
}