package ru.kamaz.itis.mycamera.presentation

import ru.kamaz.itis.mycamera.domain.interfaces.MainActivityInterface

 class MainActivityPresenter(private val view: MainActivityInterface.View): MainActivityInterface.Presenter{

    override fun init() {
        view.initContent()
        view.initVars()
        view.setListeners()
    }

    override fun addListeners() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    override fun takePhoto(){
        view.takePhoto()


   }

     override fun openGallery() {
        view.openGallery()
     }
 }