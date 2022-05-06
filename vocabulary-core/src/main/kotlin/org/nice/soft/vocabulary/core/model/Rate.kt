package org.nice.soft.vocabulary.core.model


import javax.persistence.*
import java.time.LocalDate
import javax.validation.constraints.Max
import javax.validation.constraints.Min

@Entity
@Table(name = "rate")
class Rate(
    @field:Min(value = 0, message = "Rate can't be lower than 0")
    @field:Max(value = 100, message = "Rate can't be higher than 100")
    @Column(name = "current_unit_rate", nullable = false)
    var currentUnitRate: Double = 0.0
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long? = null

    @Column(name = "last_exam_date")
    var lastExamDate: LocalDate? = null

    @Column(name = "refresh_date")
    var refreshDate: LocalDate? = null

    @Column(name = "correct")
    var correct: Int = 0

    @Column(name = "wrong")
    var wrong: Int = 0

    companion object {
        fun createCopy(rate: Rate): Rate {
            return Rate(rate.currentUnitRate).apply {
                id = rate.id
                lastExamDate = rate.lastExamDate
                refreshDate = rate.refreshDate
                correct = rate.correct
                wrong = rate.wrong
            }
        }
    }
}