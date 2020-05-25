package ru.kamaz.itis.mycamera.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import ru.kamaz.itis.mycamera.R

class GalleryActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()


    }
}