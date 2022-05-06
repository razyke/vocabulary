package org.nice.soft.vocabulary.core.service.impl

import org.nice.soft.vocabulary.core.model.DateCoefficient
import org.nice.soft.vocabulary.core.model.Rate
import org.nice.soft.vocabulary.core.service.RateService
import org.nice.soft.vocabulary.core.service.VocabularyService
import org.slf4j.LoggerFactory.getLogger
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
class RateServiceImpl(private val vocabularyService: VocabularyService) : RateService {
    private val log = getLogger(RateServiceImpl::class.java)

    @Transactional
    override fun applyCorrectAnswer(rate: Rate) = calculateNewRate(rate, true)

    @Transactional
    override fun applyWrongAnswer(rate: Rate) = calculateNewRate(rate, false)

    @Transactional
    override fun refreshVocabularyRate() {
        vocabularyService.findAll().asSequence()
            .filter {
                it.rate.lastExamDate?.let { d -> if (d == LocalDate.now()) return@filter false }
                it.rate.refreshDate == null || it.rate.refreshDate!! < LocalDate.now() }
            .forEach {
                val fixRate = fixation(it.rate)
                val previousRate = it.rate.currentUnitRate
                it.rate.currentUnitRate = previousRate - 10 + fixRate
                it.rate.currentUnitRate = adjustBorder(it.rate.currentUnitRate)
                it.rate.refreshDate = LocalDate.now()
                log.info("Degrade the word's score with id {} from {} to {}", it.id, previousRate, it.rate.currentUnitRate)
            }
    }

    private fun calculateNewRate(rate: Rate, correct: Boolean) {
        if (correct) rate.correct += 1 else rate.wrong += 1
        rate.currentUnitRate += fixation(rate) + if (correct) 10.0 else -10.0
        rate.currentUnitRate = adjustBorder(rate.currentUnitRate)
        rate.lastExamDate = LocalDate.now()
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
        return dateCoefficient * (difference / (rate.correct + rate.wrong) * 10)
    }
}