package org.nice.soft.vocabulary.core.service.impl

import org.nice.soft.vocabulary.core.exception.EntityNotFound
import org.nice.soft.vocabulary.core.model.VocabularyUnit
import org.nice.soft.vocabulary.core.repository.VocabularyPairRepository
import org.nice.soft.vocabulary.core.service.VocabularyService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import javax.persistence.EntityNotFoundException

@Service
class VocabularyServiceImpl(@Autowired private val repository: VocabularyPairRepository) : VocabularyService {

    override fun find(id: Long): VocabularyUnit =
        repository.findByIdOrNull(id) ?: throw EntityNotFound("The vocabulary pair id: $id not found")

    override fun findAll(): List<VocabularyUnit> = repository.findAll().toList()

    override fun findAllSortedByRate() = repository.findAllOrderByRate()

    override fun create(vocabularyUnit: VocabularyUnit): VocabularyUnit = repository.save(vocabularyUnit)

    override fun update(vocabularyUnit: VocabularyUnit): VocabularyUnit {
        checkVocabularyExistence(
            vocabularyUnit.id ?: throw IllegalArgumentException("Updatable entity's id should be not null")
        )
        return repository.save(vocabularyUnit)
    }

    override fun delete(vocabularyId: Long) {
        checkVocabularyExistence(vocabularyId)
        repository.deleteById(vocabularyId)
    }

    private fun checkVocabularyExistence(id: Long) {
        if (!repository.existsById(id)) {
            throw EntityNotFoundException("The vocabulary pair with id: $id doesn't exist")
        }
    }
}
