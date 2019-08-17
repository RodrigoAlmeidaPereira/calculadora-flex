package br.com.fiapi.rodrigo.calculadora_flex.ui.form

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.fiapi.rodrigo.calculadora_flex.R
import br.com.fiapi.rodrigo.calculadora_flex.ui.result.ResultActivity
import br.com.fiapi.rodrigo.calculadora_flex.watchers.DecimalTextWatcher
import kotlinx.android.synthetic.main.activity_form.*

class FormActivity : AppCompatActivity() {

    companion object {
        val GAS_PRICE_KEY = "GAS_PRICE"
        val ETHANOL_PRICE_KEY = "ETHANOL_PRICE"
        val GAS_AVERAGE_KEY = "GAS_AVERAGE"
        val ETHANOL_AVERAGE_KEY = "ETHANOL_AVERAGE"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        etGasPrice.addTextChangedListener(DecimalTextWatcher(etGasPrice))
        etEthanolPrice.addTextChangedListener(DecimalTextWatcher(etEthanolPrice))
        etGasAverage.addTextChangedListener(DecimalTextWatcher(etGasAverage))
        etEthanolAverage.addTextChangedListener(DecimalTextWatcher(etEthanolAverage))

        btCalculate.setOnClickListener {
            val nextScreen = Intent(this@FormActivity, ResultActivity::class.java)
            nextScreen.putExtra(GAS_PRICE_KEY, etGasPrice.text.toString().toDouble())
            nextScreen.putExtra(ETHANOL_PRICE_KEY, etEthanolPrice.text.toString().toDouble())
            nextScreen.putExtra(GAS_AVERAGE_KEY, etGasAverage.text.toString().toDouble())
            nextScreen.putExtra(ETHANOL_AVERAGE_KEY, etEthanolAverage.text.toString().toDouble())
            startActivity(nextScreen)
        }
    }
}
