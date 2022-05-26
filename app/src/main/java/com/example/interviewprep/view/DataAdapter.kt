package com.example.interviewprep.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.interviewprep.R
import com.example.interviewprep.databinding.LayoutRvItemBinding
import com.example.interviewprep.models.ComicsData
import com.example.interviewprep.models.Result

class DataAdapter : RecyclerView.Adapter<DataViewHolder>() {
    var comics= mutableListOf<Result>()
    fun setDataList(comics:List<Result>){
        println(comics)
        this.comics=comics.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val inflater=LayoutInflater.from(parent.context)
        val binding=LayoutRvItemBinding.inflate(inflater,parent, false)
        return DataViewHolder(binding)
    }

     override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
         val comic = comics[position]
         holder.binding.movieTitle.text = comic.name
         println(comic.name)
         Glide.with(holder.itemView.context)
             .load(comic.thumbnail.path).placeholder(R.drawable.cat_icon)
             .into(holder.binding.moviePoster)

     }

     override fun getItemCount(): Int {
         return comics.size
     }

}

class DataViewHolder(val binding: LayoutRvItemBinding) : RecyclerView.ViewHolder(binding.root) {}