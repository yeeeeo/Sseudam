package com.example.Sseudam

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.Sseudam.databinding.ActivityMantomanBinding


class ComunityListAdapter: ListAdapter<Comunity, ComunityListAdapter.ViewHolder>(diffUtil) {
    inner class ViewHolder(private val binding: ActivityMantomanBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(cm: Comunity){
            binding.Title.text = cm.title
            binding.Contents.text = cm.contents


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ActivityMantomanBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object{
        val diffUtil = object: DiffUtil.ItemCallback<Comunity>(){
            override fun areItemsTheSame(oldItem: Comunity, newItem: Comunity): Boolean {
                return oldItem.title == newItem.title
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: Comunity, newItem: Comunity): Boolean {
                return oldItem == newItem
            }
        }
    }
}
