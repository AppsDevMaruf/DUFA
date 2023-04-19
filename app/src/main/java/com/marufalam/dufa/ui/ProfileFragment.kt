package com.marufalam.dufa.ui

import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.marufalam.dufa.BaseFragment
import com.marufalam.dufa.R
import com.marufalam.dufa.data.models.getProfileInfo.ResponseProfileInfo
import com.marufalam.dufa.databinding.FragmentProfileBinding
import com.marufalam.dufa.utils.*

class ProfileFragment : BaseFragment<FragmentProfileBinding>() {
    private lateinit var userInfo: ResponseProfileInfo

    lateinit var bundle: Bundle
    override fun getFragmentView(): Int {
        return R.layout.fragment_profile
    }

    override fun configUi() {
        if (arguments != null) {
            userInfo = requireArguments().getParcelable("userinfo")!!

            if (userInfo != null) {
                binding.name.text = userInfo.name
                binding.phoneNumber.text = userInfo.phone
                binding.email.text = userInfo.email
                binding.address.text = userInfo.address
                binding.nid.text = userInfo.nid
                binding.gender.text = userInfo.gender
                binding.birthdate.text = userInfo.birthdate
                binding.department.text = userInfo.department
                binding.hall.text = userInfo.hall
                binding.bloodGroup.text = userInfo.bloodgroup
                binding.bloodGroup.setTextColor(
                    ContextCompat.getColor(
                        requireActivity(),
                        R.color.text_red
                    )
                )
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

                if (userInfo.imagePath == null) {
                    binding.userProfilePic.hide()
                    binding.profilePicAB.show()
                    binding.profilePicAB.text = userInfo.name?.let { it1 ->
                        nameAbbreviationGenerator(
                            it1
                        )
                    }
                } else {
                    binding.userProfilePic.show()
                    binding.profilePicAB.hide()

                    val profileImg = Constants.IMG_PREFIX + userInfo.imagePath
                    Log.i("TAG", "profileImg: $profileImg ")
                    binding.userProfilePic.loadImagesWithGlide(profileImg)
                }
            }

            bundle = Bundle()
            bundle.putParcelable("userinfo", userInfo)


        }

    }

    override fun setupNavigation() {
        binding.updateBtn.setOnClickListener {


            findNavController().navigate(R.id.action_profileFragment_to_userUpdateFragment, bundle)
        }

    }

    override fun binObserver() {

    }

}