package org.nice.soft.vocabulary.core.service

/**
 * Service to manage the user's properties
 */
interface UserPreferencesService {

    /**
     * Initialization for the user's preferences.
     * Checks if there are any preferences, if not creates with default ones
     */
    fun init()

    /**
     * Changes amount of word limit for checking with a new session
     * @param newAmount new limit
     */
    fun changeAmountOfWordLimit(newAmount: Int)

    /**
     * Get the amount of word limit
     * @return its value
     */
    fun getWordLimit(): Int
}
