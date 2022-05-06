package org.nice.soft.vocabulary.gui

import com.jfoenix.assets.JFoenixResources
import com.jfoenix.controls.JFXDecorator
import com.jfoenix.svg.SVGGlyph
import javafx.application.Application
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Screen
import javafx.stage.Stage
import org.nice.soft.vocabulary.core.VocabularyFactory
import org.nice.soft.vocabulary.core.service.RateService
import org.nice.soft.vocabulary.gui.controller.ControllerUtils
import org.nice.soft.vocabulary.gui.controller.View
import org.nice.soft.vocabulary.gui.controller.goToNextScene

fun main() {
    Application.launch(VocabularyGuiLauncher::class.java)
}

class VocabularyGuiLauncher : Application() {
    private val rateService = VocabularyFactory.provideInstance(RateService::class.java)

    override fun start(stage: Stage) {
        stage.title = "Vocabulary app"
        val root = ControllerUtils.loadScene(View.EMPTY)
        val decorator = applyJFXDecorator(stage, root)
        val (width, height) = getWidthAndHeight()
        val scene = Scene(decorator, width, height)
        setUpStyles(scene)
        stage.scene = scene
        stage.show()
        goToNextScene(scene, View.MAIN_PAGE)
        rateService.refreshVocabularyRate()
    }

    private fun applyJFXDecorator(stage: Stage, pane: Parent) = JFXDecorator(stage, pane).apply {
        isCustomMaximize = true
        graphic = SVGGlyph("M43 140L5 5L43 14L61 113L79 14L117 5L79 140H43Z")
    }

    private fun getWidthAndHeight(): Pair<Double, Double> {
        var width = 800.0
        var height = 600.0
        try {
            val bounds = Screen.getScreens()[0].bounds
            width = bounds.width / 2.5
            height = bounds.height / 1.35
        } catch (e: Exception) {
            println("Can't determinate bounds of the screen")
        }
        return width to height
    }

    private fun setUpStyles(scene: Scene) = scene.stylesheets.addAll(
        JFoenixResources.load("css/jfoenix-fonts.css").toExternalForm(),
        JFoenixResources.load("css/jfoenix-design.css").toExternalForm(),
        javaClass.getResource("/css/vocabulary.css")?.toExternalForm()
    )
}
