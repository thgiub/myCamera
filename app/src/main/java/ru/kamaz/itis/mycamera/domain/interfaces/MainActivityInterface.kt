package ru.kamaz.itis.mycamera.domain.interfaces

interface MainActivityInterface {
    interface View{
        fun setListeners()
        fun initVars()
        fun initContent()
        fun takePhoto()
        fun openGallery()

    }
    interface Presenter{
        fun init()
        fun addListeners()
        fun takePhoto()
        fun openGallery()
    }
}