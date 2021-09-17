package `in`.ev.cakelist.data.mappers

import `in`.ev.cakelist.data.model.CakeItem
import `in`.ev.cakelist.data.model.ErrorEntity
import `in`.ev.cakelist.domain.model.Cake
import `in`.ev.cakelist.domain.model.Error

fun toDomain (type: CakeItem): Cake {
    return Cake(type.desc, type.image, type.title)
}

fun toDomain(errorEntity: ErrorEntity): Error {
    return Error(errorEntity.status_code, errorEntity.status_message)
}

