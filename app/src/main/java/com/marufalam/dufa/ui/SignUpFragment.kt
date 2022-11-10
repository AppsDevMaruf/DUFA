package com.marufalam.dufa.ui

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.marufalam.dufa.R
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.marufalam.dufa.databinding.FragmentSignUpBinding
import com.marufalam.dufa.BaseFragment
import com.marufalam.dufa.models.register.RequestRegister
import com.marufalam.dufa.utils.*
import com.marufalam.dufa.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SignUpFragment : BaseFragment<FragmentSignUpBinding>() {
    var hasName = false
    var hasEmail = false
    var hasPhone = false
    var hasAddress = false
    var hasPassword = false
    var hasConfirmPass = false
    private val authViewModel by viewModels<AuthViewModel>()


    override fun getFragmentView(): Int {
        return R.layout.fragment_sign_up

    }

    override fun configUi() {

        binding.nameEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0.toString().trim().isEmpty()) {
                    hasName = false
                    binding.nameEt.error = "Name is required"
                    enableBtn(false, binding.signUpBtn)
                } else {
                    hasName = true
                    if (hasName && hasEmail && hasPhone && hasAddress && hasPassword && hasConfirmPass) {


                        enableBtn(true, binding.signUpBtn)

                    } else {

                        enableBtn(false, binding.signUpBtn)
                    }
                }

            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
        binding.emailEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0.toString().trim().isEmpty()) {
                    hasEmail = false
                    binding.emailEt.error = "Email is required"
                    enableBtn(false, binding.signUpBtn)
                } else {
                    isValidEmail(p0.toString().trim())
                    hasEmail = true
                    if (hasName && hasEmail && hasPhone && hasAddress && hasPassword && hasConfirmPass) {


                        enableBtn(true, binding.signUpBtn)

                    } else {

                        enableBtn(false, binding.signUpBtn)
                    }
                }

            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
        binding.contactEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0.toString().trim().isEmpty()) {
                    hasPhone = false
                    binding.contactEt.error = "Contract Number is required"
                    enableBtn(false, binding.signUpBtn)
                } else {
                    hasPhone = true
                    if (hasName && hasEmail && hasPhone && hasAddress && hasPassword && hasConfirmPass) {


                        enableBtn(true, binding.signUpBtn)

                    } else {

                        enableBtn(false, binding.signUpBtn)
                    }
                }

            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
        binding.addressEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0.toString().trim().isEmpty()) {
                    hasAddress = false
                    binding.addressEt.error = "Address is required"
                    enableBtn(false, binding.signUpBtn)
                } else {
                    hasAddress = true
                    if (hasName && hasEmail && hasPhone && hasAddress && hasPassword && hasConfirmPass) {


                        enableBtn(true, binding.signUpBtn)

                    } else {

                        enableBtn(false, binding.signUpBtn)
                    }
                }

            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
        binding.passwordInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().trim().isEmpty()) {
                    hasPassword = false
                    binding.addressEt.error = "Password is required"
                    enableBtn(false, binding.signUpBtn)
                } else {
                    validatePassWord(s.toString().trim())
                    hasPassword = true
                    if (hasName && hasEmail && hasPhone && hasAddress && hasPassword && hasConfirmPass) {


                        enableBtn(true, binding.signUpBtn)

                    } else {

                        enableBtn(false, binding.signUpBtn)
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }


        })
        binding.confPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0.toString().trim().isEmpty()) {
                    hasConfirmPass = false
                    binding.confPassword.error = "Confirm Password Required"
                    enableBtn(false, binding.signUpBtn)
                } else {
                    enableBtn(
                        isPasswordMatch(
                            binding.passwordInput.toString().trim(),
                            binding.confPassword.toString().trim()
                        ), binding.signUpBtn
                    )
                    hasConfirmPass = true
                    if (hasName && hasEmail && hasPhone && hasAddress && hasPassword && hasConfirmPass) {


                        enableBtn(true, binding.signUpBtn)

                    } else {

                        enableBtn(false, binding.signUpBtn)
                    }
                }

            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })



        binding.signUpBtn.setOnClickListener {
            authViewModel.registerUserVM(getRequestRegister())

        }
        binding.loginText.setOnClickListener {
           findNavController().popBackStack()


        }


    }

    override fun setupNavigation() {


    }

    private fun getRequestRegister(): RequestRegister {
        val name = binding.nameEt.text.toString().trim()
        val email = binding.emailEt.text.toString().trim()
        val phone = binding.contactEt.text.toString().trim()
        val address = binding.addressEt.text.toString().trim()
        val password = binding.passwordInput.text.toString().trim()
        val confPassword = binding.confPassword.text.toString().trim()
        return RequestRegister(name, email, phone, address, password, confPassword)
    }

    private fun validatePassWord(password: String) {

        val upperCase: Boolean = hasUpperCase(password)
        val lowerCase: Boolean = hasLowerCase(password)
        val hasDigit: Boolean = hasDigit(password)
        val isLength: Boolean = isLength8(password)
        val hasSpecialChar: Boolean = hasSpecailChar(password)


        if (upperCase) {
            binding.oneUpperCaseText.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.tick, 0, 0, 0
            )
            binding.oneUpperCaseText.setTextColor(resources.getColor(R.color.blue))

        } else {
            binding.oneUpperCaseText.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
            binding.oneUpperCaseText.setTextColor(resources.getColor(R.color.text_gray))
        }

        if (lowerCase) {
            binding.oneLowerCaseText.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.tick, 0, 0, 0
            )
            binding.oneLowerCaseText.setTextColor(resources.getColor(R.color.blue))

        } else {
            binding.oneLowerCaseText.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
            binding.oneLowerCaseText.setTextColor(resources.getColor(R.color.text_gray))
        }

        if (hasDigit) {
            binding.oneNumberText.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.tick, 0, 0, 0
            )
            binding.oneNumberText.setTextColor(resources.getColor(R.color.blue))

        } else {
            binding.oneNumberText.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
            binding.oneNumberText.setTextColor(resources.getColor(R.color.text_gray))
        }

        if (isLength) {
            binding.eightNumberTextLength.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.tick, 0, 0, 0
            )
            binding.eightNumberTextLength.setTextColor(resources.getColor(R.color.blue))

        } else {
            binding.eightNumberTextLength.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
            binding.eightNumberTextLength.setTextColor(resources.getColor(R.color.text_gray))
        }


        if (hasSpecialChar) {
            binding.specialCharacters.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.tick, 0, 0, 0
            )
            binding.specialCharacters.setTextColor(resources.getColor(R.color.blue))

        } else {
            binding.specialCharacters.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
            binding.specialCharacters.setTextColor(resources.getColor(R.color.text_gray))
        }




        if (hasDigit && lowerCase && upperCase && isLength && hasSpecialChar) {
            enableBtn(true, binding.signUpBtn)
        }


    }

    override fun binObserver() {
        authViewModel.registerResponseLiveDataVM.observe(viewLifecycleOwner, Observer {
            binding.progressBar.isVisible = false
            when (it) {
                is NetworkResult.Success -> {

                    toast("Registration Successful")

                    findNavController().popBackStack()
                }
                is NetworkResult.Error -> {
                    binding.signupErrorText.visibility = View.VISIBLE
                    binding.signupErrorText.text = it.message
                }
                is NetworkResult.Loading -> {
                    binding.progressBar.isVisible = true

                }
            }
        })

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