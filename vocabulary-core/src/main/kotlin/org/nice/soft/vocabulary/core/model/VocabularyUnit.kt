package org.nice.soft.vocabulary.core.model


import org.hibernate.Hibernate
import org.hibernate.validator.constraints.NotBlank
import javax.persistence.*

@Entity(name = "vocabulary")
class VocabularyUnit(
    @field:NotBlank
    @Column(name = "word")
    var word: String,

    @field:NotBlank
    @Column(name = "translation")
    var translation: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long? = null

    @OneToOne(cascade = [CascadeType.ALL], orphanRemoval = true)
    @JoinColumn(name = "rate_id", referencedColumnName = "id")
    var rate: Rate = Rate()

    override fun toString(): String {
        return "VocabularyUnit(id=$id, word='$word', translation='$translation')"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as VocabularyUnit

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    companion object {
        fun createCopy(vocabularyUnit: VocabularyUnit?): VocabularyUnit? {
            if (vocabularyUnit == null) return null
            return VocabularyUnit(vocabularyUnit.word, vocabularyUnit.translation).apply {
                id = vocabularyUnit.id
                rate = Rate.createCopy(vocabularyUnit.rate)
            }
        }
    }
}

