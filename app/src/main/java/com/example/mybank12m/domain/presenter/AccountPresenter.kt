package com.example.mybank12m.domain.presenter

import android.util.Log
import com.example.mybank12m.data.model.Account
import com.example.mybank12m.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AccountPresenter(val view: AccountContracts.View) : AccountContracts.Presenter {

    override fun loadAccounts() {

        ApiClient.accountApi.fetchAccounts().enqueue(object : Callback<List<Account>> {
            override fun onResponse(
                call: Call<List<Account>?>,
                responce: Response<List<Account>?>
            ) {
                if (responce.isSuccessful)
                    view.showAccounts(responce.body() ?: emptyList())

            }

            override fun onFailure(
                call: Call<List<Account>?>,
                t: Throwable
            ) {
                Log.e("AccountPresenter", "Ошибка при загрузке: ${t.message}", t)
            }
        })
    }

    override fun addAccount(account: Account) {
        ApiClient.accountApi.createAccount(account).enqueue(object : Callback<Account> {
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


    }

