import com.yt8492.indikate.Server

fun main() {
    val server = Server()
    server.get("/") { request, response ->
        response.message = "Hello indikate!"
    }
    server.post("/") { request, response ->
        val hogeHeader = request.headers["Hoge"]
        val fugaHeaders = request.headers.getAll("Fuga")
        val body = request.body
        println(body)
        response.headers.add("Hoge", "hoge")
        response.headers.add("Fuga", listOf("Hoge", "Fuga"))
        response.headers.add("Foo", "foo")
        response.headers.add("Foo", "bar")
        response.message = """
            hoge: $hogeHeader
            fuga: ${fugaHeaders.joinToString(",")}
            body: $body
        """.trimIndent()
        response.statusCode = 200
    }
    server.get("/hoge/fuga") { request, response ->
        response.message = "const routing"
    }
    server.get("/hoge/:fuga") { request, response ->
        val parameter = request.pathParameters.require("fuga")
        response.message = "parameter: $parameter"
    }
    server.get("/hoge/*") { request, response ->
        response.message = "unreachable section"
    }
    server.listen(8080) {
        println("server start")
    }
}
