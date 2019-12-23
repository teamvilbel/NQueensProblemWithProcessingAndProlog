package thm.vilbel.com.processing.button;

import processing.core.PApplet;

/**
 * @author Noah Ruben
 *
 *
 * @created 13.12.2019
 */
public class ProcessingButton extends ProcessingGui{
	// Basic colors for the buttons 2 themes are available.
	/** Light highlight color for the button */
	public static int HILI_COLOR_L = 0xa0e8af;

	/** Light base color for the button */
	public static int BASE_COLOR_L = 0x3ab795;
	
	/** Dark highlight color for the button */
	public static int HILI_COLOR_D = 0x035e7b;
	
	/** Dark base color for the button */
	public static int BASE_COLOR_D = 0x002e2c;
	
	/** BLACK */
	public static int BLACK = 0x0;

	/** Position of the button */
	private int buttonX, buttonY; 
	
	/** Diameter of the button*/
	private int buttonSizeX, buttonSizeY; 
	
	/** Color of the button*/
	private int baseColor, outlineColor, highlightColor;

	/** Text of the button*/
	private final String buttonText;
	
	/** Size of {@link ProcessingButton#buttonText}*/
	private float textSize;

	
	/** The Action for this button <p>
	 *  More infos {@link IProcessingButtonAction}*/
	private IProcessingButtonAction onClickAction;

	/**
	 * Default-Constructor for the ProcessingButton 
	 * 
	 * @param pa {@link ProcessingButton#pa}
	 * @param buttonX {@link ProcessingButton#buttonX}
	 * @param buttonY {@link ProcessingButton#buttonY}
	 * @param buttonSizeX {@link ProcessingButton#buttonSizeX}
	 * @param buttonSizeY {@link ProcessingButton#buttonSizeY}
	 * @param buttonText {@link ProcessingButton#buttonText}
	 * @param textSize {@link ProcessingButton#textSize}
	 * @param baseColor {@link ProcessingButton#baseColor}
	 * @param outlineColor {@link ProcessingButton#outlineColor}
	 * @param highlightColor {@link ProcessingButton#highlightColor}
	 * @param onClick {@link ProcessingButton#onClickAction}
	 */
	private ProcessingButton(PApplet pa, int buttonX, int buttonY, int buttonSizeX, int buttonSizeY, String buttonText,
			float textSize, int baseColor, int outlineColor, int highlightColor, IProcessingButtonAction onClick) {
		super(pa);
		this.buttonX = buttonX;
		this.buttonY = buttonY;
		this.buttonSizeX = buttonSizeX;
		this.buttonSizeY = buttonSizeY;
		this.baseColor = baseColor;
		this.outlineColor = outlineColor;
		this.highlightColor = highlightColor;
		this.buttonText = buttonText;
		this.onClickAction = onClick;
		this.textSize = textSize;
	}
	
	/**
	 * 
	 * @param pa {@link ProcessingButton#pa}
	 * @param buttonX {@link ProcessingButton#buttonX}
	 * @param buttonY {@link ProcessingButton#buttonY}
	 * @param buttonText {@link ProcessingButton#buttonText}
	 * @param baseColor {@link ProcessingButton#baseColor}
	 * @param outlineColor {@link ProcessingButton#outlineColor}
	 * @param highlightColor {@link ProcessingButton#highlightColor}
	 */
	public ProcessingButton(PApplet pa, int buttonX, int buttonY, String buttonText, int baseColor, int rectColor,
			int highlight) {
		this(pa, buttonX, buttonY, 90, 45, buttonText, 20, baseColor, rectColor, highlight, null);
	}

	/**
	 * 
	 * @param pa {@link ProcessingButton#pa}
	 * @param buttonX {@link ProcessingButton#buttonX}
	 * @param buttonY {@link ProcessingButton#buttonY}
	 * @param buttonSizeX {@link ProcessingButton#buttonSizeX}
	 * @param buttonSizeY {@link ProcessingButton#buttonSizeY}
	 * @param buttonText {@link ProcessingButton#buttonText}
	 */
	public ProcessingButton(PApplet pa, int buttonX, int buttonY, int buttonSizeX, int buttonSizeY, String buttonText) {
		this(pa, buttonX, buttonY, buttonSizeX, buttonSizeY, buttonText, 20, BASE_COLOR_L, BLACK, HILI_COLOR_L, null);
	}

	/**
	 * @param pa {@link ProcessingButton#pa}
	 * @param buttonX {@link ProcessingButton#buttonX}
	 * @param buttonY {@link ProcessingButton#buttonY}
	 * @param buttonText {@link ProcessingButton#buttonText}
	 */
	public ProcessingButton(PApplet pa, int buttonX, int buttonY, String buttonText) {
		this(pa, buttonX, buttonY, 90, 45, buttonText, 20, BASE_COLOR_L, BLACK, HILI_COLOR_L, null);
	}

