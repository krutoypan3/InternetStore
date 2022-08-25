package ru.oganesyan.artem.internetstore.api

import org.json.JSONObject
import org.jsoup.Jsoup

class GetHomePageItems {
    private val url = "https://run.mocky.io/v3/654bd15e-b121-49ba-a588-960956b15175"

    private fun getJsonFromUrl(): JSONObject =
        JSONObject(Jsoup.connect(url).ignoreContentType(true).get().body().text())

    fun getHotSalesItems(): MutableList<MutableMap<String, String>> {
        val jsonObject = getJsonFromUrl()
        val homeStoreListJson = jsonObject.getJSONArray("home_store")
        val homeStoreListItems: MutableList<MutableMap<String, String>> = mutableListOf()
        for (i in 0 until homeStoreListJson.length()) {
            val homeStoreListItem: MutableMap<String, String> = mutableMapOf()
            homeStoreListItem["id"] = homeStoreListJson.getJSONObject(i).getString("id")

            if (homeStoreListJson.getJSONObject(i).has("is_new"))
                homeStoreListItem["is_new"] = homeStoreListJson.getJSONObject(i).getString("is_new")
            else
                homeStoreListItem["is_new"] = "false"

            homeStoreListItem["title"] = homeStoreListJson.getJSONObject(i).getString("title")
            homeStoreListItem["subtitle"] = homeStoreListJson.getJSONObject(i).getString("subtitle")
            homeStoreListItem["picture"] = homeStoreListJson.getJSONObject(i).getString("picture")
            if (homeStoreListJson.getJSONObject(i).has("is_buy"))
                homeStoreListItem["is_buy"] = homeStoreListJson.getJSONObject(i).getString("is_buy")
            else
                homeStoreListItem["is_buy"] = "false"
            homeStoreListItems.add(homeStoreListItem)
        }
        return homeStoreListItems
    }

    fun getBestSellerItems(): MutableList<MutableMap<String, String>> {
        val jsonObject = getJsonFromUrl()
        val bestSellerListJson = jsonObject.getJSONArray("best_seller")
        val bestSellerListItems: MutableList<MutableMap<String, String>> = mutableListOf()
        for (i in 0 until bestSellerListJson.length()) {
            val bestSellerListItem: MutableMap<String, String> = mutableMapOf()
            bestSellerListItem["id"] = bestSellerListJson.getJSONObject(i).getString("id")
            bestSellerListItem["is_favorites"] = bestSellerListJson.getJSONObject(i).getString("is_favorites")
            bestSellerListItem["title"] = bestSellerListJson.getJSONObject(i).getString("title")
            bestSellerListItem["price_without_discount"] = bestSellerListJson.getJSONObject(i).getString("price_without_discount")
            bestSellerListItem["discount_price"] = bestSellerListJson.getJSONObject(i).getString("discount_price")
            bestSellerListItem["picture"] = bestSellerListJson.getJSONObject(i).getString("picture")
            bestSellerListItems.add(bestSellerListItem)
        }
        return bestSellerListItems
    }
}