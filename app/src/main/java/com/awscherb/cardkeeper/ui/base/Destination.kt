package com.awscherb.cardkeeper.ui.base

sealed class Destination(val label: String, val dest: String) {
    object Items : Destination("Items", "items")
    object Scan : Destination("Scan", "scan")
    object Create : Destination("Create", "create")
}
