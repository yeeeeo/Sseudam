package com.example.Sseudam

import User
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.Sseudam.databinding.ActivityMainBinding
import com.example.Sseudam.databinding.ActivityMainContentBinding



class UserListAdapter: ListAdapter<User, UserListAdapter.ViewHolder>(diffUtil) {
    inner class ViewHolder(private val binding: ActivityMainBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(user: User){
            binding.nameid.text = user.id
            binding.pointText.text = user.point


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ActivityMainBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object{
        val diffUtil = object: DiffUtil.ItemCallback<User>(){
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem.id == newItem.id
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem == newItem
            }
        }
    }
}
