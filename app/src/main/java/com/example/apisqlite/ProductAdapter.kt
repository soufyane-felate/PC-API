package com.example.apisqlite

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import java.time.format.DateTimeFormatter

class ProductAdapter(private val context: Context, private val productList: List<Product>) :
    RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productName: TextView = itemView.findViewById(R.id.tv_name)
        val productOs: TextView = itemView.findViewById(R.id.tv_os)
        val productPrice: TextView = itemView.findViewById(R.id.tv_price)
        val productImage: ImageView = itemView.findViewById(R.id.tv_img)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_item_view, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("NewApi")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = productList[position]

        holder.productName.text = product.nom
        holder.productPrice.text = product.prix
        Picasso.get().load(product.image).into(holder.productImage)

        val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy")
        val formattedDate = product.date_fin_os.format(formatter)
        holder.productOs.text = "End of support: $formattedDate (${product.differenceInYears()} years left)"
    }

    override fun getItemCount(): Int {
        return productList.size
    }
}
