package com.example.myapplication

import android.app.Activity
import android.app.Application
import android.content.IntentSender
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CalculatorViewModel : ViewModel() {

    private val model = CalculatorRepository.getInstance()

        /*CalculatorRetrofit(
            RetrofitBuilder.getInstance(
                "https://cm-calculadora.herokuapp.com/api/"
            )
        )
         */
       // CalculatorDatabase.getInstance(application).operationDao()

    fun getDisplayValue() : String {

        return model.getExpression()
    }
     fun onClickSymbol(symbol: String) : String{
    return model.insertSymbol(symbol)

    }
    fun onClickEquals(onFinished: () -> Unit) {
         model.performOperation(onFinished)

    }
    fun onClear() : String {
        return model.clear()
    }

    fun onGetHistory(onFinished: (List<OperationUI>) -> Unit) {
        model.getHistory(onFinished)

    }

    fun onDeleteOperation(uuid : String, onSuccess : () -> Unit){
        model.deleteOperation(uuid,onSuccess)
    }

    fun onDeleteAllOperations(onSuccess: () -> Unit){
        model.deleteAllOperations(onSuccess)

    }





}
