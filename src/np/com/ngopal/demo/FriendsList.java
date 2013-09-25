/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package np.com.ngopal.demo;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 *
 * @author Narayan G. Maharjan
 */
public class FriendsList extends Application {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
  
    /**
     * 
     * @param primaryStage 
     */
    @Override
    public void start(Stage primaryStage) {
  
        System.out.println(System.getProperty("javafx.version"));
        
        BorderPane pane = null;
        try {
            pane =(BorderPane) FXMLLoader.load(FriendsList.class.getResource("GUIFX.fxml"));
            pane.getStyleClass().add("main"); 
        } catch (IOException ex) {
            
            Logger.getLogger(FriendsList.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(-1); 
        }
        primaryStage.setScene(new Scene(pane, 700, 550));
        primaryStage.setTitle("Friends List"); 
        primaryStage.getScene().getStylesheets().add(FriendsList.class.getResource("/np/com/ngopal/css/gui.css").toExternalForm());
        primaryStage.show();
    }
}
