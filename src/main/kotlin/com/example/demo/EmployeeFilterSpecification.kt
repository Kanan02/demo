package az.pashabank.dp.ms.qa.employee.service.specification

import az.pashabank.dp.ms.qa.employee.dao.entity.EmployeeEntity
import az.pashabank.dp.ms.qa.employee.dao.entity.PositionEntity
import az.pashabank.dp.ms.qa.employee.model.EmployeeFilterCriteria
import az.pashabank.dp.ms.qa.employee.model.Field
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Predicate
import javax.persistence.criteria.Root
import org.springframework.data.jpa.domain.Specification
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class EmployeeFilterSpecification(private val employeeFilterCriteria: EmployeeFilterCriteria?) :
    Specification<EmployeeEntity> {
    override fun toPredicate(
        root: Root<EmployeeEntity>,
        query: CriteriaQuery<*>,
        criteriaBuilder: CriteriaBuilder
    ): Predicate? {
        val predicates: MutableList<Predicate> = ArrayList()

        if (employeeFilterCriteria?.firstName != null) {
            employeeFilterCriteria.firstName = "%" + employeeFilterCriteria.firstName!!.toLowerCase().trim() + "%"
            predicates.add(
                criteriaBuilder.like(
                    criteriaBuilder.lower(root.get(Field.FIRST_NAME)),
                    employeeFilterCriteria.firstName
                )
            )
        }

        if (employeeFilterCriteria?.lastName != null) {
            employeeFilterCriteria.lastName = "%" + employeeFilterCriteria.lastName!!.toLowerCase().trim() + "%"
            predicates.add(
                criteriaBuilder.like(
                    criteriaBuilder.lower(root.get(Field.LAST_NAME)),
                    employeeFilterCriteria.lastName
                )
            )
        }

        if (employeeFilterCriteria?.email != null) {
            predicates.add(criteriaBuilder.like(root.get(Field.EMAIL), "%" + employeeFilterCriteria.email + "%"))
        }

        if (employeeFilterCriteria?.employmentDateFrom != null) {
            predicates.add(
                criteriaBuilder.greaterThanOrEqualTo(
                    root.get(Field.EMPLOYMENT_DATE),
                    LocalDate.parse(employeeFilterCriteria.employmentDateFrom, DateTimeFormatter.ISO_LOCAL_DATE)
                )
            )
        }

        if (employeeFilterCriteria?.employmentDateTo != null) {
            predicates.add(
                criteriaBuilder.lessThanOrEqualTo(
                    root.get(Field.EMPLOYMENT_DATE),
                    LocalDate.parse(employeeFilterCriteria.employmentDateTo, DateTimeFormatter.ISO_LOCAL_DATE)
                )
            )
        }

        if (employeeFilterCriteria?.department != null) {
            predicates.add(criteriaBuilder.equal(root.get<Long>(Field.DEPARTMENT), employeeFilterCriteria.department))
        }

        if (employeeFilterCriteria?.position != null) {
            predicates.add(criteriaBuilder.equal(root.get<PositionEntity>(Field.POSITION), employeeFilterCriteria?.position))
        }

        return criteriaBuilder.and(*predicates.toTypedArray())
    }

}
