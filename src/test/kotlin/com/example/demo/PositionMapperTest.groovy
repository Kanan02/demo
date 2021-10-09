package az.pashabank.dp.ms.qa.employee.mapper

import az.pashabank.dp.ms.qa.employee.dao.entity.PositionEntity
import az.pashabank.dp.ms.qa.employee.model.PositionDto
import io.github.benas.randombeans.EnhancedRandomBuilder
import spock.lang.Specification

class PositionMapperTest extends Specification {
    private random = EnhancedRandomBuilder.aNewEnhancedRandom()

    def "TestMapEntityToDto"() {
        given:
        def entity = random.nextObject(PositionEntity)

        when:
        def dto = use(PositionMapperKt) {
            entity.toDto()
        }

        then:

        dto.id == entity.id
        dto.name == entity.name
        dto.description==entity.description
    }

    def "TestMapDtoToEntity"() {
        given:
        def dto = random.nextObject(PositionDto)

        when:
        def entity = use(PositionMapperKt) {
            dto.toEntity()
        }

        then:
        dto.name == entity.name
        dto.description==entity.description
    }

    def "MergeWith"() {
        given:
        def entity = random.nextObject(PositionEntity)
        def dto = random.nextObject(PositionDto)
        when:
        use(PositionMapperKt) { entity.mergeWith(dto) }

        then:
        entity.name == dto.name
        entity.description==dto.description
    }
}
