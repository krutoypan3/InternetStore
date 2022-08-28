package ru.oganesyan.artem.internetstore.downPageRecyclerView

import android.app.Activity
import android.graphics.Paint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import jp.wasabeef.glide.transformations.CropTransformation
import ru.oganesyan.artem.internetstore.R
import ru.oganesyan.artem.internetstore.categoryRecyclerView.CategoryRecyclerViewHolder
import ru.oganesyan.artem.internetstore.downPageRecyclerView.hotsalesRecyclerView.HotSalesRecyclerViewAdapter
import ru.oganesyan.artem.internetstore.downPageRecyclerView.hotsalesRecyclerView.HotSalesRecyclerViewItems

class DownPageRecyclerViewAdapter(
    private var act: Activity,
    private var hotSalesData: List<HotSalesRecyclerViewItems>,
    private var bestSellerData: List<BestSellerGridViewItems>
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemViewType(position: Int): Int = if (position == 0) 0 else 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val mLayoutInflater: LayoutInflater = LayoutInflater.from(act.applicationContext);

        val recyclerViewItem: View = if (viewType == 0)
            mLayoutInflater.inflate(R.layout.main_show_down_layout, parent, false)
        else
            mLayoutInflater.inflate(R.layout.grid_view_cell, parent, false)
        return CategoryRecyclerViewHolder(recyclerViewItem)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position == 0) {
            Log.e("Successful", "SUCCESSFUL-POINT-2")

            val recyclerViewHotSales = holder.itemView.findViewById<RecyclerView>(R.id.rv_hot_sales)
            recyclerViewHotSales.layoutManager = LinearLayoutManager(
                act, LinearLayoutManager.HORIZONTAL, false
            )

            recyclerViewHotSales.adapter = HotSalesRecyclerViewAdapter(
                act = act,
                datas = hotSalesData
            )
        } else {
            val item = bestSellerData[position - 1]

            val cellImage: ImageView = holder.itemView.findViewById(R.id.cell_image)
            Glide.with(holder.itemView.context).load(item.picture)
                .transform(CropTransformation(200, 250, CropTransformation.CropType.CENTER))
                .into(cellImage)

            val cellPrice: TextView = holder.itemView.findViewById(R.id.cell_price)
            cellPrice.text = "$${item.price_without_discount}"

            val cellPriceOld: TextView = holder.itemView.findViewById(R.id.cell_price_old)
            cellPriceOld.text = "$${item.discount_price}"
            cellPriceOld.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG;

            val cellName: TextView = holder.itemView.findViewById(R.id.cell_name)
            cellName.text = item.title

            val imageViewLike: ImageView = holder.itemView.findViewById(R.id.imageView_like)
            if (item.is_favorites)
                imageViewLike.setImageResource(R.drawable.heart_true)
            else
                imageViewLike.setImageResource(R.drawable.heart)
        }
    }

    override fun getItemCount(): Int {
        return 1 + bestSellerData.size
    }
}