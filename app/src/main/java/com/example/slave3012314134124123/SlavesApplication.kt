package com.example.slave3012314134124123

import android.app.Application
import com.vk.api.sdk.VK
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber


@HiltAndroidApp
class SlavesApplication : Application() {

    /*TODO Сделать рейтинг убрать баг
    *  dfСделать фио босса в профиле и текст фио слейва*/


    override fun onCreate() {
        super.onCreate()
        VK.initialize(this)
        Timber.plant(Timber.DebugTree())
    }
}