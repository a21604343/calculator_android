package com.example.myapplication

import androidx.lifecycle.ViewModel
import com.example.myapplication.OperationUI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CalculatorViewModel : ViewModel() {

    private val model = Calculator()

    fun getDisplayValue() = model.display

     fun onClickSymbol(symbol: String) : String{
    return model.insertSymbol(symbol)

    }

    fun onClickEquals() : String {
        val result = model.performOperation()
        return result.toString()
    }

    fun getHistory(callback: (List<OperationUI>) ->Unit) {
        model.getAllOperations({ operations ->
            val history = operations.map{
                OperationUI(it.expression,it.result,it.timestamp)
            }
           CoroutineScope(Dispatchers.Main).launch { callback(history) }
        })
    }
}
