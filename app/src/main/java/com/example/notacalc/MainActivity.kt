package com.example.notacalc

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.notacalc.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        addBtnAddOneListener()
        onChangeHandler()
    }

    class Estudiante{
        var nombre: String
        var curso: String
        var nota1: Double
        var nota2: Double
        var notaFinal: Double

        constructor(nombre: String, curso: String, nota1: Double, nota2: Double, handicap: Boolean){
            // Inicializando lo básico
            this.nombre = nombre
            this.curso = curso
            this.nota1 = nota1
            this.nota2 = nota2

            // Ahora si ole, a calcular 😈
            this.notaFinal = this.calculaTuNotaFinal(handicap)
        }

        fun calculaTuNotaFinal(handicap: Boolean): Double {
            return (nota1 + nota2) / 2 - if(handicap) 0.5 else 0.0
        }
    }

    private fun imprimirInformacion(estudiante: Estudiante){
        binding.tvResultado.text =
                "Nombre: ${estudiante.nombre}\n" +
                "Curso: ${estudiante.curso}\n" +
                "Nota 1: ${estudiante.nota1}\n" +
                "Nota 2: ${estudiante.nota2}\n" +
                "Nota Final: ${estudiante.notaFinal}"
    }

    private fun limpiarFormulario(){
        binding.etNombreEstudiante.text = null
        binding.etNombreCurso.text = null
        binding.etNota1.text = null
        binding.etNota2.text = null
        binding.cbRepeticion.isChecked = false
    }

    val formWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            binding.btnCalcularNota.isEnabled = binding.etNombreEstudiante.text.toString().isNotEmpty() &&
                    binding.etNombreCurso.text.toString().isNotEmpty() &&
                    binding.etNota1.text.toString().isNotEmpty() &&
                    binding.etNota2.text.toString().isNotEmpty()
        }
        override fun afterTextChanged(s: Editable?) {
        }
    }

    private fun onChangeHandler(){
        binding.etNombreEstudiante.addTextChangedListener(formWatcher)
        binding.etNombreCurso.addTextChangedListener(formWatcher)
        binding.etNota1.addTextChangedListener(formWatcher)
        binding.etNota2.addTextChangedListener(formWatcher)
    }

    private fun addBtnAddOneListener(){
        binding.btnCalcularNota.setOnClickListener {
            val estudiante = Estudiante(
                binding.etNombreEstudiante.text.toString(),
                binding.etNombreCurso.text.toString(),
                binding.etNota1.text.toString().toDouble(),
                binding.etNota2.text.toString().toDouble(),
                binding.cbRepeticion.isChecked
            )
            imprimirInformacion(estudiante)
            limpiarFormulario()
        }
    }
}