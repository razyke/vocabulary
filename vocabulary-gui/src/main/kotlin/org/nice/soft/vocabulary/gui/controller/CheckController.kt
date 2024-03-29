package org.nice.soft.vocabulary.gui.controller

import com.jfoenix.controls.JFXButton
import com.jfoenix.controls.JFXTextField
import io.datafx.controller.FXMLController
import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.geometry.Pos
import javafx.scene.Node
import javafx.scene.control.Label
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import javafx.scene.layout.GridPane
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.scene.text.Font
import org.nice.soft.vocabulary.core.VocabularyFactory
import org.nice.soft.vocabulary.core.service.SessionCheckService
import java.net.URL
import java.util.*

@FXMLController(value = "/fxml/check.fxml")
class CheckController : Initializable {
    private val sessionCheck = VocabularyFactory.provideInstance(SessionCheckService::class.java)
    @FXML
    private lateinit var root: GridPane

    @FXML
    lateinit var wordBox: VBox

    @FXML
    lateinit var translationBox: VBox

    @FXML
    lateinit var buttonBox: HBox

    @FXML
    lateinit var word: Label

    @FXML
    lateinit var translation: JFXTextField

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        val checkAnswerEvent = EventHandler<KeyEvent> { if (it.code == KeyCode.ENTER) checkAnswer() }
        translation.onKeyPressed = checkAnswerEvent
        root.sceneProperty().addListener { _, _, new -> if (new != null) translation.requestFocus() }
        showNextWord()
    }

    @FXML
    fun checkAnswer() {
        val isNotCorrect = !sessionCheck.checkCurrent(translation.text)
        if (isNotCorrect) {
            Toast.showError("Wrong answer, the right one is [${sessionCheck.getRightAnswer()}]", root)
        }
        clearTranslationField()
        showNextWord()
    }

    private fun clearTranslationField() {
        translation.text = ""
    }

    @FXML
    fun skipAnswer() {
        sessionCheck.skipAnswer()
        showNextWord()
    }

    private fun showNextWord() {
        val translate = sessionCheck.wordToTranslate()
        if (translate == null) {
            showResults()
        } else {
            word.text = translate
        }
    }

    private fun showResults() {
        wordBox.children.remove(word)
        translationBox.children.remove(translation)
        buttonBox.children.removeAll { it is JFXButton }
        buttonBox.children.add(createButton("Return to _home", "#D4D2CA") {
            goToNextScene(root.scene, View.MAIN_PAGE)
        })
        buttonBox.children.add(createButton("_Retry", "#FCC736") {
            goToNextScene(root.scene, View.CHECK_PAGE)
        })
        root.add(resultPane(), 1, 1)
    }

    private fun createButton(text: String, color: String, action: () -> Unit) : JFXButton {
        val button = JFXButton(text)
        button.style = "-fx-background-color: ${color};"
        button.setOnAction { action() }
        button.font = Font.font("Lucida Console", 14.0)
        root.sceneProperty().addListener { _, _, new -> if (new != null) translation.requestFocus() }
        return button
    }

    private fun resultPane(): GridPane {
        val result = sessionCheck.getResult() ?: throw IllegalStateException("Not all questions are answered")
        val pane = GridPane()
        pane.add(createHbox(
            createLabel("Total words:"),
            createLabel(result.totalWords.toString())
        ), 0, 0)
        pane.add(createHbox(
            createLabel("Correct:"),
            createLabel(result.correctAnswers.toString(), Color(1 - 0.3, 1 - 0.175, 1 - 0.7, 1.0))
        ), 0, 1)
        pane.add(createHbox(
            createLabel("Mistakes:"),
            createLabel(result.wrongAnswers.toString(), Color(1 - 0.121, 1 - 0.5, 1 - 0.5, 1.0))
        ), 1, 0)
        pane.add(createHbox(
            createLabel("Skipped"),
            createLabel(result.skippedAnswers.toString(), Color(1 - 0.43, 1 - 0.424, 1 - 0.424, 1.0))
        ), 1, 1)
        pane.hgap = 40.0
        pane.vgap = 20.0
        pane.alignment = Pos.CENTER
        return pane
    }

    private fun createHbox(vararg node: Node): HBox {
        val hBox = HBox(10.0, *node)
        hBox.alignment = Pos.CENTER
        return hBox
    }

    private fun createLabel(text: String, color: Color? = null): Label {
        val label = Label(text)
        label.font = Font.font("Lucida Console", 18.0)
        color?.let { label.textFill = it }
        return label
    }
}