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
import kotlin.collections.ArrayList

class Calculator (private val dao: OperationDao) {

    private val TAG = Calculator::class.java.simpleName
    var display : String = "0"

    private set

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
        val expression = result.toString()
        val operation = OperationRoom(
            expression = expression, result = result, timestamp = Date().time
        )
        Log.i(TAG, "O resultado da expressão é ${result}")
        CoroutineScope(Dispatchers.IO).launch {
            dao.insert(operation)

        }

        display = result.toString()

      return result

    }

      fun getAllOperations (onFinished: (List<OperationUI>) -> Unit) {
          CoroutineScope(Dispatchers.IO).launch {
            val operations =  dao.getAll()
              onFinished(operations.map {
                  OperationUI(it.uuid,it.expression,it.result,it.timestamp)
              })
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
