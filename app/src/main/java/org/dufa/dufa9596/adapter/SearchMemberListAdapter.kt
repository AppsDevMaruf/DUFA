package org.dufa.dufa9596.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.dufa.dufa9596.R
import org.dufa.dufa9596.data.models.search.Data
import org.dufa.dufa9596.databinding.MemberListRowBinding
import org.dufa.dufa9596.interfaces.MemberSelectListener
import org.dufa.dufa9596.utils.Constants
import org.dufa.dufa9596.utils.hide
import org.dufa.dufa9596.utils.nameAbbreviationGenerator
import org.dufa.dufa9596.utils.show


class SearchMemberListAdapter(private val memberSelectListener: MemberSelectListener) :
    PagingDataAdapter<Data, SearchMemberListAdapter.MemberListViewHolder>(orgparator) {


    class MemberListViewHolder(val binding: MemberListRowBinding) :
        RecyclerView.ViewHolder(binding.root)


    companion object {

        val orgparator = object : DiffUtil.ItemCallback<Data>() {
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
                holder.binding.phoneNumber.text = it.phone
                holder.binding.bloodGroup.text = it.bloodgroup
                holder.binding.department.text = it.department

                holder.itemView.setOnClickListener { _ ->
                    memberSelectListener.selectedMember(it)

                }



                if (it.imagePath == null) {
                    holder.binding.userProfilePic.hide()
                    holder.binding.profilePicAB.show()
                    holder.binding.profilePicAB.text = it.name?.let { it1 ->
                        nameAbbreviationGenerator(
                            it1
                        )
                    }
                } else {
                    holder.binding.userProfilePic.show()
                    holder.binding.profilePicAB.hide()

                    val profileImg = Constants.IMG_PREFIX + it.imagePath



                    Glide.with(holder.itemView.context)
                        .load(profileImg).placeholder(R.drawable.avatar_placeholder)
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