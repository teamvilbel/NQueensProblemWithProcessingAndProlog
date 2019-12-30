package thm.vilbel.com.processing;

import thm.vilbel.com.processing.button.ProcessingButton;

/**
 * 
 * This Interface holds the action that will be executed if a {@link ProcessingButton} is pressed.   
 * 
 * @author Noah Ruben
 *
 * @created 13.12.2019
 */
public interface IProcessingAction {
	/**
	 * This Function is called by the {@link ProcessingButton} if it is pressed
	 */
	public void doAction(); 
}
