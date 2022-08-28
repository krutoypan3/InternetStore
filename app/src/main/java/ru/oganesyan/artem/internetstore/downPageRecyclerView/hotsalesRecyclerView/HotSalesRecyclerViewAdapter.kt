package ru.oganesyan.artem.internetstore.downPageRecyclerView.hotsalesRecyclerView

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import jp.wasabeef.glide.transformations.CropCircleWithBorderTransformation
import jp.wasabeef.glide.transformations.CropTransformation
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import ru.oganesyan.artem.internetstore.GlideCropImage
import ru.oganesyan.artem.internetstore.R
import ru.oganesyan.artem.internetstore.categoryRecyclerView.CategoryRecyclerViewHolder

class HotSalesRecyclerViewAdapter(
    private var act: Activity,
    private var datas: List<HotSalesRecyclerViewItems>
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mLayoutInflater: LayoutInflater = LayoutInflater.from(act.applicationContext);

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val recyclerViewItem = mLayoutInflater.inflate(R.layout.rv_hot_sales_item, parent, false)
        return HotSalesRecyclerViewHolder(recyclerViewItem)
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
            .transform(GlideCropImage(backgroundImage, 0.7f), RoundedCornersTransformation(8, 0))
            .into(backgroundImage)
    }

    override fun getItemCount(): Int {
        return datas.size
    }
}