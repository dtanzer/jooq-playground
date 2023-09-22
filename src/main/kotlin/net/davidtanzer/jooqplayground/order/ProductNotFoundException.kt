package net.davidtanzer.jooqplayground.order

class ProductNotFoundException(message: String, cause: Throwable? = null) : RuntimeException(message, cause) {
}