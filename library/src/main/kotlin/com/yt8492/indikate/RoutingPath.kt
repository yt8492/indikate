package com.yt8492.indikate

class RoutingPath(val parts: List<RoutingPathSegment>) {

    fun evaluate(path: String): PathEvaluationResult {
        val pathParts = path.splitToSequence("/")
            .filter { it.isNotBlank() }
            .toList()
        if (pathParts.size != parts.size) {
            return PathEvaluationResult(
                false,
                PathEvaluationResult.QUALITY_FAILED,
                emptyMap()
            )
        }
        var quality = PathEvaluationResult.QUALITY_CONSTANT
        val parameters = mutableMapOf<String, String>()
        pathParts.zip(parts).forEach { (pathPart, part) ->
            when (part) {
                is RoutingPathSegment.Constant -> {
                    if (part.value != pathPart) {
                        return PathEvaluationResult(
                            false,
                            PathEvaluationResult.QUALITY_FAILED,
                            emptyMap()
                        )
                    }
                }
                is RoutingPathSegment.Parameter -> {
                    quality *= PathEvaluationResult.QUALITY_PARAMETER
                    parameters[part.value] = pathPart
                }
                is RoutingPathSegment.WildCard -> {
                    quality *= PathEvaluationResult.QUALITY_WILDCARD
                }
            }
        }
        return PathEvaluationResult(
            true,
            quality,
            parameters
        )
    }

    companion object {
        private val ROOT: RoutingPath = RoutingPath(listOf())

        fun parse(path: String): RoutingPath {
            if (path == "/") return ROOT
            val parts = path.splitToSequence("/")
                .filter { it.isNotBlank() }
                .map {
                    when {
                        it == "*" -> RoutingPathSegment.WildCard
                        it.startsWith(":") -> RoutingPathSegment.Parameter(it.drop(1))
                        else -> RoutingPathSegment.Constant(it)
                    }
                }
                .toList()
            return RoutingPath(parts)
        }
    }

    override fun toString(): String = parts.joinToString("/", "/") { it.value }
}

sealed class RoutingPathSegment {
    abstract val value: String

    data class Constant(override val value: String) : RoutingPathSegment()
    data class Parameter(override val value: String) : RoutingPathSegment()
    object WildCard : RoutingPathSegment() {
        override val value: String = "*"
    }
}

data class PathEvaluationResult(
    val succeeded: Boolean,
    val quality: Double,
    val parameters: Map<String, String>
) {
    companion object {
        const val QUALITY_CONSTANT = 1.0
        const val QUALITY_PARAMETER = 0.8
        const val QUALITY_WILDCARD = 0.5
        const val QUALITY_FAILED = 0.0
    }
}
