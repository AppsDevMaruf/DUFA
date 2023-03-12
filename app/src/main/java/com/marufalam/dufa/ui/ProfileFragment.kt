package com.marufalam.dufa.ui

import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.marufalam.dufa.BaseFragment
import com.marufalam.dufa.R
import com.marufalam.dufa.data.models.getProfileInfo.Profile
import com.marufalam.dufa.data.models.search.Data
import com.marufalam.dufa.databinding.FragmentProfileBinding
import com.marufalam.dufa.utils.*


class ProfileFragment : BaseFragment<FragmentProfileBinding>() {
    private lateinit var userInfo: Profile
    override fun getFragmentView(): Int {
        return  R.layout.fragment_profile
    }

    override fun configUi() {
//        if (arguments != null) {
//            userInfo = requireArguments().getParcelable("userinfo")!!
//
//            binding.name.text = userInfo.name
//            binding.phoneNumber.text = userInfo.phone
//            binding.email.text = userInfo.email
//            binding.address.text = userInfo.address
//            binding.nid.text = userInfo.nid
//            binding.gender.text = userInfo.gender
//            binding.birthdate.text = userInfo.birthdate
//            binding.department.text = userInfo.department
//            binding.hall.text = userInfo.hall
//            binding.bloodGroup.text = userInfo.bloodgroup
//            binding.bloodGroup.setTextColor(ContextCompat.getColor(requireActivity(), R.color.text_red))
//            binding.occupation.text = userInfo.occupation
//            binding.district.text = userInfo.district
//
//            if (userInfo.subscription == "none") {
//                binding.status.text = "Inactive"
//                binding.status.setTextColor(
//                    ContextCompat.getColor(
//                        requireActivity(),
//                        R.color.text_red
//                    )
//                )
//            } else {
//                binding.status.text = "Active"
//                binding.status.setTextColor(
//                    ContextCompat.getColor(
//                        requireActivity(),
//                        R.color.green100
//                    )
//                )
//            }
//
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
//              /*   val url = GlideUrl(
//                     profileImg,
//                     GlideUtils.glideHeaders(tokenStoreManager.getToken(Constants.TOKEN))
//                 )*/
//
//                Glide.with(requireActivity())
//                    .load(profileImg)
//                    .into(binding.userProfilePic)
//            }
//        }

    }

    override fun setupNavigation() {
        binding.updateBtn.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_userUpdateFragment,)
        }

    }

    override fun binObserver() {

    }

}