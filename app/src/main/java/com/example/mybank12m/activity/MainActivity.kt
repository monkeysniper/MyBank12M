package com.example.mybank12m.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mybank12m.data.model.Account
import com.example.mybank12m.databinding.ActivityMainBinding
import com.example.mybank12m.databinding.DialogBinding
import com.example.mybank12m.ui.adapter.AccountAdapter
import com.example.mybank12m.ui.viewmodel.AccountViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private lateinit var adapter: AccountAdapter
    private val viewModel: AccountViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        initAdapter()
        initClicks()
        subcribeToLiveData()
    }

    private  fun subcribeToLiveData(){
        viewModel.accounts.observe(this){
            adapter.submitList(it)
        }
        viewModel.successMessage.observe(this){
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun initClicks() {
        with(binding) {
            btnAdd.setOnClickListener {
                showAddDialog()
            }
        }
    }

    private fun showAddDialog() {
        val binding = DialogBinding.inflate(LayoutInflater.from(this))
        with(binding) {
            AlertDialog.Builder(this@MainActivity)
                .setTitle("Добавить счет")
                .setView(binding.root)
                .setPositiveButton("Добавить") { _, _ ->
                    val account = Account(
                        name = etName.text.toString(),
                        currency = etCurrency.text.toString(),
                        balance = etBalance.text.toString().toInt()

                    )
                    viewModel.addAccount(account)
                }
                .setNegativeButton("Отмена", null)
                .show()
        }
    }

    private fun showEditDialog(account: Account) {
        val binding = DialogBinding.inflate(LayoutInflater.from(this))
        with(binding) {
            etName.setText(account.name)
            etBalance.setText(account.balance.toString())
            etCurrency.setText(account.currency)

            AlertDialog.Builder(this@MainActivity)
                .setTitle("Изменить счет")
                .setView(binding.root)
                .setPositiveButton("Изменить") { _, _ ->
                    val updateAccount = account.copy(
                        name = etName.text.toString(),
                        currency = etCurrency.text.toString(),
                        balance = etBalance.text.toString().toInt()
                    )
                    viewModel.updateFullyAccount(updateAccount)
                }
                .setNegativeButton("Отмена", null)
                .show()
        }
    }

    private fun initAdapter() {
        with(binding) {
            adapter = AccountAdapter(
                onEdit = { showEditDialog(it) },
                onDelete = {
                    viewModel.deleteAccount(it)
                }


            )
            recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
            recyclerView.adapter = adapter
        }

    }

    override fun onResume() {
        super.onResume()
        viewModel.loadAccounts()
    }
}