package org.nice.soft.vocabulary.core.service

import org.nice.soft.vocabulary.core.model.Score

/**
 * Service to exam current vocabulary list
 */
interface SessionCheckService {
    /**
     * Get current world to translate or null if no more words to check
     */
    fun wordToTranslate(): String?

    /**
     * Exam the user's answer
     *
     * @param answer user input
     * @return right or wrong translation
     */
    fun checkCurrent(answer: String): Boolean

    /**
     * Skip current word
     */
    fun skipAnswer()

    /**
     * Return the score of current check session
     *
     * @return score if exam is finished or null if not all translations are complete
     */
    fun getResult(): Score?

    /**
     * Get current right answer for vocabulary unit
     * May return null if no more words to check
     *
     * @return right answer or null
     */
    fun getRightAnswer(): String?
}