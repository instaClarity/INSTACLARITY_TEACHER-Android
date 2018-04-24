package com.android.instaclarity.teacher.utils

import android.content.Context
import android.content.SharedPreferences
import com.android.instaclarity.teacher.BuildConfig

/**
 * SharedPreference manager class.
 */
class SharedPreferenceManager
/**
 * Private constructor
 */
private constructor(context: Context) {

    private var mEditor: SharedPreferences.Editor? = null
    private var mPreferences: SharedPreferences? = null

    init {
        initEditor(context)
    }

    /**
     * gets invoked internally from the constructor.
     *
     * @param context app global reference
     */
    private fun initEditor(context: Context) {

        mPreferences = context.getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE)
        mEditor = mPreferences!!.edit()
        mEditor!!.apply()

    }

    /**
     * To remove value associated with key
     *
     * @param key : key whose value to be removed
     */

    fun remove(key: String) {
        mEditor!!.remove(key).commit()
    }

    /**
     * Store boolean in preferenceManager.
     *
     * @param key   is value key.
     * @param value for store.
     */
    fun setBoolean(key: String, value: Boolean) {

        mEditor!!.putBoolean(key, value)
        mEditor!!.commit()

    }

    /**
     * Store string in preferenceManager.
     *
     * @param key   is value key.
     * @param value for store.
     */
    fun setString(key: String, value: String) {
        mEditor!!.putString(key, value.trim { it <= ' ' })
        mEditor!!.commit()
    }

    /**
     * Store integer in preferenceManager.
     *
     * @param key   is value key.
     * @param value for store.
     */
    fun setInt(key: String, value: Int) {
        mEditor!!.putInt(key, value)
        mEditor!!.commit()
    }

    /**
     * String value from preferenceManager.
     *
     * @param key      is value key.
     * @param defValue value.
     * @return store value.
     */
    fun getString(key: String, defValue: String): String? {
        return mPreferences!!.getString(key, defValue)
    }

    /**
     * Int value from preferenceManager.
     *
     * @param key      is value key.
     * @param defValue value.
     * @return store value.
     */
    fun getInt(key: String, defValue: Int): Int {
        return mPreferences!!.getInt(key, defValue)
    }

    fun getLong(key: String, defValue: Long): Long {
        return mPreferences!!.getLong(key, defValue)
    }

    fun setLong(key: String, value: Long) {
        mEditor!!.putLong(key, value)
        AppLogger.d(">>>>>>>>>>>" + key)
        if (key.startsWith(Constants.KEY_LANG_TIME_PREFIX)) {
            AppLogger.d(">>>>>>>>>>>$key   $value")
        }
        mEditor!!.commit()
    }

    /**
     * boolean value from preferenceManager.
     *
     * @param key      is value key.
     * @param defValue value.
     * @return store value.
     */
    fun getBoolean(key: String, defValue: Boolean): Boolean {
        return mPreferences!!.getBoolean(key, defValue)
    }

    companion object {

        const val KEY_PUBLIC_ACCESS_TOKEN = "KEY_PUBLIC_ACCESS_TOKEN"
//        const val KEY_PRIVATE_ACCESS_TOKEN = "KEY_PRIVATE_ACCESS_TOKEN"
        const val KEY_REFRESH_TOKEN = "KEY_REFRESH_TOKEN"
        private var instance: SharedPreferenceManager? = null

        /**
         * Instance of App SharedPreferenceManager.
         *
         * @param context is App context.
         * @return instance.
         */
         fun  getInstance(context: Context): SharedPreferenceManager {
            if (instance == null) {
                instance = SharedPreferenceManager(context)
            }
            return instance as SharedPreferenceManager
        }
    }
}
