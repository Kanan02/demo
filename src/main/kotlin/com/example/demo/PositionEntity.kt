package az.pashabank.dp.ms.qa.employee.dao.entity


import az.pashabank.dp.ms.qa.employee.model.LabelStatus
import az.pashabank.dp.ms.qa.employee.model.PositionStatus
import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference
import javax.persistence.*

@Entity
@Table(name = "positions")
data class PositionEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @OneToMany(mappedBy = "position", cascade = [CascadeType.ALL])
    @JsonManagedReference
    var employees : MutableList<EmployeeEntity> = mutableListOf(),

    var name: String,
    var description: String?,

    @Enumerated(EnumType.STRING)
    var status: PositionStatus,
    )

