package ru.oganesyan.artem.internetstore

import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.os.StrictMode.VmPolicy
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import ru.oganesyan.artem.internetstore.api.GetHomePageItems
import ru.oganesyan.artem.internetstore.downPageRecyclerView.BestSellerGridViewItems
import ru.oganesyan.artem.internetstore.categoryRecyclerView.CategoryRecyclerViewAdapter
import ru.oganesyan.artem.internetstore.categoryRecyclerView.CategoryRecyclerViewItems
import ru.oganesyan.artem.internetstore.downPageRecyclerView.DownPageRecyclerViewAdapter
import ru.oganesyan.artem.internetstore.downPageRecyclerView.hotsalesRecyclerView.HotSalesRecyclerViewItems


class MainActivity : AppCompatActivity() {
    // Disposable
    private var disposableHotSales: Disposable? = null
    private var disposableBestSeller: Disposable? = null

    // Элементы RecyclerView
    private lateinit var bestSellerItems: MutableList<BestSellerGridViewItems>
    private lateinit var hotSalesRecyclerViewItems: MutableList<HotSalesRecyclerViewItems>


    private var observableBestSeller = Observable.create { subscriber ->
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

    private val observableHotSales = Observable.create { subscriber ->
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val policy = ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)


        val builder = VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())


        val list = ArrayList<CategoryRecyclerViewItems>()
        list.add(
            CategoryRecyclerViewItems(
                "Phones",
                R.drawable.ellipse_activate,
                R.drawable.ic_baseline_phone_iphone_24
            )
        )
        list.add(
            CategoryRecyclerViewItems(
                "Computer",
                R.drawable.ellipse_not_activate,
                R.drawable.ic_baseline_computer_24
            )
        )
        list.add(
            CategoryRecyclerViewItems(
                "Health",
                R.drawable.ellipse_not_activate,
                R.drawable.ic_baseline_medical_services_24
            )
        )
        list.add(
            CategoryRecyclerViewItems(
                "Books",
                R.drawable.ellipse_not_activate,
                R.drawable.ic_baseline_library_books_24
            )
        )
        list.add(
            CategoryRecyclerViewItems(
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

        recyclerViewCategory.adapter = CategoryRecyclerViewAdapter(
            act = this,
            datas = list
        )

        disposableBestSeller = observableBestSeller
            .subscribe { bestSellerItemsIt ->
                bestSellerItems = bestSellerItemsIt()
                disposableHotSales = observableHotSales?.subscribe { hotSalesRecyclerViewItemsIt ->
                    hotSalesRecyclerViewItems = hotSalesRecyclerViewItemsIt()
                    Log.e("Successful", "SUCCESSFUL-POINT-1")
                    val downRecyclerView = findViewById<RecyclerView>(R.id.down_rv)
                    downRecyclerView.layoutManager = LinearLayoutManager(
                        applicationContext,
                        LinearLayoutManager.VERTICAL,
                        false
                    )
                    val gridLayoutManager = GridLayoutManager(this, 2)

                    gridLayoutManager.spanSizeLookup = object : SpanSizeLookup() {
                        override fun getSpanSize(position: Int) = if (position == 0) 2 else 1
                    }

                    downRecyclerView.layoutManager = gridLayoutManager

                    downRecyclerView.adapter = DownPageRecyclerViewAdapter(
                        this,
                        hotSalesRecyclerViewItems,
                        bestSellerItems
                    )
                }
            }
    }

    override fun onDestroy() {
        disposableHotSales?.dispose()
        disposableBestSeller?.dispose()
        super.onDestroy()
    }
}