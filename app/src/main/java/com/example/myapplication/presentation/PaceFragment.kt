package com.example.myapplication.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.myapplication.PaceApplication
import com.example.myapplication.R
import com.example.myapplication.data.AppDatabase
import com.example.myapplication.data.Pace
import com.example.myapplication.data.PaceDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat


class PaceFragment : Fragment() {

    private lateinit var database: AppDatabase
    private lateinit var dao: PaceDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = (requireActivity().application as PaceApplication).getDatabase()
        dao = database.paceDao()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pace, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val edt_Distancia: EditText = view.findViewById(R.id.distanciaKM)
        val edt_Tempo: EditText = view.findViewById(R.id.tempoMin)
        val btn_calcular: Button = view.findViewById(R.id.calcular)
        val tv_result: TextView = view.findViewById(R.id.pace)

        btn_calcular.setOnClickListener {

            if(isValidTimeFormat(edt_Tempo.text.toString())) {
                val tempoStr = edt_Tempo.text.toString()
                val distanciaStr = edt_Distancia.text.toString()

                if (tempoStr.isNotEmpty() && distanciaStr.isNotEmpty()) {

                    val tempoMinutos = formatarTempo(tempoStr).toString().toLong()
                    val distanciaKM = distanciaStr.toLong()
                    val paceMedio = tempoMinutos / distanciaKM

                    tv_result.text = "${formatarSegundos(paceMedio)}"

                    val pace = formatarSegundos(paceMedio)?.let { pace ->
                        Pace(
                            0,
                            tempoStr,
                            distanciaStr,
                            pace
                        )
                    }

                    CoroutineScope(Dispatchers.IO).launch {
                        dao.insert(pace!!)
                    }

                } else {
                    Toast.makeText(context, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, "Formato de tempo inválido", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun formatarSegundos(segundos: Long): String? {
        val horas = segundos / 3600
        val minutos = (segundos % 3600) / 60
        val segundos = segundos % 60

        val tempoString = String.format("%02d:%02d:%02d", horas, minutos, segundos)
        return tempoString
    }

    //Formatar o tempo
    @SuppressLint("SimpleDateFormat")
    private fun formatarTempo(time: String): Long {
        val formatoHoras = SimpleDateFormat("HH:mm:ss")
        val referencia = formatoHoras.parse("00:00:00")
        val dados = formatoHoras.parse(time)
        val segundos = (dados.time - referencia.time) / 1000L

        return segundos
    }

    fun isValidTimeFormat(input: String?): Boolean {
        val regex = Regex("^([01]d|2[0-3]):([0-5]d)$")
        return input?.matches(regex) ?: false
    }

    companion object {
        fun newInstance(): PaceFragment {
            return PaceFragment()
        }
    }
}