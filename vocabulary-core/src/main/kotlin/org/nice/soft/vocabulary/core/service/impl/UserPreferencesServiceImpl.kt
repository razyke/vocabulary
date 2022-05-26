package org.nice.soft.vocabulary.core.service.impl

import org.nice.soft.vocabulary.core.model.UserPreferences
import org.nice.soft.vocabulary.core.repository.UserPreferencesRepository
import org.nice.soft.vocabulary.core.service.UserPreferencesService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserPreferencesServiceImpl(private val userPreferencesRepository: UserPreferencesRepository) :
    UserPreferencesService {
    private val log = LoggerFactory.getLogger(this.javaClass)

    override fun init() {
        val notExistsById = !userPreferencesRepository.existsById(1)
        if (notExistsById) {
            userPreferencesRepository.save(UserPreferences().apply { id = 1 })
            log.info("Create new user's preferences")
        }
    }

    override fun changeAmountOfWordLimit(newAmount: Int) {
        val preferences = getUserPreferences()
        log.info("Change amount of word limit from {} to {} ", preferences.amountOfWordToCheckLimit, newAmount)
        preferences.amountOfWordToCheckLimit = newAmount
    }

    override fun getWordLimit() = getUserPreferences().amountOfWordToCheckLimit

    override fun changeDegradeModifier(newDegrade: Int) {
        val preferences = getUserPreferences()
        preferences.degradeModifier = newDegrade
    }

    override fun getDegradeModifier() = getUserPreferences().degradeModifier

    private fun getUserPreferences() = userPreferencesRepository.findById(1)
        .orElseThrow { IllegalStateException("Can't find the user's preferences") }
}
