package xchasingwaves.textopalindromo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*   // Para el control de botones
import java.text.Normalizer                             // Para 'normalizarTexto()'

class MainActivity : AppCompatActivity() {

    // Variables globales
    private var contador = 0        // Contador de palíndromos, tanto para palabras como para frases
    private var texto = ""          // Texto introducido en el área
    private var lenTexto = 0        // Longitud del texto
    private var contPalabras = 0    // Contador de palabras en total del texto
    private var contFrases = 0      // Contador de frases en total del texto

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Control de botones: Contar palabras palíndromas
        btnContarPalindromos.setOnClickListener {
            when (txtArea.length()) {
                0 -> establecerMensaje("No has introducido ningún texto")
                else -> {
                    normalizarTexto()
                    contarPalabrasPalindromas()
                    mostrarDatosPalindromos()
                }
            }
        }

        // Control de botones: Contar frases palíndromas
        btnContarFrasesPalind.setOnClickListener {
            when (txtArea.length()) {
                0 -> establecerMensaje("No has introducido ninguna frase (las frases han de acabar en punto)")
                else -> {
                    normalizarTexto()
                    contarFrasesPalindromas()
                    mostrarDatosFrasesPalindromas()
                }
            }
        }

        // Control de botones: Limpiar texto
        btnLimpiar.setOnClickListener {
            when (txtArea.length()) {
                0 -> establecerMensaje("El texto ya estaba en blanco, introduce algo y prueba")
                else -> {
                    txtArea.setText("")
                    establecerMensaje("¡Texto en blanco!")
                }
            }
        }
    }


    // Métodos para contar palabras palíndromas
    private fun contarPalabrasPalindromas() {
        contador = 0                    // Se reinicia el contador de palíndromos
        contPalabras = 0                // Se reinicia el contador de palabras en total
        lenTexto = texto.length         // Longitud del texto
        var palabra: String             // Palabra que será evaluada si es palíndroma o no
        var caracter: Char              // Caracter que es comparado en la condición

        var i = 0
        while (i < lenTexto) {
            caracter = texto[i]                                     // Se almacena el valor del caracter actual

            if (Character.isLetterOrDigit(caracter)) {              // Si el caracter es una letra o dígito:
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
        var caracterPalabra = texto[primerIndice]   // Primer caracter de la palabra
        var ultimoIndice = primerIndice             // Índice que va incrementando hasta que no sea una letra o dígito el caracter
        val palabra: String                         // Palabra que devuelve el método

        while (ultimoIndice < lenTexto && Character.isLetterOrDigit(caracterPalabra)) {
            caracterPalabra = texto[ultimoIndice]   // Se recorre el texto, leyendo cada caracter
            ultimoIndice++                          // El índice deja de aumentar si el caracter no es una letra o dígito
        }

        if (!Character.isLetterOrDigit(texto[ultimoIndice - 1])) {
            ultimoIndice--
            // Caso especial de últimos caracteres del texto
        }

        palabra = texto.substring(primerIndice, ultimoIndice)
        return palabra
    }

    private fun mostrarDatosPalindromos() {
        var nuevoMensaje = ""
        // Mensaje que será establecido con los datos de los contadores

        nuevoMensaje += when (contador) {
            0 -> "Ningún palíndromo"
            1 -> "1 palíndromo"
            else -> contador.toString() + " palíndromos"
        }

        nuevoMensaje += " | "  // Separador

        nuevoMensaje += when (contPalabras) {
            0 -> "Ninguna palabra"
            1 -> "1 palabra en total"
            else -> contPalabras.toString() + " palabras en total"
        }

        establecerMensaje(nuevoMensaje)
    }


    // Métodos para contar frases palíndromas
    private fun contarFrasesPalindromas() {
        contador = 0                    // Se reinicia el contador de frases palíndromas
        contFrases = 0                  // Se reinicia el contador de frases en total
        lenTexto = texto.length         // Longitud del texto
        var frase: String               // Frase que será evaluada si es palíndroma o no
        var caracter: Char              // Caracter que es comparado en la condición
        val caracterStop = '.'          // Caracter que determina el final de una frase

        var i = 0
        var j = 0
        while (j < lenTexto) {
            caracter = texto[j]
            // Se almacena el valor del caracter actual

            if (caracter == caracterStop) {                     // Si encuentra el caracter final de una frase:
                frase = texto.substring(i, j)                   // Extrae la frase
                frase = extraerEspacios(frase)                  // Elimina los espacios intermedios en la frase
                contador += if (esPalindromo(frase)) 1 else 0   // Se suma 1 si la frase es palíndroma
                contFrases++                                    // Al haber extraído la frase, el contador de frases aumenta
                i = j + 1                                       // El primer índice se vuelve el caracter posterior al punto
            }
            j++
        }

        texto = ""
        // Se vacía la variable para no mantenerla guardada en memoria una vez acabado el proceso
    }

    private fun extraerEspacios(frase: String): String {
        var caracter: Char              // Caracter que será evaluado a lo largo de la búsqueda
        var ultimoIndice = 0            // Índice que va incrementando hasta que no sea una letra o dígito el caracter
        val lenFrase = frase.length     // Longitud de la frase
        val fraseSinEspacios = StringBuilder()

        while (ultimoIndice < lenFrase) {
            caracter = frase[ultimoIndice]
            // Se recorre el texto, leyendo cada caracter

            if (Character.isLetterOrDigit(caracter)) {      // Si el caracter es un dígito o una letra,
                fraseSinEspacios.append(caracter)           // se añade al conjunto de caracteres que forma la frase
            }

            ultimoIndice++
            // El índice deja de aumentar una vez se lea toda la frase
        }

        return fraseSinEspacios.toString()
    }

    private fun mostrarDatosFrasesPalindromas() {
        var nuevoMensaje = ""
        // Mensaje que será establecido con los datos de los contadores

        nuevoMensaje += when (contador) {
            0 -> "Ninguna frase palíndroma"
            1 -> "1 frase palíndroma"
            else -> contador.toString() + " frases palíndromas"
        }

        nuevoMensaje += " | "  // Separador

        nuevoMensaje += when (contFrases) {
            0 -> "Ninguna frase (las frases han de acabar en punto)"
            1 -> "1 frase en total"
            else -> contFrases.toString() + " frases en total"
        }

        establecerMensaje(nuevoMensaje)
    }


    // Métodos generales
    private fun establecerMensaje(s: String) {
        mensaje.text = s
    }

    private fun normalizarTexto() {
        texto = txtArea.text.toString()
        // Guardamos el texto del área en la variable 'texto' para no modificar el contenido original

        var textoNormalizado = texto.toLowerCase().trim()
        // El texto extraído se pasa a minúscula y sin espacios al principio y al final

        textoNormalizado = Normalizer.normalize(textoNormalizado, Normalizer.Form.NFKD)
        textoNormalizado = textoNormalizado.replace("[^\\p{ASCII}]".toRegex(), "")
        // Extraemos todas las tildes y acentos de las palabras del texto

        texto = textoNormalizado
        // Finalmente, guardamos el texto normalizado en la variable 'texto'
    }
}
