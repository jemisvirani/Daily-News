package com.code.newsapp.domain.usecases.app_entry

import com.code.newsapp.domain.manager.LocalUserManager
import kotlinx.coroutines.flow.Flow

class ReadAppEntry(private val localUserManager : LocalUserManager) {
      operator fun invoke() : Flow<Boolean> {
       return localUserManager.readAppEntry()
    }
}