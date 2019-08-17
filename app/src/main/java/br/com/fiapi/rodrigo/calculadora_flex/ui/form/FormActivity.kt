package br.com.fiapi.rodrigo.calculadora_flex.ui.form

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import br.com.fiapi.rodrigo.calculadora_flex.R
import br.com.fiapi.rodrigo.calculadora_flex.model.CarData
import br.com.fiapi.rodrigo.calculadora_flex.ui.login.LoginActivity
import br.com.fiapi.rodrigo.calculadora_flex.ui.result.ResultActivity
import br.com.fiapi.rodrigo.calculadora_flex.utils.DatabaseUtil
import br.com.fiapi.rodrigo.calculadora_flex.watchers.DecimalTextWatcher
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_form.*

class FormActivity : AppCompatActivity() {

    companion object {
        val GAS_PRICE_KEY = "GAS_PRICE"
        val ETHANOL_PRICE_KEY = "ETHANOL_PRICE"
        val GAS_AVERAGE_KEY = "GAS_AVERAGE"
        val ETHANOL_AVERAGE_KEY = "ETHANOL_AVERAGE"
    }

    private lateinit var userId : String
    private lateinit var mAuth: FirebaseAuth
    private val firebaseReferenceNode = "CarData"
    private val defaultClearValueText = "0.0"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        mAuth = FirebaseAuth.getInstance()
        userId = mAuth.currentUser?.uid ?: ""
        listenerFirebaseRealtime()

        etGasPrice.addTextChangedListener(DecimalTextWatcher(etGasPrice))
        etEthanolPrice.addTextChangedListener(DecimalTextWatcher(etEthanolPrice))
        etGasAverage.addTextChangedListener(DecimalTextWatcher(etGasAverage))
        etEthanolAverage.addTextChangedListener(DecimalTextWatcher(etEthanolAverage))

        btCalculate.setOnClickListener {
            saveCarData()
            val nextScreen = Intent(this@FormActivity, ResultActivity::class.java)
            nextScreen.putExtra(GAS_PRICE_KEY, etGasPrice.text.toString().toDouble())
            nextScreen.putExtra(ETHANOL_PRICE_KEY, etEthanolPrice.text.toString().toDouble())
            nextScreen.putExtra(GAS_AVERAGE_KEY, etGasAverage.text.toString().toDouble())
            nextScreen.putExtra(ETHANOL_AVERAGE_KEY, etEthanolAverage.text.toString().toDouble())
            startActivity(nextScreen)
        }
    }

    private fun saveCarData() {
        val carData = CarData(
            etGasPrice.text.toString().toDouble(),
            etEthanolPrice.text.toString().toDouble(),
            etGasAverage.text.toString().toDouble(),
            etEthanolAverage.text.toString().toDouble()
        )

        DatabaseUtil.getDatabase()
            .getReference(firebaseReferenceNode)
            .child(userId)
            .setValue(carData)
    }

    private fun listenerFirebaseRealtime() {
        DatabaseUtil
            .getDatabase()
            .getReference(firebaseReferenceNode)
            .child(userId)
            .addValueEventListener(object : ValueEventListener {

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val carData = dataSnapshot.getValue(CarData::class.java)
                    etGasPrice.setText(carData?.gasPrice.toString())
                    etEthanolPrice.setText(carData?.ethanolPrice.toString())
                    etGasAverage.setText(carData?.gasAverage.toString())
                    etEthanolAverage.setText(carData?.ethanolAverage.toString())
                }

                override fun onCancelled(p0: DatabaseError) {
                }
            })
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.form_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            R.id.action_clear -> {
                clearData()
                return true
            }
            R.id.action_logout -> {
                logout()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun logout() {
        mAuth.signOut()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private fun clearData() {
        etGasPrice.setText(defaultClearValueText)
        etEthanolPrice.setText(defaultClearValueText)
        etGasAverage.setText(defaultClearValueText)
        etEthanolAverage.setText(defaultClearValueText)
        saveCarData()
    }
}
