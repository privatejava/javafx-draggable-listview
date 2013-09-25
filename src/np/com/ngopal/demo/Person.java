/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package np.com.ngopal.demo;



/**
 *
 * @author Narayan G. Maharjan
 */
public class Person {
    
    private String name;
    private String imageUrl;
    
    public Person(String name,String imageUrl) {
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

   
    
    
}
