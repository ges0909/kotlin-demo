package schrader

class HelloWorld {
    val greeting: String
        get() {
            return "Hello world."
        }
}

fun main(args: Array<String>) {
    println(HelloWorld().greeting)
}
