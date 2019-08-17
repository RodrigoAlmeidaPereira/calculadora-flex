package br.com.fiapi.rodrigo.calculadora_flex.ui.extensions

fun Double.format(digits: Int) = java.lang.String.format("%.${digits}f", this)