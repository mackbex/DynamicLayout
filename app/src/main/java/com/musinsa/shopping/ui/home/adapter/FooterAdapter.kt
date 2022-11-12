package com.musinsa.shopping.ui.home.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.musinsa.shopping.R
import com.musinsa.shopping.databinding.ItemHomeFooterBinding
import com.musinsa.shopping.domain.model.remote.HomeContents
import com.musinsa.shopping.util.getDataBinding


class FooterAdapter(
    private val data: HomeContents.Footer
    ) : RecyclerView.Adapter<FooterAdapter.ViewHolder>() {
    private var clickListener: FooterListener? = null

    interface FooterListener {
        fun onClick(type: HomeContents.Footer.FooterType)
    }

    fun setFooterClickListener(listener: FooterListener) {
        this.clickListener = listener
    }

    inner class ViewHolder(private val binding: ItemHomeFooterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: HomeContents.Footer) {
            binding.model = model
            binding.clickListener = clickListener
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(getDataBinding(parent, R.layout.item_home_footer))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
           holder.bind(data)
    }

    override fun getItemCount(): Int = 1
}