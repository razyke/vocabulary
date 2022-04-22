package org.nice.soft.vocabulary.gui.controller

import com.jfoenix.controls.JFXDecorator
import com.jfoenix.controls.JFXTextField
import javafx.animation.KeyFrame
import javafx.animation.Timeline
import javafx.fxml.FXMLLoader.load
import javafx.scene.Node
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.Label
import javafx.scene.layout.StackPane
import javafx.stage.Popup
import javafx.stage.Stage
import javafx.util.Duration

object ControllerUtils {
    fun loadScene(view: View): Parent = load(javaClass.getResource(view.resourcePlace))
}

fun goToNextScene(oldScene: Scene, view: View) {
    val root = oldScene.root
    if (root !is JFXDecorator) throw IllegalStateException("Root scene should be JFXDecorator")
    val contentContainer = ((root.children.last() as StackPane).children[0] as StackPane).children
    contentContainer.removeAt(0)
    contentContainer.add(ControllerUtils.loadScene(view))
}

enum class View(val resourcePlace: String) {
    MAIN_PAGE("/fxml/main-page.fxml"),
    VOCABULARY_LIST("/fxml/vocabulary-list.fxml"),
    CHECK_PAGE("/fxml/check.fxml"),
    EMPTY("/fxml/empty.fxml")
}

fun JFXTextField.addValidation() {
    this.focusedProperty().addListener { _, _, newValue ->
        if (!newValue) {
            validate()
        }
    }
}

object Toast {
    private fun createPopup(message: String, error: Boolean): Popup {
        val popup = Popup()
        popup.isAutoFix = true
        val label = Label(message)
        label.stylesheets.add("/css/toast.css")
        label.styleClass.add("toast")
        if (error) label.styleClass.add("error")
        popup.content.add(label)
        return popup
    }

    fun showInfo(message: String, node: Node) {
        val popup = createPopup(message, false)
        show(node, popup)
    }

    fun showError(message: String, node: Node) {
        val popup = createPopup(message, true)
        show(node, popup)
    }

    private fun show(node: Node, popup: Popup) {
        val stage = node.scene.window as Stage
        popup.setOnShown {
            popup.x = (stage.x + stage.width / 2 - popup.width / 2)
            popup.y = (stage.y + stage.height / 1.1 - popup.height / 2)
        }
        popup.show(stage)
        Timeline(KeyFrame(Duration.millis(1800.0), { popup.hide() })).play()
    }

}

