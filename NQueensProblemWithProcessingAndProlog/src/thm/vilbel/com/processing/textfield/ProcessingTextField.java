/**
 * 
 */
package thm.vilbel.com.processing.textfield;

import processing.core.PApplet;
import processing.core.PConstants;
import thm.vilbel.com.processing.IProcessingAction;
import thm.vilbel.com.processing.ProcessingGui;
import thm.vilbel.com.processing.button.ProcessingButton;

/**
 * @author Noah Ruben
 *
 *
 * @created 23.12.2019
 */
public class ProcessingTextField extends ProcessingGui {

	private final int playTicks = 50;
	private int ticks;

	/** Focus of the */
	private boolean focus = false;

	// Basic colors for the textField 2 themes are available.
	/** Light highlight color for the textField */
	public static int HILI_COLOR_L = 0xa0e8af;

	/** Light base color for the textField */
	public static int BASE_COLOR_L = 0x3ab795;

	/** Dark highlight color for the textField */
	public static int HILI_COLOR_D = 0x035e7b;

	/** Dark base color for the textField */
	public static int BASE_COLOR_D = 0x002e2c;

	/** BLACK */
	public static int BLACK = 0x0;

	/** BLACK */
	public static int WHITE = 0xFFFFFF;

	/** Position of the textField */
	private int textFieldX, textFieldY;

	/** Diameter of the textField */
	private int textFieldSizeX, textFieldSizeY;

	/** Color of the textField */
	private int baseColor, outlineColor;

	/** Text of the textField */
	private String headerTextFieldText;

	/** User-Text of the textField */
	private String textFieldUserText;

	/** Size of {@link ProcessingTextField#headerTextFieldText} */
	private float textSize;

	/**
	 * The Action for this button
	 * <p>
	 * More infos {@link IProcessingAction}
	 */
	private IProcessingAction onEnterAction;

	/**
	 * @param pa
	 * @param textFieldX
	 * @param textFieldY
	 * @param textFieldSizeX
	 * @param textFieldSizeY
	 * @param baseColor
	 * @param outlineColor
	 * @param headerTextFieldText
	 * @param textFieldUserText
	 * @param textSize
	 */
	public ProcessingTextField(PApplet pa, int textFieldX, int textFieldY, int textFieldSizeX, int textFieldSizeY,
			int baseColor, int outlineColor, String headerTextFieldText, String textFieldUserText, float textSize) {
		super(pa);
		this.textFieldX = textFieldX;
		this.textFieldY = textFieldY;
		this.textFieldSizeX = textFieldSizeX;
		this.textFieldSizeY = textFieldSizeY;
		this.baseColor = baseColor;
		this.outlineColor = outlineColor;
		this.headerTextFieldText = headerTextFieldText;
		this.textFieldUserText = textFieldUserText;
		this.textSize = textSize;
	}

	/**
	 * @param pa
	 * @param textFieldX
	 * @param textFieldY
	 * @param textFieldSizeX
	 * @param textFieldSizeY
	 * @param headerTextFieldText
	 * @param textFieldUserText
	 * @param textSize
	 */
	public ProcessingTextField(PApplet pa, int textFieldX, int textFieldY, int textFieldSizeX, int textFieldSizeY,
			String headerTextFieldText, String textFieldUserText, float textSize) {
		this(pa, textFieldX, textFieldY, textFieldSizeX, textFieldSizeY, BASE_COLOR_L, BLACK, headerTextFieldText,
				textFieldUserText, textSize);
	}

	/**
	 * @param pa
	 * @param textFieldX
	 * @param textFieldY
	 * @param headerTextFieldText
	 */
	public ProcessingTextField(PApplet pa, int textFieldX, int textFieldY, String headerTextFieldText) {
		this(pa, textFieldX, textFieldY, 200, 100, headerTextFieldText, "HH st", 20);
	}

