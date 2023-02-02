package com.example.ifkakao.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.example.ifkakao.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreRepositoryImpl @Inject constructor(private val likeDataStore: DataStore<Preferences>) : DataStoreRepository {
    override suspend fun getLike(id: String): Boolean = likeDataStore.data
        .map { it[booleanPreferencesKey(id)] ?: false }.first()

    override suspend fun putLike(id: String, like: Boolean) {
        likeDataStore.edit { it[booleanPreferencesKey(id)] = like }
    }
}