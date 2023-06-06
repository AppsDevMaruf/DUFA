package org.dufa.dufa9596.ui

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import org.dufa.dufa9596.BaseFragment
import org.dufa.dufa9596.R
import org.dufa.dufa9596.data.models.register.RequestRegister
import org.dufa.dufa9596.databinding.FragmentSignUpBinding
import org.dufa.dufa9596.utils.*
import org.dufa.dufa9596.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SignUpFragment : BaseFragment<FragmentSignUpBinding>() {
    private var hasName = false
    private var hasEmail = false
    private var hasPhone = false
    private var hasAddress = false
    private var hasPassword = false
    private var hasConfirmPass = false
    private val authViewModel by viewModels<AuthViewModel>()


    override fun getFragmentView(): Int {
        return R.layout.fragment_sign_up

    }

    override fun configUi() {

        binding.nameEt.onTextChanged {
            if (it.trim().isEmpty()) {
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
        binding.emailEt.onTextChanged {
            if (it.trim().isEmpty()) {
                hasEmail = false
                binding.emailEt.error = "Email is required"
                enableBtn(false, binding.signUpBtn)
            } else {
                isValidEmail(it.trim())
                hasEmail = true
                if (hasName && hasEmail && hasPhone && hasAddress && hasPassword && hasConfirmPass) {


                    enableBtn(true, binding.signUpBtn)

                } else {

                    enableBtn(false, binding.signUpBtn)
                }
            }
        }
        binding.contactEt.onTextChanged {
            if (it.trim().isEmpty()) {
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
        binding.addressEt.onTextChanged {
            if (it.trim().isEmpty()) {
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
        binding.passwordInput.onTextChanged {
            if (it.trim().isEmpty()) {
                hasPassword = false
                binding.addressEt.error = "Password is required"
                enableBtn(false, binding.signUpBtn)
            } else {
                validatePassWord(it.trim())
                hasPassword = true
                if (hasName && hasEmail && hasPhone && hasAddress && hasPassword && hasConfirmPass) {


                    enableBtn(true, binding.signUpBtn)

                } else {

                    enableBtn(false, binding.signUpBtn)
                }
            }
        }
        binding.confPassword.onTextChanged {
            if (it.trim().isEmpty()) {
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
            binding.progressBar.gone()
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
                    binding.progressBar.show()

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