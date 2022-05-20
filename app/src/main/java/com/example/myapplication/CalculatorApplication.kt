package com.example.myapplication

import android.app.Application

class CalculatorApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        CalculatorRepository.init(this,
            CalculatorRoom(CalculatorDatabase.getInstance(this).operationDao()),
            CalculatorRetrofit(RetrofitBuilder.getInstance("https://cm-calculadora.herokuapp.com/api/")
            )
        )
        FusedLocation.start(this)
        Light.start(this)
        Battery.start(this)
    }

}