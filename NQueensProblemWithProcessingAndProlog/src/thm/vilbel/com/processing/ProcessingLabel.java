/**
 * 
 */
package thm.vilbel.com.processing;

import processing.core.PApplet;

/**
 * @author Noah Ruben
 *
 *
 * @created 15.12.2019
 */
public class ProcessingLabel extends ProcessingGui {

	/** BLACK */
	public static int BLACK = 0x0;

	/** Position of the label */
	private int labelX, labelY;

	/** Color of the button */
	private int textColor;

	/** Text of the button */
	private final String labelText;

	/** Size of {@link ProcessingLabel#labelText} */
	private float textSize;

	/**
	 * @param pa {@link ProcessingLabel#pa}
	 * @param labelX {@link ProcessingLabel#labelX}
	 * @param labelY {@link ProcessingLabel#labelY}
	 * @param textColor {@link ProcessingLabel#textColor}
	 * @param labelText {@link ProcessingLabel#labelText}
	 * @param textSize {@link ProcessingLabel#textSize}
	 */
	public ProcessingLabel(PApplet pa, int labelX, int labelY, int textColor, String labelText, float textSize) {
		super(pa);
		this.labelX = labelX;
		this.labelY = labelY;
		this.textColor = textColor;
		this.labelText = labelText;
		this.textSize = textSize;
	}

	/**
	 * @param pa {@link ProcessingLabel#pa}
	 * @param labelX {@link ProcessingLabel#labelX}
	 * @param labelY {@link ProcessingLabel#labelY}
	 * @param labelText {@link ProcessingLabel#labelText}
	 */
	public ProcessingLabel(PApplet pa, int labelX, int labelY, String labelText) {
		this(pa, labelX, labelY, BLACK, labelText, 20f);
	}

	/**
	 * Draws the label on the Sketch 
	 */
	public void draw() {
		this.pa.stroke(toProcessingColor(textColor));
		this.pa.textSize(textSize);
		this.pa.text(labelText, labelX, labelY);
	}

}
