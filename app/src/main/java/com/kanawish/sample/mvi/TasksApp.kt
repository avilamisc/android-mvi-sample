package com.kanawish.sample.mvi

import android.app.Application
import com.kanawish.sample.mvi.di.ToothpickLifecycle
import com.kanawish.sample.mvi.di.openApplicationScope
import com.kanawish.sample.mvi.model.repo.TaskRepo
import com.squareup.leakcanary.LeakCanary
import timber.log.Timber
import toothpick.Toothpick
import javax.inject.Inject

/**
 */
class TasksApp : Application() {
    @Inject lateinit var taskRepo: TaskRepo

    override fun onCreate() {
        super.onCreate()

        // Debug tooling init
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return
        }
        LeakCanary.install(this)

        // Logger init
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        Timber.i("%s %d", BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE)

        // DI Root Scope init
        Toothpick.inject(this, openApplicationScope(this))
        registerActivityLifecycleCallbacks(ToothpickLifecycle())
    }

}