package com.example.myapplication

import android.app.Activity
import android.widget.Toast
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class CalculatorViewModel : ViewModel() {

    private val model = Calculator()

    fun getDisplayValue() : String {

        return model.display
    }

     fun onClickSymbol(symbol: String) : String{
    return model.insertSymbol(symbol)

    }

    fun onClickEquals() : String {
        val result = model.performOperation()
        return result.toString()
    }
    /*
    fun getHistory(callback: (List<OperationUI>) ->Unit) {
        val history = model.getHistory({ operations -> operations.map{
                OperationUI(it.expression,it.result,it.timestamp)
            })
            CoroutineScope(Dispatchers.Main).launch { callback(history) }
        }
    }

     */



    fun getLista() : ArrayList<OperationUI>{

        var  res  = model.getLista().map { OperationUI(it.expression,it.result,it.timestamp) }
        return res as ArrayList<OperationUI>
    }


    fun getHistory(callback: (List<OperationUI>) -> Unit) {
        var history : List<OperationUI> = mutableListOf()
        model.getHistory({ operations ->
            history = operations.map {
                OperationUI(it.expression, it.result, it.timestamp)
            }

        })
        CoroutineScope(Dispatchers.Main).launch { callback(history) }
    }
    /*
    fun getHistory1(callback: (List<OperationUI>) -> List<Operation>) {
       val history =  model.getHistory({op})
            CoroutineScope(Dispatchers.Main).launch { callback(history) }
        }
    }

     */






    private fun onOperationClick(activity : Activity, operation: OperationUI){
        model.onOperationClick(activity,operation)

    }

    private fun onOperationLongClick(activity : Activity, operation : OperationUI){
        model.onOperationLongClick(activity,operation)
    }


}
