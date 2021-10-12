package com.android.my.businesscard.ui

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.android.my.businesscard.App
import com.android.my.businesscard.R
import com.android.my.businesscard.data.BusinessCard
import com.android.my.businesscard.databinding.ActivityAddBusinessCardBinding
import top.defaults.colorpicker.ColorPickerPopup
import java.util.*

class AddBusinessCardActivity : AppCompatActivity() {

    private var idCard: Int = 0
    private val cBinding by lazy { ActivityAddBusinessCardBinding.inflate(layoutInflater) }
    private val mainViewModel: MainViewModel by viewModels {
        MainViewModelFactory((application as App).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(cBinding.root)
        setupCard()
        setListeners()
    }

    private fun setupCard() {
        // Recupera dados do card, caso seja edição
        idCard = intent.getIntExtra("cardId", 0)

       if (idCard != 0) {
            val card = intent.getSerializableExtra("cardObject") as BusinessCard?
            cBinding.tilName.editText?.setText(card?.nome)
            cBinding.tilEmail.editText?.setText(card?.email)
            cBinding.tilTelephone.editText?.setText(card?.telefone)
            cBinding.tilCompany.editText?.setText(card?.empresa)
            cBinding.tvColor.text = card?.fundoPersonalizado
        }
    }

    private fun setListeners() {

        cBinding.btnColor.setOnClickListener {
            ColorPickerPopup.Builder(this@AddBusinessCardActivity)
                .initialColor(Color.DKGRAY)
                .enableBrightness(true)
                .enableAlpha(true)
                .okTitle("Escolher")
                .cancelTitle("Cancelar")
                .showIndicator(true)
                .showValue(true)
                .build()
                .show(it, object: ColorPickerPopup.ColorPickerObserver {
                    override fun onColor(color: Int, fromUser: Boolean) {
                        cBinding.tvColor.text = "#${Integer.toHexString(color)}".uppercase(Locale.getDefault())
                        cBinding.btnColor.setBackgroundColor(color)

                    }

                    override fun onColorPicked(color: Int) {
                        cBinding.tvColor.text = "#${Integer.toHexString(color)}".uppercase(Locale.getDefault())
                        cBinding.btnColor.setBackgroundColor(color)
                    }

                })
        }

        cBinding.btnClose.setOnClickListener {
            finish()
        }
        cBinding.btnConfirm.setOnClickListener {

            val nome = cBinding.tilName.editText?.text.toString()
            val email = cBinding.tilEmail.editText?.text.toString()
            val empresa = cBinding.tilCompany.editText?.text.toString()
            val telefone = cBinding.tilTelephone.editText?.text.toString()
            val cor = cBinding.tvColor.text.toString()

            when {
                nome.isEmpty() -> {
                    Toast.makeText(
                        applicationContext,
                        "Preencha o nome!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                telefone.isEmpty() -> {
                    Toast.makeText(
                        applicationContext,
                        "Preencha seu telefone!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                email.isEmpty() -> {
                    Toast.makeText(
                        applicationContext,
                        "Preencha o email!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                empresa.isEmpty() -> {
                    Toast.makeText(
                        applicationContext,
                        "Preencha a empresa!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                cor.isEmpty() -> {
                    Toast.makeText(
                        applicationContext,
                        "Escolha a cor!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    val businessCard = BusinessCard(
                        id = idCard,
                        nome = nome,
                        email = email,
                        empresa = empresa,
                        telefone = telefone,
                        fundoPersonalizado = cor
                    )

                    if (idCard != 0) {
                        mainViewModel.update(businessCard)
                        Toast.makeText(
                            applicationContext,
                            R.string.label_show_update_sucess,
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        mainViewModel.insert(businessCard)
                        Toast.makeText(
                            applicationContext,
                            R.string.label_show_sucess,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    finish()
                }
            }
        }
    }
}