package com.example.myapplication


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import com.example.myapplication.databinding.ActivityMainBinding
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    //object listaHistorico : ArrayList<OperationUI>()
    var listaHistorico : ArrayList<OperationUI> = ArrayList()
    private lateinit var binding: ActivityMainBinding
    private val TAG = MainActivity::class.java.simpleName
    var instancia : Bundle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i(TAG, "onCreated invoke")
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState != null) {
            instancia = savedInstanceState
        }
        if(screenRotated(savedInstanceState)){
            Log.i(TAG, "screenRotated1")
            NavigationManager.goToCalculatorFragment(supportFragmentManager)
        }


    }

    override fun onStart(){
        super.onStart()
        Log.i(TAG, "onStart invoke")
        //listaHistorico.add(OperationUI("10+10",20.0,10))
        setSupportActionBar(binding.toolbar)
        setupDrawerMenu()
        if(!screenRotated(instancia)){
            Log.i(TAG, "screenRotated2")
            NavigationManager.goToCalculatorFragment(supportFragmentManager)
        }

    }

    override fun onBackPressed() {
        if(binding.drawer.isDrawerOpen(GravityCompat.START)){
            binding.drawer.closeDrawer(GravityCompat.START)
        }else if(supportFragmentManager.backStackEntryCount == 1){
            finish()
        }else{

            super.onBackPressed()
        }
    }

    private fun screenRotated(savedInstanceState: Bundle?): Boolean {
    return savedInstanceState != null
    }

    private fun setupDrawerMenu(){
        val toggle = ActionBarDrawerToggle(this,binding.drawer,binding.toolbar,R.string.drawer_open,R.string.drawer_close)

        binding.navDrawer.setNavigationItemSelectedListener {
            onClickNavigationItem(it)
        }
        binding.drawer.addDrawerListener(toggle)
        toggle.syncState()
    }

    private fun onClickNavigationItem(item : MenuItem) : Boolean {
        when(item.itemId) {
            R.id.nav_calculator ->
                NavigationManager.goToCalculatorFragment(supportFragmentManager)

            R.id.nav_history -> {
                NavigationManager.goToHistoryFragment(supportFragmentManager,listaHistorico)
            }

        }
        binding.drawer.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onDestroy() {
        Log.i(TAG, "onDestroy inkoke")
        super.onDestroy()
    }


}





    /*


    val listaHistorico: MutableList<OperationUI> = mutableListOf()
    private val adapter = HistoryAdapter(::onOperationLongClick, ::onOperationClick)
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


        binding.rvHistoric?.layoutManager = LinearLayoutManager(this)
        binding.rvHistoric?.adapter = adapter





    }

    override fun onDestroy() {
        Log.i(TAG, "onDestroy inkoke")
        super.onDestroy()
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

        Toast.makeText(this,"${operation.expression} = ${operation.result}",Toast.LENGTH_LONG).show()
    }

    private fun onOperationLongClick(operation : OperationUI){
        val sdf = SimpleDateFormat("dd/M/yyyy - hh:mm:ss")
        var currentDate = sdf.format(Date())
        Toast.makeText(this, currentDate,Toast.LENGTH_LONG).show()
    }

     */


