package com.example.myapplication

import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.FragmentCalculatorBinding
import com.example.myapplication.databinding.FragmentHistoryBinding
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_OPERATIONS = "param1"


/**
 * A simple [Fragment] subclass.
 * Use the [HistoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HistoryFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var operations: ArrayList<OperationUI>? = null // arguments
    private lateinit var viewModel: CalculatorViewModel
    private lateinit var binding: FragmentHistoryBinding
    private val TAG = HistoryFragment::class.java.simpleName
    //val listaHistorico : Array<OperationUI> = arguments.getParcelableArray(ARG_OPERATIONS)
    //var listaHistorico : MutableList<OperationUI> = mutableListOf()
    private val adapter = HistoryAdapter(::onOperationLongClick,::onOperationClick)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            operations = it.getParcelableArrayList(ARG_OPERATIONS)

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(
            R.layout.fragment_history,container,false
        )

        binding = FragmentHistoryBinding.bind(view)
        viewModel = ViewModelProvider(this).get(
            CalculatorViewModel::class.java
        )
        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Histórico Operações"
        return binding.root
    }

    override fun onStart(){
        super.onStart()
        //listaHistorico.add(OperationUI("5+12","17",10))
        if (arguments == null){
            Log.i(TAG, "LISTA VAZIA")
        }
        //operations?.let { it1 -> adapter.updateItems(it1) }
        viewModel.getHistory { operations?.let { it1 -> adapter.updateItems(it1) } }
        //operations?.let { adapter.updateItems(it) }
        binding.rvHistoricPortrait.layoutManager = LinearLayoutManager(activity as Context)
        binding.rvHistoricPortrait.adapter = adapter

    }

    override fun onResume() {
        super.onResume()
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR
    }

    override fun onPause() {
        super.onPause()
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR
    }
    private fun onOperationClick(operation: OperationUI){

        Toast.makeText(activity,"${operation.expression} = ${operation.result}", Toast.LENGTH_LONG).show()
    }

    private fun onOperationLongClick(operation : OperationUI){
        val sdf = SimpleDateFormat("dd/M/yyyy - hh:mm:ss")
        var currentDate = sdf.format(Date())
        Toast.makeText(activity, currentDate, Toast.LENGTH_LONG).show()
    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HistoryFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(operations : ArrayList<OperationUI>) =
            HistoryFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(ARG_OPERATIONS, operations)
                    //operations.add(OperationUI("10-7","3",10))
                    //adapter.updateItems(ArrayList(arguments?.getParcelableArrayList("ARG_OPERATIONS")))
                }
            }
    }
}