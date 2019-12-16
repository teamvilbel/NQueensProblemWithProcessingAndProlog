package tem.vilbel.com.processing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jpl7.fli.qid_t;

import processing.core.PApplet;
import processing.core.PImage;
import tem.vilbel.com.processing.button.IProcessingButtonAction;
import tem.vilbel.com.processing.button.ProcessingButton;
import tem.vilbel.com.processing.button.ProcessingLabel;
import tem.vilbel.com.prolog.SolutionsWithProlog;

/**
 * @author Noah Ruben
 *
 *
 * @created 09.12.2019
 */

public class ProcessingApplication extends PApplet {

	SolutionsWithProlog solver;

	private final int X_OFF = 125;
	private final int Y_OFF = 50;
	private final int SQUARE_SIZE = 75;
	private int SIZE = 8;

	private List<List<Integer>> solutions;
	private int solutionIndex = 0;
	private Set<List<Integer>> queens;

	private PImage img;
	private ProcessingButton startButton;
	private ProcessingButton quitButton;
	private ProcessingButton nextButton;
	private ProcessingButton prevButton;
	private ProcessingButton solveButton;
	private ProcessingButton backButton;

	private ProcessingLabel chooseLabel;
	private ProcessingButton chooseSize4Button;
	private ProcessingButton chooseSize8Button;
	private ProcessingButton chooseSize10Button;
	private ProcessingButton chooseSize16Button;
	private ProcessingButton chooseSizeUserButton;

	private boolean mainMenu;
	private boolean mainMenuChooseSize;

	private IProcessingButtonAction showSolutions4Action = new IProcessingButtonAction() {
		@Override
		public void doAction() {
			SIZE = 4;
			ProcessingApplication.this.goToBoard();
		}
	};
	private IProcessingButtonAction showSolutions8Action = new IProcessingButtonAction() {
		@Override
		public void doAction() {
			SIZE = 8;
			ProcessingApplication.this.goToBoard();
		}
	};
	private IProcessingButtonAction showSolutions10Action = new IProcessingButtonAction() {
		@Override
		public void doAction() {
			SIZE = 10;
			ProcessingApplication.this.goToBoard();
		}
	};
	private IProcessingButtonAction showSolutions16Action = new IProcessingButtonAction() {
		@Override
		public void doAction() {
			SIZE = 16;
			ProcessingApplication.this.goToBoard();
		}
	};
	private IProcessingButtonAction showSolutionsUserAction = new IProcessingButtonAction() {
		@Override
		public void doAction() {
			ProcessingApplication.this.SIZE = 10;
			solveButton = new ProcessingButton(ProcessingApplication.this, ProcessingApplication.this.width / 2 - 45, ProcessingApplication.this.SQUARE_SIZE * ProcessingApplication.this.SIZE + 2 *Y_OFF, "SOLVE");
//			solver = new SolutionsWithProlog(SIZE, queens);
//			solutions = solver.solve();
			mainMenu = false;
			mainMenuChooseSize = false;
			allowUserInput = true;
		}
	};

	private IProcessingButtonAction startAction = new IProcessingButtonAction() {
		@Override
		public void doAction() {
			ProcessingApplication.this.mainMenuChooseSize = true;
		}
	};

	private IProcessingButtonAction quitAction = new IProcessingButtonAction() {

		@Override
		public void doAction() {
			System.out.println("Quit");
			ProcessingApplication.this.exit();
		}
	};

	private IProcessingButtonAction backAction = new IProcessingButtonAction() {
		
		@Override
		public void doAction() {
			mainMenu = true;
		}
	};
	private IProcessingButtonAction nextAction = new IProcessingButtonAction() {

		@Override
		public void doAction() {
			System.out.println("Next");
			solutionIndex++;

			if (solutionIndex < 0) {
				solutionIndex = 0;
			} else if (solutionIndex > solutions.size() - 1) {
				solutionIndex = solutions.size() - 1;
			}
			queens = getProcessingIndexFromPrologIndex(solutions.get(solutionIndex));

		}
	};
	private IProcessingButtonAction prevAction = new IProcessingButtonAction() {

		@Override
		public void doAction() {
			System.out.println("Prev");
			solutionIndex--;
			if (solutionIndex < 0) {
				solutionIndex = 0;
			} else if (solutionIndex > solutions.size() - 1) {
				solutionIndex = solutions.size() - 1;
			}
			queens = getProcessingIndexFromPrologIndex(solutions.get(solutionIndex));
		}
	};

	private boolean allowUserInput = false;

	public ProcessingApplication() {
		queens = new HashSet<List<Integer>>();
		mainMenu = true;
		mainMenuChooseSize = false;
		solver = new SolutionsWithProlog(SIZE, queens);
	}

