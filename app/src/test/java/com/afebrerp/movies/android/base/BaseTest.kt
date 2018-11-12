package com.afebrerp.movies.android.base

import androidx.annotation.CallSuper
import io.mockk.MockKAnnotations
import io.mockk.unmockkAll
import org.junit.After
import org.junit.Before

abstract class BaseTest {

    @CallSuper
    @Before
    open fun before() {
        MockKAnnotations.init(this)
    }

    @After
    fun after() =
            unmockkAll()
}