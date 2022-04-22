package org.nice.soft.vocabulary.core

import org.nice.soft.vocabulary.core.config.VocabularyCoreConfiguration
import org.nice.soft.vocabulary.core.model.VocabularyUnit
import org.nice.soft.vocabulary.core.service.VocabularyService
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.AnnotationConfigApplicationContext

fun main() {
    val app: ApplicationContext = AnnotationConfigApplicationContext(VocabularyCoreConfiguration::class.java)
    val service = app.getBean(VocabularyService::class.java)
    val vocabularyPair = VocabularyUnit(word = "Hello", translation = "Привет")
    service.create(vocabularyPair)
    println("Should save it")
    service.findAll().forEach {
        println(it)
    }
}
