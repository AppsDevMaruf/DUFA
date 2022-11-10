package com.marufalam.dufa.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.marufalam.dufa.databinding.MemberListRowBinding

import com.marufalam.dufa.models.dashboard.ResponseMemberList

class MemberListAdapter :
    ListAdapter<ResponseMemberList.User, MemberListAdapter.MemberListViewHolder>(MemberListDiffUtil()) {


    class MemberListViewHolder(val binding: MemberListRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ResponseMemberList.User) {
            binding.member = item
        }
    }


    class MemberListDiffUtil : DiffUtil.ItemCallback<ResponseMemberList.User>() {
        override fun areItemsTheSame(oldItem: ResponseMemberList.User, newItem:ResponseMemberList.User): Boolean {
            return oldItem== newItem
        }

        override fun areContentsTheSame(
            oldItem: ResponseMemberList.User,
            newItem: ResponseMemberList.User
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