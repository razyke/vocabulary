package org.nice.soft.vocabulary.gui.controller

import com.jfoenix.controls.JFXTextField
import io.datafx.controller.FXMLController
import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.Scene
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyCodeCombination
import javafx.scene.input.KeyCombination
import javafx.scene.input.KeyEvent
import javafx.scene.layout.GridPane
import org.nice.soft.vocabulary.core.VocabularyFactory
import org.nice.soft.vocabulary.core.model.VocabularyUnit
import org.nice.soft.vocabulary.core.service.VocabularyService
import java.net.URL
import java.util.*

@FXMLController(value = "/fxml/main-page.fxml", title = "Vocabulary App")
open class MainController : Initializable {
    private val vocabularyService = VocabularyFactory.provideInstance(VocabularyService::class.java)

    @FXML
    private lateinit var root: GridPane

    @FXML
    private lateinit var word: JFXTextField

    @FXML
    private lateinit var translation: JFXTextField

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        word.addValidation()
        translation.addValidation()
        val addPairHandler = EventHandler<KeyEvent> { if (it.code == KeyCode.ENTER) addNewVocabularyPair() }
        word.onKeyPressed = addPairHandler
        translation.onKeyPressed = addPairHandler
        root.sceneProperty().addListener { _, _, new -> if (new != null) initAccelerator(new) }
    }

    private fun initAccelerator(scene: Scene) {
        scene.accelerators[KeyCodeCombination(KeyCode.W, KeyCombination.ALT_DOWN)] = Runnable{ word.requestFocus() }
        scene.accelerators[KeyCodeCombination(KeyCode.T, KeyCombination.ALT_DOWN)] = Runnable{ translation.requestFocus() }
    }

    @FXML
    fun addNewVocabularyPair() {
        withValidInput {
            try {
                vocabularyService.create(VocabularyUnit(word = word.text, translation = translation.text))
                Toast.showInfo("Word and translation added", root)
            } catch (exception: Exception) {
                Toast.showError(exception.localizedMessage, root)
            }
        }
    }

    @FXML
    fun goToVocabularyList() {
        goToNextScene(root.scene, View.VOCABULARY_LIST)
    }

    @FXML
    fun goToCheckingPage() {
        goToNextScene(root.scene, View.CHECK_PAGE)
    }

    private fun withValidInput(action: () -> Unit) {
        if (word.text.isNotBlank() && translation.text.isNotBlank()) {
            action()
        } else {
            word.validate()
            translation.validate()
        }
    }
}