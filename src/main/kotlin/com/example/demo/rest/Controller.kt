package com.example.demo.rest

import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.take
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

import kotlin.sequences.sequence as sequence

@RestController
class Controller {

    @FlowPreview
    @GetMapping("/nextPage", produces = ["application/json", "text/event-stream"])
    suspend fun nextPage(@RequestParam number: Int, @RequestParam size: Int): Flow<Item> {
        val itemsGenerator = sequence {
            val start = number * size
            val stop = (number + 1) * size
            for(i in start..stop){
                Thread.sleep(500)
                yield(i)
            }
        }

        return itemsGenerator.asFlow().take(size).map(::Item)
    }


    class Item(val number: Int) {
        val text: String = "Jestem numerem $number"
    }
}



