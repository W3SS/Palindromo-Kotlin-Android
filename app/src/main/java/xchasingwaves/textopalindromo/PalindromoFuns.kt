package xchasingwaves.textopalindromo

/**
 * Devuelve un resultado booleano indicando si la palabra introducida
 * por parámetro es un palíndromo (palabra que se escribe igual en ambas
 * direcciones).
 *
 * /!\ El principal método (el que se debe usar con un sólo parámetro) depende de este.
 *
 * @param palabra Palabra la cual se comprobará si es palíndroma o no
 * @param lenPalabra Cantidad de caracteres que contiene la palabra
 * @return true si la palabra es palíndroma, false si no lo es.
 */
private fun esPalindromo(palabra: String, lenPalabra: Int): Boolean {

    if (lenPalabra < 2) {
        return true
    } else if (palabra[0] == palabra[lenPalabra - 1]) {
        return esPalindromo(palabra.substring(1, lenPalabra - 1), lenPalabra - 2)
        // Sabiendo que la primera y última letra coinciden, comprobamos si
        // la subcadena entre ambas letras sigue siendo palíndroma.
    }

    return false
}

/**
 * Devuelve un resultado booleano indicando si la palabra introducida
 * por parámetro es un palíndromo (palabra que se escribe igual en ambas
 * direcciones).
 *
 * /!\ Palabras de longitud 1 o textos vacíos no se considerarán palíndromas.
 *
 * @param texto Palabra la cual se comprobará si es palíndroma o no
 * @return true si la palabra es palíndroma, false si no lo es.
 */
fun esPalindromo(texto: String): Boolean {
    val palabra = texto.toLowerCase().trim()
    val lenTexto = palabra.length

    return if (lenTexto < 2 || palabra[0] != palabra[lenTexto - 1]) {
        false
        // Si es menor de 2 caracteres o si el primer y último caracter no coinciden
    } else {
        esPalindromo(palabra.substring(1, lenTexto - 1), lenTexto - 2)
        // Si el primer y último caracter coinciden y es mayor o igual que dos caracteres
    }
    // Toda esta condición se puede simplificar por un solo 'return', pero por legibilidad se deja tal y como está
}
