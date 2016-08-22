package javafx.view;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.model.BasePokemon;
import javafx.model.Pokemon;
import javafx.model.SearchTree;
import javafx.model.Type;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.util.Duration;
import javafx.util.Pair;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PullMenu extends TitledPane implements Initializable {

	private static final ObservableList<Pokemon> pokemonList = FXCollections.observableArrayList();
	private final SearchTree<BasePokemon> searchTree = new SearchTree<>();
	private final HashMap<String, BasePokemon> hashMap = new HashMap<>();

	private final String saveFileLocation = System.getProperty("user.home") + System.getProperty("file.separator")
			+ "poke-stats" + System.getProperty("file.separator");

	@FXML
	private TextField idField, nameField, hpField, attackField, defenseField, spaField, spdField, speedField;

	public PullMenu() {
		setExpanded(true);

		expandedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (newValue) {
					new Timeline(new KeyFrame(Duration.seconds(0), new KeyValue(maxWidthProperty(), getWidth())),
							new KeyFrame(Duration.seconds(1), new KeyValue(maxWidthProperty(), 200.0))).play();
				} else {
					new Timeline(new KeyFrame(Duration.seconds(0), new KeyValue(maxWidthProperty(), getWidth())),
							new KeyFrame(Duration.seconds(1), new KeyValue(maxWidthProperty(), 0.0))).play();
				}
			}
		});

	}

	public ObservableList<Pokemon> getPokemonList() {
		return pokemonList;
	}

	public void addPokemon() {
		if (allFieldsHaveText())
			if (tryToAddPokemon(getIdF(), getName(), getHp(), getAttack(), getDefense(), getSpa(), getSpd(), getSpeed(),
					true)) {
				hpField.clear();
				attackField.clear();
				defenseField.clear();
				spaField.clear();
				spdField.clear();
				speedField.clear();
			}
	}

	private boolean tryToAddPokemon(String id, String name, String hp, String attack, String defense, String spa,
			String spd, String speed, boolean save) {
		Integer i_hp, i_attack, i_defense, i_spa, i_spd, i_speed;

		i_hp = integerParser(hp);
		i_attack = integerParser(attack);
		i_defense = integerParser(defense);
		i_spa = integerParser(spa);
		i_spd = integerParser(spd);
		i_speed = integerParser(speed);

		if (i_hp != null && i_attack != null && i_defense != null && i_spa != null && i_spd != null
				&& i_speed != null) {
			addPokemon(id, name, i_hp, i_attack, i_defense, i_spa, i_spd, i_speed, save);
			return true;
		} else
			return false;
	}

	private Integer integerParser(String text) {
		Integer retInteger = null;

		try {
			retInteger = Integer.parseInt(text);
		} catch (NullPointerException | NumberFormatException e) {
			e.printStackTrace();
		}
		return retInteger;
	}

	private void addPokemon(String id, String name, Integer hp, Integer attack, Integer defense, Integer spa,
			Integer spd, Integer speed, boolean save) {
		Pokemon pokemon = new Pokemon(id, name, hp, attack, defense, spa, spd, speed);

		if (save)
			savePokemon(pokemon);

		pokemonList.add(pokemon);
	}

	private void savePokemon(Pokemon pokemon) {
		File f = new File(saveFileLocation + "pokemons.save");
		f.getParentFile().mkdirs();

		try (BufferedWriter w = new BufferedWriter(new FileWriter(f))) {
			w.write("# ID:\tName:\tHP:\tAttack:\tDefense:\tSP. Atk:\tSP. Def:\tSpeed:\n!" + getIdF() + "\n\n");

			for (Pokemon p : pokemonList) {
				w.write(p.getId() + ":" + p.getName() + ":" + p.getHp().getText() + ":" + p.getAttack().getText() + ":"
						+ p.getDefense().getText() + ":" + p.getSpa().getText() + ":" + p.getSpd().getText() + ":"
						+ p.getSpeed().getText() + "\n");
			}
			if (pokemon != null)
				w.write(pokemon.getId() + ":" + pokemon.getName() + ":" + pokemon.getHp().getText() + ":"
						+ pokemon.getAttack().getText() + ":" + pokemon.getDefense().getText() + ":"
						+ pokemon.getSpa().getText() + ":" + pokemon.getSpd().getText() + ":"
						+ pokemon.getSpeed().getText());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void loadPokemonList() {
		System.out.println("Loading list...");
		File f = new File(saveFileLocation + "PokemonList.txt");

		if (f.exists()) {
			System.out.println("Exist!");
			try (BufferedReader r = new BufferedReader(new FileReader(f))) {
				String line = null;
				String name = null, hp = null, att = null, def = null, spa = null, spd = null, speed = null;
				String[] evo = null;
				Type[] types = null;
				while ((line = r.readLine()) != null) {
					if (line.length() > 0 && line.charAt(0) != '#') {
						if (line.charAt(0) == '!') {
							name = line.substring(1);
						} else if(line.charAt(0) == '@') {
							evo = line.substring(1).split("/");
						}else if (line.charAt(0) == '*') {
							String[] split = line.substring(1).split("/");
							types = new Type[split.length];
							for(int i = 0; i < split.length; i++)
								types[i] = Type.getType(split[i]);
						}else if (line.length() > 0) {
							if(line.contains("HP:"))
								hp = line.substring(line.indexOf("HP:") + "HP:".length());
							else if(line.contains("Attack:"))
								att = line.substring(line.indexOf("Attack:") + "Attack:".length());
							else if(line.contains("Defense:"))
								def = line.substring(line.indexOf("Defense:") + "Defense:".length());
							else if(line.contains("SPA:"))
								spa = line.substring(line.indexOf("SPA:") + "SPA:".length());
							else if(line.contains("SPD:"))
								spd = line.substring(line.indexOf("SPD:") + "SPD:".length());
							else if(line.contains("Speed:"))
								speed = line.substring(line.indexOf("Speed:") + "Speed:".length());
						}
						
						if(name != null && hp != null && att != null && def != null && spa != null && spd != null && speed != null) {
							BasePokemon base = new BasePokemon(name, evo, types, null, pairParser(hp), pairParser(att), pairParser(def), pairParser(spa), pairParser(spd), pairParser(speed));
							searchTree.put(line.substring(1).toLowerCase(), base);
							hashMap.put(name, base);
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			BasePokemon pkm = null;
			BasePokemon typePkm = null;
			Double lastVal = 0.0;
			Integer lastTypeVal = 0;
			for(BasePokemon p : hashMap.values()) {
				Double currentVal = null;
				Integer typeVal = 0;
				for(Type t : p.getType()) {
					typeVal -= t.superEffective().length;
					typeVal += t.notEffective().length;
					typeVal += t.immune().length*2;
				}
				if(typeVal > lastTypeVal && (!p.getName().equals("Saidine") && !p.getName().equals("Frynai") && !p.getName().equals("Daikatuna"))) {
					typePkm = p;
					lastTypeVal = typeVal;
				}
				if(p.getEvolutions()[p.getEvolutions().length-1].equals(p.getName())) {
					p.getHp().getValue();
					p.getAttack().getValue();
					p.getDefense().getValue();
					p.getSpa().getValue();
					p.getSpd().getValue();
					p.getSpeed().getValue();
					currentVal = (double) (p.getHp().getValue() + p.getAttack().getValue() + p.getDefense().getValue() + p.getSpa().getValue() + p.getSpd().getValue() + p.getSpeed().getValue());
				}else if(p.getEvolutions()[p.getEvolutions().length-1].contains("Mega") && p.getEvolutions()[p.getEvolutions().length-2].equals(p.getName())) {
					currentVal = (double) (p.getHp().getValue() + p.getAttack().getValue() + p.getDefense().getValue() + p.getSpa().getValue() + p.getSpd().getValue() + p.getSpeed().getValue());
				}
				if(currentVal != null && !p.getName().equals("Leviathao") && !p.getName().equals("Seikamater") && !p.getName().equals("Baitatao") && !p.getName().equals("Urayne") && !p.getName().equals("Yatagaryu") && !p.getName().equals("Garlikid") && !p.getName().equals("Laissure") && !p.getName().equals("Beliaddon") && !p.getName().equals("Fafninter") && !p.getName().equals("Feliger")) {
					if(currentVal > lastVal) {
					pkm = p;
					lastVal = currentVal;
				}}
			}
			System.out.println("Best base stats pokemon = " + pkm.getName() + " with rating: " + lastVal);
			System.out.println("Best type pokemon = " + typePkm.getName());
		}
	}

	private Pair<Integer, Integer> pairParser(String s) {
		String[] split = s.split("-");
		Pair<Integer, Integer> pair = null;
		
		if(split.length == 2)
			pair = new Pair<>(integerParser(split[0]), integerParser(split[1]));
		
		return pair;
	}
	
	public void loadPokemons() {
		File f = new File(saveFileLocation + "pokemons.save");

		if (f.exists()) {
			try (BufferedReader r = new BufferedReader(new FileReader(f))) {
				String line = null;
				while ((line = r.readLine()) != null) {
					if (line.length() > 0 && line.charAt(0) != '#')
						if (line.charAt(0) != '!') {
							String[] split = line.split(":");

							if (split.length == 8)
								tryToAddPokemon(split[0], split[1], split[2], split[3], split[4], split[5], split[6],
										split[7], false);
						} else {
							if (line.length() > 1)
								idField.setText(line.substring(1));
						}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private boolean fieldEmptyCheck(TextField field) {
		if (field.getText().isEmpty()) {
			field.setStyle("-fx-border-color: red;");
			return false;
		} else {
			field.setStyle("-fx-border-color: lightgrey;");
			return true;
		}
	}

	private boolean fieldCheck(TextField field) {
		boolean okay = fieldEmptyCheck(field);
		Integer i;

		if (okay) {
			i = integerParser(field.getText());
			if (i == null || i < 0 || i > 31) {
				okay = false;
				field.setStyle("-fx-border-color: red;");
			} else
				field.setStyle("-fx-border-color: lightgrey;");
		}

		return okay;
	}

	private boolean allFieldsHaveText() {
		return fieldEmptyCheck(idField) && checkNameField() && fieldCheck(hpField) && fieldCheck(attackField)
				&& fieldCheck(defenseField) && fieldCheck(spaField) && fieldCheck(spdField) && fieldCheck(speedField);
	}

	public String getIdF() {
		return idField.getText();
	}

	public String getName() {
		return nameField.getText();
	}

	public String getHp() {
		return hpField.getText();
	}

	public String getAttack() {
		return attackField.getText();
	}

	public String getDefense() {
		return defenseField.getText();
	}

	public String getSpa() {
		return spaField.getText();
	}

	public String getSpd() {
		return spdField.getText();
	}

	public String getSpeed() {
		return speedField.getText();
	}

	public void remove(Pokemon p) {
		pokemonList.remove(p);
		savePokemon(null);
	}

	public void buttonPullMenu(ImageView pullMenuIcon) {
		setExpanded(!isExpanded());
		if (isExpanded())
			pullMenuIcon.setImage(new Image("javafx/view/image/Arrow_Right.png"));
		else
			pullMenuIcon.setImage(new Image("javafx/view/image/Arrow_Left.png"));
	}

	private boolean checkNameField() {
		if (nameField.getText().isEmpty())
			return false;

		if (searchTree.autocorrect(nameField.getText().toLowerCase()) != null)
			nameField.setStyle("-fx-border-color: lightgrey;");
		else
			nameField.setStyle("-fx-border-color: red;");

		return true;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		loadPokemons();
		loadPokemonList();

		nameField.textProperty().addListener((observable, oldValue, newValue) -> {
			checkNameField();
		});
	}
}
