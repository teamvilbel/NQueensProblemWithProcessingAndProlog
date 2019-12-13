package tem.vilbel.com.processing;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


import processing.core.PApplet;
import processing.core.PImage;
import tem.vilbel.com.processing.button.IProcessingButtonAction;
import tem.vilbel.com.processing.button.ProcessingButton;

/**
 * @author Noah Ruben
 *
 *
 * @created 09.12.2019
 */

public class SchachBrett extends PApplet {

	private final int X_OFF = 50;
	private final int Y_OFF = 50;
	private final int SQUARE_SIZE = 75;
	private final int SIZE = 10;

	private Set<int[]> queens;

	private PImage img;
	private ProcessingButton startButton;
	private ProcessingButton quitButton;

	private boolean mainMenu;
	private IProcessingButtonAction startSolutionAction = new IProcessingButtonAction() {

		@Override
		public void doAction() {
			SchachBrett.this.mainMenu = false;
		}
	};
	private IProcessingButtonAction quitAction = new IProcessingButtonAction() {

		@Override
		public void doAction() {
			System.out.println("Quit");
			SchachBrett.this.exit();
		}
	};

	public SchachBrett() {
		super();
		queens = new HashSet<int[]>();
		mainMenu = true;
	}

	public void settings() {
		size(1000, 1000);
//		fullScreen();
		img = loadImage("./resources/Chess_queen_icon.png", "png");
		startButton = new ProcessingButton(this, width / 2 - 100, 200, "Start");
		quitButton = new ProcessingButton(this, width / 2 - 100, 300, "Quit");
		startButton.onClick(startSolutionAction);
		quitButton.onClick(quitAction);
	}

	public void draw() {
		background(255);
		if (mainMenu) {
			drawMainMenu();
		} else {
			drawField();
			drawQueenTrail();

			// Draw queens
			for (int[] index : queens) {
				image(img, index[0] * SQUARE_SIZE + X_OFF, index[1] * SQUARE_SIZE + Y_OFF, SQUARE_SIZE, SQUARE_SIZE);
			}
		}
	}

	private void drawMainMenu() {
		startButton.draw();
		quitButton.draw();
	}

	private void drawQueenTrail() {
		for (int[] index : queens) {
			// Horizontal und vertikal
			for (int i = 0; i < SIZE; i++) {
				fill(64);
				int tempX = index[0] * SQUARE_SIZE + X_OFF;
				int tempY = index[1] * SQUARE_SIZE + Y_OFF;
				rect(i * SQUARE_SIZE + X_OFF, tempY, SQUARE_SIZE, SQUARE_SIZE);
				rect(tempX, i * SQUARE_SIZE + Y_OFF, SQUARE_SIZE, SQUARE_SIZE);
			}
			// Diagolnal
			fill(64);
			int temp_ix = index[0];
			int temp_iy = index[1];
			while (temp_ix != 0 && temp_iy != 0) {
				temp_ix--;
				temp_iy--;
			}

			while (temp_ix != SIZE && temp_iy != SIZE) {
				int tempX = temp_ix * SQUARE_SIZE + X_OFF;
				int tempY = temp_iy * SQUARE_SIZE + Y_OFF;
				rect(tempX, tempY, SQUARE_SIZE, SQUARE_SIZE);
				temp_ix++;
				temp_iy++;
			}

			temp_ix = index[0];
			temp_iy = index[1];
			while (temp_ix > SIZE && temp_iy != 0) {
				temp_ix++;
				temp_iy--;
			}

			while (temp_ix >= 0 && temp_iy != SIZE) {
				int tempX = temp_ix * SQUARE_SIZE + X_OFF;
				int tempY = temp_iy * SQUARE_SIZE + Y_OFF;
				rect(tempX, tempY, SQUARE_SIZE, SQUARE_SIZE);
				temp_ix--;
				temp_iy++;
			}

		}

	}

	private void drawField() {

		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				int tempX = i * SQUARE_SIZE + X_OFF;
				int tempY = j * SQUARE_SIZE + Y_OFF;
				if ((i + j) % 2 == 0) {
					fill(0);
					rect(tempX, tempY, SQUARE_SIZE, SQUARE_SIZE);
				} else {
					fill(255);
					rect(tempX, tempY, SQUARE_SIZE, SQUARE_SIZE);
				}
			}
		}
	}

	public void mouseClicked() {
		System.out.println(System.lineSeparator() + "MouseX: " + mouseX + ", " + " MouseY: " + mouseY);
		if (mainMenu) {
			startButton.mousePressed();
			quitButton.mousePressed();
		} else {
			int[] index = getChessTileFromMouse(mouseX, mouseY);
			System.out.println("Chess tile index " + Arrays.toString(index));
			if (index[0] >= 0 && index[1] >= 0) {
				queens.add(index);
			}
		}
	}

	private int[] getChessTileFromMouse(int x, int y) {
		if (x > SIZE * SQUARE_SIZE + X_OFF || y > SIZE * SQUARE_SIZE + Y_OFF || x < X_OFF || y < Y_OFF) {
			return new int[] { -1, -1 };
		} else {
			int tempX = (x - X_OFF);
			tempX = tempX - (tempX % SQUARE_SIZE);
			tempX = tempX / SQUARE_SIZE;

			int tempY = (y - Y_OFF);
			tempY = tempY - (tempY % SQUARE_SIZE);
			tempY = tempY / SQUARE_SIZE;
			return new int[] { tempX, tempY };
		}
	}

	public static void startApp() {
		String[] appletArgs = new String[] { SchachBrett.class.getName() };
		PApplet.main(appletArgs);
	}	
}
