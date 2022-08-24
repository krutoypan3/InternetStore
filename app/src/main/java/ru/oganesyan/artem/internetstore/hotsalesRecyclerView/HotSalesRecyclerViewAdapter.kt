package ru.oganesyan.artem.internetstore.hotsalesRecyclerView

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.DrawableImageViewTarget
import ru.oganesyan.artem.internetstore.R
import ru.oganesyan.artem.internetstore.categoryRecyclerView.RecyclerViewHolder

class HotSalesRecyclerViewAdapter(private var act: Activity, private var datas: List<HotSalesRecyclerViewItems>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mLayoutInflater: LayoutInflater = LayoutInflater.from(act.applicationContext);

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val recyclerViewItem = mLayoutInflater.inflate(R.layout.rv_hot_sales_item, parent, false)
        return RecyclerViewHolder(recyclerViewItem)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = datas[position]

        val backgroundImage: ImageView = holder.itemView.findViewById(R.id.backgroundImage)
        val newNotify: TextView = holder.itemView.findViewById(R.id.new_notify)
        val nameHotSales: TextView = holder.itemView.findViewById(R.id.name_hot_sales)
        val subTextHotSales: TextView = holder.itemView.findViewById(R.id.sub_text_hot_sales)
        val btnBuyNow: TextView = holder.itemView.findViewById(R.id.btn_buy_now)


        if (!item.is_buy)
            btnBuyNow.visibility = View.INVISIBLE
        if (!item.is_new)
            newNotify.visibility = View.INVISIBLE

        nameHotSales.text = item.title
        subTextHotSales.text = item.subtitle

        Glide.with(act.applicationContext).load(item.picture)
            .into(backgroundImage)

    }

    override fun getItemCount(): Int {
        return datas.size
    }
}