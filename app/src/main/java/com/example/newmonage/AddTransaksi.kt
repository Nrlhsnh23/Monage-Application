package com.example.newmonage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.example.newmonage.api.MonageData
import com.example.newmonage.api.RetrofitHelper
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.create

class AddTransaksi : AppCompatActivity() {
    lateinit var addTransaction : Button
    lateinit var labelInput : TextInputEditText
    lateinit var labelLayout : TextInputLayout
    lateinit var amountInput : TextInputEditText
    lateinit var amountLayout : TextInputLayout
    lateinit var tanggalInput: TextInputEditText
    lateinit var tanggalLayout : TextInputLayout
    lateinit var closeBtn : ImageButton

    val apiKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImljYXBwaW1nY2FzZ3hjeGd0aGJpIiwicm9sZSI6ImFub24iLCJpYXQiOjE2NzA1NzUxOTYsImV4cCI6MTk4NjE1MTE5Nn0.XxDLVw5GRojK4emEVUuTMmJt6RaXQzJoy5DLMoXH7Bw"
    val token = "Bearer $apiKey"

    val MonageApi = RetrofitHelper.getInstance().create(com.example.newmonage.api.MonageApi::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_transaksi)

        addTransaction = findViewById(R.id.addTransactionBtn)
        labelInput= findViewById(R.id.labelInput)
        labelLayout= findViewById(R.id.labelLayout)
        amountInput= findViewById(R.id.amountInput)
        amountLayout= findViewById(R.id.amountLayout)
        tanggalInput= findViewById(R.id.tanggalInput)
        tanggalLayout= findViewById(R.id.labelLayout)
        closeBtn= findViewById(R.id.closeBtn)


        tanggalInput.addTextChangedListener {
            if(it!!.count() > 0)
                tanggalLayout.error = null
        }

        labelInput.addTextChangedListener {
            if(it!!.count() > 0)
                labelLayout.error = null
        }

        amountInput.addTextChangedListener {
            if(it!!.count() > 0)
                amountLayout.error = null
        }

        addTransaction.setOnClickListener {
            val tgl = tanggalInput.text.toString()
            val label = labelInput.text.toString()
            val amount = amountInput.text.toString().toDoubleOrNull()

            if (tgl.isEmpty())
                tanggalLayout.error = "Please enter a valid label"

            if (label.isEmpty())
                labelLayout.error = "Please enter a valid label"

            if (amount == null)
                amountLayout.error = "Please enter a valid amount"

            CoroutineScope(Dispatchers.Main).launch {
                val data = MonageData(
                    tanggal = tanggalInput.text.toString(),
                    label = labelInput.text.toString(),
                    amount = amountInput.text.toString().toDouble(),
                )
                val response = MonageApi.create(token = token, apiKey = apiKey, monageData = data)

                Toast.makeText(
                    applicationContext,
                    "Berhasil menambah transaksi",
                    Toast.LENGTH_SHORT
                ).show()

                finish()
            }
        }
        closeBtn.setOnClickListener {
            finish()
        }
    }
}