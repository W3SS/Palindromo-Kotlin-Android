package aquodev.textopalindromo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*   // Para el control de botones
import java.text.Normalizer                             // Para 'normalizarTexto()'

class MainActivity : AppCompatActivity() {

    // Variables globales
    private var contador = 0                // Contador de palíndromos, tanto para palabras como para frases
    private var texto = ""                  // Texto introducido en el área
    private var textoAnterior = ""          // Para evitar cálculos sobre el mismo texto repetidamente
    private var valorBtnUltimo: Byte = 0    // Para conocer cuál es el último botón presionado
    private var contPalabras = 0            // Contador de palabras en total del texto
    private var contFrases = 0              // Contador de frases en total del texto
    private val finalFrase = '.'            // Caracter final de frase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Control de botones: Contar palabras palíndromas
        btnContarPalindromos.setOnClickListener {
            when {
                textoVacio() -> establecerMensaje("No has introducido ningún texto")
                textoSinRepetir(1) -> {
                    almacenarTextoOriginal()
                    normalizarTexto()
                    contarPalabrasPalindromas()
                    mostrarDatosPalindromos()
                }
            }

            if (txtArea.hasFocus()) {
                txtArea.clearFocus()    // Quita la atención al área de texto
            }
        }

        // Control de botones: Contar frases palíndromas
        btnContarFrasesPalind.setOnClickListener {
            when {
                textoVacio() -> establecerMensaje("No has introducido ninguna frase (las frases han de acabar en punto)")
                textoSinRepetir(2) -> {
                    almacenarTextoOriginal()
                    normalizarTexto()
                    contarFrasesPalindromas()
                    mostrarDatosFrasesPalindromas()
                }
            }

            if (txtArea.hasFocus()) {
                txtArea.clearFocus()    // Quita la atención al área de texto
            }
        }

