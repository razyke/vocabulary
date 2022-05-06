package org.nice.soft.vocabulary.core.repository

import org.nice.soft.vocabulary.core.model.VocabularyUnit
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

/**
 * Crud repository for VocabularyUnit
 */
interface VocabularyPairRepository : CrudRepository<VocabularyUnit, Long> {

    @Query("select v from vocabulary v order by v.rate.currentUnitRate asc")
    fun findAllOrderByRate(): List<VocabularyUnit>
}
