package com.example.myapplication


import net.objecthunter.exp4j.ExpressionBuilder

import java.util.*
import com.example.myapplication.OperationUI

abstract class Calculator {

    var expression: String = "0"

    fun insertSymbol(symbol: String): String {
        expression = if(expression == "0") symbol else "$expression$symbol"
        return expression
    }

    fun clear(): String {
        expression = "0"
        return expression
    }

    fun deleteLastSymbol(): String {
        expression = if(expression.length > 1) expression.dropLast(1) else "0"
        return expression
    }

    open fun performOperation(onFinished: () -> Unit) {
        val expressionBuilder = ExpressionBuilder(expression).build()
        val result = expressionBuilder.evaluate()
        expression = result.toString()
        onFinished()
    }

    abstract fun insertOperations(operations: List<OperationUI>, onFinished: (List<OperationUI>) -> Unit)
    abstract fun getLastOperation(onFinished: (String) -> Unit)
    abstract fun deleteOperation(uuid: String, onFinished: () -> Unit)
    abstract fun deleteAllOperations(onFinished: () -> Unit)
    abstract fun getHistory(onFinished: (List<OperationUI>) -> Unit)

}