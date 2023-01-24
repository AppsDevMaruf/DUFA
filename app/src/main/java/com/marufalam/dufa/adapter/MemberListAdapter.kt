package com.marufalam.dufa.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.marufalam.dufa.data.models.dashboard.Allmember
import com.marufalam.dufa.databinding.MemberListRowBinding

class MemberListAdapter(var context: Context, var data: MutableList<Allmember>) :
    RecyclerView.Adapter<MemberListAdapter.MemberListViewHolder>() {


    class MemberListViewHolder(val binding: MemberListRowBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberListViewHolder {
        val binding =
            MemberListRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MemberListViewHolder(binding)
    }

    // method for filtering our recyclerview items.
    fun filterList(filterList: ArrayList<Allmember>) {
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
            it.district.text = user.district


        }
    }


    override fun getItemCount(): Int {
        return data.size
    }


}