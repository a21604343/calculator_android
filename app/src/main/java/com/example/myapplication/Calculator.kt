package com.example.myapplication

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.objecthunter.exp4j.ExpressionBuilder

class Calculator {

    private val TAG = Calculator::class.java.simpleName
    var display : String = "0"
    private val listaHistorico = mutableListOf<Operation>()



    fun insertSymbol(symbol : String) : String {
        Log.i(TAG, "Click no Butao $symbol")
        if(symbol == "C"){
            display = ""
        }
        else if (symbol == "P"){
            display.subSequence(0,display.length-2)
        }else{
            if (display == "0") {
                display = symbol
            } else {
                display+=symbol
            }
        }
        return display
    }


    fun performOperation() : Double {
        Log.i(TAG, "Click no butao =")

        val expressionBuilder = ExpressionBuilder(display).build()
        val result = expressionBuilder.evaluate()
        Log.i(TAG, "O resultado da expressão é ${result}")
        CoroutineScope(Dispatchers.IO).launch {
            addHistory(display,result)

        }

      return result

    }

    suspend fun addHistory(expression : String, result : Double) {
        Thread.sleep(30*1000)
        listaHistorico.add(Operation(expression,result))
    }

    fun getAllOperations(callback: (List<Operation> ) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            Thread.sleep(30*1000)
            callback(listaHistorico.toList())
        }
    }
}
