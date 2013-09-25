
package np.com.ngopal.autocomplete;


import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;

/**
 * This class is main Control class which extends from Control <br>
 * and also implements basic functions of  the AutoFillTextBoxFactory<br>
 * 
 * You can easily utilize the AutoFillTextBox in your application<br>
 * 
 * e.g <br>
 * <pre>
 *      //..codes
 *      AutoFillTextBox autobox = new AutoFillTextBox("helo","prefix","dog","city");
 *      autobox.setLimit(7);
 *      //..add autobox to your scene then the output must be like this:
 * </pre>
 * Output:
 * <br> 
 *      <img src="http://blog.ngopal.com.np/wp-content/uploads/2011/07/screen.png" align="center"/>
 * <br>
 * 
 *    
 *
 * @author Narayan G. Maharjan
 * @see <a href="http://www.blog.ngopal.com.np"> Blog </a>
 * 
 */
public class AutoFillTextBox<T> extends Control implements AutoFillTextBoxFactory<T>{
    
    //==========
    //ATTRIBUTES
    //==========
    private TextField textbox;
    private ListView<T> listview;
    private Node downArrow;
    private ObjectProperty<TextField> textProperty;
    private ObservableList<T> data = FXCollections.observableArrayList();;   
    private boolean filterMode;
    private int limit;
    
    
    
    
    /**
     * Constructor
     */
  /*  public AutoFillTextBox() {
        init();
    }
    */
    public AutoFillTextBox(){
        ObservableList<T> list= FXCollections.observableArrayList();
        init();
        this.data = list;
        
    }
    public AutoFillTextBox(ObservableList<T> data) {        
        //this();
        init();
        this.data = data;
    }
    
    public ObjectProperty<TextField> textProperty(){
        return textProperty;
    }
    
    /*=================================
     * Initialize the AutoFillTextBox *
     *================================*/
    private void init() {
        getStyleClass().setAll("autocomplete-text");
        textbox = new TextField(); 
        textProperty = new SimpleObjectProperty();
        textProperty.set(textbox);
        downArrow = new StackPane();
        
        listview = new ListView();    
        limit=5;
        filterMode=false;
        setFocusTraversable(true);
        super.focusedProperty().addListener(new ChangeListener<Boolean>(){
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0,
					Boolean arg1, Boolean arg2) {
				if(arg2)
					textbox.requestFocus();
			}
        	
        });
        
        listview.focusedProperty().addListener(new ChangeListener<Boolean>(){
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0,
					Boolean arg1, Boolean arg2) {
				if(arg2){
					requestFocus();					
				}
			}
        	
        });
       /* textbox.focusedProperty().addListener(new ChangeListener<Boolean>(){
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0,
					Boolean arg1, Boolean arg2) {
				requestFocus();				
			}
        	
        });*/
        
        /*focusedProperty().addListener(new ChangeListener<Boolean>(){
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0,
					Boolean arg1, Boolean arg2) {
				if(textbox !=null){
					if(arg2)textbox.requestFocus();
					
				}
				
			}
        	
        });*/
        
    }

    
    
    public T getItem() {
        return listview.getSelectionModel().getSelectedItem();
    }
    
    public String getText() {        
        return textbox.getText();        
    }  
  
    public void addData(T data){
        this.data.add(data); 
        
    }
    
    
    
    /*--------------------
     * OVERRIDEN METHODS * 
     --------------------*/
    
    
    @Override
    public void setData(ObservableList<T> data){
        this.data = data;
    }    
    
    @Override
    public ObservableList<T> getData(){
        return data;
    }
    
    @Override
    public ListView<T> getListview() {
        return listview;
    }

    @Override
    public TextField getTextbox() {
        return textbox;
    }   
    
    public Node getDrop(){
    	return downArrow;
    }

    @Override
    public void setListLimit(int limit) {
        
        this.limit=limit;
       
    }

    @Override
    public int getListLimit() {
        return limit;
    }

    @Override
    public void setFilterMode(boolean filter) {
        filterMode = filter;        
    }

    @Override
    public boolean getFilterMode() {
        return filterMode;
    }
    
    
    
   
    
}
