package org.nice.soft.vocabulary.core.service.impl

import org.nice.soft.vocabulary.core.exception.EntityNotFound
import org.nice.soft.vocabulary.core.model.VocabularyUnit
import org.nice.soft.vocabulary.core.repository.VocabularyPairRepository
import org.nice.soft.vocabulary.core.service.VocabularyService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import javax.persistence.EntityNotFoundException

@Service
class VocabularyServiceImpl(@Autowired private val repository: VocabularyPairRepository) : VocabularyService {
    private val log = LoggerFactory.getLogger(VocabularyServiceImpl::class.java)

    override fun find(id: Long): VocabularyUnit =
        logOnError {  repository.findByIdOrNull(id) } ?: throw EntityNotFound("The vocabulary pair id: $id not found")

    override fun findAll(): List<VocabularyUnit> = logOnError {  repository.findAll().toList() }

    override fun findAllSortedByRate() = logOnError {  repository.findAllOrderByRate() }

    override fun create(vocabularyUnit: VocabularyUnit): VocabularyUnit = logOnError {
        repository.save(vocabularyUnit)
    }

    override fun update(vocabularyUnit: VocabularyUnit): VocabularyUnit {
        checkVocabularyExistence(
            vocabularyUnit.id ?: throw IllegalArgumentException("Updatable entity's id should be not null")
        )
        return logOnError {  repository.save(vocabularyUnit) }
    }

    override fun delete(vocabularyId: Long) {
        checkVocabularyExistence(vocabularyId)
        logOnError { repository.deleteById(vocabularyId) }
    }

    private fun checkVocabularyExistence(id: Long) {
        if (!repository.existsById(id)) {
            log.error("The vocabulary pair with id '{}' doesn't exist", id)
            throw EntityNotFoundException("The vocabulary pair with id: $id doesn't exist")
        }
    }

    private fun <T> logOnError(body: () -> T): T {
        try {
            return body()
        } catch (e: Exception) {
            log.error("Unexpected service error occurred", e)
            throw e
        }
    }
}
