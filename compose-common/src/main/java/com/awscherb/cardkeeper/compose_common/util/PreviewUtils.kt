package com.awscherb.cardkeeper.compose_common.util

val SampleLicense = """
    @
    ANSI 1234
    DCTFirstname
    DCSLastname
    DBD02082024
    DBB09011993
    DBA09012030
    DAG123 Fake Street
    DAINew York
    DAJNY
    DAK10001
""".trimIndent()

val SampleContact = """
            BEGIN:VCARD
            VERSION:3.0
            N:Lastname;Firstname
            FN:Firstname Lastname
            ORG:CompanyName
            TITLE:JobTitle
            ADR:;;123 Sesame St;SomeCity;CA;12345;USA
            TEL;WORK;VOICE:1234567890
            TEL;CELL:Mobile
            TEL;FAX:
            EMAIL;WORK;INTERNET:foo@email.com
            URL:http://website.com
            END:VCARD
            """.trimIndent()

val SampleWifi = "WIFI:T:WPA;S:network_name;P:passsw0rd;H:;"

val SampleTel = "TEL:3125550690"

val SampleEmail = "mailto:recip@example.com?cc=other@example.com&subject=hello&body=email body"