	/**
	 *  Draws the Button on the Sketch 
	 */
	public void draw() {

		if (overRect(buttonX, buttonY, buttonSizeX, buttonSizeY)) {
			this.pa.fill(toProcessingColor(highlightColor));
		} else {
			this.pa.fill(toProcessingColor(baseColor));
		}
		this.pa.stroke(toProcessingColor(outlineColor));
		this.pa.rect(buttonX, buttonY, buttonSizeX, buttonSizeY, 7);

		this.pa.textSize(textSize);
		this.pa.fill(toProcessingColor(outlineColor));
		this.pa.text(this.buttonText, buttonX + 10, buttonY + (buttonSizeY / 2) + (textSize / 2));

	}

	
	/**
	 * This should be called in {@link PApplet#mousePressed()} to give the button access to the mouse-press-event.
	 */
	public void mousePressed() {
		if (onClickAction == null) {
			return;
		} else {
			if (overButton()) {
				onClickAction.doAction();
			}
		}
	}

	/**
	 * Checks if the mouse is currenty over the rectangle.
	 * 
	 * @param x X-Position of the Rectangle to check
	 * @param y X-Position of the Rectangle to check
	 * @param width width of the Rectangle to check
	 * @param height height of the Rectangle to check
	 * @return <b>true</b> if the mouse is currently over the Rectangle other wise <b>false</b>
	 */
	boolean overRect(int x, int y, int width, int height) {
		if (this.pa.mouseX >= x && this.pa.mouseX <= x + width && this.pa.mouseY >= y && this.pa.mouseY <= y + height) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Uses the {@link ProcessingButton#overRect(int, int, int, int)} with {@link ProcessingButton#buttonX}, {@link ProcessingButton#buttonY}, {@link ProcessingButton#buttonSizeX} and {@link ProcessingButton#buttonSizeY} 
	 * 
	 * @return <b>true</b> if the mouse is currently over the button other wise <b>false</b>
	 */
	boolean overButton() {
		return overRect(buttonX, buttonY, buttonSizeX, buttonSizeY);
	}

	/**
	 * @return the {@link #onClickAction}
	 */
	public IProcessingButtonAction getOnClickEvent() {
		return onClickAction;
	}

	/**
	 * @param onClickAction the {@link #onClickAction} to set
	 */
	public void onClick(IProcessingButtonAction onClickAction) {
		this.onClickAction = onClickAction;
	}

	/**
	 * @return the {@link #buttonX}
	 */
	public int getButtonX() {
		return buttonX;
	}

	/**
	 * @param buttonX the {@link #buttonX} to set
	 */
	public void setButtonX(int buttonX) {
		this.buttonX = buttonX;
	}

	/**
	 * @return the {@link #buttonY}
	 */
	public int getButtonY() {
		return buttonY;
	}

	/**
	 * @param buttonY the {@link #buttonY} to set
	 */
	public void setButtonY(int buttonY) {
		this.buttonY = buttonY;
	}

	/**
	 * @return the {@link #buttonSizeX}
	 */
	public int getButtonSizeX() {
		return buttonSizeX;
	}

	/**
	 * @param buttonSizeX the {@link #buttonSizeX} to set
	 */
	public void setButtonSizeX(int buttonSizeX) {
		this.buttonSizeX = buttonSizeX;
	}

	/**
	 * @return the {@link #buttonSizeY}
	 */
	public int getButtonSizeY() {
		return buttonSizeY;
	}

	/**
	 * @param buttonSizeY the {@link #buttonSizeY} to set
	 */
	public void setButtonSizeY(int buttonSizeY) {
		this.buttonSizeY = buttonSizeY;
	}

	/**
	 * @return the {@link #baseColor}
	 */
	public int getBaseColor() {
		return baseColor;
	}

	/**
	 * @param baseColor the {@link #baseColor} to set
	 */
	public void setBaseColor(int baseColor) {
		this.baseColor = baseColor;
	}

	/**
	 * @return the {@link #outlineColor}
	 */
	public int getOutlineColor() {
		return outlineColor;
	}

	/**
	 * @param outlineColor the {@link #outlineColor} to set
	 */
	public void setOutlineColor(int outlineColor) {
		this.outlineColor = outlineColor;
	}

	/**
	 * @return the {@link #highlightColor}
	 */
	public int getHighlightColor() {
		return highlightColor;
	}

	/**
	 * @param highlightColor the {@link #highlightColor} to set
	 */
	public void setHighlightColor(int highlightColor) {
		this.highlightColor = highlightColor;
	}

	/**
	 * @return the {@link #textSize}
	 */
	public float getTextSize() {
		return textSize;
	}

	/**
	 * @param textSize the {@link #textSize} to set
	 */
	public void setTextSize(float textSize) {
		this.textSize = textSize;
	}

	/**
	 * @return the {@link #buttonText}
	 */
	public String getButtonText() {
		return buttonText;
	}
	
}