	public void settings() {
		size(1000, 1000);
//		fullScreen();
		img = loadImage("./resources/Chess_queen_icon.png", "png");

		startButton = new ProcessingButton(this, width / 2 - 100, 200, "Start");
		quitButton = new ProcessingButton(this, width / 2 - 100, 300, "Quit");
		nextButton = new ProcessingButton(this, width - 100, 50, "Next");
		prevButton = new ProcessingButton(this, 20, 50, "Prev");
		backButton = new ProcessingButton(this, width / 2 - 45, SQUARE_SIZE * SIZE + 300, "Back");
		
		chooseLabel = new ProcessingLabel(this, width / 2 - 100, 250, "Choose Size:");
		chooseSize4Button = new ProcessingButton(this, width / 2 - 300, 300, "4x4");
		chooseSize8Button = new ProcessingButton(this, width / 2 - 200, 300, "8x8");   
		chooseSize10Button = new ProcessingButton(this, width / 2 - 100, 300, "10x10");
		chooseSize16Button = new ProcessingButton(this, width / 2, 300, "16x16");
		chooseSizeUserButton = new ProcessingButton(this, width / 2 + 100, 300,150, 45, "Enter Size");

		startButton.onClick(startAction);
		quitButton.onClick(quitAction);
		nextButton.onClick(nextAction);
		prevButton.onClick(prevAction);
		backButton.onClick(backAction);
		
		
		chooseSize4Button.onClick(showSolutions4Action); 
		chooseSize8Button.onClick(showSolutions8Action); 
		chooseSize10Button.onClick(showSolutions10Action);
		chooseSize16Button.onClick(showSolutions16Action);
		chooseSizeUserButton.onClick(showSolutionsUserAction);
	}

	public void draw() {
		background(255);
		if (mainMenu) {
			drawMainMenu();
		} else {
			drawField();
			drawQueenTrail();
			prevButton.draw();
			nextButton.draw();
			backButton.draw();
			
			if(allowUserInput) {
				solveButton.draw();
			}

			// Draw queens
			for (List<Integer> index : queens) {
				image(img, index.get(0) * SQUARE_SIZE + X_OFF, index.get(1) * SQUARE_SIZE + Y_OFF, SQUARE_SIZE,
						SQUARE_SIZE);
			}
		}
	}

	private void drawMainMenu() {
		if (mainMenuChooseSize) {
			chooseLabel.draw();
			chooseSize4Button.draw();
			chooseSize8Button.draw();
			chooseSize10Button.draw();
			chooseSize16Button.draw();
			chooseSizeUserButton.draw();
			quitButton.setButtonY(400);
		} else {
			startButton.draw();
		}
		quitButton.draw();
	}

	private void drawQueenTrail() {
		for (List<Integer> index : queens) {
			// Horizontal und vertikal
			for (int i = 0; i < SIZE; i++) {
				fill(64);
				int tempX = index.get(0) * SQUARE_SIZE + X_OFF;
				int tempY = index.get(1) * SQUARE_SIZE + Y_OFF;
				rect(i * SQUARE_SIZE + X_OFF, tempY, SQUARE_SIZE, SQUARE_SIZE);
				rect(tempX, i * SQUARE_SIZE + Y_OFF, SQUARE_SIZE, SQUARE_SIZE);
			}
			// Diagolnal
			fill(64);
			int temp_ix = index.get(0);
			int temp_iy = index.get(1);
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

			temp_ix = index.get(0);
			temp_iy = index.get(1);
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
			if (mainMenuChooseSize) {
				chooseSize4Button.mousePressed();       
				chooseSize8Button.mousePressed();       
				chooseSize10Button.mousePressed();      
				chooseSize16Button.mousePressed();      
				chooseSizeUserButton.mousePressed(); 
			}
		} else {
			List<Integer> index = getChessTileFromMouse(mouseX, mouseY);
			System.out.println("Chess tile index " + Arrays.toString(index.toArray()));
			if (index.get(0) >= 0 && index.get(1) >= 0) {
				queens.add(index);
			}
			nextButton.mousePressed();
			prevButton.mousePressed();
			backButton.mousePressed();
			if(allowUserInput) {
				solveButton.mousePressed();
			}
		}
	}

	private List<Integer> getChessTileFromMouse(int x, int y) {
		if (x > SIZE * SQUARE_SIZE + X_OFF || y > SIZE * SQUARE_SIZE + Y_OFF || x < X_OFF || y < Y_OFF) {
			return Arrays.asList(-1, -1);
		} else {
			int tempX = (x - X_OFF);
			tempX = tempX - (tempX % SQUARE_SIZE);
			tempX = tempX / SQUARE_SIZE;

			int tempY = (y - Y_OFF);
			tempY = tempY - (tempY % SQUARE_SIZE);
			tempY = tempY / SQUARE_SIZE;
			return Arrays.asList(tempX, tempY);
		}
	}

	private Set<List<Integer>> getProcessingIndexFromPrologIndex(List<Integer> solution) {
		Set<List<Integer>> indices = new HashSet<List<Integer>>();
		for (Integer integer : solution) {
			ArrayList<Integer> tempIndex = new ArrayList<Integer>();
			tempIndex.add(solution.indexOf(integer));
			tempIndex.add(integer - 1);
			indices.add(tempIndex);
		}
		return indices;
	}

	/**
	 * 
	 */
	private void goToBoard() {
		solver = new SolutionsWithProlog(SIZE, queens);
		solutions = solver.solve();
		queens = getProcessingIndexFromPrologIndex(solutions.get(solutionIndex));
		mainMenu = false;
		mainMenuChooseSize = false;
	}

	public static void startApp() {
		String[] appletArgs = new String[] { ProcessingApplication.class.getName() };
		PApplet.main(appletArgs);
	}
}
