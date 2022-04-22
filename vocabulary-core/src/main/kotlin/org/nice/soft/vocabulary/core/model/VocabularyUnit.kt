package org.nice.soft.vocabulary.core.model

import javax.persistence.*

@Entity(name = "vocabulary")
class VocabularyUnit(
    @Column(name = "word")
    var word: String,

    @Column(name = "translation")
    var translation: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    var id: Long? = null

    override fun toString(): String {
        return "VocabularyUnit(id=$id, word='$word', translation='$translation')"
    }
}

