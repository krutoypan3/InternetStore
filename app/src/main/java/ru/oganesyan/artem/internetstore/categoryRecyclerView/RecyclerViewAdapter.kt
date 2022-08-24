package ru.oganesyan.artem.internetstore.categoryRecyclerView

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import ru.oganesyan.artem.internetstore.R

class RecyclerViewAdapter(private var act: Activity, private var datas: List<RecyclerViewItems>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mLayoutInflater: LayoutInflater = LayoutInflater.from(act.applicationContext);

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val recyclerViewItem = mLayoutInflater.inflate(R.layout.rv_category_item, parent, false)
        return RecyclerViewHolder(recyclerViewItem)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = datas[position]

        val circleImageView: ImageView = holder.itemView.findViewById(R.id.circleImageView)
        val mainTextView: TextView = holder.itemView.findViewById(R.id.mainTextView)
        val iconImageView: ImageView = holder.itemView.findViewById(R.id.iconImageView)

        mainTextView.text = item.mainText
        if (item.circleColor == R.drawable.ellipse_not_activate)
            mainTextView.setTextColor(act.getColor(R.color.black))


        circleImageView.background = ContextCompat.getDrawable(act, item.circleColor)
        iconImageView.background = ContextCompat.getDrawable(act, item.icon)
    }

    override fun getItemCount(): Int {
        return datas.size
    }
}