/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package np.com.ngopal.demo;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Callback;
import np.com.ngopal.autocomplete.AutoFillTextBox;
import np.com.ngopal.listcell.ListCellX;

/**
 *
 * @author Narayan G. Maharjan
 */
public class GUIFX implements Initializable {
    
    @FXML
    private AutoFillTextBox filterName,filterPerson;
    @FXML
    private Text heading,personTxt,nameTxt;
    @FXML
    private VBox namePane,personPane;
    @FXML
    private Rectangle rect;
    @FXML
    private ListView nameList;
    @FXML
    private ListView personList;
    
    //Adding some fashion styles for some text and rectangle guys
    public void setStyles(){
        personTxt.getStyleClass().add("sub-heading");
        nameTxt.getStyleClass().add("sub-heading");
        heading.getStyleClass().add("heading");
        rect.getStyleClass().add("rect");
    }
    
    
    /*This is the main initializer of the JavaFX Application */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //I got styles 
        setStyles();
                
        
        
        final ObservableList<Person> obj = FXCollections.observableArrayList();
        final String[] str = new String[]{"Linda","Michael","Mike","Olic","Narayan"};
        
        for(int i=0; i<str.length; i++){
            obj.add(new Person(str[i],
                    GUIFX.class.getResource("/np/com/ngopal/images/"+ (i+1) +".png").toExternalForm()));  
            
        }
   
        //Here I've just added some ability for my Autofill control
        filterName.setFilterMode(true); 
        filterName.setData(obj);
        filterName.getTextbox().setPromptText("Filter.."); 
        filterName.getListview().setVisible(false);
	nameList.itemsProperty().bind(filterName.getListview().itemsProperty());
        
        //Here The same behaviour i've added to my second autofill they are bros
        filterPerson.setFilterMode(true);
        filterPerson.setData(obj);
        filterPerson.getTextbox().setPromptText("Filter.."); 
        filterPerson.getListview().setVisible(false);
        personList.itemsProperty().bind(filterPerson.getListview().itemsProperty());
        personList.getStyleClass().add("mylist"); 
        
        nameList.setCellFactory(new Callback<ListView<Person>,ListCell<Person>> (){

            @Override
            public ListCellX<Person> call(ListView<Person> arg0) {
                
                ListCellX<Person> cell = new ListCellX<Person>(){
                    @Override
                    public void updateItem(Person item,boolean empty){
                        if(item != null){
                            super.updateItem(item, empty); 
                        
                            setText(item.getName());
                            
                        }
                    }        
                };
                cell.init(obj);
                return cell;
                
            }
        });
        
        personList.setCellFactory(new Callback<ListView<Person>,ListCell<Person>> (){

            @Override
            public ListCell<Person> call(ListView<Person> arg0) {
                //My cell is on my way to call
                ListCellX<Person> cell = new ListCellX<Person>(){
                    @Override
                    public void updateItem(Person item,boolean empty){
                        super.updateItem(item,empty);
                        if(item!=null){   
                            //-----------------------
                            //I'm making my GUI baby
                            //-----------------------
                            HBox box = new HBox();
                            
                            StackPane pane = new StackPane();                            
                            ImageView r = new ImageView();
                            Image im = new Image(item.getImageUrl());
                            r.setImage(im);
                            r.setFitHeight(50);
                            r.setPreserveRatio(true);
                            r.setFitWidth(50);
                            Rectangle rect = new Rectangle();
                            rect.setWidth(50);
                            rect.setHeight(50);
                            pane.getChildren().addAll(rect,r);
                            
                            
                            Text t = new Text(item.getName());                                    
                            box.getChildren().addAll(pane,t);
                            box.setSpacing(10);
                            box.setPadding( new Insets(5,5,5,5)); 
                            
                            
                            //finally every thing is just setup
                            setGraphic(box);
                        }
                    }
                };
                //this is my chemical solution
                //Need to call on every cell for making things work
                cell.init(obj);
                
                //Take my cell
                return cell;
            }

        });
      
        
        //This is just for layouting all the gui components in main application
        StackPane.setAlignment(personTxt, Pos.CENTER_LEFT);
        StackPane.setAlignment(nameTxt, Pos.CENTER_LEFT);
        StackPane.setAlignment(filterPerson, Pos.CENTER_RIGHT);
        StackPane.setAlignment(filterName, Pos.CENTER_RIGHT);
        
        //Somekind of flexible and adaptable behaviour
        HBox.setHgrow(namePane,Priority.ALWAYS);
        HBox.setHgrow(personPane,Priority.ALWAYS);
        
    }    
}
