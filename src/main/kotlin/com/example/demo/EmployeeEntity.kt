package az.pashabank.dp.ms.qa.employee.dao.entity

import az.pashabank.dp.ms.qa.employee.model.EmployeeStatus
import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonManagedReference
import java.time.LocalDate
import java.time.LocalDateTime
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.hibernate.annotations.Where
import javax.persistence.*

@Entity
@Table(name = "employees")
@Where(clause = "status='ACTIVE'")
data class EmployeeEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long? = null,

        var firstName: String,

        var lastName: String,

        @ManyToOne
        @JoinColumn(name = "position_id", referencedColumnName = "id")
        @JsonBackReference
        var position: PositionEntity,

        var phone: String,

        var email: String,

        var department: Long,

        var employmentDate: LocalDate,

        var birthDate: LocalDate,

        @Enumerated(EnumType.STRING)
        var status: EmployeeStatus,

        @OneToMany(mappedBy = "employee", cascade = [CascadeType.ALL])
        @JsonManagedReference
        var jobHistories: MutableList<JobHistoryEntity> = mutableListOf(),

        @ManyToMany(cascade = [CascadeType.ALL])
        @JoinTable(name = "employees_labels",
                joinColumns = [JoinColumn(name = "employee_id", referencedColumnName = "id")],
                inverseJoinColumns = [JoinColumn(name = "label_id", referencedColumnName = "id")]
        )
        @JsonManagedReference
        var labels: MutableSet<LabelEntity> = mutableSetOf(),

        @CreationTimestamp
        var createdAt: LocalDateTime? = null,

        @UpdateTimestamp
        var updatedAt: LocalDateTime? = null
)
