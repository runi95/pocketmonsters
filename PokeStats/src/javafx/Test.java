package javafx;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javafx.model.BasePokemon;
import javafx.model.Type;
import javafx.util.Pair;

public class Test {
	public static void main(String[] args) {
		ArrayList<BasePokemon> pkmList = getPokemon();
//		 ArrayList<BasePokemon> pkmList = new ArrayList<>();
//		 pkmList.add(getPokemon("Metalynx", null));

		try (BufferedWriter w = new BufferedWriter(new FileWriter(new File(
				System.getProperty("user.home") + System.getProperty("file.separator") + "PokemonList.txt")))) {
			for (BasePokemon p : pkmList) {
				String evo = null;
				for (String s : p.getEvolutions())
					if (evo == null)
						evo = "@" + s;
					else if (s != null)
						evo += "/" + s;
				w.write("!" + p.getName() + "\n" + evo + "\n*" + p.getTypesString() + "\nHP:" + p.getHp().getKey() + "-" + p.getHp().getValue()
						+ "\nAttack:" + p.getAttack().getKey() + "-" + p.getAttack().getValue() + "\nDefense:"
						+ p.getDefense().getKey() + "-" + p.getDefense().getValue() + "\nSPA:" + p.getSpa().getKey()
						+ "-" + p.getSpa().getValue() + "\nSPD:" + p.getSpd().getKey() + "-" + p.getSpd().getValue()
						+ "\nSpeed:" + p.getSpeed().getKey() + "-" + p.getSpeed().getValue() + "\n\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<BasePokemon> getPokemon() {
		ArrayList<BasePokemon> basePokemonList = new ArrayList<>();
		ArrayList<String> list = new ArrayList<>();

		URL url = null;
		HttpURLConnection urlConnection = null;

		try {
			url = new URL("http://pokemon-uranium.wikia.com/wiki/Tandor_Pokedex");
			urlConnection = (HttpURLConnection) url.openConnection();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try (BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()))) {
			String line;
			while ((line = r.readLine()) != null) {
				list.add(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (String s : list) {
			if (s.contains(
					"</td><td style=\"text-align: center; height: 32px; width: 32px;\"> <span class=\"plainlinks\"><a href=\"/wiki/")) {
				String sub = s.substring(
						"</td><td style=\"text-align: center; height: 32px; width: 32px;\"> <span class=\"plainlinks\"><a href=\"/wiki/"
								.length());
				String name = sub.substring(0, sub.indexOf('"'));
				int index = 0;
				if (sub.contains("data-src=\"")) {
					index = sub.indexOf("data-src=\"") + "data-src=\"".length();
				} else {
					index = sub.indexOf("><img src=\"") + "><img src=\"".length();
				}
				String imageStart = sub.substring(index);
				String image = imageStart.substring(0, imageStart.indexOf('"'));

				BasePokemon base = getPokemon(name, image);
				if (base != null)
					basePokemonList.add(base);
			}
		}

		try (BufferedWriter w = new BufferedWriter(new FileWriter(
				new File(System.getProperty("user.home") + System.getProperty("file.separator") + "test.txt")))) {
			for (String s : list)
				w.write(s + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}

		return basePokemonList;
	}

	public static BasePokemon getPokemon(String name, String image) {
		ArrayList<String> list = new ArrayList<>();
		BasePokemon pkm = null;

		URL url = null;
		HttpURLConnection urlConnection = null;

		try {
			url = new URL("http://pokemon-uranium.wikia.com/wiki/" + name);
			urlConnection = (HttpURLConnection) url.openConnection();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try (BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()))) {
			String line;
			while ((line = r.readLine()) != null) {
				list.add(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		try (BufferedWriter w = new BufferedWriter(new FileWriter(
				new File(System.getProperty("user.home") + System.getProperty("file.separator") + "test.txt")))) {
			ArrayList<String> minvalues = new ArrayList<>();
			ArrayList<String> maxvalues = new ArrayList<>();
			String[] evolutions = new String[10];
			int typescnt = 0;
			Type[] types = null;
			Type type = null;
			int evo = 0;

			for (int i = 0; i < list.size(); i++) {
				String s = list.get(i);
				if (s.contains("<th style=\"background: #")) {
					if (list.size() > i + 5) {
						if (list.get(i + 4).contains("<small>") && list.get(i + 5).contains("<small>")) {
							minvalues.add(getMinValue(list.get(i + 5)));
							maxvalues.add(getMaxValue(list.get(i + 5)));
						}
					}
				} else if (s.contains("evol") && s.startsWith("<td> <small>")) {
					if (list.size() > i + 3) {
						int index = list.get(i + 3).indexOf("<span style=\"color:#000;\">");
						evolutions[evo] = list.get(i + 3).substring(index + "<span style=\"color:#000;\">".length());
						evolutions[evo] = evolutions[evo].substring(0, evolutions[evo].indexOf('<'));
						evo++;
					}
				}else if (s.contains("<td style=\"width: 45px; font-size: 85%; line-height:12px; background: ") && s.contains("type")) {
					type = Type.getType(s.substring(s.indexOf("<b>") + "<b>".length(), s.indexOf("</b>")));
					if(type != null)
					if(types == null) {
						types = new Type[++typescnt];
						types[typescnt-1] = type;
					}else if(!type.equals("Unknown")){
						Type[] temp = new Type[++typescnt];
						for(int j = 0; j < types.length; j++)
							temp[j] = types[j];
						types = temp;
						types[typescnt-1] = type;
					}
				}
				w.write(s + "\n");
			}
			System.out.println("Adding Pokemon: " + name);
			if (((minvalues.size() == 6 && maxvalues.size() == 6)
					|| (minvalues.size() == 12 && maxvalues.size() == 12)) && types != null) {
				Pair<Integer, Integer> hp = new Pair<>(integerParser(minvalues.get(0)),
						integerParser(maxvalues.get(0)));
				Pair<Integer, Integer> attack = new Pair<>(integerParser(minvalues.get(1)),
						integerParser(maxvalues.get(1)));
				Pair<Integer, Integer> defense = new Pair<>(integerParser(minvalues.get(2)),
						integerParser(maxvalues.get(2)));
				Pair<Integer, Integer> spa = new Pair<>(integerParser(minvalues.get(3)),
						integerParser(maxvalues.get(3)));
				Pair<Integer, Integer> spd = new Pair<>(integerParser(minvalues.get(4)),
						integerParser(maxvalues.get(4)));
				Pair<Integer, Integer> speed = new Pair<>(integerParser(minvalues.get(5)),
						integerParser(maxvalues.get(5)));

				pkm = new BasePokemon(name, evolutions, types, image, hp, attack, defense, spa, spd, speed);
			} else
				System.out.println("Bad size!");

			// System.out.println("HP: " + minvalues.get(0) + " - " +
			// maxvalues.get(0));
			// System.out.println("Attack: " + minvalues.get(1) + " - " +
			// maxvalues.get(1));
			// System.out.println("Defense: " + minvalues.get(2) + " - " +
			// maxvalues.get(2));
			// System.out.println("SPA. : " + minvalues.get(3) + " - " +
			// maxvalues.get(3));
			// System.out.println("SPD: " + minvalues.get(4) + " - " +
			// maxvalues.get(4));
			// System.out.println("Speed: " + minvalues.get(5) + " - " +
			// maxvalues.get(5));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return pkm;
	}
	
	public static String getMinValue(String s) {
		String s2 = s.substring(s.indexOf("<small>") + "<small>".length());
		for (int i = 0; i < s2.length(); i++) {
			if (s2.charAt(i) == ' ') {
				return s2.substring(0, i);
			}
		}

		return null;
	}

	public static String getMaxValue(String s) {
		String s2 = s.substring(0, s.indexOf("</small>"));
		for (int i = s2.length() - 1; i > 0; i--) {
			if (s2.charAt(i) == ' ') {
				return s2.substring(i + 1, s2.length());
			}
		}

		return null;
	}

	private static Integer integerParser(String text) {
		Integer retInteger = null;

		try {
			retInteger = Integer.parseInt(text);
		} catch (NullPointerException | NumberFormatException e) {
			e.printStackTrace();
		}
		return retInteger;
	}
}
