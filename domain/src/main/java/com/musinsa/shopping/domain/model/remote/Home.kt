package com.musinsa.shopping.domain.model.remote


data class Home(
    val data: List<HomeContents>,
) {

    sealed class HomeContents {

        data class Header(
            val title: String,
            val iconURL: String? = null,
            val linkURL: String? = null,
        )

        data class Footer(
            val title: String,
            val type: FooterType,
            val iconURL: String? = null,
        ) {
            enum class FooterType {
                MORE, REFRESH, NONE;

                companion object {
                    fun from(type: String?): FooterType = values().find { it.name == type } ?: NONE
                }
            }
        }

        data class BannersContents(
            val header: Header? = null,
            val banners: List<Banner>,
            val footer: Footer? = null
        ) : HomeContents() {
            data class Banner(
                val linkURL: String,
                val thumbnailURL: String,
                val title: String,
                val description: String,
                val keyword: String,
            )
        }

        data class GridContents(
            val header: Header? = null,
            val goods: List<GridGoods>,
            val footer: Footer? = null
        ) : HomeContents() {
            data class GridGoods(
                val linkURL: String,
                val thumbnailURL: String,
                val brandName: String,
                val price: Long,
                val saleRate: Int,
                val hasCoupon: Boolean,
            )
        }

        data class ScrollContents(
            val header: Header? = null,
            val goods: List<ScrollGoods>,
            val footer: Footer? = null
        ) : HomeContents() {
            data class ScrollGoods(
                val linkURL: String,
                val thumbnailURL: String,
                val brandName: String,
                val price: Long,
                val saleRate: Int,
                val hasCoupon: Boolean,
            )

        }

        data class StyleContents(
            val header: Header? = null,
            val styles: List<Styles>,
            val footer: Footer? = null
        ) : HomeContents() {
            data class Styles(
                val linkURL: String,
                val thumbnailURL: String
            )
        }

        object UnknownContents : HomeContents()
    }


}

