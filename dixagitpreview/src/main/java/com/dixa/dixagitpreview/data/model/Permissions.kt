package com.dixa.dixagitpreview.data.model

data class Permissions (
    val admin    : Boolean? = null,
    val maintain : Boolean? = null,
    val push     : Boolean? = null,
    val triage   : Boolean? = null,
    val pull     : Boolean? = null
)