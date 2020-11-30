package com.alfred0ga.skimmia.repositories

import com.alfred0ga.skimmia.db.UserDatabase
import com.alfred0ga.skimmia.models.User

class UserRepository(
    val db: UserDatabase
) {
    suspend fun saveUser(user: User) = db.getUserDao().saveUser(user)
    fun getUserData() = db.getUserDao().getUserData()
}