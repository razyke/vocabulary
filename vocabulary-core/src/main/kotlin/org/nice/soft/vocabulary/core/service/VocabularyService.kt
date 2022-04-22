package org.nice.soft.vocabulary.core.service

import org.nice.soft.vocabulary.core.model.VocabularyUnit
import org.nice.soft.vocabulary.core.exception.EntityNotFound

/**
 * Service to process vocabulary words
 */
interface VocabularyService {
    /**
     * Find vocabulary pair by id
     * @param id the entity key
     * @throws EntityNotFound in case if entity not found by the specified id
     * @return the found entity
     */
    fun find(id: Long): VocabularyUnit

    /**
     * Find all vocabulary pairs
     * @return entities or an empty list
     */
    fun findAll(): List<VocabularyUnit>

    /**
     * Create a new vocabulary pair
     * @param vocabularyUnit the pair of word and translation
     * @return the persisted entity with id
     */
    fun create(vocabularyUnit: VocabularyUnit): VocabularyUnit

    /**
     * Update existing vocabulary pair
     * @param vocabularyUnit entity to update
     * @throws EntityNotFound in case if entity not found by the specified id
     * @return updated entity
     */
    fun update(vocabularyUnit: VocabularyUnit): VocabularyUnit

    /**
     * Delete vocabulary pair by it id
     * @param vocabularyId entity id
     * @throws EntityNotFound in case if entity not found by the specified id
     */
    fun delete(vocabularyId: Long)
}
