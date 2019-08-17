package br.com.fiapi.rodrigo.calculadora_flex.ui.result

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import br.com.fiapi.rodrigo.calculadora_flex.R
import br.com.fiapi.rodrigo.calculadora_flex.ui.extensions.format
import br.com.fiapi.rodrigo.calculadora_flex.ui.form.FormActivity
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        if (intent.extras == null) {
            Toast.makeText(this, "Não foi possível realizar a operação", Toast.LENGTH_SHORT).show()
        } else {
            calculate()
        }
    }

    private fun calculate() {
        val gasPrice = intent.extras?.getDouble(FormActivity.GAS_PRICE_KEY) ?: 1.0
        val ethanolPrice = intent.extras?.getDouble(FormActivity.ETHANOL_PRICE_KEY) ?: 0.0
        val gasAverage = intent.extras?.getDouble(FormActivity.GAS_AVERAGE_KEY) ?: 1.0
        val ethanolAverage = intent.extras?.getDouble(FormActivity.ETHANOL_AVERAGE_KEY) ?: 0.0

        val performanceOfMyCar = ethanolAverage / gasAverage
        val priceOfFuelIndex = ethanolPrice / gasPrice

        if (priceOfFuelIndex <= performanceOfMyCar) {
            tvSuggestion.text = getString(R.string.ethanol)
        } else {
            tvSuggestion.text = getString(R.string.gasoline)
        }

        tvGasAverageResult.text = (gasPrice / gasAverage).format(2)
        tvEthanolAverageResult.text = (ethanolPrice / ethanolAverage).format(2)
        tvFuelRatio.text = getString(R.string.label_fuel_ratio,performanceOfMyCar.format(2))
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}
