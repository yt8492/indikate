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
