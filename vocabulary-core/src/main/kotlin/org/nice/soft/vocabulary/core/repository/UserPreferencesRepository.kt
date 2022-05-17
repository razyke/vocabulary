package org.nice.soft.vocabulary.core.repository

import org.nice.soft.vocabulary.core.model.UserPreferences
import org.springframework.data.repository.CrudRepository

/**
 * Crud repository for the user's preferences
 */
interface UserPreferencesRepository : CrudRepository<UserPreferences, Long>
