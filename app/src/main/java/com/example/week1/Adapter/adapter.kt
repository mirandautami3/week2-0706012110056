package com.example.week1.Adapter

import Interface.cardListener
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.week1.R
import com.example.week1.databinding.DataListBinding
import com.example.week1.model.Hewan


class adapter(val listDataHewan : ArrayList<Hewan>, val cardListener: cardListener) :
    RecyclerView.Adapter<adapter.viewHolder>() {



    class viewHolder(val itemView: View, val cardListener: cardListener) :
        RecyclerView.ViewHolder(itemView) {

        val binding = DataListBinding.bind(itemView)

        fun setData(data: Hewan) {
            binding.titlecard.text = data.nama
            if (data.imageUri!!.isNotEmpty()) {
                binding.imageView3.setImageURI(Uri.parse(data.imageUri))
            }
            itemView.setOnClickListener {
                cardListener.onCardClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.data_list, parent, false)
        return viewHolder(view, cardListener)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.setData(listDataHewan[position])
    }

    override fun getItemCount(): Int {
        return listDataHewan.size
    }
}