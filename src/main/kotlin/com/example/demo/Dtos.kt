package az.pashabank.dp.ms.qa.employee.model

import az.pashabank.dp.ms.qa.employee.dao.entity.PositionEntity
import java.time.LocalDate
import java.time.LocalDateTime

data class LabelDto(var id: Long, var name: String, var color: String,var description: String?)

data class LabelIdDto(var id: Long)

data class EmployeeDto(
    var id: Long, var firstName: String, var lastName: String, var position: PositionEntity, var phone: String,
    var email: String, var department: Long, var employmentDate: LocalDate, var birthDate: LocalDate,
    var createdAt: LocalDateTime?, var updatedAt: LocalDateTime?
)

data class EmployeeFilterCriteria(
    var firstName: String?,
    var lastName: String?,
    var position: PositionEntity?,
    var email: String?,
    var department: Long?,
    var employmentDateFrom: String?,
    var employmentDateTo: String?,
)

data class EmployeeSortingCriteria(
    val sortByCreatedAt: String?,
    val sortByEmploymentDate: String?,
    val sortByDepartment: String?
)

data class JobHistoryDto(
    var id: Long, var squadId: Long?, var startDate: LocalDate,
    var endDate: LocalDate?, var inCost: Boolean, var role: Role, var employeeId: Long,
    var createdAt: LocalDateTime?, var updatedAt: LocalDateTime?
)

data class JobHistorySortingCriteria(
    val sortByCreatedAt: String?,
    val sortByStartDate: String?,
    val sortByEndDate: String?,
    val sortBySquadId: String?
)

data class PositionDto(
    var id:Long,var name: String,var description: String?
)

