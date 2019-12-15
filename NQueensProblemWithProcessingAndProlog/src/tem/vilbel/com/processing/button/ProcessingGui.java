/**
 * 
 */
package tem.vilbel.com.processing.button;

import processing.core.PApplet;

/**
 * @author Noah Ruben
 *
 *
 * @created 15.12.2019
 */
public abstract class ProcessingGui {
	
	
	/** Reference to the processing sketch */
	PApplet pa;
	
	
	/**
	 * @param pa {@link ProcessingGui#pa}
	 */
	public ProcessingGui(PApplet pa) {
		this.pa = pa;
	}
	
	/**
	 * Transfers a HEX RGB value into the processing color value.
	 * 
	 * @param color a RGB-Color as int representation from the HEX-Value  
	 * @return the color as Processing color int
	 */
	protected int toProcessingColor(int color) {
		return this.pa.color(this.pa.red(color), this.pa.green(color), this.pa.blue(color));
	}

	
	public abstract void draw();

	/**
	 * @return the pa
	 */
	public PApplet getPa() {
		return pa;
	}
	

}
