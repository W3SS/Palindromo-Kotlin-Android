package aquodev.textopalindromo

import kotlinx.android.synthetic.main.activity_main.*   // Para identificar objetos de MainActivity
import java.text.Normalizer                             // Para 'normalizarTexto()'


// Variables para mostrar datos
private var texto = ""                  // Texto introducido en el área
private var textoAnterior = ""          // Texto original, para evitar repetición de cálculos
private var ultimoBtnPulsado = ""       // Para saber el último botón pulsado
private var contador = 0                // Contador de palíndromos, tanto para palabras como para frases
private var contPalabras = 0            // Contador de palabras en total del texto
private var contFrases = 0              // Contador de frases en total del texto
private val finalFrase = '.'            // Caracter final de frase


/**
 * CONTROL DE BOTONES: btnContarPalindromos
 */
internal fun MainActivity.funcionPalabras(): String? {
    val esteBoton = "btnContarPalindromos"      // Identificamos el botón pulsado
    almacenarTextoOriginal()                    // Usamos 'MainActivity' en esta función

    if (botonYaPulsado(esteBoton) && textoYaRepetido()) {   // Si ya hemos pulsado este botón y el texto es repetido,
        return null                                         // devolvemos 'null' y cortamos el flujo de la función
    } else {                                                // En caso contrario,
        ultimoBtnPulsado = esteBoton                        // guardamos el último botón pulsado
    }

    return when {
        textoVacio() -> "No has introducido ningún texto"
        else -> {
            normalizarTexto()                   // Se normaliza el texto,
            contarPalabrasPalindromas()         // se cuentan las palabras palíndromas y
            devolverDatosPalindromos()          // se devuelve un string con los datos
        }
    }
}


private fun contarPalabrasPalindromas() {
    contador = 0                    // Se reinicia el contador de palíndromos
    contPalabras = 0                // Se reinicia el contador de palabras en total
    var palabra: String             // Palabra que será evaluada si es palíndroma o no
    var caracter: Char              // Caracter que es comparado en la condición

    var i = 0
    while (i < texto.length) {                                  // Se recorre el texto al completo,
        caracter = texto[i]                                     // analizando cada caracter
        if (caracter.isLetterOrDigit()) {                       // Si el caracter es una letra o dígito:
            palabra = extraerPalabra(i)                         // Extrae la palabra a partir de ese caracter
            contador += if (esPalindromo(palabra)) 1 else 0     // Se suma 1 si la palabra es palíndroma
            contPalabras++                                      // Al haber extraído la palabra, el contador total aumenta
            i += palabra.length - 1                             // El primer índice se vuelve el caracter posterior a la palabra
        }
        i++
    }
}


private fun extraerPalabra(primerIndice: Int): String {
    var caracter: Char                          // Primer caracter de la palabra
    var ultimoIndice = primerIndice             // Índice que va incrementando hasta que no sea una letra o dígito el caracter

    for (i in primerIndice until texto.length) {    // Se recorre el texto desde el primer índice,
        caracter = texto[i]                         // analizando cada caracter
        if (!caracter.isLetterOrDigit()) break      // Termina el recorrido cuando el caracter no es dígito ni letra
        ultimoIndice++                              // Avanza hasta el siguiente caracter si no termina el recorrido
    }

    return texto.substring(primerIndice, ultimoIndice)
    // Devuelve la palabra
}


private fun devolverDatosPalindromos(): String {
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

    return nuevoMensaje.toString()
    // Devuelve el resultado para establecerlo como mensaje
}


/**
 * CONTROL DE BOTONES: btnContarFrasesPalind
 */
internal fun MainActivity.funcionFrases(): String? {
    val esteBoton = "btnContarFrasesPalind"     // Identificamos el botón pulsado
    almacenarTextoOriginal()                    // Usamos 'MainActivity' en esta función

    if (botonYaPulsado(esteBoton) && textoYaRepetido()) {   // Si ya hemos pulsado este botón y el texto es repetido,
        return null                                         // devolvemos 'null' y cortamos el flujo de la función
    } else {                                                // En caso contrario,
        ultimoBtnPulsado = esteBoton                        // guardamos el último botón pulsado
    }

    return when {
        textoVacio() -> "No has introducido ninguna frase (las frases han de acabar en punto)"
        else -> {
            normalizarTexto()                   // Se normaliza el texto,
            contarFrasesPalindromas()           // se cuentan las frases palíndromas y
            devolverDatosFrasesPalindromas()    // se devuelve un string con los datos
        }
    }
}


