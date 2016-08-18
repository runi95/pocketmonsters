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

public class Test {
	public static void main(String[] args) {
		ArrayList<String> list = new ArrayList<>();
		
		URL url = null;
		HttpURLConnection urlConnection = null;
		
		try {
			url = new URL("http://pokemon-uranium.wikia.com/wiki/Tandor_Pokedex");
			urlConnection =(HttpURLConnection) url.openConnection();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try(BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()))) {
			String line;
			while((line = r.readLine()) != null) {
				list.add(line);
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		for(String s : list) {
			if(s.contains("</td><td style=\"text-align: center; height: 32px; width: 32px;\"> <span class=\"plainlinks\"><a href=\"/wiki/")) {
				String sub = s.substring("</td><td style=\"text-align: center; height: 32px; width: 32px;\"> <span class=\"plainlinks\"><a href=\"/wiki/".length());
				String name = sub.substring(0, sub.indexOf('"'));
				int index = 0;
				if(sub.contains("data-src=\"")) {
					index = sub.indexOf("data-src=\"") + "data-src=\"".length();
				}else{
					index = sub.indexOf("><img src=\"") + "><img src=\"".length();
				}
				String imageStart = sub.substring(index);
				String image = imageStart.substring(0, imageStart.indexOf('"'));
				
				System.out.println();
			}
		}
		
		try(BufferedWriter w = new BufferedWriter(new FileWriter(new File(System.getProperty("user.home") + System.getProperty("file.separator") + "test.txt")))) {
			for(String s : list)
				w.write(s + "\n");
		}catch(IOException e) {
			e.printStackTrace();
		}
		
	}
}
