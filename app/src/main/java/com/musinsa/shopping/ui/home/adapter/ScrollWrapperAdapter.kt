package com.musinsa.shopping.ui.home.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.musinsa.shopping.R
import com.musinsa.shopping.databinding.ItemHomeScrollWrapperBinding
import com.musinsa.shopping.util.getDataBinding

class ScrollWrapperAdapter(
    private val adapter: ScrollAdapter
) : RecyclerView.Adapter<ScrollWrapperAdapter.ViewHolder>() {
    private var lastScrollX = 0

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ScrollWrapperAdapter.ViewHolder {
        return ViewHolder(getDataBinding(parent, R.layout.item_home_scroll_wrapper))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(adapter, lastScrollX)
    }

    override fun getItemCount(): Int = 1

    inner class ViewHolder(
        val binding: ItemHomeScrollWrapperBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(adapter: ScrollAdapter, lastScrollX: Int) {

            binding.rcScrollWrapper.itemAnimator = null
            binding.rcScrollWrapper.adapter = adapter
            binding.rcScrollWrapper.layoutManager =
                object : LinearLayoutManager(binding.root.context, HORIZONTAL, false) {
                    override fun checkLayoutParams(lp: RecyclerView.LayoutParams?): Boolean {
                        return lp?.let {
                            lp.width = width / 3
                            true
                        } ?: run {
                            super.checkLayoutParams(lp)
                        }
                    }
                }

            binding.rcScrollWrapper.scrollBy(lastScrollX, 0)
        }
    }

    override fun onViewRecycled(holder: ViewHolder) {
        lastScrollX = holder.binding.rcScrollWrapper.computeHorizontalScrollOffset()

        super.onViewRecycled(holder)
    }
}
