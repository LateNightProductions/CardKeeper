package com.awscherb.cardkeeper.types

import java.text.SimpleDateFormat
import java.util.Calendar

data class DriverLicenseType(
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

    companion object Matcher : ExtendedTypeParser<DriverLicenseType> {

        private val DateFormat = SimpleDateFormat("MMM d, yyyy")
        private val CalenderInstance = Calendar.getInstance()

        override fun inputMatches(input: String): Boolean {
            return input.contains("DCA", ignoreCase = true) ||
                    input.contains("DCT", ignoreCase = true)
        }

        override fun parseResult(input: String): DriverLicenseType {
            if (!inputMatches(input)) throw IllegalArgumentException("Invalid type")

            val dict = mutableMapOf<String, String>()

            input.lineSequence()
                .filter { it.isNotBlank() }
                .filter { !(it.contains("ANSI", ignoreCase = true) || it.contains("@")) }
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
                expiration = expiration.trim().toPrettyDate(),
                issueDate = issueDate.trim().toPrettyDate(),
                dob = dob.trim().toPrettyDate(),
                streetAddress = streetAddress.trim(),
                city = city.trim(),
                state = state.trim(),
                postal = postal.trim()
            )
        }

        private fun String.toPrettyDate(): String {
            val month = substring(0..<2).toInt()
            val day = substring(2..<4).toInt()
            val year = substring(4..<length).toInt()
            with(CalenderInstance) {
                set(Calendar.MONTH, month)
                set(Calendar.DAY_OF_MONTH, day)
                set(Calendar.YEAR, year)
            }
            return DateFormat.format(CalenderInstance.time)
        }
    }
}