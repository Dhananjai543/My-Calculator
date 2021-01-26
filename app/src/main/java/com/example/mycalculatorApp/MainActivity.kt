package com.example.mycalculatorApp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {
    private var lastNumeric = false
    private var lastDot = false
    private var lastEquals = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onBackspace(view: View){
        if(tvInput.text.isEmpty()){
            return
        }
        tvInput.text = tvInput.text.substring(0,tvInput.text.length-1)
    }
    fun onDigit(view: View){
        if(lastEquals){
            tvInput.text = ""
            lastEquals = false
        }

        tvInput.append((view as Button).text)
        lastNumeric = true
    }
    fun onClear(view: View){
        tvInput.setText("")
        lastNumeric = false
        lastDot = false
        lastEquals = false
    }

    fun onDecimalPoint(view: View){
        if(lastNumeric && !lastDot){
            tvInput.append(".")
            lastNumeric = false
            lastDot = true
        }
    }

    fun onEqual(view: View){
        lastEquals = true
        if(lastNumeric){
            var tvValue = tvInput.text.toString()
            var prefix = ""
            try {
                if(tvValue.startsWith("-")){
                    prefix = "-"
                    tvValue = tvValue.substring(1)
                }
                if(tvValue.contains("-")){
                    val splitValue = tvValue.split("-")
                    var one = splitValue[0]
                    var two = splitValue[1]

                    if(!prefix.isEmpty()){
                        one = prefix + one
                    }

                    tvInput.text = removeZeroesAfterDot((one.toDouble() - two.toDouble()).toString())
                }else if(tvValue.contains("*")){
                    val splitValue = tvValue.split("*")
                    var one = splitValue[0]
                    var two = splitValue[1]

                    if(!prefix.isEmpty()){
                        one = prefix + one
                    }

                    tvInput.text = removeZeroesAfterDot((one.toDouble() * two.toDouble()).toString())
                }
                else if(tvValue.contains("+")){
                    val splitValue = tvValue.split("+")
                    var one = splitValue[0]
                    var two = splitValue[1]

                    if(!prefix.isEmpty()){
                        one = prefix + one
                    }

                    tvInput.text = removeZeroesAfterDot((one.toDouble() + two.toDouble()).toString())
                }
                else if(tvValue.contains("/")){
                    val splitValue = tvValue.split("/")
                    var one = splitValue[0]
                    var two = splitValue[1]

                    if(!prefix.isEmpty()){
                        one = prefix + one
                    }

                    tvInput.text = removeZeroesAfterDot((one.toDouble() / two.toDouble()).toString())
                }
                else if(tvValue.contains("%")){
                    val splitValue = tvValue.split("%")
                    var one = splitValue[0]
                    var two = splitValue[1]

                    if(!prefix.isEmpty()){
                        one = prefix + one
                    }

                    tvInput.text = removeZeroesAfterDot((one.toDouble() % two.toDouble()).toString())
                }
            }catch(e: ArithmeticException){
                e.printStackTrace()
            }
        }
    }

    private fun removeZeroesAfterDot(result: String) : String{
        var value = result
        if(result.contains(".0")){
            value = result.substring(0,result.length - 2)
        }
        return value
    }
    fun onOperator(view: View){
        if(lastNumeric && !isOperatorAdded(tvInput.text.toString())){
            tvInput.append((view as Button).text)
            lastNumeric = false
            lastDot = false
            lastEquals = false
        }
    }

    private fun isOperatorAdded(value: String) : Boolean{
        return if (value.startsWith("-")){
            false
        }else{
            value.contains("/") || value.contains("*") || value.contains("+") ||
                    value.contains("-") || value.contains("%")
        }
    }
}