package com.musinsa.shopping.data.model.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject

@Serializable
data class HomeContentsResponse(
    @SerialName("data") val data: List<HomeItemResponse>,
) {

    @Serializable
    data class HomeItemResponse(

        @SerialName("header")
        val header: HeaderResponse? = null,
        @SerialName("contents")
        val contents: ContentsResponse,
        @SerialName("footer")
        val footer: FooterResponse? = null
    ) {

        @Serializable
        data class HeaderResponse(
            @SerialName("title")
            val title: String,
            @SerialName("iconURL")
            val iconURL: String? = null,
            @SerialName("linkURL")
            val linkURL: String? = null,
        )

        @Serializable
        data class FooterResponse(
            @SerialName("title")
            val title: String,
            @SerialName("type")
            val type: String,
            @SerialName("iconURL")
            val iconURL: String? = null,
        )
    }

    @Serializable(with = ProjectSerializer::class)
    sealed class ContentsResponse {
        @Serializable
        data class BannerContentsResponse(
            @SerialName("banners")
            val banners: List<BannerResponse>
        ) : ContentsResponse() {
            @Serializable
            data class BannerResponse(
                @SerialName("linkURL")
                val linkURL: String,
                @SerialName("thumbnailURL")
                val thumbnailURL: String,
                @SerialName("title")
                val title: String,
                @SerialName("description")
                val description: String,
                @SerialName("keyword")
                val keyword: String,
            )
        }

        @Serializable
        data class GridContentsResponse(
            @SerialName("goods")
            val goods: List<GridGoodsResponse>,
        ) : ContentsResponse() {
            @Serializable
            data class GridGoodsResponse(
                @SerialName("linkURL")
                val linkURL: String,
                @SerialName("thumbnailURL")
                val thumbnailURL: String,
                @SerialName("brandName")
                val brandName: String,
                @SerialName("price")
                val price: Long,
                @SerialName("saleRate")
                val saleRate: Int,
                @SerialName("hasCoupon")
                val hasCoupon: Boolean,
            )
        }

        @Serializable
        data class ScrollContentsResponse(
            @SerialName("goods")
            val goods: List<ScrollGoodsResponse>,
        ) : ContentsResponse() {
            @Serializable
            data class ScrollGoodsResponse(
                @SerialName("linkURL")
                val linkURL: String,
                @SerialName("thumbnailURL")
                val thumbnailURL: String,
                @SerialName("brandName")
                val brandName: String,
                @SerialName("price")
                val price: Long,
                @SerialName("saleRate")
                val saleRate: Int,
                @SerialName("hasCoupon")
                val hasCoupon: Boolean,
            )
        }

        @Serializable
        data class StyleContentsResponse(
            @SerialName("styles")
            val styles: List<StylesResponse>,
        ) : ContentsResponse() {
            @Serializable
            data class StylesResponse(
                @SerialName("linkURL")
                val linkURL: String,
                @SerialName("thumbnailURL")
                val thumbnailURL: String
            )
        }

        @Serializable
        object UnknownContentsResponse : ContentsResponse()

    }
}


object ProjectSerializer :
    JsonContentPolymorphicSerializer<HomeContentsResponse.ContentsResponse>(HomeContentsResponse.ContentsResponse::class) {
    override fun selectDeserializer(element: JsonElement) =
        when (element.jsonObject["type"]?.toString()) {
            "\"BANNER\"" -> {
                HomeContentsResponse.ContentsResponse.BannerContentsResponse.serializer()
            }
            "\"GRID\"" -> {
                HomeContentsResponse.ContentsResponse.GridContentsResponse.serializer()
            }
            "\"SCROLL\"" -> {
                HomeContentsResponse.ContentsResponse.ScrollContentsResponse.serializer()
            }
            "\"STYLE\"" -> {
                HomeContentsResponse.ContentsResponse.StyleContentsResponse.serializer()
            }
            else -> {
                HomeContentsResponse.ContentsResponse.UnknownContentsResponse.serializer()
            }
        }
}





