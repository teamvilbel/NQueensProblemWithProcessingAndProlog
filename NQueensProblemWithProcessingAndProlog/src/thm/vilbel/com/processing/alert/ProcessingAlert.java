/**
 * 
 */
package thm.vilbel.com.processing.alert;

import processing.core.PApplet;
import thm.vilbel.com.processing.button.ProcessingGui;

/**
 * @author Noah Ruben
 *
 *
 * @created 23.12.2019
 */
public class ProcessingAlert extends ProcessingGui {

	private final int playTicks = 250;
	private int ticks;

	/** BLACK */
	public static int BLACK = 0x0;

	/** RED */
	public static int RED = 0xd63e64;

	/** Position of the alert */
	private int alertX, alertY;

	/** Diameter of the alert */
	private int alertSizeX, alertSizeY;

	/** Color of the alert */
	private int baseColor, outlineColor;

	/** Text of the alert */
	private final String alertText;

	/** Size of {@link ProcessingAlert#alertText} */
	private float textSize;

	/**
	 * @param pa
	 * @param alertX
	 * @param alertY
	 * @param alertSizeX
	 * @param alertSizeY
	 * @param alertText
	 * @param textSize
	 */
	public ProcessingAlert(PApplet pa, int alertX, int alertY, int alertSizeX, int alertSizeY, String alertText) {
		super(pa);
		this.alertX = alertX;
		this.alertY = alertY;
		this.alertSizeX = alertSizeX;
		this.alertSizeY = alertSizeY;
		this.alertText = alertText;
		this.textSize = 20;
		baseColor = RED;
		outlineColor = BLACK;
		ticks = playTicks;
	}

	@Override
	public void draw() {
		if (ticks < playTicks) {
			this.pa.fill(toProcessingColor(baseColor));
			this.pa.stroke(toProcessingColor(outlineColor));
			this.pa.rect(alertX, alertY, alertSizeX, alertSizeY, 7);

			this.pa.textSize(textSize);
			this.pa.fill(toProcessingColor(outlineColor));
			this.pa.text(this.alertText, alertX + 10, alertY + textSize + 10);
			ticks++;
		}
	}
	
	public void resetAlert() {
		this.ticks = 0;
	}

}