private fun contarFrasesPalindromas() {
    contador = 0                    // Se reinicia el contador de frases palíndromas
    contFrases = 0                  // Se reinicia el contador de frases en total
    var frase: String               // Frase que será evaluada si es palíndroma o no
    var caracter: Char              // Caracter que es comparado en la condición

    var i = 0
    for (j in texto.indices) {                              // Se recorre el texto completo,
        caracter = texto[j]                                 // analizando cada caracter
        if (caracter == finalFrase) {                       // Si encuentra el caracter final de una frase:
            frase = extraerFrase(i)                         // Extrae la frase sin espacios desde el primer caracter
            contador += if (esPalindromo(frase)) 1 else 0   // Se suma 1 si la frase es palíndroma
            contFrases += if (frase.isNotEmpty()) 1 else 0  // Al haber extraído la frase, el contador de frases aumenta
            i = j + 1                                       // El primer índice se vuelve el caracter posterior al punto
        }
    }
}


private fun extraerFrase(primerIndice: Int): String {
    var caracter: Char  // Caracter comparado en la condición
    val fraseSinEspacios = StringBuilder()

    for (i in primerIndice until texto.length) {    // Se recorre la frase en el texto,
        caracter = texto[i]                         // analizando cada caracter

        if (caracter.isLetterOrDigit()) {           // Si el caracter es un dígito o una letra,
            fraseSinEspacios.append(caracter)       // se añade al conjunto de caracteres que forma la frase
        } else if (caracter == finalFrase) {        // Si el caracter es el final de frase,
            break                                   // el bucle termina
        }
    }

    return fraseSinEspacios.toString()
    // Devuelve la frase sin espacios
}


private fun devolverDatosFrasesPalindromas(): String {
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

    return nuevoMensaje.toString()
    // Devuelve el resultado para establecerlo como mensaje
}


/**
 * CONTROL DE BOTONES: btnLimpiar
 */
internal fun MainActivity.funcionLimpiarTexto(): String? {
    val esteBoton = "btnLimpiar"                // Identificamos el botón pulsado
    almacenarTextoOriginal()                    // Usamos 'MainActivity' en esta función

    return when {
        !textoVacio() -> {                      // Cuando el texto no está vacío,
            txtArea.text.clear()                // limpia el texto y se avisa
            ultimoBtnPulsado = ""               // La primera pulsación se guarda como vacía
            "¡Texto en blanco!"
        }
        !botonYaPulsado(esteBoton) -> {         // Cuando este botón se pulsa por segunda vez,
            ultimoBtnPulsado = esteBoton        // se guarda y avisa que el texto ya estaba en blanco
            "El texto ya estaba en blanco, introduce algo y prueba"
        }
        else -> null                            // Cuando el botón se pulsa por tercera vez
    }
}


/**
 * FUNCIONES GENERALES (no son específicas de un botón en concreto)
 */
private fun textoVacio() = texto.isEmpty()
// Comprueba si el texto no tiene ningún caracter

private fun textoYaRepetido() = textoAnterior == texto
// Comprueba si el texto introducido es el mismo que el anterior tras pulsar un botón

private fun botonYaPulsado(esteBoton: String) = ultimoBtnPulsado == esteBoton
// Comprueba si el botón que se ha pulsado fue el mismo pulsado anteriormente

private fun MainActivity.almacenarTextoOriginal() {
    texto = txtArea.text.toString()
    // Se extrae el texto de 'txtArea'
}

private fun normalizarTexto() {
    textoAnterior = texto
    // Se almacena el texto original, en caso de que se pulse el mismo botón dos veces
    // seguidas y el texto sea diferente los cálculos se hagan

    texto = texto.toLowerCase().trim { !it.isLetterOrDigit() && it != finalFrase }
    // El texto se pasa a minúscula y sin caracteres que no sean letras o dígitos al principio y
    // al final (excepto en el caso del caracter que marca el final de una frase)

    texto = Normalizer.normalize(texto, Normalizer.Form.NFKD)
    texto = texto.replace("[^\\p{ASCII}]".toRegex(), "")
    // Extraemos todas las tildes y acentos de las palabras del texto, y guardamos dicho texto
}
