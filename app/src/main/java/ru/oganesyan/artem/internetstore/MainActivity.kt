package ru.oganesyan.artem.internetstore

import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.os.StrictMode.VmPolicy
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.oganesyan.artem.internetstore.categoryRecyclerView.RecyclerViewAdapter
import ru.oganesyan.artem.internetstore.categoryRecyclerView.RecyclerViewItems
import ru.oganesyan.artem.internetstore.hotsalesRecyclerView.HotSalesRecyclerViewAdapter
import ru.oganesyan.artem.internetstore.hotsalesRecyclerView.HotSalesRecyclerViewItems


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val policy = ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)


        val builder = VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())


        val list = ArrayList<RecyclerViewItems>()
        list.add(
            RecyclerViewItems(
                "Phones",
                R.drawable.ellipse_activate,
                R.drawable.ic_baseline_phone_iphone_24
            )
        )
        list.add(
            RecyclerViewItems(
                "Computer",
                R.drawable.ellipse_not_activate,
                R.drawable.ic_baseline_computer_24
            )
        )
        list.add(
            RecyclerViewItems(
                "Health",
                R.drawable.ellipse_not_activate,
                R.drawable.ic_baseline_medical_services_24
            )
        )
        list.add(
            RecyclerViewItems(
                "Books",
                R.drawable.ellipse_not_activate,
                R.drawable.ic_baseline_library_books_24
            )
        )
        list.add(
            RecyclerViewItems(
                "Android phones",
                R.drawable.ellipse_not_activate,
                R.drawable.ic_baseline_phone_android_24
            )
        )

        val recyclerViewCategory = findViewById<RecyclerView>(R.id.rv_category)


        recyclerViewCategory.layoutManager = LinearLayoutManager(
            applicationContext,
            LinearLayoutManager.HORIZONTAL,
            false
        )

        recyclerViewCategory.adapter = RecyclerViewAdapter(
            act = this,
            datas = list
        )

        val observable = Observable.create { subscriber ->
            val homeStoreListItems: MutableList<MutableMap<String, String>> = mutableListOf()
            val homeStoreListItem: MutableMap<String, String> = mutableMapOf()
            homeStoreListItem["id"] = "12"
            homeStoreListItem["is_new"] = "true"
            homeStoreListItem["title"] = "Title"
            homeStoreListItem["subtitle"] = "This is subtitle"
            homeStoreListItem["picture"] = "https://avatars.githubusercontent.com/u/65137814?v=4"
            homeStoreListItem["is_buy"] = "true"
            homeStoreListItems.add(homeStoreListItem)
            subscriber.onNext(homeStoreListItems)
//            subscriber.onNext(GetHomePageItems().getItems())
        }

        val disposable = observable
            .subscribeOn(Schedulers.io()) // Выбираем ядро на котором будет выполняться наш observable
            .map { mass ->
                {
                    val hotSalesRecyclerViewItems: MutableList<HotSalesRecyclerViewItems> =
                        mutableListOf()
                    for (i in mass) hotSalesRecyclerViewItems += HotSalesRecyclerViewItems(
                        i["id"].toString(),
                        i["is_new"].toBoolean(),
                        i["title"].toString(),
                        i["subtitle"].toString(),
                        i["picture"].toString(),
                        i["is_buy"].toBoolean(),
                    )
                    hotSalesRecyclerViewItems
                }
            }
            .observeOn(AndroidSchedulers.mainThread()) // Выбираем ядро на котором будет выполняться наш код после observable
            .subscribe {    // Код, выполняющийся после observable
                try {
                    val recyclerViewHotSales = findViewById<RecyclerView>(R.id.rv_hot_sales)
                    recyclerViewHotSales.layoutManager = LinearLayoutManager(
                        applicationContext, LinearLayoutManager.HORIZONTAL, false
                    )
                    recyclerViewHotSales.adapter = HotSalesRecyclerViewAdapter(
                        act = this,
                        datas = it()
                    )
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
    }
}