package com.vkdev.numbersinfo.data.mapper

import com.vkdev.numbersinfo.data.dto.NumberInfoDto
import com.vkdev.numbersinfo.data.entity.NumberEntity
import com.vkdev.numbersinfo.domain.model.NumberInfoModel

fun NumberEntity.toModel(): NumberInfoModel{
    return NumberInfoModel(
        id = this.id,
        text = this.text,
        number = this.number,
    )
}

fun NumberInfoDto.toModel(): NumberInfoModel{
    return NumberInfoModel(
        text = this.text,
        number = this.number,
    )
}