package com.example.ifkakao.data.csv

interface CsvParser<T> {
    fun parse(csvString: String): List<T>
}