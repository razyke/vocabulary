package org.nice.soft.vocabulary.core.model

import java.util.*

class CheckSession(private val pairsToCheck: List<VocabularyUnit>) {
    private var currentUnit: VocabularyUnit? = null
    private var translate: String? = null
    private var answer: String? = null
    private var correctAnswerCount = 0
    private var skippedAnswerCount = 0
    private var wrongAnswerCount = 0
    private val checkQueue = ArrayDeque<VocabularyUnit>()
    private val random = Random()

    init {
        checkQueue.addAll(pairsToCheck.shuffled())
        getNextPair()
    }

    fun getWordToTranslate(): String? {
        return translate
    }

    fun checkAnswer(userAnswer: String): Boolean {
        val isCorrect = answer.equals(userAnswer, true)
        if (isCorrect) {
            correctAnswerCount++
        } else {
            wrongAnswerCount++
            currentUnit?.let { checkQueue.addLast(it) }
        }
        getNextPair()
        return isCorrect
    }

    fun skipAnswer() {
        skippedAnswerCount++
        getNextPair()
    }

    fun getResult(): Score? {
        return if (checkQueue.isEmpty()) Score(
            correctAnswerCount,
            skippedAnswerCount,
            wrongAnswerCount,
            pairsToCheck.size
        ) else null
    }

    private fun getNextPair() {
        if (checkQueue.isNotEmpty()) {
            currentUnit = checkQueue.pop()
            if (random.nextBoolean()) {
                answer = currentUnit?.translation
                translate = currentUnit?.word
            } else {
                answer = currentUnit?.word
                translate = currentUnit?.translation
            }
        } else {
            answer = null
            translate = null
        }
    }
}

class Score(val correctAnswers: Int, val skippedAnswers: Int, val wrongAnswers: Int, val totalWords: Int)