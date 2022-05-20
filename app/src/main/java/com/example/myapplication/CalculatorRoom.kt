package com.example.myapplication

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class CalculatorRoom(private val dao: OperationDao) : Calculator() {

    override fun performOperation(onFinished: () -> Unit) {
        val currentExpression = expression
        super.performOperation {
            val operation = OperationRoom(expression = currentExpression, result = expression.toDouble(), timestamp = Date().time)
            CoroutineScope(Dispatchers.IO).launch { dao.insert(operation) }
            onFinished()
        }
    }

    override fun insertOperations(operations: List<OperationUI>, onFinished: (List<OperationUI>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val history = operations.map { OperationRoom(it.uuid, it.expression, it.result, it.timestamp) }
            dao.insertAll(history)
            onFinished(operations)
        }
    }

    override fun getLastOperation(onFinished: (String) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            // Também pode ser realizado com uma query com o Room, ver getLastOperation do OperationDao
            // o método do dao é o mais correto, este serve apenas para apresentar outra solução :)
            getHistory { onFinished(it.last().expression) }
        }
    }

    override fun deleteOperation(uuid: String, onSuccess: () -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val result = dao.delete(uuid)
            // se == 1 eliminou o registo, se for 0 não encontrou o registo a eliminar
            if(result == 1) onSuccess()
        }
    }

    override fun deleteAllOperations(onFinished: () -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            dao.deleteAll()
            onFinished()
        }
    }

    override fun getHistory(onFinished: (List<OperationUI>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val operations = dao.getAll()
            onFinished(operations.map { OperationUI(it.uuid, it.expression, it.result, it.timestamp) })
        }
    }

}
