package org.dufa.dufa9596.adapter

import android.util.Log
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
import org.dufa.dufa9596.utils.gone
import org.dufa.dufa9596.utils.hide
import org.dufa.dufa9596.utils.loadImagesWithGlide
import org.dufa.dufa9596.utils.nameAbbreviationGenerator
import org.dufa.dufa9596.utils.show


class SearchMemberListAdapter(private val memberSelectListener: MemberSelectListener) :
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
                if (it.name.isNullOrBlank()) {
                    holder.binding.name.gone()
                } else {
                    holder.binding.name.text = it.name
                }
                if (it.phone.isNullOrBlank()) {
                    holder.binding.phoneNumber.gone()
                } else {
                    holder.binding.phoneNumber.text = it.phone
                }
                if (it.bloodgroup.isNullOrBlank()) {
                    holder.binding.bloodGroup.gone()
                } else {
                    holder.binding.bloodGroup.text = it.bloodgroup
                }
                if (it.department.isNullOrBlank()) {
                    holder.binding.department.gone()
                } else {
                    holder.binding.department.text = it.department
                }


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
                    holder.binding.userProfilePic.loadImagesWithGlide(profileImg)
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