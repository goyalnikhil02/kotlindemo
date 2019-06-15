package codemwnci.bootdemo

import org.springframework.web.bind.annotation.*
import java.time.Instant
import java.util.concurrent.atomic.AtomicLong

@RestController
class TodoResource() {
    val counter = AtomicLong()

    @GetMapping("/greeting")
    fun greeting(@RequestParam(value = "name", defaultValue = "World") name: String) =
            Greeting(counter.incrementAndGet(), "Hello, $name")
	



}
