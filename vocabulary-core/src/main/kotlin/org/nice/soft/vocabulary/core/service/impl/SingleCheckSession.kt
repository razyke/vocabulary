package org.nice.soft.vocabulary.core.service.impl

import org.nice.soft.vocabulary.core.model.CheckSession
import org.nice.soft.vocabulary.core.model.Score
import org.nice.soft.vocabulary.core.service.RateService
import org.nice.soft.vocabulary.core.service.SessionCheckService
import org.nice.soft.vocabulary.core.service.VocabularyService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct

// todo change it, using user preferences
private const val WORDS_COUNT_TO_CHECK = 15

@Service
@Scope("prototype")
class SingleCheckSession(
    @Autowired private val vocabularyService: VocabularyService, @Autowired private val rateService: RateService
) : SessionCheckService {
    private val log = LoggerFactory.getLogger(SessionCheckService::class.java)
    private lateinit var session: CheckSession

    @PostConstruct
    fun initialization() {
        val vocabularyUnits = vocabularyService.findAllSortedByRate()
        session = CheckSession(vocabularyUnits.take(WORDS_COUNT_TO_CHECK), rateService)
    }

    override fun wordToTranslate() = session.getWordToTranslate()

    override fun checkCurrent(answer: String): Boolean {
        val isCorrect = session.checkAnswer(answer)
        if (isCorrect) {
            log.info("The '{}' is correct answer", answer)
        } else {
            log.warn("The '{}' is wrong answer", answer)
        }
        return isCorrect
    }

    override fun skipAnswer() = session.skipAnswer()

    override fun getResult(): Score? {
        val result = session.getResult()
        result?.let {
            log.info(
                "Exam is completed: Correct - {}, Wrong - {}, Skipped - {}",
                it.correctAnswers,
                it.wrongAnswers,
                it.skippedAnswers
            )
        }
        return result
    }
}