package az.pashabank.dp.ms.qa.employee.dao.repository

import az.pashabank.dp.ms.qa.employee.dao.entity.JobHistoryEntity
import az.pashabank.dp.ms.qa.employee.dao.entity.PositionEntity
import az.pashabank.dp.ms.qa.employee.model.LabelStatus
import az.pashabank.dp.ms.qa.employee.model.PositionStatus
import org.springframework.data.repository.CrudRepository

interface PositionRepository: CrudRepository<PositionEntity, Long> {
    fun findByStatus(status: PositionStatus) : List<PositionEntity>
}