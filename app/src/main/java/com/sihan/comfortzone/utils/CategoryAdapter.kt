package com.sihan.comfortzone.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.sihan.comfortzone.R
import com.sihan.comfortzone.domains.Category
import com.sihan.comfortzone.repositories.MyAdapter
import com.sihan.comfortzone.repositories.OnCategoryListener

class CategoryAdapter(var context: Context, private var categories: List<Category>, private var onCategoryListener: OnCategoryListener) :
    MyAdapter, RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout = LayoutInflater.from(context).inflate(R.layout.row_categoty, parent, false)
        return ViewHolder(layout, onCategoryListener, categories)
    }

    override fun getItemCount(): Int = categories.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(categories[position], context)
    }

    class ViewHolder(
        view: View,
        private var onCategoryListener: OnCategoryListener,
        private var categories: List<Category>
    ) : RecyclerView.ViewHolder(view), View.OnClickListener {
        fun bindItem(category: Category, context: Context) {
            val imageView: ImageView = itemView.findViewById(R.id.category_image)
            val imageRef = Firebase.storage.reference.child("categories/"+category.imagePath)
            itemView.findViewById<TextView>(R.id.category_name).text = category.name
            itemView.setOnClickListener(this)
            GlideApp.with(context)
                .load(imageRef)
                .into(imageView)
        }

        override fun onClick(p0: View?) {
            onCategoryListener.onCategoryClicked(categories[adapterPosition])
        }
    }

    override fun setItem(items: List<*>) {
        @Suppress("UNCHECKED_CAST")
        categories = items as List<Category>
    }

    override fun dataChanged() {
        this.notifyDataSetChanged()
    }
}