import com.yt8492.indikate.Server

fun main() {
    val server = Server()
    server.get("/") { request, response ->
        response.message = "Hello indikate!"
    }
    server.post("/") { request, response ->
        val contentType = request.headers["content-type"]
        val body = request.body
        println(body)
        println(request.headers.getAll("Hoge").joinToString("\n") { "Hoge: $it" })
        response.message = """
            contentType: $contentType
            body: $body
        """.trimIndent()
        response.statusCode = 200
    }
    server.listen(8080) {
        println("server start")
    }
}
