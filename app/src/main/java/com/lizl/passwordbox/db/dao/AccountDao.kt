package com.lizl.passwordbox.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.lizl.passwordbox.mvvm.model.AccountModel

@Dao
interface AccountDao : BaseDao<AccountModel>
{
    @Query("select * from accounts")
    fun getAllAccountLiveData(): LiveData<MutableList<AccountModel>>

    @Query("select * from accounts")
    fun getAllAccount(): MutableList<AccountModel>

    @Query("select count (*) from accounts")
    fun getAccountsCount(): Int

    @Query("DELETE FROM accounts")
    fun deleteAll()

    @Query("select * from accounts where account like '%' || :keyword || '%' or description like '%' || :keyword || '%' or desPinyin like '%' || :keyword || '%'")
    fun search(keyword: String): MutableList<AccountModel>

    @Query("select * from accounts where description == :description and account == :account")
    fun search(description: String, account: String): AccountModel?
}