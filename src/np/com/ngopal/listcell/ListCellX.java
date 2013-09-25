/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package np.com.ngopal.listcell;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.ListCell;
import javafx.scene.effect.InnerShadow;
import javafx.scene.input.*;
import javafx.scene.paint.Color;

/**
 *
 * @author Narayan G. Maharajan
 */
public class ListCellX<T> extends ListCell<T> implements ChangeListener<Number>{
    //This attribute is for making my drag n drop totally private
    
    public static DataFormat dataFormat =  new DataFormat("mycell");
    //I keep track of which item is now currently being dragged
    private static IntegerProperty ind = new SimpleIntegerProperty(-1);
    
    //I can hold the dragged item Object for some time.
    private static Object temp = null;
    
    //I'm the item Object keeper. Come to me if you need any item
    private ObservableList items;
    
    //I'm the one who can make things draggable
    private boolean draggable =true; 
    
    //When any cell is dragged then I'm being named which index to be deleted
    private static int toBeDeleted = -1;
    
    //It's my class of fashion
    private String styleclass = "list-cellx";
   
    //I can make things happen
    private void setDraggable(boolean b){
        draggable = b;
    }
    
    //Call me if you want to verify for drag work
    public boolean isDraggable(){
        return draggable;
    }
    
    //Basic mind making I'm adding up to the cell so that
    //those cell can learn how to take place of another cell    
    public void init(ObservableList itms){
        items = itms;

        this.indexProperty().addListener(this);
        this.getStyleClass().add(styleclass); 
        //this.setStyle("-fx-background-color : red; ");
    }
    
    
    /**
     * I'm always ready for update 
     * @param item
     * @param empty 
     */
    @Override
    public void updateItem(T item,boolean empty){
        super.updateItem(item, empty); 
       
    }
   

    /**
     * If any changes happens for my 'items' I'm watching on you.
     * @param arg0
     * @param arg1
     * @param arg2 
     */
    @Override
    public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
        
        //Wait are you draggable ? and you are on our company?
        if(isDraggable() && getIndex() < items.size()){
                //If some kind of mice will click on your then do this
                setOnMouseClicked(new EventHandler<MouseEvent>(){

                    @Override
                    public void handle(MouseEvent arg0) {
                        getListView().scrollTo(getIndex()); 
                    }   
                    
                });
                //Some body is dragging and they are on me.
                setOnDragEntered(new EventHandler<DragEvent>(){
                    @Override
                    public void handle(DragEvent arg0) {
                        //System.out.println("Entered");
                        if(arg0.getTransferMode() == TransferMode.MOVE){
                            String cellS = (String)arg0.getDragboard().getContent(dataFormat);
                            
                            Object o = arg0.getDragboard().getContent(dataFormat);
                            if(toBeDeleted == getIndex()){
                                return;
                            }
                            if(toBeDeleted != -1){
                                items.remove(toBeDeleted);
                                toBeDeleted = -1;
                            }
                            if(o != null && temp != null ){
                                if(getIndex() < items.size())                                    
                                    items.add(getIndex(),(T)temp);
                                else if(getIndex() == items.size())
                                    items.add((T)temp);
                                
                            }
                            
                            ind.set(getIndex());
                        }
                    }

                });
                ind.addListener(new InvalidationListener(){

                    @Override
                    public void invalidated(Observable observable) {
                         if(getIndex() == ind.get()){
                            InnerShadow is = new InnerShadow();
                            is.setOffsetX(1.0);
                            is.setColor(Color.web("#666666"));
                            is.setOffsetY(1.0);                               
                            setEffect(is);
                        }else{
                            setEffect(null);
                        }   
                    }
                    
                });
                //Some body just went off dragging from my cell.
                setOnDragExited(new EventHandler<DragEvent>(){

                    @Override
                    public void handle(DragEvent arg0) {
                        //System.out.println("Exited");
                        if(arg0.getTransferMode() == TransferMode.MOVE){
                            Object o = arg0.getDragboard().getContent(dataFormat);
                            if(o != null){
                                setEffect(null);                          
                                if(getIndex()<items.size())
                                    toBeDeleted = getIndex();
                                  
                            }
                        }
                    }

                });
               
                //OMG! That mice pressed me. I need to take some action
                pressedProperty().addListener(new ChangeListener<Boolean>(){

                    @Override
                    public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
                        InnerShadow is = new InnerShadow();
                                is.setOffsetX(1.0);
                                is.setColor(Color.web("#666666"));
                                is.setOffsetY(1.0);                               
                        if(arg2){
                           // //System.out.println("Pressed " + getIndex() + " "+items.size());
                         
                            
                        }
                        else
                            setEffect(null);    
                    }

                });
            
                //Ok I'm off I'm Over stop dragging me man!
                setOnDragOver(new EventHandler<DragEvent>(){
                    @Override
                    public void handle(DragEvent event) {           
                        //System.out.println("Over");
                        event.acceptTransferModes(TransferMode.MOVE);                
                    }

                });
                
                //Hey hey hey You are dragging me! Wait I need to call somebody
                setOnDragDetected(new EventHandler<MouseEvent>(){
                        @Override
                        public void handle(MouseEvent event) {
                            //System.out.println("Detected");
                            Dragboard db = getListView().startDragAndDrop(TransferMode.MOVE);
                            temp = items.get(getIndex());
                            toBeDeleted = getIndex();
                            Object item = items.get(getIndex());
                            /* Put a string on a dragboard */
                            ClipboardContent content = new ClipboardContent();
                            if(item != null)
                                content.put(dataFormat,item.toString());             
                            else
                                content.put(dataFormat,"XData");             
                            db.setContent(content); 
                            event.consume();

                        }

                });
                
//                setOnDragDropped(new EventHandler<DragEvent>(){
//                    @Override
//                    public void handle(DragEvent arg0) {
//                        if(arg0.getTransferMode() == TransferMode.MOVE){
//                            Object o = arg0.getDragboard().getContent(dataFormat);
//                            if(o != null && temp != null){
//                                temp = null;
//                                ind.set(-1);
//                                toBeDeleted = -1;                                
//                            }
//                        }
//                    }
//
//                });
        }
    }
}
