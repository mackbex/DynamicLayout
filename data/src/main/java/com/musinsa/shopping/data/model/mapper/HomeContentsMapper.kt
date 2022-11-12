package com.musinsa.shopping.data.model.mapper

import com.musinsa.shopping.data.model.remote.*
import com.musinsa.shopping.domain.model.remote.*

fun HomeResponse.mapToDomain(): Home {
    return Home(
        data = this.data.map {
            it.mapToDomain()
        }
    )
}

fun HomeResponse.HomeContentsResponse.mapToDomain(): Home.HomeContents {
    return when(this.contents) {
        is HomeResponse.ContentsResponse.UnknownContentsResponse -> {
            Home.HomeContents.UnknownContents
        }
        is HomeResponse.ContentsResponse.BannerContentsResponse -> {
            Home.HomeContents.BannersContents(
                header = this.header?.mapToDomain(),
                banners = this.contents.banners.map {
                    Home.HomeContents.BannersContents.Banner(
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
        is HomeResponse.ContentsResponse.GridContentsResponse -> {
            Home.HomeContents.GridContents(
                header = this.header?.mapToDomain(),
                goods = this.contents.goods.map {
                    Home.HomeContents.GridContents.GridGoods(
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
        is HomeResponse.ContentsResponse.ScrollContentsResponse -> {
            Home.HomeContents.ScrollContents(
                header = this.header?.mapToDomain(),
                goods = this.contents.goods.map {
                    Home.HomeContents.ScrollContents.ScrollGoods (
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
        is HomeResponse.ContentsResponse.StyleContentsResponse -> {
            Home.HomeContents.StyleContents(
                header = this.header?.mapToDomain(),
                styles = this.contents.styles.map {
                    Home.HomeContents.StyleContents.Styles(
                        linkURL = it.linkURL,
                        thumbnailURL = it.thumbnailURL
                    )
                },
                footer = this.footer?.mapToDomain(),
            )
        }
    }
}

fun HomeResponse.HomeContentsResponse.HeaderResponse.mapToDomain(): Home.HomeContents.Header {
    return Home.HomeContents.Header(
        title = this.title,
        linkURL = this.linkURL,
        iconURL = this.iconURL
    )
}

fun HomeResponse.HomeContentsResponse.FooterResponse.mapToDomain(): Home.HomeContents.Footer {
    return Home.HomeContents.Footer(
        title = this.title,
        type = Home.HomeContents.Footer.FooterType.from(this.type),
        iconURL = this.iconURL
    )
}