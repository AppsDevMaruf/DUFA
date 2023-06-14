package org.dufa.dufa9596.ui

import android.app.Dialog
import android.app.ProgressDialog
import android.net.Uri
import android.util.Log
import android.view.Window
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.canhub.cropper.CropImage
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import org.dufa.dufa9596.BaseFragment
import org.dufa.dufa9596.R
import org.dufa.dufa9596.data.models.vouchers.RequestVoucher
import org.dufa.dufa9596.databinding.FragmentVoucherBinding
import org.dufa.dufa9596.utils.*
import org.dufa.dufa9596.viewmodel.DashboardViewModel
import dagger.hilt.android.AndroidEntryPoint
import id.zelory.compressor.Compressor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileOutputStream


@AndroidEntryPoint
class VoucherFragment : BaseFragment<FragmentVoucherBinding>() {
    private val dashboardViewModel by viewModels<DashboardViewModel>()
    var hasVoucherNum = false
    var hasVoucherAmount = false
    var hasVoucherDate = false
    var hasVoucherImg = false

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

        binding.voucherNumber.onTextChanged {
            if (it.isNotBlank()) {
                hasVoucherNum = true
                enableBtn(
                    (hasVoucherNum && hasVoucherAmount && hasVoucherDate && hasVoucherImg),
                    binding.uploadVoucherBtn
                )
            }
        }
        binding.voucherAmount.onTextChanged {
            if (it.isNotBlank()) {
                hasVoucherAmount = true
                enableBtn(
                    (hasVoucherNum && hasVoucherAmount && hasVoucherDate && hasVoucherImg),
                    binding.uploadVoucherBtn
                )
            }
        }

        binding.voucherDate.setOnClickListener {
            datePickerFun(binding.voucherDate)
            hasVoucherDate = true
            enableBtn(
                (hasVoucherNum && hasVoucherAmount && hasVoucherDate && hasVoucherImg),
                binding.uploadVoucherBtn
            )
        }


        binding.uploadVoucher.setOnClickListener {

            startCameraWithoutUri(includeCamera = true, includeGallery = true)
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
        hasVoucherImg = true
        enableBtn(
            (hasVoucherNum && hasVoucherAmount && hasVoucherDate && hasVoucherImg),
            binding.uploadVoucherBtn
        )


    }

    private fun upload(fileUri: Uri) {
        val requestVoucher = RequestVoucher(
            binding.voucherDate.text.toString(),
            binding.voucherAmount.text.toString(),
            binding.voucherNumber.text.toString()
        )
        dialog.setCancelable(false)
        dialog.show()


        val filesDir = requireActivity().filesDir
        val file = File(filesDir, "voucher${System.currentTimeMillis()}.png")

        Log.i("uploadFileDir", "upload:$file")

        val inputStream = requireActivity().contentResolver.openInputStream(fileUri)
        val outputStream = FileOutputStream(file)
        inputStream!!.copyTo(outputStream)


        CoroutineScope(Dispatchers.IO).launch {
            val nFile = Compressor.compress(requireContext(), file)
            Log.i("TAG", "uploadREQ: $nFile")

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
            Log.i("TAG", "uploadREQ: $requestFile")

            val body = MultipartBody.Part.createFormData("file_name", file.name, requestFile)

            val amount: RequestBody =
                requestVoucher.amount.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val date: RequestBody =
                requestVoucher.date.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val voucherNumber: RequestBody = requestVoucher.voucher_number
                .toRequestBody("multipart/form-data".toMediaTypeOrNull())



            Log.i("TAG", "uploadImg: ${body.toString()}")


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
                }
                is NetworkResult.Loading -> {
                    dialog.show()
                }
                is NetworkResult.Success -> {
                    dialog.dismiss()
                    if (it.data?.success == true) {
                        findNavController().navigateUp()
                        findNavController().navigate(R.id.action_voucherFragment_to_voucherListFragment)

                    }


                }
            }


        }


    }


}