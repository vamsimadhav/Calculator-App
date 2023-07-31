package com.vmh.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.vmh.calculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var tvInput: TextView

    private var dotPresent = false
    private var lastDigitNumeric = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        tvInput = binding.tvInput
    }

    fun onDigit(view: View){
        tvInput.append((view as Button).text)
        lastDigitNumeric = true
    }

    fun onAC(view: View){
        tvInput.text = ""
    }

    fun onClr(view: View){
        val s = tvInput.text
        s.dropLast(1)
        tvInput.text = s
    }

    fun onDecimal(view: View){
        if(!dotPresent && lastDigitNumeric){
            tvInput.append((view as Button).text)
            dotPresent = true
        }
    }

    fun onEquals(view: View){
        if(lastDigitNumeric){
            var tvValue = tvInput.text.toString()

            var prefix = ""

            var ans = ""

            if(tvValue.startsWith('-')){
                prefix = "-"
                tvValue = tvValue.substring(1)
            }

            if(tvValue.contains("-")){
                val splitValue = tvValue.split("-")

                var one = splitValue[0]
                var two = splitValue[1]

                if(prefix.isNotEmpty()){
                    one = prefix + one
                }

                ans = (one.toDouble() - two.toDouble()).toString()
                tvInput.text = removeZeroAfterDot(ans)
            }else if(tvValue.contains("+")){
                val splitValue = tvValue.split("+")

                var one = splitValue[0]
                var two = splitValue[1]

                if(prefix.isNotEmpty()){
                    one = prefix + one
                }

                ans = (one.toDouble() + two.toDouble()).toString()
                tvInput.text = removeZeroAfterDot(ans)
            }else if(tvValue.contains("/")){
                val splitValue = tvValue.split("/")

                var one = splitValue[0]
                var two = splitValue[1]

                if(prefix.isNotEmpty()){
                    one = prefix + one
                }

                ans = (one.toDouble() / two.toDouble()).toString()
                tvInput.text = removeZeroAfterDot(ans)
            }else if(tvValue.contains("*")){
                val splitValue = tvValue.split("*")

                var one = splitValue[0]
                var two = splitValue[1]

                if(prefix.isNotEmpty()){
                    one = prefix + one
                }

                ans = (one.toDouble() * two.toDouble()).toString()
                tvInput.text = removeZeroAfterDot(ans)
            }else if(tvValue.contains("%")){
                val splitValue = tvValue.split("%")

                var one = splitValue[0]
                var two = splitValue[1]

                if(prefix.isNotEmpty()){
                    one = prefix + one
                }

                ans = (one.toDouble() % two.toDouble()).toString()
                tvInput.text = removeZeroAfterDot(ans)
            }
        }
    }

    private fun removeZeroAfterDot(result: String): String{
        if(result.contains(".0")){
            return result.substring(0,result.length-2)
        }
        return result
    }

    fun onOperator(view: View){
        tvInput.text?.let {
            if(lastDigitNumeric && !isOperatorAdded(it.toString())){
                tvInput.append((view as Button).text)
                lastDigitNumeric = false
                dotPresent = false
            }
        }
    }

    fun isOperatorAdded(operator: String): Boolean{
        return if(operator.startsWith('-')){
            false
        }else{
                    operator.contains('/') ||
                    operator.contains('+') ||
                    operator.contains('-') ||
                    operator.contains('*') || operator.contains('%')
        }
    }
}