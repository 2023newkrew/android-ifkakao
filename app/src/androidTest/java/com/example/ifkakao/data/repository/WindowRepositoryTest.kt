package com.example.ifkakao.data.repository

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class WindowRepositoryTest {
    private val resources = ApplicationProvider.getApplicationContext<Context>().resources
    private val windowRepositoryImpl = WindowRepositoryImpl(resources)

    @Test
    fun testGetIsDualPane() = runBlocking {
        val isDualPane = windowRepositoryImpl.getIsDualPane()
//        Assert.assertEquals(isDualPane, false) // phone
         Assert.assertEquals(isDualPane, true) // tablet
    }
}