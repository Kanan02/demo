package az.pashabank.dp.ms.qa.employee.service

import az.pashabank.dp.ms.qa.employee.dao.entity.PositionEntity
import az.pashabank.dp.ms.qa.employee.dao.repository.PositionRepository
import az.pashabank.dp.ms.qa.employee.logger.DPLogger
import az.pashabank.dp.ms.qa.employee.mapper.mergeWith
import az.pashabank.dp.ms.qa.employee.mapper.toDto
import az.pashabank.dp.ms.qa.employee.mapper.toEntity
import az.pashabank.dp.ms.qa.employee.model.NotFoundException
import az.pashabank.dp.ms.qa.employee.model.PositionDto
import az.pashabank.dp.ms.qa.employee.model.PositionStatus
import org.springframework.stereotype.Service

@Service
class PositionService(private val positionRepository: PositionRepository) {

    companion object {
        private val log: DPLogger = DPLogger.getLogger(this::class.java)
    }

    fun getPositions(): List<PositionDto> {
        log.info("ActionLog.getPositions.start")

        return positionRepository.findByStatus(PositionStatus.ACTIVE).map { it.toDto() }
    }

    fun deletePosition(id: Long) {
        log.info("ActionLog.deletePosition.start id:$id")

        val position = getPositionIfExist(id)

        position.status = PositionStatus.DELETED

        positionRepository.save(position)
        log.info("ActionLog.deletePosition.success id:$id")
    }

    fun createPosition(position: PositionDto) {
        log.info("ActionLog.createPosition.start")

        positionRepository.save(position.toEntity())

        log.info("ActionLog.createPosition.success")
    }

    fun editPosition(id: Long, position: PositionDto) {
        log.info("ActionLog.editPosition.start id:$id")

        val existingPosition = getPositionIfExist(id)
        existingPosition.mergeWith(position)
        positionRepository.save(existingPosition)
        log.info("ActionLog.editPosition.success id:$id")
    }

    private fun getPositionIfExist(id: Long): PositionEntity {
        return positionRepository.findById(id).orElseThrow {
            log.error("ActionLog.getPositionIfExist.error label:$id not found")
            throw NotFoundException("exception.qa-employee.label-not-found")
        }
    }
}