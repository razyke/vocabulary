package org.nice.soft.vocabulary.core.service.impl

import org.nice.soft.vocabulary.core.model.DateCoefficient
import org.nice.soft.vocabulary.core.model.Rate
import org.nice.soft.vocabulary.core.service.RateService
import org.nice.soft.vocabulary.core.service.UserPreferencesService
import org.nice.soft.vocabulary.core.service.VocabularyService
import org.slf4j.LoggerFactory.getLogger
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
class RateServiceImpl(
    private val vocabularyService: VocabularyService,
    private val userPreferencesService: UserPreferencesService
) : RateService {
    private val log = getLogger(RateServiceImpl::class.java)

    override fun applyCorrectAnswer(rate: Rate) = calculateNewRate(rate, true)

    override fun applyWrongAnswer(rate: Rate) = calculateNewRate(rate, false)

    @Transactional
    override fun refreshVocabularyRate() {
        val degradeModifier = userPreferencesService.getDegradeModifier()
        vocabularyService.findAll().asSequence()
            .filter {
                if (it.rate.lastExamDate == null || it.rate.lastExamDate == LocalDate.now()) return@filter false
                it.rate.refreshDate == null || it.rate.refreshDate!! < LocalDate.now() }
            .forEach {
                val fixRate = fixation(it.rate)
                val previousRate = it.rate.currentUnitRate
                it.rate.currentUnitRate = previousRate - degradeModifier + fixRate
                it.rate.currentUnitRate = adjustBorder(it.rate.currentUnitRate)
                it.rate.refreshDate = LocalDate.now()
                log.info(
                    "Degrade the word's score with id {} from {} to {}",
                    it.id,
                    previousRate,
                    it.rate.currentUnitRate
                )
            }
    }

    private fun calculateNewRate(rate: Rate, correct: Boolean): Rate {
        val newRate = Rate.createCopy(rate)
        if (correct) newRate.correct += 1 else newRate.wrong += 1
        newRate.currentUnitRate += fixation(newRate) + if (correct) 10.0 else -10.0
        newRate.currentUnitRate = adjustBorder(newRate.currentUnitRate)
        newRate.lastExamDate = LocalDate.now()
        return newRate
    }

    private fun adjustBorder(unitRate: Double) = when {
        unitRate > 100.0 -> 100.0
        unitRate < 0 -> 0.0
        else -> unitRate
    }

    private fun fixation(rate: Rate): Double {
        val difference = rate.correct - rate.wrong
        val dateCoefficient = DateCoefficient.get(rate.lastExamDate)
        if (difference == 0) return 0.0
        return dateCoefficient * (difference.toDouble() / (rate.correct + rate.wrong) * 10)
    }
}