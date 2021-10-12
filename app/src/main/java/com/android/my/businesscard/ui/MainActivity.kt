package com.android.my.businesscard.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.android.my.businesscard.App
import com.android.my.businesscard.databinding.ActivityMainBinding
import com.android.my.businesscard.util.Image

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val mainViewModel: MainViewModel by viewModels {
        MainViewModelFactory((application as App).repository)
    }
    private val adapter by lazy { BusinessCardAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.rvCars.adapter = adapter
        getAllBusinessCard()
        setListeners()
    }

    private fun getAllBusinessCard() {
        mainViewModel.getAll().observe(this, {
            adapter.submitList(it)
        })
    }

    private fun setListeners() {
        binding.fab.setOnClickListener {
            startActivity(Intent(this@MainActivity, AddBusinessCardActivity::class.java))
        }
        adapter.listenerShare = { card ->
            Image.share(this@MainActivity, card)
        }
        adapter.listenerEditOrDelete = {
            val alertDialog = AlertDialog.Builder(this@MainActivity)
            alertDialog.setCancelable(true)
            alertDialog.setTitle("Excluir ou Editar")
            alertDialog.setMessage("O que deseja fazer?")

            alertDialog.setPositiveButton("Editar") { _ , _ ->
                val intent = Intent(this@MainActivity, AddBusinessCardActivity::class.java)
                intent.putExtra("cardId", it.id)
                intent.putExtra("cardObject", it)
                startActivity(intent)
            }
            alertDialog.setNegativeButton("Deletar") { _ , _ ->
                try {
                        mainViewModel.delete(it)
                        mainViewModel.getAll().observe(this, { adapter.submitList(it) })
                        Toast.makeText(
                            applicationContext,
                            "Sucesso ao deletar!",
                            Toast.LENGTH_SHORT
                        ).show()

                } catch (e: Exception) {
                Log.e("Error", "Falha ao deletar ${e.message}")
                }
            }
            alertDialog.create()
            alertDialog.show()

        }
    }
}