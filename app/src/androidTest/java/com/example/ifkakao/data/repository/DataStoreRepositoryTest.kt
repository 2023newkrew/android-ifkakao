package com.example.ifkakao.data.repository

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import androidx.test.core.app.ApplicationProvider
import com.example.ifkakao.DATA_STORE_NAME_LIKE
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class DataStoreRepositoryTest {
    private val Context.dataStore by preferencesDataStore(DATA_STORE_NAME_LIKE)
    private val dataStoreRepositoryImpl = DataStoreRepositoryImpl(ApplicationProvider.getApplicationContext<Context>().dataStore)

    @Test
    fun testDataStoreIO() = runBlocking {
        dataStoreRepositoryImpl.putLike("jjoo.s", true)
        Assert.assertEquals(dataStoreRepositoryImpl.getLike("jjoo.s"), true)
    }
}