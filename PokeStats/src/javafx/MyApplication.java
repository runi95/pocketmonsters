package javafx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.view.MainWindow;

public class MyApplication extends Application {

		private static int width, height;
		private Scene scene;
		private static StackPane root = new StackPane();
		
		public static StackPane getRoot() {
			return root;
		}
		
		public int getWidth() {
			return width;
		}
		
		public int getHeight() {
			return height;
		}
		
		@Override
		public void start(Stage primaryStage) throws Exception {
			
			startValues(primaryStage);
			setStartScene(primaryStage);
		}
		
		private void startValues(Stage stage) {
			width = 1280;
			height = 768;
			
			stage.setTitle("PokemonStats");
		}
		
		private void setStartScene(Stage stage) {
			root.getChildren().add(new MainWindow());
			
			scene = new Scene(root, width, height);
			stage.setScene(scene);
			stage.show();
		}
	}
