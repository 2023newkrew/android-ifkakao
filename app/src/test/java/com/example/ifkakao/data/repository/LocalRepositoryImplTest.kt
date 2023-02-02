package com.example.ifkakao.data.repository

import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test


object MockEditor1 : Editor {
    override fun putString(key: String?, value: String?): Editor {
        return this
    }

    override fun putStringSet(key: String?, values: MutableSet<String>?): Editor {
        return this
    }

    override fun putInt(key: String?, value: Int): Editor {
        return this
    }

    override fun putLong(key: String?, value: Long): Editor {
        return this
    }

    override fun putFloat(key: String?, value: Float): Editor {
        return this
    }

    override fun putBoolean(key: String?, value: Boolean): Editor {
        return this
    }

    override fun remove(key: String?): Editor {
        return this
    }

    override fun clear(): Editor {
        return this
    }

    override fun commit(): Boolean {
        return true
    }

    override fun apply() {
        return
    }
}


object MockSharedPreference1 : SharedPreferences {
    override fun getAll(): MutableMap<String, *> {
        return mutableMapOf<String, String>()
    }

    override fun getString(key: String?, defValue: String?): String? {
        return ""
    }

    override fun getStringSet(key: String?, defValues: MutableSet<String>?): MutableSet<String>? {
        return setOf("1", "2", "3").toMutableSet()
    }

    override fun getInt(key: String?, defValue: Int): Int {
        return 1
    }

    override fun getLong(key: String?, defValue: Long): Long {
        return 1
    }

    override fun getFloat(key: String?, defValue: Float): Float {
        return 1f
    }

    override fun getBoolean(key: String?, defValue: Boolean): Boolean {
        return true
    }

    override fun contains(key: String?): Boolean {
        return true
    }

    override fun edit(): SharedPreferences.Editor {
        return MockEditor1
    }

    override fun registerOnSharedPreferenceChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener?) {
        return
    }

    override fun unregisterOnSharedPreferenceChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener?) {
        return
    }
}

object MockEditor2 : Editor {
    override fun putString(key: String?, value: String?): Editor {
        return this
    }

    override fun putStringSet(key: String?, values: MutableSet<String>?): Editor {
        throw Exception("mock error")
    }

    override fun putInt(key: String?, value: Int): Editor {
        return this
    }

    override fun putLong(key: String?, value: Long): Editor {
        return this
    }

    override fun putFloat(key: String?, value: Float): Editor {
        return this
    }

    override fun putBoolean(key: String?, value: Boolean): Editor {
        return this
    }

    override fun remove(key: String?): Editor {
        return this
    }

    override fun clear(): Editor {
        return this
    }

    override fun commit(): Boolean {
        return true
    }

    override fun apply() {
        return
    }
}


object MockSharedPreference2 : SharedPreferences {
    override fun getAll(): MutableMap<String, *> {
        return mutableMapOf<String, String>()
    }

    override fun getString(key: String?, defValue: String?): String? {
        return ""
    }

    override fun getStringSet(key: String?, defValues: MutableSet<String>?): MutableSet<String>? {
        throw Exception("mock error")
    }

    override fun getInt(key: String?, defValue: Int): Int {
        return 1
    }

    override fun getLong(key: String?, defValue: Long): Long {
        return 1
    }

    override fun getFloat(key: String?, defValue: Float): Float {
        return 1f
    }

    override fun getBoolean(key: String?, defValue: Boolean): Boolean {
        return true
    }

    override fun contains(key: String?): Boolean {
        return true
    }

    override fun edit(): SharedPreferences.Editor {
        return MockEditor2
    }

    override fun registerOnSharedPreferenceChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener?) {
        return
    }

    override fun unregisterOnSharedPreferenceChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener?) {
        return
    }

}


class LocalRepositoryImplTest {


    @Test
    fun loadLikes() {
        val localRepositoryImpl1 = LocalRepositoryImpl(MockSharedPreference1)
        val localRepositoryImpl2 = LocalRepositoryImpl(MockSharedPreference2)

        assertEquals(localRepositoryImpl1.loadLikes(), setOf("1","2","3"))
        assertEquals(localRepositoryImpl2.loadLikes(), emptySet<String>())
    }

    @Test
    fun saveLike() {
        val localRepositoryImpl1 = LocalRepositoryImpl(MockSharedPreference1)
        val localRepositoryImpl2 = LocalRepositoryImpl(MockSharedPreference2)

        assertEquals(localRepositoryImpl1.saveLike(emptySet()),true)
        assertEquals(localRepositoryImpl2.saveLike(emptySet()),false)
    }
}