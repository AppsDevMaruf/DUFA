package com.marufalam.dufa.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import com.marufalam.dufa.R
import com.marufalam.dufa.`interface`.MemberSelectListener
import com.marufalam.dufa.databinding.MemberListRowBinding
import com.marufalam.dufa.models.dashboard.ResponseMemberList
class MemberListAdapter(
    var memberSelectListener: MemberSelectListener,private var memberModelArrayList: ArrayList<ResponseMemberList>,
    var context: Context
) :
    RecyclerView.Adapter<MemberListAdapter.ViewHolder>() {

    // creating a variable for array list and context.


    // method for filtering our recyclerview items.
    fun filterList(filterList: ArrayList<ResponseMemberList>) {
        // below line is to add our filtered
        // list in our course array list.
        memberModelArrayList = filterList
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // below line is to inflate our layout.
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.member_list_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // setting data to our views of recycler view.
        val model: ResponseMemberList =memberModelArrayList[position]
        Glide.with(holder.img.context)
            .load("")
            .placeholder(R.drawable.loadpreview)
            .into(holder.img)
        holder.name.text = model.users


        holder.itemView.setOnClickListener {
            memberSelectListener.selectedMember(model)

        }


    }

    override fun getItemCount(): Int {
        // returning the size of array list.
        return memberModelArrayList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // creating variables for our views.
        lateinit var name: TextView
        var bloodGroup: TextView
        var number: TextView
        var img: ShapeableImageView

        init {
            // initializing our views with their ids.
            name = itemView.findViewById(R.id.name)
            bloodGroup = itemView.findViewById(R.id.blood_group)
            number = itemView.findViewById(R.id.number)
            img = itemView.findViewById(R.id.profile_image)

        }
    }

    // creating a constructor for our variables.

}



    /*ListAdapter<ResponseMemberList, MemberListAdapter.MemberListViewHolder>(MemberListDiffUtil()) {


    class MemberListViewHolder(val binding: MemberListRowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ResponseMemberList) {
            binding.user = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberListViewHolder {
        val binding =
            MemberListRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MemberListViewHolder(binding)
    }


    override fun onBindViewHolder(holder: MemberListViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class MemberListDiffUtil : DiffUtil.ItemCallback<ResponseMemberList>() {
        override fun areItemsTheSame(
            oldItem: ResponseMemberList,
            newItem: ResponseMemberList
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: ResponseMemberList,
            newItem: ResponseMemberList
        ): Boolean {
            return oldItem == newItem
        }

    }*/

