package org.dufa.dufa9596.ui

import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import org.dufa.dufa9596.BaseFragment
import org.dufa.dufa9596.R
import org.dufa.dufa9596.data.models.search.Data
import org.dufa.dufa9596.databinding.FragmentUserDetailsBinding
import org.dufa.dufa9596.utils.*
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class UserDetailsFragment :BaseFragment<FragmentUserDetailsBinding>() {


    override fun getFragmentView(): Int {
        return R.layout.fragment_user_details
    }

    override fun configUi() {
        if (arguments != null) {
            val userInfo: Data = requireArguments().getParcelable("memberInfo")!!
            binding.name.replaceFirstLC(userInfo.name)
            binding.phoneNumber.text = userInfo.phone
            binding.email.text = userInfo.email
            binding.address.replaceFirstLC(userInfo.address)
            binding.nid.text = userInfo.nid
            binding.gender.text = userInfo.gender
            binding.birthdate.text = userInfo.birthdate
            binding.department.text = userInfo.department
            binding.hall.text = userInfo.hall
            binding.bloodGroup.text = userInfo.bloodgroup
            binding.bloodGroup.setTextColor(ContextCompat.getColor(requireActivity(), R.color.text_red))
            binding.occupation.text = userInfo.occupation
            binding.district.text = userInfo.district

            if (userInfo.subscription == "none") {
                binding.status.text = "Inactive"
                binding.status.setTextColor(
                    ContextCompat.getColor(
                        requireActivity(),
                        R.color.text_red
                    )
                )
            } else {
                binding.status.text = "Active"
                binding.status.setTextColor(
                    ContextCompat.getColor(
                        requireActivity(),
                        R.color.green100
                    )
                )
            }


            binding.userProfilePic.show()
            binding.profilePicAB.hide()

            val profileImg = Constants.IMG_PREFIX + userInfo.imagePath


            Glide.with(requireActivity())
                .load(profileImg)
                .placeholder(R.drawable.avatar_placeholder)
                .into(binding.userProfilePic)

//            if (userInfo.imagePath == null) {
//                binding.userProfilePic.hide()
//                binding.profilePicAB.show()
//                binding.profilePicAB.text = userInfo.name?.let { it1 ->
//                    nameAbbreviationGenerator(
//                        it1
//                    )
//                }
//            } else {
//                binding.userProfilePic.show()
//                binding.profilePicAB.hide()
//
//                val profileImg = Constants.IMG_PREFIX + userInfo.imagePath
//
//
//                Glide.with(requireActivity())
//                    .load(profileImg)
//                    .placeholder(R.drawable.avatar_placeholder)
//                    .into(binding.userProfilePic)
//            }
        }

    }

    override fun setupNavigation() {

    }


}