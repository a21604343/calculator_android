package com.example.myapplication

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.FragmentCalculatorBinding
import net.objecthunter.exp4j.ExpressionBuilder
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CalculatorFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CalculatorFragment : Fragment() {

    private lateinit var binding: FragmentCalculatorBinding
    private val TAG = CalculatorFragment::class.java.simpleName
    var listaHistorico : ArrayList<OperationUI> = ArrayList()

    private val adapter = HistoryAdapter(::onOperationLongClick,::onOperationClick)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(
            R.layout.fragment_calculator,container,false
        )
        binding = FragmentCalculatorBinding.bind(view)
        return binding.root
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


        binding.rvHistoric?.layoutManager = LinearLayoutManager(activity as Context)
        binding.rvHistoric?.adapter = adapter





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
        val expressionStart = binding.textVisor.text.toString()
        val expression = ExpressionBuilder(
            binding.textVisor.text.toString()
        ).build()
        binding.textVisor.text = expression.evaluate().toString()

        var op : OperationUI = OperationUI(expressionStart,binding.textVisor.text.toString(),System.currentTimeMillis())
        listaHistorico.add(op)
        adapter.updateItems(listaHistorico)
        Log.i(TAG, "O resultado da expressão é ${binding.textVisor.text}")

    }

    private fun onOperationClick(operation: OperationUI){

        Toast.makeText(activity,"${operation.expression} = ${operation.result}", Toast.LENGTH_LONG).show()
    }

    private fun onOperationLongClick(operation : OperationUI){
        val sdf = SimpleDateFormat("dd/M/yyyy - hh:mm:ss")
        var currentDate = sdf.format(Date())
        Toast.makeText(activity, currentDate, Toast.LENGTH_LONG).show()
    }


}