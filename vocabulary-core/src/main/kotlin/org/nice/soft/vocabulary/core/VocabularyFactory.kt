package org.nice.soft.vocabulary.core

import org.nice.soft.vocabulary.core.config.VocabularyCoreConfiguration
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.AnnotationConfigApplicationContext

object VocabularyFactory {
    private val context: ApplicationContext

    init {
        context = AnnotationConfigApplicationContext(VocabularyCoreConfiguration::class.java)
    }

    fun <T : Any> provideInstance(classType: Class<T>): T = context.getBean(classType)
}
