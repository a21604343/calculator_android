package com.example.myapplication

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.FragmentCalculatorBinding
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


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
    private lateinit var viewModel: CalculatorViewModel
    private val TAG = CalculatorFragment::class.java.simpleName
    var listaHistorico : ArrayList<OperationUI> = ArrayList()

    private val adapter = HistoryAdapter(::onOperationLongClick,::onOperationClick)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.i(TAG, "create view")
        val view = inflater.inflate(
            R.layout.fragment_calculator,container,false
        )
        binding = FragmentCalculatorBinding.bind(view)
        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Calculadora"
        viewModel = ViewModelProvider(activity as ViewModelStoreOwner).get(
            CalculatorViewModel::class.java
        )

        binding.textVisor.text = viewModel.getDisplayValue()
        Log.i(TAG, binding.textVisor.text.toString())

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        //viewModel.getHistory { listaHistorico }v
        getAllOperationsRetrofit { updateList(it) }

        binding.button1.setOnClickListener {binding.textVisor.text = viewModel.onClickSymbol("1")}
        binding.button2.setOnClickListener {binding.textVisor.text = viewModel.onClickSymbol("2")}
        binding.button3.setOnClickListener {binding.textVisor.text = viewModel.onClickSymbol("3")}
        binding.button4.setOnClickListener {binding.textVisor.text = viewModel.onClickSymbol("4")}
        binding.button5.setOnClickListener {binding.textVisor.text = viewModel.onClickSymbol("5")}
        binding.button6.setOnClickListener {binding.textVisor.text = viewModel.onClickSymbol("6")}
        binding.button7.setOnClickListener {binding.textVisor.text = viewModel.onClickSymbol("7")}
        binding.button8.setOnClickListener {binding.textVisor.text = viewModel.onClickSymbol("8")}
        binding.button9.setOnClickListener {binding.textVisor.text = viewModel.onClickSymbol("9")}
        binding.button0.setOnClickListener {binding.textVisor.text = viewModel.onClickSymbol("0")}
        binding.buttonAdition.setOnClickListener {binding.textVisor.text = viewModel.onClickSymbol("+")}
        binding.buttonMinus.setOnClickListener {binding.textVisor.text = viewModel.onClickSymbol("-")}
        binding.buttonDivide.setOnClickListener {binding.textVisor.text = viewModel.onClickSymbol("/")}
        binding.buttonQuestionMark.setOnClickListener {binding.textVisor.text = viewModel.onClickSymbol("*")}
        binding.buttonClear.setOnClickListener {binding.textVisor.text = viewModel.onClear()}
        binding.buttonSmaller.setOnClickListener {binding.textVisor.text = viewModel.onClickSymbol("P")}
        binding.buttonEquals.setOnClickListener {onClickEquals()}


        binding.rvHistoric?.layoutManager = LinearLayoutManager(activity as Context)
        binding.rvHistoric?.adapter = adapter





    }

    private fun updateList(fires : List<OperationUI>){
        CoroutineScope(Dispatchers.Main).launch {
            adapter.updateItems(fires)
        }
    }

    private fun getAllOperationsRetrofit(callback: (List<OperationUI>) -> Unit){
        viewModel.onGetHistory(callback)

    }

    private fun onClickEquals() {
        viewModel.onClickEquals {
            CoroutineScope(Dispatchers.Main).launch {
                //Toast.makeText(context, getString(R.string.registry_saved), Toast.LENGTH_LONG).show()
                binding.textVisor.text = viewModel.getDisplayValue()
                viewModel.onGetHistory { updateHistory(it) }
            }
        }
    }

    private fun updateHistory(operations: List<OperationUI>) {
        val history = operations.map { OperationUI(it.uuid, it.expression, it.result, it.timestamp) }
        CoroutineScope(Dispatchers.Main).launch {
            adapter.updateItems(history)
        }
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