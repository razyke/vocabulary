package org.nice.soft.vocabulary.core.service

import org.nice.soft.vocabulary.core.model.Rate

/**
 * Service to rect and calculate rate on the user's actions
 */
interface RateService {

    /**
     * Calculate and return new rate on the correct answer scenario
     */
    fun applyWrongAnswer(rate: Rate)

    /**
     * Calculate and return new rate on the wrong answer scenario
     */
    fun applyCorrectAnswer(rate: Rate)

    /**
     * Refresh vocabulary pairs rate depends on how long ago was checking
     */
    fun refreshVocabularyRate()
}