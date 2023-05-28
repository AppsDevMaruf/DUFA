package com.marufalam.dufa.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.marufalam.dufa.data.models.dashboard.all_member.ResponseAllMember
import com.marufalam.dufa.databinding.MemberListRowBinding

class MemberListAdapter(var context: Context, var data: MutableList<ResponseAllMember.Data.Member>) :
    RecyclerView.Adapter<MemberListAdapter.MemberListViewHolder>() {


    class MemberListViewHolder(val binding: MemberListRowBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberListViewHolder {
        val binding =
            MemberListRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MemberListViewHolder(binding)
    }

    // method for filtering our recyclerview items.
    fun filterList(filterList: ArrayList<ResponseAllMember.Data.Member>) {
        // below line is to add our filtered
        // list in our course array list.
        data = filterList
        // below line is to notify our adapter
        // as change in recycler view data.
         notifyDataSetChanged()

        Log.i("TAG", "filterList:$data")


    }

    override fun onBindViewHolder(holder: MemberListViewHolder, position: Int) {

        val user = data[position]

        holder.binding.also {
            it.name.text = user.name
            it.bloodGroup.text = user.bloodgroup
            it.phoneNumber.text = user.phone
            it.department.text = user.department
        }
    }


    override fun getItemCount(): Int {
        return data.size
    }

    companion object {
        private val Comparator =
            object : DiffUtil.ItemCallback<ResponseAllMember.Data.Member>() {
                override fun areItemsTheSame(
                    oldItem: ResponseAllMember.Data.Member,
                    newItem: ResponseAllMember.Data.Member
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: ResponseAllMember.Data.Member,
                    newItem: ResponseAllMember.Data.Member
                ): Boolean {
                    return oldItem == newItem
                }


            }

    }


}