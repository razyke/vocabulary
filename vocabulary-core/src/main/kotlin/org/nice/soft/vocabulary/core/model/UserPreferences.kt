package org.nice.soft.vocabulary.core.model

import javax.persistence.*

@Entity(name = "user_preferences")
class UserPreferences {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long? = null

    @Column(name = "amount_of_word_to_check_limit")
    var amountOfWordToCheckLimit: Int = 15
}