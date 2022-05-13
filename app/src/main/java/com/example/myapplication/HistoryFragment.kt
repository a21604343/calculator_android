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
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.FragmentCalculatorBinding
import com.example.myapplication.databinding.FragmentHistoryBinding
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
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
        viewModel = ViewModelProvider(activity as ViewModelStoreOwner).get(
            CalculatorViewModel::class.java
        )
        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Histórico Operações"
        return binding.root
    }

    override fun onStart(){
        super.onStart()
        getAllOperationsWs { updateList(it) }
        //viewModel.getHistory { adapter.updateItems(it) }




        binding.rvHistoricPortrait.layoutManager = LinearLayoutManager(activity as Context)
        binding.rvHistoricPortrait.adapter = adapter

    }

    private fun updateList(fires : List<OperationUI>){


        CoroutineScope(Dispatchers.Main).launch {
            adapter.updateItems(fires)
        }
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


    private fun getAllOperationsWs(callback: (List<OperationUI>) -> Unit){
        data class GetAllOperationsResponse(val uuid : String, val expression : String, val result: Double, val timestamp : Long)

        CoroutineScope(Dispatchers.IO).launch {
            val request : Request = Request.Builder()
                .url("https://cm-calculadora.herokuapp.com/api/operations")
                .addHeader("apikey","8270435acfead39ccb03e8aafbf37c49359dfbbcac4ef4769ae82c9531da0e17")
                .build()

                val response = OkHttpClient().newCall(request).execute().body
                if (response != null){
                    val responseObj = Gson().fromJson(response.string(),
                        Array<GetAllOperationsResponse>::class.java).toList()
                    callback(responseObj.map {
                        OperationUI(it.uuid,it.expression,it.result,it.timestamp)
                    })

                }
        }

    }

    companion object {

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