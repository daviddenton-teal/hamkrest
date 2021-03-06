package com.natpryce.hamkrest

import com.natpryce.hamkrest.assertion.assertThat
import kotlin.test.Test

class DelimitingValuesInStrings {
    @Test
    fun quotes_around_string_values_and_escaped_within() {
        assertThat(describe("hello, \"bob\""), equalTo("\"hello, \\\"bob\\\"\""))
    }
    
    @Test
    fun pairs() {
        assertThat(describe(Pair("x", "y")), equalTo("(\"x\", \"y\")"))
    }
    
    @Test
    fun triples() {
        assertThat(describe(Triple("x", "y", "z")), equalTo("(\"x\", \"y\", \"z\")"))
    }
    
    @Test
    fun iterables() {
        assertThat(describe(listOf(1, 2)), equalTo("[1, 2]"))
        assertThat(describe(listOf("1", "2")), equalTo("""["1", "2"]"""))
    }
    
    @Test
    fun sets() {
        assertThat(describe(setOf(1, 2)), equalTo("{1, 2}"))
        assertThat(describe(setOf("1", "2")), equalTo("""{"1", "2"}"""))
    }
    
    @Test
    fun ranges() {
        assertThat(describe(1..8), equalTo("1..8"))
    }
    
    @Test
    fun maps() {
        assertThat(describe(mapOf("a" to 1, "b" to 2)), equalTo("""{"a":1, "b":2}"""))
    }
    
    @Test
    fun self_describing_objects() {
        val d = object : SelfDescribing {
            override val description = "d"
        }
        
        assertThat(describe(d), equalTo("d"))
    }
    
    class IterableButNotCollection(private vararg val elements: String) : Iterable<String> {
        override fun iterator() = elements.iterator()
        override fun toString() = elements.joinToString("/")
    }
    
    @Test
    fun does_not_generate_collectionlike_description_for_iterables_that_are_not_collections() {
        val thing = IterableButNotCollection("foo", "bar", "baz")
        assertThat(describe(thing), equalTo("foo/bar/baz"))
    }
}

