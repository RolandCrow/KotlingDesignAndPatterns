package concurrency

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import java.util.concurrent.TimeUnit

fun main() {
    runBlocking {
        val pagesProducer = producePages()
        val domProducer = produceDom(pagesProducer)
        val titleProducer = produceTitles(domProducer)
        titleProducer.consumeEach {
            println(it)
        }
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
fun CoroutineScope.producePages() = produce {
    fun getPages():List<String> {
        return listOf(
        "<html><body><h1>Cool stuff</h1></body></html>",
        "<html><body><h1>Even more stuff</h1></body></html>"
        )
    }

    val pages = getPages()
    while (this.isActive) {
        for(p in pages) {
            send(p)
        }
        delay(TimeUnit.SECONDS.toMillis(5))
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
fun CoroutineScope.produceDom(pages:ReceiveChannel<String>) = produce {
    fun parsedDom(page: String): Document {
        return Document(page)
    }

    for(p in pages) {
        send(parsedDom(p))
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
fun CoroutineScope.produceTitles(parsedPage: ReceiveChannel<Document>) = produce {
    fun getTitles(dom: Document): List<String> {
        return dom.getElementsByTagName().map {
            it.toString()
        }
    }

    for(page in parsedPage) {
        for(t in getTitles(page)) {
            send(t)
        }
    }
}

data class Document(val html: String) {
    fun getElementsByTagName(): List<Document> {
        return listOf(Document("Some title"))
    }
}