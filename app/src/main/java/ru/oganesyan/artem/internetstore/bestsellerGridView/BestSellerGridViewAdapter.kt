package ru.oganesyan.artem.internetstore.bestsellerGridView

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import ru.oganesyan.artem.internetstore.GlideCropImage
import ru.oganesyan.artem.internetstore.R


class BestSellerGridViewAdapter(
    private val context: Context,
    private var datas: List<BestSellerGridViewItems>
) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val grid: View = when (convertView) {
            null -> {
                LayoutInflater.from(context)
                    .inflate(R.layout.grid_view_cell, parent, false)
            }
            else -> convertView
        }

        val item = datas[position]

        val cellImage: ImageView = grid.findViewById(R.id.cell_image)
        Glide.with(context).load(item.picture).transform(GlideCropImage(cellImage, 1.0f)).into(cellImage)

        val cellPrice: TextView = grid.findViewById(R.id.cell_price)
        cellPrice.text = item.price_without_discount

        val cellPriceOld: TextView = grid.findViewById(R.id.cell_price_old)
        cellPriceOld.text = item.discount_price

        val cellName: TextView = grid.findViewById(R.id.cell_name)
        cellName.text = item.title

        val imageViewLike: ImageView = grid.findViewById(R.id.imageView_like)
        if (item.is_favorites)
            imageViewLike.setImageResource(R.drawable.heart_true)
        else
            imageViewLike.setImageResource(R.drawable.heart)

        return grid
    }

    override fun getCount(): Int {
        return datas.size
    }

    override fun getItem(position: Int): Any {
        return datas[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
}