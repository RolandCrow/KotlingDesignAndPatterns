package functional_with_arrow

import arrow.core.Either
import arrow.core.NonEmptyList
import arrow.fx.coroutines.parMap
import arrow.fx.coroutines.parMapOrAccumulate
import arrow.fx.coroutines.parMapUnordered
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.toList
import java.net.URL

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
suspend fun main() {
    val tasks = 'a'..'z'
    val wikiArticles = tasks.parMap {
        fetchAsync("https://en.wikipedia.org/wiki/$it")
    }
    println(wikiArticles[1])
    val wikiArticleUnordered = tasks.asFlow().parMapUnordered(concurrency = 2) {
        fetchAsync("https://en.wikipedia.org/wiki/$it")
    }.toList()

    println(wikiArticleUnordered)
    val wikiArticleOrTimeout: Either<NonEmptyList<RuntimeException>,List<String>> =
        tasks.parMapOrAccumulate {  letter ->
            withTimeout(10L) {
                fetchAsync("https://en.wikipedia.org/wiki/$letter")
            }
        }
}

suspend fun fetchAsync(urlFetch: String):String =  withContext(Dispatchers.IO) {
    val url = URL(urlFetch)
    val inputStream = url.openStream()
    inputStream.bufferedReader().use {
        val content = it.readText()
        content
    }
}