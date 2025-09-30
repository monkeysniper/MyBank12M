package com.example.mybank12m.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mybank12m.data.model.Account
import com.example.mybank12m.data.model.AccountState
import com.example.mybank12m.network.AccountApi
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val accountApi: AccountApi
) : ViewModel() {

    private val _accounts = MutableLiveData<List<Account>>()
    val accounts: LiveData<List<Account>> = _accounts

    private val _successMessage = MutableLiveData<String>()
    val successMessage: LiveData<String> = _successMessage

    fun loadAccounts() {
        accountApi.fetchAccounts().enqueue(object : Callback<List<Account>> {
            override fun onResponse(
                call: Call<List<Account>?>,
                responce: Response<List<Account>?>
            ) {
                if (responce.isSuccessful) {
                    val result = responce.body() ?: emptyList()
                    _accounts.value = result
                }

            }

            override fun onFailure(
                call: Call<List<Account>?>,
                t: Throwable
            ) {
                Log.e("AccountPresenter", "Ошибка при загрузке: ${t.message}", t)
            }
        })
    }

    fun addAccount(account: Account) {
        accountApi.createAccount(account).enqueue(object : Callback<Account> {
            override fun onResponse(
                p0: Call<Account?>,
                p1: Response<Account?>
            ) {
                if (p1.isSuccessful) loadAccounts()
            }

            override fun onFailure(
                p0: Call<Account?>,
                p1: Throwable
            ) {

            }

        })
    }

    fun updateFullyAccount(account: Account) {
        accountApi.updateFullyAccount(account.id!!, account)
            .enqueue(object : Callback<Unit> {
                override fun onResponse(
                    p0: Call<Unit?>,
                    p1: Response<Unit?>
                ) {
                    if (p1.isSuccessful) loadAccounts()
                }

                override fun onFailure(p0: Call<Unit?>, p1: Throwable) {
                }

            })
    }

    fun updateStateAccount(
        accountId: String,
        accountState: AccountState
    ) {
        accountApi.updateAccountState(accountId, accountState)
            .enqueue(object : Callback<Unit> {
                override fun onResponse(
                    p0: Call<Unit?>,
                    p1: Response<Unit?>
                ) {
                    if (p1.isSuccessful) {
                        _successMessage.value="Состояние счета изменено "
                        loadAccounts()
                    }
                }

                override fun onFailure(p0: Call<Unit?>, p1: Throwable) {
                }

            })
    }

    fun deleteAccount(accountId: String) {
        accountApi.deleteAccount(accountId).enqueue(object : Callback<Unit> {
            override fun onResponse(
                p0: Call<Unit?>,
                p1: Response<Unit?>
            ) {
                if (p1.isSuccessful) loadAccounts()
            }

            override fun onFailure(p0: Call<Unit?>, p1: Throwable) {
            }

        })
    }


}

