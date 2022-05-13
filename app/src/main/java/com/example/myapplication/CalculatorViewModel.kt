package com.example.myapplication

import android.app.Activity
import android.app.Application
import android.content.IntentSender
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CalculatorViewModel (application : Application) : AndroidViewModel(application) {

    private val model = Calculator(
        CalculatorDatabase.getInstance(application).operationDao()
    )

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





    fun getHistory(onFinished: (List<OperationUI>) -> Unit) {
        model.getAllOperations(onFinished)

    }



    private fun onOperationClick(activity : Activity, operation: OperationUI){
        model.onOperationClick(activity,operation)

    }

    private fun onOperationLongClick(activity : Activity, operation : OperationUI){
        model.onOperationLongClick(activity,operation)
    }


}
