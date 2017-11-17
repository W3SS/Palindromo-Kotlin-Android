package aquodev.textopalindromo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*   // Para el control de botones


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Control de botones: Contar palabras palíndromas
        btnContarPalindromos.setOnClickListener { ejecutarFuncion("btnContarPalindromos") }

        // Control de botones: Contar frases palíndromas
        btnContarFrasesPalind.setOnClickListener { ejecutarFuncion("btnContarFrasesPalind") }

        // Control de botones: Limpiar texto
        btnLimpiar.setOnClickListener { ejecutarFuncion("btnLimpiar") }
    }

    // Cambia el mensaje que se muestra
    private fun establecerMensaje(s: String) {
        mensaje.text = s
    }

    // Determina la función del botón pulsado y establece el mensaje con los datos
    private fun ejecutarFuncion(boton: String) {
        val datos: String? = when (boton) {
            "btnContarPalindromos" -> funcionPalabras()
            "btnContarFrasesPalind" -> funcionFrases()
            "btnLimpiar" -> funcionLimpiarTexto()
            else -> throw IllegalArgumentException("El botón $boton no existe")
            // En caso de no existir el botón, se lanza un IAE con el mensaje para
            // leerlo en el debugger
        }

        if (datos != null) establecerMensaje(datos)
        // Se establece el mensaje de datos si no se está pulsando el mismo botón
        // dos veces seguidas (null indica este caso)

        if (txtArea.hasFocus()) txtArea.clearFocus()
        // Si 'txtArea' está pulsado, es desmarcado
    }
}
