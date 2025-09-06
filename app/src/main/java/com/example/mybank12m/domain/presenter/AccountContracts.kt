package com.example.mybank12m.domain.presenter

import com.example.mybank12m.data.model.Account

interface AccountContracts {
    interface View {
        fun showAccounts(list: List<Account>)
    }

    interface Presenter {
        fun loadAccounts()
    }
}