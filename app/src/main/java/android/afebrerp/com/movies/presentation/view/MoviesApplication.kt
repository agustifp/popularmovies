/*
 * Copyright (c) 2018 FutureWorkshops. All rights reserved.
 */

package android.afebrerp.com.movies.presentation.view

import android.afebrerp.com.movies.presentation.koinInjector.generalModules
import android.app.Application
import org.koin.android.ext.android.startKoin
import org.koin.standalone.KoinComponent


class MoviesApplication : Application(), KoinComponent {

    override fun onCreate() {
        super.onCreate()
        startKoin(this, generalModules)
    }

}
