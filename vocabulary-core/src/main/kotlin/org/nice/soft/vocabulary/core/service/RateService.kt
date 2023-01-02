package org.nice.soft.vocabulary.core.service

import org.nice.soft.vocabulary.core.model.Rate

/**
 * Service to rect and calculate rate on the user's actions
 */
interface RateService {

    /**
     * Calculate and return new rate on the wrong answer scenario
     * @param rate current rate
     * @return new rate
     */
    fun applyWrongAnswer(rate: Rate): Rate

    /**
     * Calculate and return new rate on the correct answer scenario
     * @param rate current rate
     * @return new rate
     */
    fun applyCorrectAnswer(rate: Rate): Rate

    /**
     * Refresh vocabulary pairs rate depends on how long ago was checking
     */
    fun refreshVocabularyRate()
}