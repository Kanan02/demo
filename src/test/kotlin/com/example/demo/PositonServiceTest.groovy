package az.pashabank.dp.ms.qa.employee.service

import az.pashabank.dp.ms.qa.employee.dao.entity.EmployeeEntity
import az.pashabank.dp.ms.qa.employee.dao.entity.LabelEntity
import az.pashabank.dp.ms.qa.employee.dao.entity.PositionEntity
import az.pashabank.dp.ms.qa.employee.dao.repository.PositionRepository
import az.pashabank.dp.ms.qa.employee.mapper.LabelMapperKt
import az.pashabank.dp.ms.qa.employee.mapper.PositionMapperKt
import az.pashabank.dp.ms.qa.employee.model.LabelDto
import az.pashabank.dp.ms.qa.employee.model.LabelStatus
import az.pashabank.dp.ms.qa.employee.model.NotFoundException
import az.pashabank.dp.ms.qa.employee.model.PositionDto
import az.pashabank.dp.ms.qa.employee.model.PositionStatus
import io.github.benas.randombeans.EnhancedRandomBuilder
import spock.lang.Specification

import java.time.LocalDateTime

class PositionServiceTest extends  Specification {
    private PositionRepository positionRepository
    private PositionService positionService
    private random = EnhancedRandomBuilder.aNewEnhancedRandom()

    def setup() {
        positionRepository = Mock()
        positionService = new PositionService(positionRepository)
    }

    def "TestGetPositions"() {
        given:
        def pos1 = random.nextObject(PositionEntity)
        def pos2 = random.nextObject(PositionEntity)
        def pos3 = random.nextObject(PositionEntity)
        def positionEntity = [pos1, pos2, pos3]
        def positionDto = [
                use(PositionMapperKt) { pos1.toDto() },
                use(PositionMapperKt) { pos2.toDto() },
                use(PositionMapperKt) { pos3.toDto() }
        ]

        when:
        def result = positionService.getPositions()

        then:
        1 * positionRepository.findByStatus(PositionStatus.ACTIVE) >> positionEntity

        result == positionDto
    }

    def "TestCreatePosition"() {
        given:
        def request = new PositionDto(0, "pos", "desc")
        def savedEntity = use(PositionMapperKt) { request.toEntity() }

        when:
        positionService.createPosition(request)

        then:
        1 * positionRepository.save(savedEntity)
    }

    def "TestEditPosition success case"() {
        given:
        def id = 1
        def employee=random.nextObject(EmployeeEntity)
        def employeeList = [employee]
        def request = new PositionDto(0, "position", "desc1")
        def existingPosition = new PositionEntity(id,employeeList ,"position", "desc1",PositionStatus.ACTIVE)
        def savedEntity = new PositionEntity(id,employeeList ,"position", "desc2",PositionStatus.ACTIVE)

        when:
        positionService.editPosition(id, request)

        then:
        1 * positionRepository.findById(id) >> Optional.of(existingPosition)
        1 * positionRepository.save(savedEntity)
    }

    def "TestEditPosition error case, position not found"() {
        given:
        def id = 1
        def request = new PositionDto(0, "pos", "desc1")

        when:
        positionService.editPosition(id, request)

        then:
        1 * positionRepository.findById(id) >> Optional.empty()
        0 * positionRepository.save()

        NotFoundException ex = thrown()
        ex.message == "exception.qa-employee.position-not-found"
    }

    def "TestDeletePosition success case"() {
        given:
        def id = 1
        def employee=random.nextObject(EmployeeEntity)
        def employeeList = [employee]
        def existingPosition = new PositionEntity(id,employeeList ,"position", "desc1",PositionStatus.ACTIVE)
        def savedEntity = new PositionEntity(id,employeeList ,"position", "desc2",PositionStatus.ACTIVE)

        when:
        positionService.deletePosition(id)

        then:
        1 * positionRepository.findById(id) >> Optional.of(existingPosition)
        1 * positionRepository.save(savedEntity)
    }

    def "TestDeletePosition error case, position not found"() {
        given:
        def id = 1

        when:
        positionService.deletePosition(id)

        then:
        1 * positionRepository.findById(id) >> Optional.empty()
       // 0 * positionRepository.save()

        NotFoundException ex = thrown()
        ex.message == "exception.qa-employee.position-not-found"
    }
}
