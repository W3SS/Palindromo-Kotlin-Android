package aquodev.textopalindromo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*   // Para el control de botones


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Control de botones: Contar palabras palíndromas
        btnContarPalindromos.setOnClickListener {
            contarYMostrarDatosPalabras()
            desmarcarTxtArea()
        }

        // Control de botones: Contar frases palíndromas
        btnContarFrasesPalind.setOnClickListener {
            contarYMostrarDatosFrases()
            desmarcarTxtArea()
        }

        // Control de botones: Limpiar texto
        btnLimpiar.setOnClickListener {
            limpiarTexto()
            desmarcarTxtArea()
        }


    }

    // Función para cambiar el mensaje que se muestra
    private fun establecerMensaje(s: String) {
        mensaje.text = s
    }

    // Función para que deje de marcarse 'txtArea' si está marcado
    private fun desmarcarTxtArea() {
        if (txtArea.hasFocus()) {
            txtArea.clearFocus()    // Quita la atención al área de texto
        }
    }

    // Función que se ejecuta al pulsar 'btnContarPalindromos'
    private fun contarYMostrarDatosPalabras() {
        val datos: String? = funcionPalabras(this)

        if (datos != null) establecerMensaje(datos)
    }

    // Función que se ejecuta al pulsar 'btnContarFrasesPalind'
    private fun contarYMostrarDatosFrases() {
        val datos: String? = funcionFrases(this)

        if (datos != null) establecerMensaje(datos)
    }

    // Función que se realiza al pulsar 'btnLimpiar'
    private fun limpiarTexto() {
        val datos = funcionLimpiarTexto(this)

        if (datos != null) establecerMensaje(datos)
    }
}
