package ru.kamaz.itis.mycamera.ui

import android.content.ContentValues
import android.hardware.Camera
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE
import android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import ru.kamaz.itis.mycamera.CameraPreview
import ru.kamaz.itis.mycamera.R
import ru.kamaz.itis.mycamera.domain.interfaces.MainActivityInterface
import ru.kamaz.itis.mycamera.presentation.MainActivityPresenter
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(), MainActivityInterface.View {
    private var mCamera: Camera? = null
    private var mPreview: CameraPreview?=null

    private lateinit var presenter: MainActivityInterface.Presenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter = MainActivityPresenter(this)
        presenter.init()
        mCamera= getCameraInstence()

        mPreview= mCamera?.let {
            CameraPreview(this, it)
        }

        mPreview?.also {
            preview.addView(it)
        }
        setListeners()
    }


    fun getCameraInstence():Camera?{
       return try {
           Camera.open()
       }catch (e:Exception){
           null
       }
    }

    override fun setListeners() {
       photo.setOnClickListener {
           presenter?.takePhoto()

       }
        bt_gallery.setOnClickListener {
            presenter.openGallery()
        }
    }

    override fun initVars() {

    }

    override fun initContent() {

    }

    override fun takePhoto() {

        val mPicture = Camera.PictureCallback { data, _ -> val pictureFile: File = getOutputMediaFile(MEDIA_TYPE_IMAGE) ?: run {
            Log.d(ContentValues.TAG, ("Error creating media file, check storage permissions"))
            return@PictureCallback
        }

            try {
                val fos = FileOutputStream(pictureFile)
                fos.write(data)
                fos.close()
            } catch (e: FileNotFoundException) {
                Log.d(ContentValues.TAG, "File not found: ${e.message}")
            } catch (e: IOException) {
                Log.d(ContentValues.TAG, "Error accessing file: ${e.message}")
            }
        }
        mCamera?.takePicture(null,null,mPicture)
    }

    override fun openGallery() {
        GalleryActivity()
    }

    private fun getOutputMediaFile(type: Int): File? {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        val mediaStorageDir = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
            "MyCameraApp"
        )
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        mediaStorageDir.apply {
            if (!exists()) {
                if (!mkdirs()) {
                    Log.d("MyCameraApp", "failed to create directory")
                    return null
                }
            }
        }
        // Create a media file name
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        return when (type) {
            MEDIA_TYPE_IMAGE -> {
                File("${mediaStorageDir.path}${File.separator}IMG_$timeStamp.jpg")
            }
            MEDIA_TYPE_VIDEO -> {
                File("${mediaStorageDir.path}${File.separator}VID_$timeStamp.mp4")
            }
            else -> null
        }
    }



}
