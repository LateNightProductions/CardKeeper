package com.awscherb.cardkeeper.util

import java.lang.IllegalArgumentException

/**
 * Extended types to support beyond just the ParsedType that ZXINg gets us
 * i.e. a driver's license
 */
sealed class ExtendedType {
}


class DriverLicenseType(
    val firstName: String,
    val lastName: String,
    val expiration: String,
    val issueDate: String,
    val dob: String,
    val streetAddress: String,
    val city: String,
    val state: String,
    val postal: String
) : ExtendedType() {

    fun getFullName() = "$firstName $lastName"

}

object ExtendedTypesHelper {

    private val MATCHERS = listOf<ExtendedTypeParser<*>>(DriverLicenseMatcher)

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

object DriverLicenseMatcher : ExtendedTypeParser<DriverLicenseType> {

    override fun inputMatches(input: String): Boolean {
        return input.contains("DCA", ignoreCase = true) ||
                input.contains("DCT", ignoreCase = true)
    }

    override fun parseResult(input: String): DriverLicenseType {
        if (!inputMatches(input)) throw IllegalArgumentException("Invalid type")

        val dict = mutableMapOf<String, String>()

        input.lineSequence()
            .filter { it.isNotBlank() }
            .filter { !it.contains("ANSI", ignoreCase = true) && !it.contains("@") }
            .forEach {
                val prefix = it.substring(0..<3).capitalize()
                val suffix = it.substring(3..<it.length)
                dict[prefix] = suffix
            }

        val firstName = dict["DCT"] ?: dict["DCA"] ?: ""
        val lastName = dict["DCS"] ?: dict["DAB"] ?: ""
        val expiration = dict["DBA"] ?: ""
        val issueDate = dict["DBD"] ?: ""
        val dob = dict["DBB"] ?: ""
        val streetAddress = dict["DAG"] ?: ""
        val city = dict["DAI"] ?: ""
        val state = dict["DAJ"] ?: ""
        val postal = dict["DAK"] ?: ""

        return DriverLicenseType(
            firstName = firstName.trim(),
            lastName = lastName.trim(),
            expiration = expiration.trim(),
            issueDate = issueDate.trim(),
            dob = dob.trim(),
            streetAddress = streetAddress.trim(),
            city = city.trim(),
            state = state.trim(),
            postal = postal.trim()
        )


    }

}

