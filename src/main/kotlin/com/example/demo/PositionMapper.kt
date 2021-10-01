package az.pashabank.dp.ms.qa.employee.mapper

import az.pashabank.dp.ms.qa.employee.dao.entity.PositionEntity
import az.pashabank.dp.ms.qa.employee.model.PositionDto
import az.pashabank.dp.ms.qa.employee.model.PositionStatus

fun PositionEntity.toDto() =
    PositionDto(
        id = id!!,
        name = name,
        description=description,
    )

fun PositionDto.toEntity() =
    PositionEntity(
        name = name,
        description=description,
        status = PositionStatus.ACTIVE
    )

fun PositionEntity.mergeWith(positionDto: PositionDto) {
    name = positionDto.name
    description=positionDto.description
}
