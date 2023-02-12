package com.example.mylibrary

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mylibrary.databinding.BookItemBinding
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import java.text.FieldPosition


class MyAdapter(private val data:ArrayList<Book>, private val bookEvents :BookEvents ):RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    inner class MyViewHolder(val binding: BookItemBinding):RecyclerView.ViewHolder(binding.root) {

        fun bindData(position: Int) {
            Glide.with(binding.root.context)
                .load(data[position].imageUrl)
                .transform(RoundedCornersTransformation(16,4))
                .into(binding.itemImgMain)

            binding.itemTxtSubject.text = data[position].txtSubject
            binding.itemTxtWriter.text = data[position].txtWriter
            binding.itemTxtNationality.text = data[position].txtNational
            binding.itemTxtYear.text = data[position].txtPublishyear
            binding.itemRatingRatingBar.rating = data[position].rating

            itemView.setOnClickListener {
                bookEvents.onClickBook()
            }
            itemView.setOnLongClickListener {
                bookEvents.onClickLongBook( data[adapterPosition], adapterPosition)
                true
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = BookItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.bindData(position)

    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun addBook (newBook: Book) {
        data.add(0, newBook)
        notifyItemInserted(0)
    }

    fun removeBook (oldBook:Book, oldPosition: Int) {
        data.remove(oldBook)
        notifyItemRemoved(oldPosition)
    }



    fun setData(newList: ArrayList<Book>){

        data.clear()
        data.addAll( newList )

        notifyDataSetChanged()

    }

    interface BookEvents {
        fun onClickBook()
        fun onClickLongBook(book: Book , pos:Int)


    }
}