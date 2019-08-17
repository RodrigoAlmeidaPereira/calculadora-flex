package br.com.fiapi.rodrigo.calculadora_flex.ui.splash

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import br.com.fiapi.rodrigo.calculadora_flex.R
import br.com.fiapi.rodrigo.calculadora_flex.ui.form.FormActivity
import br.com.fiapi.rodrigo.calculadora_flex.ui.login.LoginActivity
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    private val DELAY_TIME = 3500L
    private val USER_PEFERENCES_KEY = "user_preferences"
    private val OPEN_FIRST_KEY = "open_first"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        var preferences = getSharedPreferences(USER_PEFERENCES_KEY, Context.MODE_PRIVATE)

        val isFirstOpen = preferences.getBoolean(OPEN_FIRST_KEY, true)
        if (isFirstOpen) {
            this.markAppAlreadyOpen(preferences)
            this.showSplash()
        } else {
            this.showLogin()
        }
    }

    private fun markAppAlreadyOpen(preferences: SharedPreferences) {
        preferences
            .edit()
            .putBoolean(OPEN_FIRST_KEY, false)
            .apply()

    }

    private fun showLogin() {
        val nextScreen = Intent(this@SplashActivity, LoginActivity::class.java)
        startActivity(nextScreen)
        finish()
    }

    private fun showSplash() {
        val animation = AnimationUtils.loadAnimation(this, R.anim.animacao_splash)
        animation.reset()
        ivLogo.clearAnimation()
        ivLogo.startAnimation(animation)

        Handler().postDelayed({
            val nextScreen = Intent(this@SplashActivity, FormActivity::class.java)
            startActivity(nextScreen)
            finish()
        }, DELAY_TIME)
    }

}
