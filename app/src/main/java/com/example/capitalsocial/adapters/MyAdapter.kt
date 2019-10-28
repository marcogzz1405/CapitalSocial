package com.example.capitalsocial.adapters

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.capitalsocial.R
import com.example.capitalsocial.activities.DetailsActivity
import com.example.capitalsocial.models.CardViewData
import kotlinx.android.synthetic.main.content_cardview.view.*

class MyAdapter(val state: Array<CardViewData>, val context: Context): RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.content_cardview, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.image.setImageResource(state[position].image)
        holder.text.text = state[position].name
        holder.cardview.setOnClickListener {
            if (holder.image != null && holder.text != null){
                val intent = Intent(context, DetailsActivity::class.java)
                intent.putExtra("image", state[position].image)
                intent.putExtra("name", state[position].name)
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return state.size
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val image = view.ivImage
        val text = view.tvTitle
        val cardview = view.cardview
    }

}

