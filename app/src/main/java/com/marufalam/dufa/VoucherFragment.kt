package com.marufalam.dufa

import android.app.Dialog
import android.app.ProgressDialog
import android.net.Uri
import android.util.Log
import android.view.Window
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import com.canhub.cropper.CropImage
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import com.marufalam.dufa.data.models.vouchers.RequestVoucher
import com.marufalam.dufa.databinding.FragmentVoucherBinding
import com.marufalam.dufa.utils.*
import com.marufalam.dufa.viewmodel.DashboardViewModel
import dagger.hilt.android.AndroidEntryPoint
import id.zelory.compressor.Compressor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileOutputStream


@AndroidEntryPoint
class VoucherFragment : BaseFragment<FragmentVoucherBinding>() {
    private val dashboardViewModel by viewModels<DashboardViewModel>()

    lateinit var dialog: ProgressDialog

    lateinit var voucherUri: Uri

    companion object {
        private val PERMISSIONS = arrayOf(
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.CAMERA
        )
    }

    private lateinit var permissionsRequest: ActivityResultLauncher<Array<String>>
    private val customCropImage = registerForActivityResult(CropImageContract()) {
        if (it !is CropImage.CancelledResult) {
            handleCropImageResult(it.uriContent!!)
        }
    }

    override fun getFragmentView(): Int {

        return R.layout.fragment_voucher
    }

    override fun configUi() {

        permissionsRequest = getPermissionsRequest()

        dialog = ProgressDialog(requireContext())
        dialog.setTitle("Voucher Uploading...")


        binding.birthdate.setOnClickListener {
            datePickerFun(binding.birthdate)
        }


        binding.uploadVoucher.setOnClickListener {

            startCameraWithoutUri(includeCamera = false, includeGallery = true)
        }


        binding.uploadVoucherBtn.setOnClickListener {

            upload(voucherUri)


        }

    }


    private fun getPermissionsRequest() =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            if (isAllPermissionsGranted(PERMISSIONS)) {
                showImagePickerDialog()
            } else {

            }
        }


    private fun handleCropImageResult(uri: Uri) {

        voucherUri = uri

        binding.uploadVoucher.setImageURI(uri)


    }

    private fun upload(fileUri: Uri) {
        val requestVoucher = RequestVoucher(
            binding.birthdate.text.toString(),
            binding.voucherAmount.text.toString(),
            binding.voucherNumber.text.toString()
        )
        dialog.setCancelable(false)
        dialog.show()


        val filesDir = requireActivity().filesDir
        val file = File(filesDir, "profile${System.currentTimeMillis()}.png")

        Log.i("uploadFileDir", "upload:$file")

        val inputStream = requireActivity().contentResolver.openInputStream(fileUri)
        val outputStream = FileOutputStream(file)
        inputStream!!.copyTo(outputStream)


        CoroutineScope(Dispatchers.IO).launch {
            val nFile = Compressor.compress(requireContext(), file)

         //   val requestBodys = nFile.asRequestBody("image/*".toMediaTypeOrNull())
//            val requestBody: RequestBody = MultipartBody.Builder()
//                .setType(MultipartBody.FORM)
//                .addFormDataPart(file.name, "file_name", requestBodys)
//                .addFormDataPart("date", "23-5-23")
//                .addFormDataPart("amount", "2000")
//                .addFormDataPart("voucher_number", "password33")
//                .build()
//
//
//            val part = MultipartBody.Part.createFormData("file_name", file.name, requestBody)


            val requestFile: RequestBody = RequestBody.create(
                "multipart/form-data".toMediaTypeOrNull(),
                nFile
            )

            val body = MultipartBody.Part.createFormData("file_name", file.name, requestFile)

            val amount: RequestBody =
                requestVoucher.amount.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val date: RequestBody =
                requestVoucher.date.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val voucherNumber: RequestBody = requestVoucher.voucher_number
                .toRequestBody("multipart/form-data".toMediaTypeOrNull())






            dashboardViewModel.uploadVoucherVm(
                part = body,
                amount = amount,
                date = date,
                voucher_number = voucherNumber
            )
        }


        inputStream.close()
        outputStream.close()

    }

    private fun showImagePickerDialog() {

        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.dialog_custom_layout)

        dialog.findViewById<TextView>(R.id.cancel_button).setOnClickListener {
            dialog.dismiss()
        }

        val galleryBtn = dialog.findViewById(R.id.galleryBtn) as TextView
        val cameraBtn = dialog.findViewById(R.id.cameraBtn) as TextView


        galleryBtn.setOnClickListener {
            startCameraWithoutUri(includeCamera = false, includeGallery = true)
            dialog.dismiss()
        }

        cameraBtn.setOnClickListener {
            startCameraWithoutUri(includeCamera = true, includeGallery = false)
            dialog.dismiss()
        }

        dialog.show()

    }

    private fun startCameraWithoutUri(includeCamera: Boolean, includeGallery: Boolean) {
        customCropImage.launch(
            CropImageContractOptions(
                uri = null,
                cropImageOptions = CropImageOptions(
                    imageSourceIncludeCamera = includeCamera,
                    imageSourceIncludeGallery = includeGallery,
                ),
            ),
        )
    }

    override fun binObserver() {

        dashboardViewModel.responseUploadVoucherVMLD.observe(viewLifecycleOwner) {

            Log.i("TAG", "raw: ${it.data?.data} ")

            when (it) {
                is NetworkResult.Error -> {

                    dialog.dismiss()
                    Log.i("TAG", "Error: ${it.data} ")
                    Log.i("TAG", "Error: ${it} ")
                }
                is NetworkResult.Loading -> {
                    dialog.show()
                }
                is NetworkResult.Success -> {
                    dialog.dismiss()
                    if (it.data?.success == true) {

                        Log.i("TAG", "success: $it ")

                    }


                }
            }


        }


    }


}