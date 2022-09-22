package com.marufalam.dufa.fragments.signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.passwordstrengthcalculator.PasswordStrengthCalculator
import com.marufalam.dufa.R
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.example.passwordstrengthcalculator.StrengthLevel
import com.marufalam.dufa.databinding.FragmentSignUpBinding


class SignUpFragment : Fragment() {
    private var color: Int = R.color.weak
    private lateinit var binding: FragmentSignUpBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignUpBinding.inflate(layoutInflater)
        val passwordStrengthCalculator = PasswordStrengthCalculator()
        val inputValidation = InputValidation()

        binding.nameEt.addTextChangedListener(inputValidation)


        //password validation

        binding.passwordInput. addTextChangedListener(passwordStrengthCalculator)

        // Observers
        passwordStrengthCalculator.strengthLevel.observe(viewLifecycleOwner, Observer {strengthLevel ->
            displayStrengthLevel(strengthLevel)
        })
        passwordStrengthCalculator.strengthColor.observe(requireActivity(), Observer {strengthColor ->
            color = strengthColor
        })

        passwordStrengthCalculator.lowerCase.observe(requireActivity(), Observer {value ->
            displayPasswordSuggestions(value, binding.lowerCaseImg, binding.lowerCaseTxt)
        })
        passwordStrengthCalculator.upperCase.observe(requireActivity(), Observer {value ->
            displayPasswordSuggestions(value, binding.upperCaseImg, binding.upperCaseTxt)
        })
        passwordStrengthCalculator.digit.observe(requireActivity(), Observer {value ->
            displayPasswordSuggestions(value, binding.digitImg, binding.digitTxt)
        })
        passwordStrengthCalculator.specialChar.observe(requireActivity(), Observer {value ->
            displayPasswordSuggestions(value, binding.specialCharImg, binding.specialCharTxt)
        })
        return binding.root
    }
    private fun displayPasswordSuggestions(value: Int, imageView: ImageView, textView: TextView) {
        if(value == 1){
            imageView.setColorFilter(ContextCompat.getColor(requireActivity(), R.color.bulletproof))
            textView.setTextColor(ContextCompat.getColor(requireActivity(), R.color.bulletproof))
        }else{
            imageView.setColorFilter(ContextCompat.getColor(requireActivity(), R.color.darkGray))
            textView.setTextColor(ContextCompat.getColor(requireActivity(), R.color.darkGray))
        }
    }

    private fun displayStrengthLevel(strengthLevel: StrengthLevel) {
        binding.signUpBtn.isEnabled = strengthLevel == StrengthLevel.BULLETPROOF

        binding.strengthLevelTxt.text = strengthLevel.name
        binding.strengthLevelTxt.setTextColor(ContextCompat.getColor(requireActivity(), color))
        binding.strengthLevelIndicator.setBackgroundColor(ContextCompat.getColor(requireActivity(), color))
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
    }


}