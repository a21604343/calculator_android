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

    var listaHistorico : MutableList<String> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i(TAG, "onCreated invoke")
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }


    override fun onStart() {
        super.onStart()

        binding.button1.setOnClickListener {onClickSymbol("1")}
        binding.button2.setOnClickListener {onClickSymbol("2")}
        binding.button3.setOnClickListener {onClickSymbol("3")}
        binding.button4.setOnClickListener {onClickSymbol("4")}
        binding.button5.setOnClickListener {onClickSymbol("5")}
        binding.button6.setOnClickListener {onClickSymbol("6")}
        binding.button7.setOnClickListener {onClickSymbol("7")}
        binding.button8.setOnClickListener {onClickSymbol("8")}
        binding.button9.setOnClickListener {onClickSymbol("9")}
        binding.button0.setOnClickListener {onClickSymbol("0")}
        binding.buttonAdition.setOnClickListener {onClickSymbol("+")}
        binding.buttonMinus.setOnClickListener {onClickSymbol("-")}
        binding.buttonDivide.setOnClickListener {onClickSymbol("/")}
        binding.buttonQuestionMark.setOnClickListener {onClickSymbol("*")}
        binding.buttonClear.setOnClickListener {onClickSymbol("C")}
        binding.buttonSmaller.setOnClickListener {onClickSymbol("P")}
        binding.buttonEquals.setOnClickListener {onClickEquals()}







    }
    private fun onClickSymbol(symbol: String){

        Log.i(TAG, "Click no Butao $symbol")
        if(symbol == "C"){
            binding.textVisor.text = ""
        }
        else if (symbol == "P"){
            binding.textVisor.text.subSequence(0,binding.textVisor.text.length-2)
        }else{
            if (binding.textVisor.text == "0") {
                binding.textVisor.text = symbol
            } else {
                binding.textVisor.append(symbol)
            }
        }






    }

    private fun onClickEquals(){

        Log.i(TAG, "Click no butao =")
        val expression = ExpressionBuilder(
            binding.textVisor.text.toString()
        ).build()
        binding.textVisor.text = expression.evaluate().toString()
        listaHistorico.add("$expression = ${binding.textVisor.text}")
        Log.i(TAG, "O resultado da expressão é ${binding.textVisor.text}")

    }
}

