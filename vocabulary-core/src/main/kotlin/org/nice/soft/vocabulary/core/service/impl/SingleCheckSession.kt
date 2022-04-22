package org.nice.soft.vocabulary.core.service.impl

import org.nice.soft.vocabulary.core.model.CheckSession
import org.nice.soft.vocabulary.core.service.SessionCheckService
import org.nice.soft.vocabulary.core.service.VocabularyService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct

@Service
@Scope("prototype")
class SingleCheckSession(@Autowired private val vocabularyService: VocabularyService) : SessionCheckService {
    private lateinit var session: CheckSession

    @PostConstruct
    fun initialization() {
        val vocabularyUnits = vocabularyService.findAll()
        session = CheckSession(vocabularyUnits)
    }

    override fun wordToTranslate() = session.getWordToTranslate()

    override fun checkCurrent(answer: String) = session.checkAnswer(answer)

    override fun skipAnswer() = session.skipAnswer()

    override fun getResult() = session.getResult()
}