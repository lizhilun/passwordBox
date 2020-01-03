package com.lizl.demo.passwordbox.dao

import androidx.room.Dao
import androidx.room.Query
import com.lizl.demo.passwordbox.model.AccountModel

@Dao
interface AccountDao : BaseDao<AccountModel>
{
    @Query("select * from accounts")
    fun getAllDiary(): MutableList<AccountModel>

    @Query("select count (*) from accounts")
    fun getDiariesCount(): Int

    @Query("DELETE FROM accounts")
    fun deleteAll()

    @Query("select * from accounts where account like '%' || :keyword || '%' or description like '%' || :keyword || '%' or desPinyin like '%' || :keyword || '%'")
    fun search(keyword: String): MutableList<AccountModel>

    @Query("select * from accounts where description == :description and account == :account")
    fun search(description: String, account: String): AccountModel?
}