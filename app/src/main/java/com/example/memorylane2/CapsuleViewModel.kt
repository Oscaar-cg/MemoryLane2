package com.example.memorylane2

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CapsuleViewModel(application: Application) : AndroidViewModel(application) {

    val capsules = MutableLiveData<MutableList<Capsule>>()

    init {
        loadData()
    }

    fun addCapsule(capsule: Capsule) {
        val list = capsules.value ?: mutableListOf()
        list.add(capsule)
        capsules.value = list
        saveData()
    }

    fun deleteCapsule(index: Int) {
        val list = capsules.value ?: return
        list.removeAt(index)
        capsules.value = list
        saveData()
    }

    fun updateCapsule(index: Int, capsule: Capsule) {
        val list = capsules.value ?: return
        list[index] = capsule
        capsules.value = list
        saveData()
    }

    private fun saveData() {
        val sharedPref = getApplication<Application>().getSharedPreferences("capsules", 0)
        val json = Gson().toJson(capsules.value)
        sharedPref.edit().putString("data", json).apply()
    }

    private fun loadData() {
        val sharedPref = getApplication<Application>().getSharedPreferences("capsules", 0)
        val json = sharedPref.getString("data", null)

        if (json != null) {
            val type = object : TypeToken<MutableList<Capsule>>() {}.type
            capsules.value = Gson().fromJson(json, type)
        } else {
            capsules.value = mutableListOf()
        }
    }
}