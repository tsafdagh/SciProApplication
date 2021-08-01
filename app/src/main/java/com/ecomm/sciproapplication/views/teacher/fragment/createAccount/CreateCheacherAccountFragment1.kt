package com.ecomm.sciproapplication.views.teacher.fragment.createAccount

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.ecomm.sciproapplication.R
import com.ecomm.sciproapplication.entities.school.School
import com.ecomm.sciproapplication.utils.showSnackBar
import com.ecomm.sciproapplication.views.teacher.fragment.createAccount.BottomSheet.schoolBtmSheet.SchoolBottomSheetFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.create_cheacher_account_fragment.*
import kotlinx.android.synthetic.main.layout_bottomsheet_img.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

private const val TAG = "CreateCheacherAccountFr"

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class CreateCheacherAccountFragment : Fragment() {

    companion object {
        fun newInstance() = CreateCheacherAccountFragment()
    }

    val viewModel by activityViewModels<CreateCheacherAccountViewModel>()

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<*>
    private val PERMISSION_REQUEST_CODE_PHOTO = 3
    private val IMAGE_CAPTURE_CODE = 1001
    private val RC_SELECT_IMAGE = 1023
    private val PERMISSION_REQUEST_CODE_GALLERY = 4


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.create_cheacher_account_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        id_creation_loading.visibility = View.GONE
        id_imag_progess.visibility = View.GONE
        id_click_to_define.visibility = View.GONE

        configureOnClickListener()
        bottomSheetBehavior = BottomSheetBehavior.from(id_camera_gallery_bottomsheet)

    }


    private fun showSelectedSchool(schoolList: MutableList<School>) {
        val arrayAdapter = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_list_item_1,
            android.R.id.text1,
            schoolList.map { it.name })
        id_selected_scholls.adapter = arrayAdapter
    }

    private fun configureOnClickListener() {
        id_uer_image_profil.setOnClickListener {
            if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED)
            } else {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED)
            }
        }

        id_chat_ib_gallery_select.setOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            if (hasNoGalleryPermissions()) {
                requestGalleryPermissions()
            } else {
                openGallerie()
            }
        }

        id_chat_ib_camera_select.setOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            if (hasNoCameraPermissions()) {
                requestCameraPermissions()
            } else {
                openCamera()
            }
        }

        id_select_your_school.setOnClickListener {
            val bottomSheet = SchoolBottomSheetFragment(onComplete = {
                viewModel.schoolList.add(it)
                viewModel.schoolList.distinct()
                showSelectedSchool(viewModel.schoolList)

            })

            bottomSheet.show(childFragmentManager, "")
        }

        id_next_button.setOnClickListener {

            viewModel.name = id_name.text.toString()
            viewModel.firstName = id_surname.text.toString()
            viewModel.email = id_email.text.toString()
            viewModel.registrationNumber = id_matricule.text.toString()

            if (fieldValid()) {
                Navigation.findNavController(requireView())
                    .navigate(R.id.action_createCheacherAccountFragment_to_createTeacherAccountFragment2)
            }
        }

    }

    private fun fieldValid() =
        (checkName() && checkFirstName() && checkSchool() && checkMatricule())


    private fun checkName(): Boolean {
        return if (id_name.text.isNotEmpty()) {
            true
        } else {
            id_name.error = getString(R.string.enter_name)
            false
        }
    }

    private fun checkFirstName(): Boolean {
        return if (id_surname.text.isNotEmpty()) {
            true
        } else {
            id_surname.error = getString(R.string.enter_first_name)
            false
        }
    }

    private fun checkMatricule(): Boolean {
        return if (id_matricule.text.isNotEmpty()) {
            true
        } else {
            id_matricule.error = getString(R.string.enter_matricule_name)
            false
        }
    }

    private fun checkSchool(): Boolean {
        return if (viewModel.schoolList.isNotEmpty()) {
            true
        } else {
            Toast.makeText(requireContext(), "Please select your school", Toast.LENGTH_SHORT).show()
            false
        }
    }

    private var imageUri: Uri? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SELECT_IMAGE && resultCode == Activity.RESULT_OK &&
            data != null && data.data != null
        ) {
            imageUri = data.data!!

            showSelectedImages()

        } else if (requestCode == IMAGE_CAPTURE_CODE && resultCode == Activity.RESULT_OK) {
            showSelectedImages()
        }
    }

    private fun showSelectedImages() {
        Glide.with(id_uer_image_profil.context)
            .load(imageUri)
            .transform(CircleCrop())
            .error(R.drawable.circle_account)
            .into(id_uer_image_profil)

        uploadUserImage()
    }

    private fun hasNoCameraPermissions(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.CAMERA
        ) != PackageManager.PERMISSION_GRANTED
    }

    private fun hasNoGalleryPermissions(): Boolean {
        return (ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) != PackageManager.PERMISSION_GRANTED)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_REQUEST_CODE_PHOTO -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Log.i(TAG, "Permission has been denied by user")
                } else {
                    Log.i(TAG, "Permission has been granted by user")
                    openCamera()
                }
            }

            PERMISSION_REQUEST_CODE_GALLERY -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Log.i(TAG, "Permission has been denied by user")
                } else {
                    Log.i(TAG, "Permission has been granted by user")
                    openGallerie()
                }
            }
        }
    }

    private val galleryPermissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
    private fun requestGalleryPermissions() {
        requestPermissions(galleryPermissions, PERMISSION_REQUEST_CODE_GALLERY)
    }

    private val cameraPermissions = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )

    private fun requestCameraPermissions() {
        requestPermissions(cameraPermissions, PERMISSION_REQUEST_CODE_PHOTO)
    }

    private fun openCamera() {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "New PictureGroup")
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera")
        imageUri = requireContext().contentResolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            values
        )
        //camera intent
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)

        cameraLauncher.launch(cameraIntent)

        //  startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE)
    }

    var cameraLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // There are no request codes
                val data: Intent? = result.data
                showSelectedImages()
            }
        }

    var galleryLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // There are no request codes
                val data: Intent? = result.data
                imageUri = data?.data!!
                showSelectedImages()
            }
        }

    private fun openGallerie() {
        val intent = Intent().apply {
            type = "image/*"
            action = Intent.ACTION_GET_CONTENT
            putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("image/jpeg", "image/png"))
        }

        Intent.createChooser(intent, "Selectionnez une image")
        galleryLauncher.launch(Intent.createChooser(intent, "Selectionnez une image"))
    }

    private fun uploadUserImage() {
        requireView().showSnackBar(getString(R.string.upload_image_progress))
        id_imag_progess.visibility = View.VISIBLE

        id_click_to_define.visibility = if (imageUri != null) {
            View.GONE
        } else {
            View.VISIBLE
        }

        viewModel.uploadUserImage(imageUri!!, addImageToListOfImages = { url: String ->
            requireView().showSnackBar(getString(R.string.upload_image_finished))
            viewModel.imageUrl = url
            id_imag_progess.visibility = View.GONE
        })
    }

}