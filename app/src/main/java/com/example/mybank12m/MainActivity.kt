package com.example.mybank12m

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mybank12m.data.model.Account
import com.example.mybank12m.databinding.ActivityMainBinding
import com.example.mybank12m.domain.presenter.AccountContracts
import com.example.mybank12m.domain.presenter.AccountPresenter
import com.example.mybank12m.ui.adapter.AccountAdapter

class MainActivity : AppCompatActivity(), AccountContracts.View {

    lateinit var binding: ActivityMainBinding
    private lateinit var presenter: AccountContracts.Presenter
    private lateinit var adapter: AccountAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        presenter = AccountPresenter(this)

        initAdapter()
    }

    private fun initAdapter() {
        with(binding) {
            adapter = AccountAdapter()
            recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
            recyclerView.adapter = adapter
        }

    }

    override fun onResume() {
        super.onResume()
        presenter.loadAccounts()
    }


    override fun showAccounts(list: List<Account>) {
        adapter.submitList(list)
    }
}
