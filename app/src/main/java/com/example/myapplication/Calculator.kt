package com.example.myapplication

import android.app.Activity
import android.util.Log
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.objecthunter.exp4j.ExpressionBuilder
import java.text.SimpleDateFormat
import java.util.*

class Calculator {

    private val TAG = Calculator::class.java.simpleName
     var display : String = "0"
   var displaySaved : String = ""
    private val listaHistorico = mutableListOf<Operation>()

   /*
    fun checkDisplay() : String{
        if(display.isEmpty()){
            display = "0"
        }
        return display
    }

    */

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
        displaySaved = display
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

        display = result.toString()
        displaySaved = display
      return result

    }

    suspend fun addHistory(expression : String, result : Double) {
        Thread.sleep(30*1000)
        listaHistorico.add(Operation(expression,result))
    }

    fun getHistory(callback: (List<Operation> ) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            Thread.sleep(30*1000)
            callback(listaHistorico.toList())
        }
    }

     fun onOperationClick(activity : Activity, operation: OperationUI){

        Toast.makeText(activity,"${operation.expression} = ${operation.result}", Toast.LENGTH_LONG).show()
    }

     fun onOperationLongClick(activity : Activity,operation : OperationUI){
        val sdf = SimpleDateFormat("dd/M/yyyy - hh:mm:ss")
        var currentDate = sdf.format(Date())
        Toast.makeText(activity, currentDate, Toast.LENGTH_LONG).show()
    }



}
