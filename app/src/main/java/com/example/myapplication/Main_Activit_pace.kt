package com.example.myapplication

    import android.annotation.SuppressLint
    import androidx.appcompat.app.AppCompatActivity
    import android.os.Bundle
    import android.widget.Button
    import android.widget.EditText
    import android.widget.TextView
    import android.widget.Toast
    import java.text.SimpleDateFormat

    class MainActivity : AppCompatActivity() {

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)

            val edt_Distancia: EditText = findViewById(R.id.distanciaKM)
            val edt_Tempo: EditText = findViewById(R.id.tempoMin)
            val btn_calcular: Button = findViewById(R.id.calcular)
            val tv_result: TextView = findViewById(R.id.pace)

            btn_calcular.setOnClickListener {

                val tempoStr = edt_Tempo.text.toString()
                val distanciaStr = edt_Distancia.text.toString()

                if (tempoStr.isNotEmpty() && distanciaStr.isNotEmpty()) {

                    val tempoMinutos = formatarTempo(tempoStr).toString().toLong()
                    val distanciaKM = distanciaStr.toString().toLong()
                    val paceMedio = tempoMinutos / distanciaKM

                    tv_result.text = "${formatarSegundos(paceMedio)}"


                } else {
                    Toast.makeText(this, "Preecha todos os campos", Toast.LENGTH_LONG).show()
                }

            }
        }
        // Converter os Segundos em Horas
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
    }
}