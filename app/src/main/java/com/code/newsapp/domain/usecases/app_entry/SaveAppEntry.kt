package com.code.newsapp.domain.usecases.app_entry

import com.code.newsapp.domain.manager.LocalUserManager

class SaveAppEntry(private val localUserManager : LocalUserManager) {
    suspend  operator fun invoke(){
        localUserManager.saveAppEntry()
    }
}