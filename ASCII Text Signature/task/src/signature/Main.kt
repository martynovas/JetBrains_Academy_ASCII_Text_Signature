package signature

import java.io.File
import java.util.*

abstract class RomanSymbol {
    var chr: Char = ' '
    var width: Int = 0
    protected var mas = Array<String>(10) { "" }

    fun getline(num: Int): String {
        return mas[num]
    }
}

class RomanAlpha(val scanner: Scanner):RomanSymbol() {
    init {
        val line = scanner.nextLine()
        chr = line[0]
        width = line.substring(2).toInt()
        for (r in mas.indices) {
            mas[r] = scanner.nextLine()
        }
    }
}

class RomanSpace():RomanSymbol() {
    init {
        chr = ' '
        width = 10
        for (r in mas.indices) {
            mas[r] = " ".repeat(width)
        }
    }
}

class RomanAlphabet() {
    private val scanner = Scanner(File("C:\\Users\\a.martynov\\IdeaProjects\\ASCII Text Signature\\ASCII Text Signature\\task\\src\\signature\\roman.txt"))
    private var mas: MutableMap<Char, RomanSymbol> = mutableMapOf()

    init {
        val firstLine = scanner.nextLine()
        for (chr in ('a'..'z')) {
            val a = RomanAlpha(scanner)
            mas[a.chr] = a
        }
        for (chr in ('A'..'Z')) {
            val a = RomanAlpha(scanner)
            mas[a.chr] = a
        }
        mas[' '] = RomanSpace()
    }

    fun get(chr: Char) = mas[chr]
}

fun getRomanSentence(romanAlphabet: RomanAlphabet, word: String): Array<RomanSymbol> =
        Array(word.length) {
            romanAlphabet.get(word[it])!!
        }

fun getRomanString(romanAlphabet: RomanAlphabet, word: String): Array<String> {
    val sentence = getRomanSentence(romanAlphabet, word)
    val lines = Array<String>(10) { "" }
    for (r in 0..9) {
        for (c in sentence) {
            lines[r] += c.getline(r)
        }
        lines[r] = lines[r].substring(0, lines[r].lastIndex)
    }
    return lines
}

fun sbustr(str: String, charNum: Int): String {
    var c = 0
    var start = 0
    var end = 0
    for (i in 0..str.lastIndex) {
        if (str[i] == '%') {
            c++
            if (c == charNum)
                start = i + 1
            else if (c > charNum) {
                end = i - 1
                break
            }
        }
    }

    return str.substring(start, end + 1)
}

fun getMediumString(message: String): Array<String> {
    val result = arrayOf("", "", "")
    val alphabet = arrayOf(
            "%____ %___  %____ %___  %____ %____ %____ %_  _ %_ % _ %_  _ %_    %_  _ %_  _ %____ %___  %____ %____ %____ %___ %_  _ %_  _ %_ _ _ %_  _ %_   _ %___  %",
            "%|__| %|__] %|    %|  \\ %|___ %|___ %| __ %|__| %| % | %|_/  %|    %|\\/| %|\\ | %|  | %|__] %|  | %|__/ %[__  % |  %|  | %|  | %| | | % \\/  % \\_/  %  /  %",
            "%|  | %|__] %|___ %|__/ %|___ %|    %|__] %|  | %| %_| %| \\_ %|___ %|  | %| \\| %|__| %|    %|_\\| %|  \\ %___] % |  %|__| % \\/  %|_|_| %_/\\_ %  |   % /__ %")

    for (i in message.toUpperCase()) {
        for (r in 0..2) {
            result[r] += when (i) {
                ' ' -> "     "
                else -> sbustr(alphabet[r], i.toInt() - 64)
            }
        }
    }
    result[0] = result[0].substring(0, result[0].lastIndex)
    result[1] = result[1].substring(0, result[1].lastIndex)
    result[2] = result[2].substring(0, result[2].lastIndex)
    return result
}

fun printMessage(message: Array<String>) {
    println(message[0])
    println(message[1])
    println(message[2])
}

fun printSignature(name: Array<String>, status: Array<String>) {
    val lengthMessage = Math.max(name[0].length, status[0].length)
//    val startName = if (name[0].length < status[0].length) (status[0].length / 2) - (name[0].length / 2) + (status[0].length % 2) - 1 else 0
//    val startStatus = if (name[0].length > status[0].length) (name[0].length / 2) - (status[0].length / 2) + (name[0].length % 2) - 1 else 0
    val startName = if (name[0].length < status[0].length) (status[0].length - name[0].length) / 2 else 0
    val startStatus = if (name[0].length > status[0].length) (name[0].length - status[0].length) / 2 else 0
    for (r in 1..15) {
        for (c in -4..lengthMessage + 4) {
            var chr = when (r) {
                1, 15 -> '8'
                in 2..11 -> when (c) {
                    -4,-3, lengthMessage + 3, lengthMessage + 4 -> '8'
                    in startName..startName + name[0].length - 1 -> name[r - 2][c - startName]
                    else -> ' '
                }
                in 12..14 -> when (c) {
                    -4,-3, lengthMessage + 3, lengthMessage + 4 -> '8'
                    in startStatus..startStatus + status[0].length - 1 -> status[r - 12][c - startStatus]
                    else -> ' '
                }
                else -> ' '
            }

            if (chr != null)
                print(chr)
        }
        println()
    }
}


fun test() {
    val romanAlphabet = RomanAlphabet()

    printSignature(getRomanString(romanAlphabet,"A B"), getMediumString("a"))
    println();

    printSignature(getRomanString(romanAlphabet,"A B"), getMediumString("ab"))
    println();

    printSignature(getRomanString(romanAlphabet,"A B"), getMediumString("abc"))
    println();

    printSignature(getRomanString(romanAlphabet,"AB B"), getMediumString("a"))
    println();

    printSignature(getRomanString(romanAlphabet,"A BC"), getMediumString("a"))
    println();

    printSignature(getRomanString(romanAlphabet,"A B"), getMediumString("abcdefghijklmn"))
    println();

    printSignature(getRomanString(romanAlphabet,"A B"), getMediumString("abcdefghijklm"))
    println();

    printSignature(getRomanString(romanAlphabet,"A B"), getMediumString("abcdefghijkl"))
    println();

    printSignature(getRomanString(romanAlphabet,"A B"), getMediumString("abcdefghijk"))
    println();

    printSignature(getRomanString(romanAlphabet,"Bill Gates"), getMediumString("VIP"))
    println();

    printSignature(getRomanString(romanAlphabet,"Bill Gates"), getMediumString("VIC"))
    println();

    printSignature(getRomanString(romanAlphabet,"Bill Gates"), getMediumString("VBP"))
    println();

    printSignature(getRomanString(romanAlphabet,"Bill Gates"), getMediumString("AIP"))
    println();

    printSignature(getRomanString(romanAlphabet,"Bill Gates"), getMediumString("ABC"))
    println();

    printSignature(getRomanString(romanAlphabet,"A BC"), getMediumString("abc"))
    println();

    printSignature(getRomanString(romanAlphabet,"A B"), getMediumString("VIP"))
    println();

    printSignature(getRomanString(romanAlphabet,"Bill Gates"), getMediumString("a"))
    println();


    printSignature(getRomanString(romanAlphabet,"B G"), getMediumString("a"))
    println();
}

fun main() {
    val romanAlphabet = RomanAlphabet()
    print("Enter name and surname: ")
    val message = readLine()
    print("Enter person's status: ")
    val status = readLine()
    printSignature(getRomanString(romanAlphabet,message!!), getMediumString(status!!))
}
