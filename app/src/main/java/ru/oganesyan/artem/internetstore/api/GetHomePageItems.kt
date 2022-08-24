package ru.oganesyan.artem.internetstore.api

import org.json.JSONObject
import org.jsoup.Jsoup

class GetHomePageItems {
    private val url = "https://run.mocky.io/v3/654bd15e-b121-49ba-a588-960956b15175"

    fun getItems(): MutableList<MutableMap<String, String>> {
        val jsonString: String = Jsoup.connect(url).get().toString()
        val jsonObject = JSONObject(jsonString)
        val homeStoreListJson = jsonObject.getJSONArray("home_store")
        val homeStoreListItems: MutableList<MutableMap<String, String>> = mutableListOf()
        for (i in 0 until homeStoreListJson.length()) {
            val homeStoreListItem: MutableMap<String, String> = mutableMapOf()
            homeStoreListItem["id"] = homeStoreListJson.getJSONObject(i).getString("id")
            homeStoreListItem["is_new"] = homeStoreListJson.getJSONObject(i).getString("is_new")
            homeStoreListItem["title"] = homeStoreListJson.getJSONObject(i).getString("title")
            homeStoreListItem["subtitle"] = homeStoreListJson.getJSONObject(i).getString("subtitle")
            homeStoreListItem["picture"] = homeStoreListJson.getJSONObject(i).getString("picture")
            homeStoreListItem["is_buy"] = homeStoreListJson.getJSONObject(i).getString("is_buy")
            homeStoreListItems.add(homeStoreListItem)
        }
        return homeStoreListItems
    }
}