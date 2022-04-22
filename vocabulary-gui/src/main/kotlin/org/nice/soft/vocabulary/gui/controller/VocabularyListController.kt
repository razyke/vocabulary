package org.nice.soft.vocabulary.gui.controller

import com.jfoenix.controls.*
import com.jfoenix.controls.cells.editors.TextFieldEditorBuilder
import com.jfoenix.controls.cells.editors.base.GenericEditableTreeTableCell
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject
import io.datafx.controller.ViewController
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.TreeTableColumn
import javafx.scene.input.MouseEvent
import javafx.scene.layout.StackPane
import javafx.util.Callback
import org.nice.soft.vocabulary.core.VocabularyFactory
import org.nice.soft.vocabulary.core.model.VocabularyUnit as Vocabulary
import org.nice.soft.vocabulary.core.service.VocabularyService
import java.net.URL
import java.util.*
import java.util.function.Predicate

@ViewController(value = "/fxml/vocabulary-list.fxml")
class VocabularyListController : Initializable {
    private val vocabularyService = VocabularyFactory.provideInstance(VocabularyService::class.java)
    @FXML
    private lateinit var root: StackPane

    @FXML
    private lateinit var vocabularyTableView: JFXTreeTableView<ObservableVocabularyUnit>

    @FXML
    private lateinit var wordColumn: JFXTreeTableColumn<ObservableVocabularyUnit, String>

    @FXML
    private lateinit var translationColumn: JFXTreeTableColumn<ObservableVocabularyUnit, String>

    @FXML
    private lateinit var removeButton: JFXButton

    @FXML
    lateinit var searchField: JFXTextField

    private var observableVocabularyUnit : ObservableList<ObservableVocabularyUnit>? = null

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        removeButton.isDisable = true
        wordColumn.cellValueFactory = Callback { it.value.value.word }
        wordColumn.cellFactory = Callback { GenericEditableTreeTableCell(TextFieldEditorBuilder()) }
        wordColumn.onEditCommit = updateVocabulary(true)
        translationColumn.cellValueFactory = Callback { it.value.value.translation }
        translationColumn.cellFactory = Callback { GenericEditableTreeTableCell(TextFieldEditorBuilder()) }
        translationColumn.onEditCommit = updateVocabulary(false)
        initTable()
        setUpFocusForRemovePair()
        setUpSearchField()
    }

    fun returnToHome() {
        goToNextScene(root.scene, View.MAIN_PAGE)
    }

    fun removePair() {
        val unit = vocabularyTableView.selectionModel.selectedItemProperty().get().value
        vocabularyService.delete(unit.id)
        observableVocabularyUnit?.remove(unit)
        if (observableVocabularyUnit?.size == 0) {
            removeButton.isDisable = true
        }
    }

    private fun updateVocabulary(isWordUpdate: Boolean): EventHandler<TreeTableColumn.CellEditEvent<ObservableVocabularyUnit, String>> {
        return EventHandler {
            val item = it.treeTableView.getTreeItem(it.treeTablePosition.row).value
            val unit = Vocabulary(item.word.value, item.translation.value).apply { id = item.id }
            if (isWordUpdate) unit.word = it.newValue else unit.translation = it.newValue
            vocabularyService.update(unit)
            if (isWordUpdate) item.word.set(it.newValue) else item.translation.set(it.newValue)
        }
    }

    private fun initTable() {
        val vocabularyList = vocabularyService.findAll()
        val observableObservableVocabularyUnits = FXCollections.observableArrayList<ObservableVocabularyUnit>()
        vocabularyList.map { ObservableVocabularyUnit(it.id!!, it.word, it.translation) }
            .forEach { observableObservableVocabularyUnits.add(it) }
        observableVocabularyUnit = observableObservableVocabularyUnits
        vocabularyTableView.root = RecursiveTreeItem(observableVocabularyUnit) { it.children }
        vocabularyTableView.isShowRoot = false
        vocabularyTableView.isEditable = true
    }

    private fun setUpFocusForRemovePair() {
        vocabularyTableView.addEventHandler(MouseEvent.MOUSE_CLICKED) {
            val target = it.target
            if (target is GenericEditableTreeTableCell<*, *> && !target.isEmpty) {
                removeButton.isDisable = false
            }
        }
    }

    private fun setUpSearchField() {
        searchField.textProperty().addListener { _, _, newVal ->
            vocabularyTableView.predicate = Predicate {
                return@Predicate it.value.word.get().contains(newVal) || it.value.translation.get().contains(newVal)
            }
        }
    }

    internal class ObservableVocabularyUnit(val id: Long, word: String, translation: String) :
        RecursiveTreeObject<ObservableVocabularyUnit>() {
        val translation: SimpleStringProperty
        val word: SimpleStringProperty

        init {
            this.word = SimpleStringProperty(word)
            this.translation = SimpleStringProperty(translation)
        }
    }
}