	@Override
	public void draw() {
		// rect
		this.pa.stroke(toProcessingColor(outlineColor));
		this.pa.fill(toProcessingColor(baseColor));
		this.pa.rect(textFieldX, textFieldY, textFieldSizeX, textFieldSizeY, 7);

		// HeaderText
		this.pa.textSize(textSize);
		this.pa.fill(toProcessingColor(outlineColor));
		this.pa.text(this.headerTextFieldText, textFieldX + 5, textFieldY + 10 + (textSize / 2));

		// TextFieldRect
		this.pa.stroke(toProcessingColor(outlineColor));
		this.pa.fill(toProcessingColor(WHITE));
		this.pa.rect(textFieldX + 5, textFieldY + (textFieldSizeY / 2), textFieldSizeX - 10, textSize + 10, 7);
		// Text
		this.pa.textSize(textSize);
		this.pa.fill(toProcessingColor(outlineColor));
		if (textFieldUserText.isEmpty() || textFieldUserText != null) {
			this.pa.text(this.textFieldUserText, textFieldX + 10, textFieldY + (textFieldSizeY / 2) + 2,
					textFieldSizeX - 10, textSize + 10);
		}

		// Cursor

		if (ticks <= playTicks && focus) {
			int xoff = (int) (pa.textWidth(getTextFieldUserText()));
//			int xoff = (int) (getTextFieldUserText().length() * 9);
			int x1 = textFieldX + 10 + xoff;
			int y1 = textFieldY + (textFieldSizeY / 2) + 5;

			int y2 = (int) (y1 + textSize);
			this.pa.line(x1, y1, x1, y2);
		} else if (ticks > 2 * playTicks && focus) {
			resettextField();
		}
		ticks++;

	}

	public void keyPressed(char key) {
//		49 == 1 - 57 ==9
		String temp = getTextFieldUserText();
		if (key == PConstants.BACKSPACE) {
			if (temp.length() > 0) {
				temp = temp.substring(0, temp.length() - 1);
			}
		} else if (key >= '1' && key <= '9') {
			temp = temp + key;
		}
		setTextFieldUserText(temp);
		if (key == PConstants.ENTER) {
			this.onEnterAction.doAction();
		}
	}

	public void resettextField() {
		this.ticks = 0;
	}

	/**
	 * Checks if the mouse is currenty over the rectangle.
	 * 
	 * @param x      X-Position of the Rectangle to check
	 * @param y      X-Position of the Rectangle to check
	 * @param width  width of the Rectangle to check
	 * @param height height of the Rectangle to check
	 * @return <b>true</b> if the mouse is currently over the Rectangle other wise
	 *         <b>false</b>
	 */
	boolean overRect(int x, int y, int width, int height) {
		if (this.pa.mouseX >= x && this.pa.mouseX <= x + width && this.pa.mouseY >= y && this.pa.mouseY <= y + height) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Uses the {@link ProcessingButton#overRect(int, int, int, int)} with
	 * {@link ProcessingButton#buttonX}, {@link ProcessingButton#buttonY},
	 * {@link ProcessingButton#buttonSizeX} and {@link ProcessingButton#buttonSizeY}
	 * 
	 * @return <b>true</b> if the mouse is currently over the button other wise
	 *         <b>false</b>
	 */
	public boolean overTextField() {
		return overRect(textFieldX, textFieldY, textFieldSizeX, textFieldSizeY);
	}

	/**
	 * @return the {@link #onClickAction}
	 */
	public IProcessingAction getOnEnterEvent() {
		return onEnterAction;
	}

	/**
	 * @param onClickAction the {@link #onClickAction} to set
	 */
	public void onEnter(IProcessingAction onEnterAction) {
		this.onEnterAction = onEnterAction;
	}

	/**
	 * @return the textFieldUserText
	 */
	public String getTextFieldUserText() {
		return textFieldUserText;
	}

	/**
	 * @param textFieldUserText the textFieldUserText to set
	 */
	public void setTextFieldUserText(String textFieldUserText) {
		this.textFieldUserText = textFieldUserText;
	}

	/**
	 * @return the focus
	 */
	public boolean isFocus() {
		return focus;
	}

	/**
	 * @param focus the focus to set
	 */
	public void setFocus(boolean focus) {
		this.focus = focus;
	}

}
