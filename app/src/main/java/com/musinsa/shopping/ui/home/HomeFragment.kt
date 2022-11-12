package com.musinsa.shopping.ui.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.musinsa.shopping.databinding.FragmentHomeBinding
import com.musinsa.shopping.domain.Resource
import com.musinsa.shopping.domain.model.remote.HomeContents
import com.musinsa.shopping.ui.home.adapter.*
import com.musinsa.shopping.util.autoCleared
import com.musinsa.shopping.util.setScrollSensitivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var binding: FragmentHomeBinding by autoCleared()
    private val viewModel: HomeViewModel by viewModels()

    private val concatAdapter: ConcatAdapter by lazy {
        ConcatAdapter()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        with(binding) {
            lifecycleOwner = viewLifecycleOwner
            rcHome.apply {
                layoutManager = setLayoutManager()
                adapter = concatAdapter
                setScrollSensitivity()
            }
        }

        initObservers()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.fetchHomeContents()
    }

    private fun setLayoutManager(): GridLayoutManager {
        return GridLayoutManager(requireActivity(), 12).apply {
            spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return when (concatAdapter.getWrappedAdapterAndPosition(position).first) {
                        is HeaderAdapter -> 12
                        is FooterAdapter -> 12
                        is GridAdapter -> 4
                        is StyleAdapter -> 6
                        is ScrollWrapperAdapter -> 12
                        is BannerWrapperAdapter -> 12
                        else -> 12
                    }
                }
            }
        }
    }

    private fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            launch {
                viewModel.homeContentsState.collect {
                    when (it) {
                        is Resource.Success -> {
                            initAdapter(it.data)
                        }
                        is Resource.Failure -> {
                        }
                        is Resource.Loading -> {}
                    }
                }
            }
        }
    }

    private fun initAdapter(homeContents: HomeContents) {
        homeContents.data.forEach { homeItem ->

            when (homeItem) {
                is HomeContents.HomeItem.BannersContents -> {

                    concatAdapter.addFormAdapter(
                        header = setHeaderAdapter(homeItem.header),
                        contents = BannerWrapperAdapter(homeItem.banners,
                            BannerAdapter().apply {
                                setBannerListener(object : BannerAdapter.BannerListener {
                                    override fun onClick(link: String) {
                                        val browserIntent =
                                            Intent(Intent.ACTION_VIEW, Uri.parse(link))
                                        startActivity(browserIntent)
                                    }
                                })
                            }
                        ),
                        footer = null
                    )
                }
                is HomeContents.HomeItem.GridContents -> {

                    val goodsAdapter = GridAdapter().apply {
                        setGridClickListener(object : GridAdapter.GridGoodsListener {
                            override fun onClick(link: String) {
                                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
                                startActivity(browserIntent)
                            }
                        })
                        submitList(homeItem.goods.take(viewModel.gridGoodsIndex))
                    }

                    concatAdapter.addFormAdapter(
                        header = setHeaderAdapter(homeItem.header),
                        contents = goodsAdapter,
                        footer = setFooterAdapter(homeItem.footer) { _, adapter ->
                            viewModel.gridGoodsIndex += 3
                            goodsAdapter.submitList(homeItem.goods.take(viewModel.gridGoodsIndex))
                            if (homeItem.goods.size <= viewModel.gridGoodsIndex) {
                                binding.rcHome.post {
                                    concatAdapter.removeAdapter(adapter)
                                }
                            }
                        }
                    )
                }
                is HomeContents.HomeItem.ScrollContents -> {

                    val scrollWrapperAdapter = ScrollWrapperAdapter(homeItem.goods, ScrollAdapter().apply {
                        setScrollClickListener(object : ScrollAdapter.ScrollGoodsListener {
                            override fun onClick(link: String) {
                                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
                                startActivity(browserIntent)
                            }
                        })
                    })

                    concatAdapter.addFormAdapter(
                        header = setHeaderAdapter(homeItem.header),
                        contents = scrollWrapperAdapter,
                        footer = setFooterAdapter(homeItem.footer) { _, _ ->
                            scrollWrapperAdapter.updateData(homeItem.goods.shuffled())
                        }
                    )
                }

                is HomeContents.HomeItem.StyleContents -> {

                    val styleAdapter = StyleAdapter().apply {
                        setStyleClickListener(object : StyleAdapter.StyleListener {
                            override fun onClick(link: String) {
                                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
                                startActivity(browserIntent)
                            }
                        })
                        submitList(homeItem.styles.take(viewModel.gridStyleIndex))
                    }

                    concatAdapter.addFormAdapter(
                        header = setHeaderAdapter(homeItem.header),
                        contents = styleAdapter,
                        footer = setFooterAdapter(homeItem.footer) { _, adapter ->
                            viewModel.gridStyleIndex += 2

                            styleAdapter.submitList(homeItem.styles.take(viewModel.gridStyleIndex))

                            if (homeItem.styles.size <= viewModel.gridStyleIndex) {
                                binding.rcHome.post {
                                    concatAdapter.removeAdapter(adapter)
                                }
                            }
                        }
                    )
                }
                HomeContents.HomeItem.UnknownContents -> {}
            }
        }
    }

    private fun setHeaderAdapter(header: HomeContents.Header?): HeaderAdapter? {
        return header?.let {
                HeaderAdapter(header).apply {
                    setHeaderClickListener(object : HeaderAdapter.HeaderListener {
                        override fun onClick(link: String?) {
                            link?.run {
                                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
                                startActivity(browserIntent)
                            }
                        }
                    })
                }
        } ?: run {
            null
        }
    }

    private fun setFooterAdapter(footer: HomeContents.Footer?, callback:((type: HomeContents.Footer.FooterType, adapter: FooterAdapter) -> Unit)? = null): FooterAdapter? {
        return footer?.let {
            FooterAdapter(footer).apply {
                setFooterClickListener(object : FooterAdapter.FooterListener {
                    override fun onClick(type: HomeContents.Footer.FooterType) {
                        callback?.invoke(type, this@apply)
                    }
                })
            }
        } ?: run {
            null
        }
    }

    private fun <T: ViewHolder> ConcatAdapter.addFormAdapter(header: HeaderAdapter?, contents: Adapter<T>, footer: FooterAdapter?) {
        header?.let { this.addAdapter(it) }
        this.addAdapter(contents)
        footer?.let { this.addAdapter(it) }
    }
}