package com.baszczyk.mercdream.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE

import androidx.room.Query
import com.baszczyk.mercdream.database.enities.Deposit
import com.baszczyk.mercdream.database.enities.Mercedes
import com.baszczyk.mercdream.database.enities.PiggyBank
import com.baszczyk.mercdream.database.enities.User

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

    @Query("SELECT * FROM piggy WHERE user_id = :userId ORDER BY piggyId DESC")
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

    @Query("SELECT * FROM piggy WHERE piggyId = :id")
    fun getPiggy(id: Long): PiggyBank

    @Query("SELECT * FROM mercedes WHERE mercedes_id = :id")
    fun getMercedes(id: Long): Mercedes

    @Query("UPDATE piggy SET actual_amount= :amount WHERE piggyId = :id")
    fun updatePiggyBank(amount: Double, id: Long)

    @Query("SELECT * FROM deposit WHERE mercedes_id = :id ORDER BY deposit_id DESC")
    fun getAllDepositsForCurrentPiggy(id: Long): List<Deposit>

    @Query("SELECT * FROM deposit WHERE user_id = :id ORDER BY deposit_id DESC")
    fun getAllDeposits(id: Long): List<Deposit>

    @Query("DELETE FROM piggy WHERE piggyId= :id")
    fun deletePiggy(id: Long)
}