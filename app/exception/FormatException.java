/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exception;

/**
 *
 * @author daviddale
 */
public class FormatException extends RuntimeException {
    
    public FormatException(){
        super();
    }
    
    public FormatException(String msg){
        super( msg );
    }
    
    public FormatException( String msg, Exception e ){
        super( msg, e );
    }
    
}
