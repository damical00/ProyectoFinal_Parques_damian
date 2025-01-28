package com.example.proyectofinal_parques.modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class ParquesJSON(
    @SerialName(value = "id")
    val id: String,

    @SerialName(value = "nombre")
    val nombre: String,

    @SerialName(value = "extension")
    val extension: String,

    @SerialName(value = "especies")
    val especies: String
) {
}