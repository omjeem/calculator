package com.example.calc

import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.example.calc.databinding.ActivityMainBinding
import net.objecthunter.exp4j.Expression
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var lastNumeric = false
    var stateError = false
    var lastDot = false

    private lateinit var expression: Expression
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun onClearClick(view: View) {
        binding.textCalculation.text = ""
        lastNumeric = false
    }

    fun onBackClick(view: View) {
        binding.textCalculation.text = binding.textCalculation.text.toString().dropLast(1)
        try {
            val lastChar = binding.textCalculation.text.toString().last()
            if (lastChar.isDigit()) {
                onEquals()
            }
        } catch (e: Exception) {
            binding.textResult.text = ""
            binding.textResult.visibility = View.GONE
            Log.e("Last char error", e.toString())
        }

    }

    fun onOperatorClick(view: View) {
        if (!stateError && lastNumeric) {
            binding.textCalculation.append((view as Button).text)
            lastDot = false
            lastNumeric = false
            onEquals()
        }
    }

    fun onEqualClick(view: View) {
        onEquals()
        binding.textCalculation.text = binding.textResult.text.toString().drop(1)
    }

    fun onDigitClick(view: View) {
        if (stateError) {
            binding.textCalculation.text = (view as Button).text
            stateError = false
        } else {
            binding.textCalculation.append((view as Button).text)
        }
        lastNumeric = true
        //binding.textCalculation.text = binding.textResult.text.toString() // Update the calculation text
        onEquals()
    }

    fun onAllClearClick(view: View) {
        binding.textResult.text = ""
        binding.textCalculation.text = ""
        stateError = false
        lastDot = false
        lastNumeric = false
        binding.textResult.visibility = View.GONE
    }

        fun onEquals(){
        if(lastNumeric && !stateError){
            val txt = binding.textCalculation.text.toString()
            expression = ExpressionBuilder(txt).build()
            try{
                val result = expression.evaluate()
                binding.textResult.visibility=View.VISIBLE
                binding.textResult.text="="+ result.toString()
            }catch (ex:ArithmeticException){
                Log.e("evaluate error",ex.toString())
                binding.textResult.text="Error"
                stateError=true
                lastNumeric=false
            }
        }
    }







}