package com.example.news

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.ButtonBarLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_news.view.*

class NewsListAdapter( private val listner :NewsItemcClicked): RecyclerView.Adapter<NewsViewHolder>(){

      val items :ArrayList<News> =ArrayList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {



        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news,parent,false )


        val viewHolder =  NewsViewHolder(view)
        view.setOnClickListener{

            listner.onItemClicked(items[viewHolder.adapterPosition])
           }
          return viewHolder



       }

    override fun getItemCount(): Int {
         return items.size
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val currentItem = items[position]
        holder.titelview.text= currentItem.tile
        holder.author.text=currentItem.author
        Glide.with(holder.itemView.context).load(currentItem.imageUrl).into(holder.image)
    }
     fun updatenews(updatenews:ArrayList<News>){
         items.clear()
         items.addAll(updatenews)
         notifyDataSetChanged()
             }
           }




          class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
          val titelview:TextView = itemView.findViewById(R.id.titel)
          val image:ImageView=itemView.findViewById(R.id.image)
          val author:TextView=itemView.findViewById(R.id.author)
         }


        interface NewsItemcClicked{
        fun onItemClicked(item:News)
          }






 