        // Control de botones: Limpiar texto
        btnLimpiar.setOnClickListener {
            when {
                textoVacio() -> establecerMensaje("El texto ya estaba en blanco, introduce algo y prueba")
                else -> {
                    txtArea.text.clear()
                    establecerMensaje("¡Texto en blanco!")
                }
            }

            if (txtArea.hasFocus()) {
                txtArea.clearFocus()    // Quita la atención al área de texto
            }
        }
    }


    // Métodos para contar palabras palíndromas
    private fun contarPalabrasPalindromas() {
        contador = 0                    // Se reinicia el contador de palíndromos
        contPalabras = 0                // Se reinicia el contador de palabras en total
        var palabra: String             // Palabra que será evaluada si es palíndroma o no
        var caracter: Char              // Caracter que es comparado en la condición

        var i = 0
        while (i < texto.length) {
            caracter = texto[i]                                     // Se almacena el valor del caracter actual
            if (caracter.isLetterOrDigit()) {                       // Si el caracter es una letra o dígito:
                palabra = extraerPalabra(i)                         // Extrae la palabra a partir de ese caracter
                contador += if (esPalindromo(palabra)) 1 else 0     // Se suma 1 si la palabra es palíndroma
                contPalabras++                                      // Al haber extraído la palabra, el contador total aumenta
                i += palabra.length - 1                             // El primer índice se vuelve el caracter posterior a la palabra
            }
            i++
        }

        texto = ""
        // Se vacía la variable para no mantenerla guardada en memoria una vez acabado el proceso
    }

    private fun extraerPalabra(primerIndice: Int): String {
        var caracter: Char                          // Primer caracter de la palabra
        var ultimoIndice = primerIndice             // Índice que va incrementando hasta que no sea una letra o dígito el caracter

        for (i in primerIndice until texto.length) {
            caracter = texto[i]                     // Se recorre el texto, leyendo cada caracter
            ultimoIndice++

            if (!caracter.isLetterOrDigit()) {
                ultimoIndice--
                break
            }
        }

        return texto.substring(primerIndice, ultimoIndice)
        // Devuelve la palabra
    }

    private fun mostrarDatosPalindromos() {
        val nuevoMensaje = StringBuilder()
        // Mensaje que será establecido con los datos de los contadores

        nuevoMensaje.append(
                when (contador) {
                    0 -> "Ningún palíndromo"
                    1 -> "1 palíndromo"
                    else -> contador.toString() + " palíndromos"
                }
        )

        nuevoMensaje.append(" | ") // Separador

        nuevoMensaje.append(
                when (contPalabras) {
                    0 -> "Ninguna palabra"
                    1 -> "1 palabra en total"
                    else -> contPalabras.toString() + " palabras en total"
                }
        )

        establecerMensaje(nuevoMensaje.toString())
        valorBtnUltimo = 1    // Valor del botón pulsado
    }


    // Métodos para contar frases palíndromas
    private fun contarFrasesPalindromas() {
        contador = 0                    // Se reinicia el contador de frases palíndromas
        contFrases = 0                  // Se reinicia el contador de frases en total
        var frase: String               // Frase que será evaluada si es palíndroma o no
        var caracter: Char              // Caracter que es comparado en la condición

        var i = 0
        for (j in texto.indices) {
            caracter = texto[j]                                 // Se almacena el valor del caracter actual
            if (caracter == finalFrase) {                       // Si encuentra el caracter inicial de una frase:
                frase = extraerFrase(i)                         // Extrae la frase sin espacios desde el primer caracter
                contador += if (esPalindromo(frase)) 1 else 0   // Se suma 1 si la frase es palíndroma
                contFrases += if (frase.isNotEmpty()) 1 else 0  // Al haber extraído la frase, el contador de frases aumenta
                i = j + 1                                       // El primer índice se vuelve el caracter posterior al punto
            }
        }

        texto = ""
        // Se vacía la variable para no mantenerla guardada en memoria una vez acabado el proceso
    }

    private fun extraerFrase(primerIndice: Int): String {
        var caracter: Char  // Caracter comparado en la condición
        val fraseSinEspacios = StringBuilder()

        for (i in primerIndice until texto.length) {    // Se recorre la frase caracter a caracter
            caracter = texto[i]

            if (caracter.isLetterOrDigit()) {           // Si el caracter es un dígito o una letra:
                fraseSinEspacios.append(caracter)       // Se añade al conjunto de caracteres que forma la frase
            } else if (caracter == finalFrase) {
                break
            }
        }

        return fraseSinEspacios.toString()
    }

    private fun mostrarDatosFrasesPalindromas() {
        val nuevoMensaje = StringBuilder()
        // Mensaje que será establecido con los datos de los contadores

        nuevoMensaje.append(
                when (contador) {
                    0 -> "Ninguna frase palíndroma"
                    1 -> "1 frase palíndroma"
                    else -> contador.toString() + " frases palíndromas"
                }
        )

        nuevoMensaje.append(" | ")  // Separador

        nuevoMensaje.append(
                when (contFrases) {
                    0 -> "Ninguna frase (las frases han de acabar en punto)"
                    1 -> "1 frase en total"
                    else -> contFrases.toString() + " frases en total"
                }
        )

        establecerMensaje(nuevoMensaje.toString())
        valorBtnUltimo = 2    // Valor del botón pulsado
    }


    // Métodos generales
    private fun establecerMensaje(s: String) {
        mensaje.text = s
    }

    private fun textoVacio(): Boolean {
        return txtArea.text.isEmpty()
    }

    private fun textoSinRepetir(valorBtnPulsado: Byte): Boolean {
        return txtArea.text.toString() != textoAnterior || valorBtnUltimo != valorBtnPulsado
    }

    private fun almacenarTextoOriginal() {
        textoAnterior = txtArea.text.toString()
    }

    private fun normalizarTexto() {
        texto = txtArea.text.toString().toLowerCase().trim()
        // El texto de 'txtArea' se extrae, se pasa a minúscula y sin espacios al principio y al final

        texto = Normalizer.normalize(texto, Normalizer.Form.NFKD)
        texto = texto.replace("[^\\p{ASCII}]".toRegex(), "")
        // Extraemos todas las tildes y acentos de las palabras del texto, y guardamos dicho texto
    }
}
