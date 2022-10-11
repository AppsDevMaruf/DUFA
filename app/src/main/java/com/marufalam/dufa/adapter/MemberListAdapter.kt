package com.marufalam.dufa.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.marufalam.dufa.databinding.MemberListRowBinding
import com.marufalam.dufa.models.MemberList

class MemberListAdapter :
    ListAdapter<MemberList.User, MemberListAdapter.MemberListViewHolder>(MemberListDiffUtil()) {


    class MemberListViewHolder(val binding: MemberListRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MemberList.User) {
            binding.member = item
        }
    }


    class MemberListDiffUtil : DiffUtil.ItemCallback<MemberList.User>() {
        override fun areItemsTheSame(oldItem: MemberList.User, newItem: MemberList.User): Boolean {
          return oldItem== newItem
        }

        override fun areContentsTheSame(
            oldItem: MemberList.User,
            newItem: MemberList.User
        ): Boolean {
            return oldItem== newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberListViewHolder {
        val binding = MemberListRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MemberListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MemberListViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }


}