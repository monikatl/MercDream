package com.baszczyk.mercdream.database

import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE

import androidx.room.Query
import java.util.jar.Attributes

@Dao
interface PiggyDatabaseDao {

    @Insert
    fun insertMercedes(mercedes: Mercedes)

    @Insert
    fun insertPiggyBank(piggyBank: PiggyBank)

    @Insert
    fun insertDeposit(deposit: Deposit)

    @Insert(onConflict = REPLACE)
    fun insertNewUser(user: User)

    @Query("SELECT mercedes_id FROM mercedes ORDER BY mercedes_id DESC LIMIT 1")
    fun getMercedesId(): Long?

    @Query("SELECT * FROM piggy WHERE user_id = :userId")
    fun getAllPiggies(userId: Long): List<PiggyBank>

    @Query("SELECT * FROM user ORDER BY user_id DESC LIMIT 1")
    fun getCurrentUser(): User

    @Query("SELECT password FROM user WHERE name = :name")
    fun getUserPassword(name: String): String

    @Query("SELECT name FROM user")
    fun getAllUsersNames(): List<String>

    @Query("SELECT piggyId FROM piggy WHERE user_id = :userId")
    fun getPiggiesId(userId: Long): List<Long?>

    @Query("SELECT * FROM user WHERE name = :name")
    fun getUser(name: String): User?
}