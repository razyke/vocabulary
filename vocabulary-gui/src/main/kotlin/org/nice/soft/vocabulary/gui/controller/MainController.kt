package org.nice.soft.vocabulary.gui.controller

import com.jfoenix.controls.JFXAlert
import com.jfoenix.controls.JFXButton
import com.jfoenix.controls.JFXDialogLayout
import com.jfoenix.controls.JFXTextField
import io.datafx.controller.FXMLController
import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.Label
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyCodeCombination
import javafx.scene.input.KeyCombination
import javafx.scene.input.KeyEvent
import javafx.scene.layout.GridPane
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.stage.Modality
import org.nice.soft.vocabulary.core.VocabularyFactory
import org.nice.soft.vocabulary.core.model.VocabularyUnit
import org.nice.soft.vocabulary.core.service.UserPreferencesService
import org.nice.soft.vocabulary.core.service.VocabularyService
import java.net.URL
import java.util.*

@FXMLController(value = "/fxml/main-page.fxml", title = "Vocabulary App")
open class MainController : Initializable {
    private val vocabularyService = VocabularyFactory.provideInstance(VocabularyService::class.java)
    private val userPreferencesService = VocabularyFactory.provideInstance(UserPreferencesService::class.java)

    @FXML
    private lateinit var root: GridPane

    @FXML
    private lateinit var word: JFXTextField

    @FXML
    private lateinit var translation: JFXTextField

    @FXML
    private lateinit var prefButton: JFXButton

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        word.addValidation()
        translation.addValidation()
        val addPairHandler = EventHandler<KeyEvent> { if (it.code == KeyCode.ENTER) addNewVocabularyPair() }
        word.onKeyPressed = addPairHandler
        translation.onKeyPressed = addPairHandler
        root.sceneProperty().addListener { _, _, new -> if (new != null) initAccelerator(new) }
        prefButton.graphic = ImageView(Image("img/preference.png", 32.0, 30.0, true, true))
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
                Toast.showInfo("Word [${word.text}] and its translation are [${translation.text}] added", root)
                clearInputFieldsAndMakeFocusToWord()
            } catch (exception: Exception) {
                Toast.showError(exception.localizedMessage, root)
            }
        }
    }

    private fun clearInputFieldsAndMakeFocusToWord() {
        word.text = ""
        translation.text = ""
        word.requestFocus()
    }

    @FXML
    fun goToVocabularyList() {
        goToNextScene(root.scene, View.VOCABULARY_LIST)
    }

    @FXML
    fun goToCheckingPage() {
        goToNextScene(root.scene, View.CHECK_PAGE)
    }

    @FXML
    fun openPreferences() {
        val wordLimit = userPreferencesService.getWordLimit()
        val degradeModifier = userPreferencesService.getDegradeModifier()
        val alert = JFXAlert<Any>(root.scene.window)
        alert.initModality(Modality.APPLICATION_MODAL)
        alert.isOverlayClose = false
        val header = Label("User Properties")
        val (wordsLimitBox, limitField) = createLabelPlusInputBox("How many words to check: ", wordLimit.toString())
        val (degradeBox, degradeField) = createLabelPlusInputBox("Change degrade modifier: ", degradeModifier.toString())
        alert.setContent(JFXDialogLayout().apply {
            setHeading(header)
            setBody(VBox(
                wordsLimitBox,
                degradeBox
            ).apply { spacing = 50.0 })
            setActions(
                JFXButton("Cancel").apply { setOnAction { alert.hideWithAnimation() } },
                JFXButton("Submit").apply { setOnAction {
                    try {
                        userPreferencesService.changeAmountOfWordLimit(limitField.text.toInt())
                        userPreferencesService.changeDegradeModifier(degradeField.text.toInt())
                        alert.hideWithAnimation()
                    } catch (e: Exception) {
                        Toast.showError(e.localizedMessage, root)
                    }
                } },
            )
        })
        alert.show()
        header.requestFocus()
    }

    private fun createLabelPlusInputBox(label: String, fieldInput: String): Pair<HBox, JFXTextField> {
        val field = JFXTextField(fieldInput).apply { maxWidth = 50.0; alignment = Pos.CENTER }
        return HBox(Label(label).apply { padding = Insets(5.0, 0.0, 0.0, 0.0) }, field)
            .apply { spacing = 9.0 } to field
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