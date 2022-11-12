package com.musinsa.shopping.ui.home.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.musinsa.shopping.R
import com.musinsa.shopping.databinding.ItemHomeScrollWrapperBinding
import com.musinsa.shopping.domain.model.remote.Home
import com.musinsa.shopping.util.getDataBinding

class ScrollWrapperAdapter(
    private val data: List<Home.HomeContents.ScrollContents.ScrollGoods>,
    private val adapter: ScrollAdapter
) : RecyclerView.Adapter<ScrollWrapperAdapter.ViewHolder>() {

    private var lastScrollX = 0
    private var binding: ItemHomeScrollWrapperBinding? = null


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ScrollWrapperAdapter.ViewHolder {
        return ViewHolder(
            getDataBinding<ItemHomeScrollWrapperBinding>(
                parent,
                R.layout.item_home_scroll_wrapper
            ).apply {
                binding = this
            })
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(adapter)
    }

    override fun getItemCount(): Int = 1

    inner class ViewHolder(
        val binding: ItemHomeScrollWrapperBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(adapter: ScrollAdapter) {

            binding.rcScrollWrapper.apply {
                itemAnimator = null
                this.adapter = adapter.apply {
                    setData(data)
                    if(!hasObservers()) {
                        setHasStableIds(true)
                    }
                }
            }

            binding.rcScrollWrapper.post {
                binding.rcScrollWrapper.scrollTo(lastScrollX, 0)
            }
        }
    }

    override fun onViewRecycled(holder: ViewHolder) {
        lastScrollX = holder.binding.rcScrollWrapper.computeHorizontalScrollOffset()

        super.onViewRecycled(holder)
    }

    fun updateData(data: List<Home.HomeContents.ScrollContents.ScrollGoods>) {
        binding?.rcScrollWrapper?.run {
            stopScroll()
            scrollToPosition(0)
            this@ScrollWrapperAdapter.adapter.apply {
                setData(data)
            }
        }
    }
}
