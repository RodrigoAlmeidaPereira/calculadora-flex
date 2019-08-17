package br.com.fiapi.rodrigo.calculadora_flex.utils

import com.google.firebase.database.FirebaseDatabase

class DatabaseUtil {
    companion object {

        private val firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()

        init {
            firebaseDatabase.setPersistenceEnabled(true)
        }

        fun getDatabase() : FirebaseDatabase {
            return firebaseDatabase
        }
    }
}