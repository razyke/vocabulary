package org.nice.soft.vocabulary.core.service.impl

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.whenever
import org.nice.soft.vocabulary.core.model.Rate
import org.nice.soft.vocabulary.core.model.VocabularyUnit
import org.nice.soft.vocabulary.core.service.UserPreferencesService
import org.nice.soft.vocabulary.core.service.VocabularyService
import java.time.LocalDate

@ExtendWith(MockitoExtension::class)
class RateServiceImplTest {

    @Mock
    private lateinit var vocabularyService: VocabularyService

    @Mock
    private lateinit var userPreferencesService: UserPreferencesService

    @InjectMocks
    private lateinit var rateService: RateServiceImpl

    @BeforeEach
    fun setUp() {
        whenever(userPreferencesService.getDegradeModifier()).thenReturn(10)
    }

    @Test
    fun refreshVocabularyRate() {
        //given
        val unit = VocabularyUnit("Hello", "Привет")
        unit.rate = Rate(30.0).apply {
            correct = 5
            wrong = 0
            refreshDate = LocalDate.now().minusDays(2)
            lastExamDate = LocalDate.now().minusDays(1)
        }
        whenever(vocabularyService.findAll()).thenReturn(listOf(unit))

        //when
        rateService.refreshVocabularyRate()

        //then
        assertEquals(22.0, unit.rate.currentUnitRate)
    }

    @Test
    fun refreshVocabularyRateOnLowerBorder() {
        //given
        val unit = VocabularyUnit("Hello", "Привет")
        unit.rate = Rate(0.0).apply {
            correct = 3
            wrong = 3
            refreshDate = LocalDate.now().minusDays(2)
            lastExamDate = LocalDate.now().minusDays(1)
        }
        whenever(vocabularyService.findAll()).thenReturn(listOf(unit))

        //when
        rateService.refreshVocabularyRate()

        //then
        assertEquals(0.0, unit.rate.currentUnitRate)
    }

    @Test
    fun refreshVocabularyRateForNotExaminedUnit() {
        //given
        val unit = VocabularyUnit("Hello", "Привет")
        unit.rate = Rate(0.0)
        whenever(vocabularyService.findAll()).thenReturn(listOf(unit))

        //when
        rateService.refreshVocabularyRate()

        //then
        assertEquals(0.0, unit.rate.currentUnitRate)
    }
}