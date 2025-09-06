package com.example.mybank12m.domain.presenter

import com.example.mybank12m.data.model.Account

class AccountPresenter(val view:AccountContracts.View):AccountContracts.Presenter {
    override fun loadAccounts() {
        val testMockAccounts= listOf(
            Account(
                id="1",
                name = "obank",
                "USD",
                balance = 1000
            ),Account(
                id="2",
                name = "mbank",
                "USD",
                balance = 2000
            ),Account(
                id="3",
                name = "bakai",
                "USD",
                balance = 3000
            ),
        )
        view.showAccounts(testMockAccounts)
    }

}