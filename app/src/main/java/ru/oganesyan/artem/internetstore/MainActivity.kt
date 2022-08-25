package ru.oganesyan.artem.internetstore

import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.os.StrictMode.VmPolicy
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import ru.oganesyan.artem.internetstore.api.GetHomePageItems
import ru.oganesyan.artem.internetstore.bestsellerGridView.BestSellerGridViewAdapter
import ru.oganesyan.artem.internetstore.bestsellerGridView.BestSellerGridViewItems
import ru.oganesyan.artem.internetstore.categoryRecyclerView.RecyclerViewAdapter
import ru.oganesyan.artem.internetstore.categoryRecyclerView.RecyclerViewItems
import ru.oganesyan.artem.internetstore.hotsalesRecyclerView.HotSalesRecyclerViewAdapter
import ru.oganesyan.artem.internetstore.hotsalesRecyclerView.HotSalesRecyclerViewItems


class MainActivity : AppCompatActivity() {
    private var disposableHotSales: Disposable? = null
    private var disposableBestSeller: Disposable? = null

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

        disposableHotSales = Observable.create { subscriber ->
            subscriber.onNext(GetHomePageItems().getHotSalesItems())
        }
            .subscribeOn(Schedulers.newThread()) // Выбираем ядро на котором будет выполняться наш observable
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

        disposableHotSales = Observable.create { subscriber ->
            subscriber.onNext(GetHomePageItems().getBestSellerItems())
        }
            .subscribeOn(Schedulers.newThread()) // Выбираем ядро на котором будет выполняться наш observable
            .map { mass ->
                {
                    val bestSellerGridViewItems: MutableList<BestSellerGridViewItems> =
                        mutableListOf()
                    for (i in mass) bestSellerGridViewItems += BestSellerGridViewItems(
                        i["id"].toString(),
                        i["is_favorites"].toBoolean(),
                        i["title"].toString(),
                        i["price_without_discount"].toString(),
                        i["discount_price"].toString(),
                        i["picture"].toString()
                    )
                    bestSellerGridViewItems
                }
            }
            .observeOn(AndroidSchedulers.mainThread()) // Выбираем ядро на котором будет выполняться наш код после observable
            .subscribe {    // Код, выполняющийся после observable
                try {
                    val gridview: GridView = findViewById(R.id.gridview)
                    gridview.adapter = BestSellerGridViewAdapter(
                        context = this,
                        datas = it()
                    )
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
    }

    override fun onDestroy() {
        disposableHotSales?.dispose()
        disposableBestSeller?.dispose()
        super.onDestroy()
    }
}