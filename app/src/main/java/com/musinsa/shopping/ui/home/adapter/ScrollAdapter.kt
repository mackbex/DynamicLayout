package com.musinsa.shopping.ui.home.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.musinsa.shopping.R
import com.musinsa.shopping.databinding.ItemHomeScrollBinding
import com.musinsa.shopping.domain.model.remote.HomeContents
import com.musinsa.shopping.util.getDataBinding


class ScrollAdapter : RecyclerView.Adapter<ScrollAdapter.ViewHolder>() {

    private val list = mutableListOf<HomeContents.HomeItem.ScrollContents.ScrollGoods>()
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

    override fun getItemId(position: Int): Long {
        return list[position].hashCode().toLong()
    }

    override fun onBindViewHolder(holder: ScrollAdapter.ViewHolder, position: Int) {
        list.getOrNull(position)?.let {
            holder.bind(it)
        }
    }

    fun setData(newList: List<HomeContents.HomeItem.ScrollContents.ScrollGoods>) {
        this.list.apply {
            clear()
            addAll(newList)
            notifyItemRangeChanged(0, size)
        }
    }

    inner class ViewHolder(private val binding: ItemHomeScrollBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: HomeContents.HomeItem.ScrollContents.ScrollGoods) {
            binding.model = model
            binding.clickListener = clickListener
        }
    }

    override fun getItemCount() = list.size
}