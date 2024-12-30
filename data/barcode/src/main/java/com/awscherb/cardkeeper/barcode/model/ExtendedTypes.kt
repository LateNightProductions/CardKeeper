package com.awscherb.cardkeeper.barcode.model

/**
 * Extended types to support beyond just the ParsedType that ZXINg gets us
 * i.e. a driver's license
 */
sealed class ExtendedType

object ExtendedTypesHelper {

    private val MATCHERS = listOf<ExtendedTypeParser<*>>(DriverLicenseType.Matcher)

    fun mayBeExtendedType(input: String): Boolean {
        return MATCHERS.any { it.inputMatches(input) }
    }

    fun matchType(input: String): ExtendedType? {
        return MATCHERS.firstOrNull { it.inputMatches(input) }?.parseResult(input)
    }
}

interface ExtendedTypeParser<T : ExtendedType> {
    fun inputMatches(input: String): Boolean

    fun parseResult(input: String): T
}



