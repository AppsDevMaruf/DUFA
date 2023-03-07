package com.marufalam.dufa.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.marufalam.dufa.data.models.search.Data
import com.marufalam.dufa.databinding.MemberListRowBinding
import com.marufalam.dufa.`interface`.MemberSelectListener
import com.marufalam.dufa.utils.*


class SearchMemberListAdapter() :
    PagingDataAdapter<Data, SearchMemberListAdapter.MemberListViewHolder>(comparator) {


    class MemberListViewHolder(val binding: MemberListRowBinding) :
        RecyclerView.ViewHolder(binding.root)


    companion object {

        val comparator = object : DiffUtil.ItemCallback<Data>() {
            override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
                return oldItem == newItem
            }
        }


    }

    override fun onBindViewHolder(holder: MemberListViewHolder, position: Int) {

        getItem(position).let {

            if (it != null) {
                holder.binding.name.text = it.name
                holder.binding.phoneNumber.text =it.phone
                holder.binding.bloodGroup.text =it.bloodgroup
                holder.binding.department.text =it.department

              /*  holder.itemView.setOnClickListener {_->
                    memberSelectListener.selectedMember(it)

                }*/



                if (it.imagePath!=null) {
                    holder.binding.userProfilePic.hide()
                    holder.binding.profilePicAB.show()
                    holder.binding.profilePicAB.text = nameAbbreviationGenerator(it.name)
                } else {
                    holder.binding.userProfilePic.show()
                    holder.binding.profilePicAB.hide()

                    val profileImg = Constants.IMG_PREFIX + it.imagePath

                   /* val url = GlideUrl(
                        profileImg,
                        GlideUtils.glideHeaders(tokenStoreManager.getToken(Constants.TOKEN))
                    )*/

                    Glide.with(holder.itemView.context)
                        .load(profileImg)
                        .into(holder.binding.userProfilePic)
                }

            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberListViewHolder {
        return MemberListViewHolder(
            MemberListRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }


}