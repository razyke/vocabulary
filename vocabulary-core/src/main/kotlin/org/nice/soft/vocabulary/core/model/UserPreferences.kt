package org.nice.soft.vocabulary.core.model

import javax.persistence.*
import javax.validation.constraints.Max
import javax.validation.constraints.Min

@Entity(name = "user_preferences")
class UserPreferences {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long? = null

    @Column(name = "amount_of_word_to_check_limit")
    var amountOfWordToCheckLimit: Int = 15

    @Column(name = "degrade_modifier")
    @field:Min(value = 1, message = "Degrade can't be lower than 1")
    @field:Max(value = 35, message = "Degrade can't be higher than 35")
    var degradeModifier: Int = 10
}