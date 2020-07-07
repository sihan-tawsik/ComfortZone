package com.sihan.comfortzone.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sihan.comfortzone.R
import com.sihan.comfortzone.domains.CartItem
import com.sihan.comfortzone.repositories.MyAdapter

class ShoppingCartAdapter(var context: Context, private var cartItems: List<CartItem>) :
    MyAdapter, RecyclerView.Adapter<ShoppingCartAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout = LayoutInflater.from(context).inflate(R.layout.row_cart_item, parent, false)
        return ViewHolder(layout)
    }

    override fun getItemCount(): Int = cartItems.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(cartItems[position])
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        fun bindItem(cartItem: CartItem){
            itemView.findViewById<TextView>(R.id.product_name).text = cartItem.product.name
            itemView.findViewById<TextView>(R.id.product_quantity).text = cartItem.quantity.toString()
        }
    }

    override fun setItem(items: List<*>) {
        @Suppress("UNCHECKED_CAST")
        cartItems = items as List<CartItem>
    }

    override fun dataChanged() {
        this.notifyDataSetChanged()
    }
}