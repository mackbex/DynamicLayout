package com.musinsa.shopping.data.model.mapper

import com.musinsa.shopping.data.model.remote.*
import com.musinsa.shopping.domain.model.remote.*

fun HomeContentsResponse.mapToDomain(): HomeContents {
    return HomeContents(
        data = this.data.map {
            it.mapToDomain()
        }
    )
}

fun HomeContentsResponse.HomeItemResponse.mapToDomain(): HomeContents.HomeItem {
    return when(this.contents) {
        is HomeContentsResponse.ContentsResponse.UnknownContentsResponse -> {
            HomeContents.HomeItem.UnknownContents
        }
        is HomeContentsResponse.ContentsResponse.BannerContentsResponse -> {
            HomeContents.HomeItem.BannersContents(
                header = this.header?.mapToDomain(),
                banners = this.contents.banners.map {
                    HomeContents.HomeItem.BannersContents.Banner(
                        linkURL = it.linkURL,
                        thumbnailURL = it.thumbnailURL,
                        title = it.title,
                        description = it.description,
                        keyword = it.keyword,
                    )
                },
                footer = this.footer?.mapToDomain(),
            )
        }
        is HomeContentsResponse.ContentsResponse.GridContentsResponse -> {
            HomeContents.HomeItem.GridContents(
                header = this.header?.mapToDomain(),
                goods = this.contents.goods.map {
                    HomeContents.HomeItem.GridContents.GridGoods(
                        linkURL = it.linkURL,
                        thumbnailURL = it.thumbnailURL,
                        brandName = it.brandName,
                        price = it.price,
                        saleRate = it.saleRate,
                        hasCoupon = it.hasCoupon,
                    )
                },
                footer = this.footer?.mapToDomain(),
            )
        }
        is HomeContentsResponse.ContentsResponse.ScrollContentsResponse -> {
            HomeContents.HomeItem.ScrollContents(
                header = this.header?.mapToDomain(),
                goods = this.contents.goods.map {
                    HomeContents.HomeItem.ScrollContents.ScrollGoods (
                        linkURL = it.linkURL,
                        thumbnailURL = it.thumbnailURL,
                        brandName = it.brandName,
                        price = it.price,
                        saleRate = it.saleRate,
                        hasCoupon = it.hasCoupon,
                    )
                },
                footer = this.footer?.mapToDomain(),
            )
        }
        is HomeContentsResponse.ContentsResponse.StyleContentsResponse -> {
            HomeContents.HomeItem.StyleContents(
                header = this.header?.mapToDomain(),
                styles = this.contents.styles.map {
                    HomeContents.HomeItem.StyleContents.Styles(
                        linkURL = it.linkURL,
                        thumbnailURL = it.thumbnailURL
                    )
                },
                footer = this.footer?.mapToDomain(),
            )
        }
    }
}

fun HomeContentsResponse.HomeItemResponse.HeaderResponse.mapToDomain(): HomeContents.Header {
    return HomeContents.Header(
        title = this.title,
        linkURL = this.linkURL,
        iconURL = this.iconURL
    )
}

fun HomeContentsResponse.HomeItemResponse.FooterResponse.mapToDomain(): HomeContents.Footer {
    return HomeContents.Footer(
        title = this.title,
        type = HomeContents.Footer.FooterType.from(this.type),
        iconURL = this.iconURL
    )
}