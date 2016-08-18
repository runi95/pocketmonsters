package javafx.view;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.Resources;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.model.MyLabel;
import javafx.model.Pokemon;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public class MainWindow extends BorderPane implements Initializable {

	public MainWindow() {
		Resources.loadFXML(this);
	}

	@FXML
	private TableView<Pokemon> pokemonTable;
	@FXML
	private TextField searchField;
	@FXML
	private ImageView pullMenuIcon;
	@FXML
	private PullMenu pullMenu;
	
	private TableColumn<Pokemon, MyLabel> createNewTableColumn(String columnName, String variableName, int minWidth,
			int prefWidth, int maxWidth) {
		TableColumn<Pokemon, MyLabel> column = new TableColumn<>(columnName);
		column.setCellValueFactory(new PropertyValueFactory<Pokemon, MyLabel>(variableName));
		column.setEditable(false);
		
		if (minWidth != -1)
			column.setMinWidth(minWidth);
		if (prefWidth != -1)
			column.setPrefWidth(prefWidth);
		if (maxWidth != -1)
			column.setMaxWidth(maxWidth);

		return column;
	}

	public void buttonPullMenu() {
		pullMenu.buttonPullMenu(pullMenuIcon);
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		pokemonTable.getColumns().add(createNewTableColumn("Name", "name", -1, 150, -1));
		pokemonTable.getColumns().add(createNewTableColumn("HP", "hp", -1, 50, -1));
		pokemonTable.getColumns().add(createNewTableColumn("Attack", "attack", -1, 50, -1));
		pokemonTable.getColumns().add(createNewTableColumn("Defense", "defense", -1, 50, -1));
		pokemonTable.getColumns().add(createNewTableColumn("SPA", "spa", -1, 50, -1));
		pokemonTable.getColumns().add(createNewTableColumn("SPD", "spd", -1, 50, -1));
		pokemonTable.getColumns().add(createNewTableColumn("Speed", "speed", -1, 50, -1));
		pokemonTable.getColumns().add(createNewTableColumn("Total IV", "iv", -1, 75, -1));
		pokemonTable.getColumns().add(createNewTableColumn("Rating", "rating", -1, 75, -1));
		
		FilteredList<Pokemon> filteredData = new FilteredList<>(pullMenu.getPokemonList(), p -> true);
		
		searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(pokemon -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (pokemon.getId().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches first name.
                } else if (pokemon.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                }
                return false; // Does not match.
            });
        });
		
		SortedList<Pokemon> sortedData = new SortedList<>(filteredData);
		sortedData.comparatorProperty().bind(pokemonTable.comparatorProperty());
		
		pokemonTable.setItems(sortedData);
		
//		pokemonTable.setItems(pullMenu.getPokemonList());
	}
}
