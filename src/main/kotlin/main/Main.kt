package dk.maxkandersen.main

import dk.maxkandersen.type.BoolType
import dk.maxkandersen.type.FunctionType
import dk.maxkandersen.type.IntType
import dk.maxkandersen.type.QuantifyingTypeScheme
import dk.maxkandersen.type.TypeVar

fun main() {

    val a = TypeVar("a")
    val b = TypeVar("b")
    val fresh0 = TypeVar()
    val fresh1 = TypeVar()
    val intTyp = IntType
    val boolTyp = BoolType
    val funtyp = FunctionType(a, b)
    val typescheme = QuantifyingTypeScheme(b, funtyp)
    println(a)
    println(b)
    println(fresh0)
    println(fresh1)
    println(intTyp)
    println(boolTyp)
    println(funtyp)
    println(typescheme)
}