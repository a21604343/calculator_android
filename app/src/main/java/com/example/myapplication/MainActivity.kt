package com.example.myapplication

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.myapplication.databinding.ActivityMainBinding
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val TAG = MainActivity::class.java.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i(TAG,"onCreated invoke")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }



    override fun onStart(){
        super.onStart()

        binding.button1.setOnClickListener{
            Log.i(TAG, "Click no Butao 1")
            if(binding.textVisor.text == "0"){
                binding.textVisor.text = "1"
            }else{
                binding.textVisor.append("1")
            }
        }

        binding.buttonAdition.setOnClickListener{
            Log.i(TAG, "Click no butao +")
            binding.textVisor.append("+")
        }

        binding.buttonEquals.setOnClickListener{
            Log.i(TAG, "Click no butao =")
            val expression = ExpressionBuilder(
                binding.textVisor.text.toString()
            ).build()
            binding.textVisor.text = expression.evaluate().toString()
            Log.i(TAG, "O resultado da expressão é ${binding.textVisor.text}")
        }


    }
}