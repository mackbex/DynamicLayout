package com.musinsa.shopping.ui.home.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.musinsa.shopping.R
import com.musinsa.shopping.databinding.ItemHomeBannerViewpagerBinding
import com.musinsa.shopping.domain.model.remote.HomeContents
import com.musinsa.shopping.util.getDataBinding


class BannerWrapperAdapter(
    private val data: List<HomeContents.HomeItem.BannersContents.Banner>,
    private val adapter: BannerAdapter
) : RecyclerView.Adapter<BannerWrapperAdapter.ViewHolder>() {

    private var lastPage = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(getDataBinding(parent, R.layout.item_home_banner_viewpager))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(adapter, data)
    }

    override fun getItemCount(): Int = 1

    inner class ViewHolder(val binding: ItemHomeBannerViewpagerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(adapter: BannerAdapter, data: List<HomeContents.HomeItem.BannersContents.Banner>) {
            binding.vpBanner.apply {

                offscreenPageLimit = 3
                this.adapter = adapter.apply {
                    submitList(data)
                }


                registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        binding.current = binding.vpBanner.currentItem + 1
                    }
                })

                setCurrentItem(lastPage, false)

            }

            binding.total = data.size
        }
    }

    override fun onViewRecycled(holder: ViewHolder) {
        lastPage = holder.binding.vpBanner.currentItem
        super.onViewRecycled(holder)
    }


}