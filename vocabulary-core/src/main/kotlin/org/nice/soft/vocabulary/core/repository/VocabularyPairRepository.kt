package org.nice.soft.vocabulary.core.repository

import org.nice.soft.vocabulary.core.model.VocabularyUnit
import org.springframework.data.repository.CrudRepository

/**
 * Crud repository for VocabularyUnit
 */
interface VocabularyPairRepository : CrudRepository<VocabularyUnit, Long> {

    fun findAllByOrderByRateCurrentUnitRateAscRateLastExamDateAsc(): List<VocabularyUnit>
}
