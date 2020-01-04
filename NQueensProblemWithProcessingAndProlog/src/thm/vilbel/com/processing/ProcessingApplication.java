package thm.vilbel.com.processing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import processing.core.PApplet;
import processing.core.PImage;
import processing.event.MouseEvent;
import thm.vilbel.com.processing.alert.ProcessingAlert;
import thm.vilbel.com.processing.button.ProcessingButton;
import thm.vilbel.com.processing.textfield.ProcessingTextField;
import thm.vilbel.com.prolog.SolutionsWithProlog;

/**
 * @author Noah Ruben
 *
 *
 * @created 09.12.2019
 */

public class ProcessingApplication extends PApplet {

	SolutionsWithProlog solver;

	private final int windowHeight = 1000;
	private final int windowWidth = 1000;

	private final int firstRow = 100;
	private final int firstColl = 100;

	private final int X_OFF = 125;
	private final int Y_OFF = 50;
	private final int SQUARE_SIZE = 75;
	private int squareScale = 0;
	private int SIZE = 8;

	private List<List<Integer>> solutions;
	private int solutionIndex = 0;
	private Set<List<Integer>> queens;
	private Map<List<Integer>, List<QueenPosition>> queenLines;

	private PImage queenImg;

	// Buttons
	private ProcessingButton fullscreenButton;
	private ProcessingButton startButton;
	private ProcessingButton quitButton;
	private ProcessingButton nextButton;
	private ProcessingButton prevButton;
	private ProcessingButton solveButton;
	// button to trigger userInputTextField in UserInputScreen
	private ProcessingButton sizeButton;
	private ProcessingButton backButton;
	private ProcessingButton chooseSize4Button;
	private ProcessingButton chooseSize8Button;
	private ProcessingButton chooseSize10Button;
	private ProcessingButton chooseUserSizeButton;
	private ProcessingButton chooseUserInputButton;

	// Labels
	private ProcessingLabel chooseLabel;
	private ProcessingLabel solutionLabel;

	// Alerts
	private ProcessingAlert alertNoSolution;
	private ProcessingAlert alertSolutionCount;

	// Textfields
	private ProcessingTextField sizeInputTextField;
	// textField to set BoardSize in UserInputScreen
	private ProcessingTextField userInputTextField;

	// state booleans TODO real State machine
	private boolean mainMenu;
	private boolean mainMenuChooseSize;
	private boolean disableQueenDeletion;
	private boolean showTextField;
	private boolean showUserInputTextField;
	private boolean allowUserInput;

	private boolean fullscreen;

	// Actions
	private IProcessingAction showSolutions4Action = new IProcessingAction() {
		@Override
		public void doAction() {
			SIZE = 4;
			disableQueenDeletion = true;
			ProcessingApplication.this.goToBoard();
		}
	};
	private IProcessingAction showSolutions8Action = new IProcessingAction() {
		@Override
		public void doAction() {
			SIZE = 8;
			disableQueenDeletion = true;
			ProcessingApplication.this.goToBoard();
		}
	};
	private IProcessingAction showSolutions10Action = new IProcessingAction() {
		@Override
		public void doAction() {
			SIZE = 10;
			disableQueenDeletion = true;
			ProcessingApplication.this.goToBoard();
		}
	};
	private IProcessingAction enterUserSizeAction = new IProcessingAction() {

		@Override
		public void doAction() {
			ProcessingApplication.this.showTextField = true;
			ProcessingApplication.this.sizeInputTextField.setFocus(true);
		}
	};
	private IProcessingAction showSolutionsUserAction = new IProcessingAction() {
		@Override
		public void doAction() {
			allowUserInput = true;
			disableQueenDeletion = false;
			ProcessingApplication.this.SIZE = 8;
			solveButton = new ProcessingButton(ProcessingApplication.this, ProcessingApplication.this.width / 2 - 45,
					ProcessingApplication.this.SQUARE_SIZE * ProcessingApplication.this.SIZE + 2 * Y_OFF, "SOLVE");
			solveButton.onClick(solveAction);
			sizeButton = new ProcessingButton(ProcessingApplication.this, ProcessingApplication.this.width / 2 - 45,
					ProcessingApplication.this.SQUARE_SIZE * ProcessingApplication.this.SIZE + 4 * Y_OFF, "SIZE");
			sizeButton.onClick(newSizeAction);
			mainMenu = false;
			mainMenuChooseSize = false;
		}
	};
	private IProcessingAction solveAction = new IProcessingAction() {
		@Override
		public void doAction() {
			disableQueenDeletion = true;
			solver = new SolutionsWithProlog(SIZE, queens);
			solutions = solver.solve(queens);
			solutionIndex = 0;
			if (solutions.isEmpty()) {
				alertNoSolution.resetAlert();
				disableQueenDeletion = false;
			} else {
				alertSolutionCount = new ProcessingAlert(ProcessingApplication.this, width / 2 - 45,
						SQUARE_SIZE * SIZE + 200, 150, 50, (solutions.size()) + " Solutions!");
				alertSolutionCount.resetAlert();
				queens = getProcessingIndexFromPrologIndex(solutions.get(solutionIndex));
			}
		}
	};
	private IProcessingAction newSizeAction = new IProcessingAction() {
		@Override
		public void doAction() {
			ProcessingApplication.this.showUserInputTextField = true;
			ProcessingApplication.this.userInputTextField.setFocus(true);
		}
	};
	private IProcessingAction startAction = new IProcessingAction() {
		@Override
		public void doAction() {
			ProcessingApplication.this.mainMenuChooseSize = true;
		}
	};
	private IProcessingAction quitAction = new IProcessingAction() {

		@Override
		public void doAction() {
			System.out.println("Quit");
			ProcessingApplication.this.exit();
		}
	};
	private IProcessingAction backAction = new IProcessingAction() {

		@Override
		public void doAction() {
			mainMenu = true;
			mainMenuChooseSize = true;
			showTextField = false;
			disableQueenDeletion = true;
			allowUserInput = false;
			solutionIndex = 0;
			queens.clear();
			queenLines.clear();
		}
	};
	private IProcessingAction nextAction = new IProcessingAction() {

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
	private IProcessingAction prevAction = new IProcessingAction() {

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

	private IProcessingAction toggleFullscreenAction = new IProcessingAction() {

		@Override
		public void doAction() {
			ProcessingApplication.this.fullscreen = !ProcessingApplication.this.fullscreen;
			System.out.println(displayHeight + "," + displayWidth);
			System.out.println("Fullscreen:" + fullscreen);
			if (fullscreen) {
				ProcessingApplication.this.surface.setSize(displayWidth, displayHeight);
				ProcessingApplication.this.surface.setLocation(-10, 0); // TODO Warum diese Zahlen
			} else {
				ProcessingApplication.this.surface.setSize(windowWidth, windowHeight);
				ProcessingApplication.this.surface.setLocation((displayWidth / 2) - 10 - (windowWidth / 2), 0); // TODO
																												// Warum
																												// diese
																												// Zahlen
			}
		}
	};

	public ProcessingApplication() {
		queens = new HashSet<List<Integer>>();
		queenLines = new HashMap<List<Integer>, List<QueenPosition>>();
		solver = new SolutionsWithProlog(SIZE, queens);

		mainMenu = true;
		disableQueenDeletion = true; // TODO reverse logic
		mainMenuChooseSize = false;
		allowUserInput = false;
		fullscreen = false;
	}

	/**
	 * Settings f�r Processing bevor Processing initialisiert wurde.
	 */
	public void settings() {
		size(windowWidth, windowHeight);
		queenImg = loadImage("./resources/Chess_queen_icon.png", "png");
	}

	/**
	 * Settings f�r Processing nachdem Processing initialisiert wurde. Hier kann auf
	 * Processing zugegriffen werden.
	 */
	public void setup() {
		surface.setResizable(true);
		System.out.println("Set window resizable\n\rDisplay size:");
		System.out.println(displayHeight + "," + displayWidth);
		guiInit();
	}

	/**
	 * Erstellung der GUI Komponenten.
	 */
	private void guiInit() {
		fullscreenButton = new ProcessingButton(this, width - 50, 0, 25, 25, "F");

		startButton = new ProcessingButton(this, width / 2 - 100, 200, "Start");
		quitButton = new ProcessingButton(this, width / 2 - 100, 300, "Quit");
		nextButton = new ProcessingButton(this, width - 100, 50, "Next");
		prevButton = new ProcessingButton(this, 20, 50, "Prev");
		backButton = new ProcessingButton(this, width / 2 - 45, SQUARE_SIZE * SIZE + 300, "Back");

		chooseLabel = new ProcessingLabel(this, width / 2 - 100, 250, "Choose Size:");
		chooseSize4Button = new ProcessingButton(this, width / 2 - 300, 300, "4x4");
		chooseSize8Button = new ProcessingButton(this, width / 2 - 200, 300, "8x8");
		chooseSize10Button = new ProcessingButton(this, width / 2 - 100, 300, "10x10");
		chooseUserSizeButton = new ProcessingButton(this, width / 2, 300, 150, 45, "Enter Size");
		chooseUserInputButton = new ProcessingButton(this, width / 2 + 160, 300, 150, 45, "User Input");

		fullscreenButton.onClick(toggleFullscreenAction);
		startButton.onClick(startAction);
		quitButton.onClick(quitAction);
		nextButton.onClick(nextAction);
		prevButton.onClick(prevAction);
		backButton.onClick(backAction);

		chooseSize4Button.onClick(showSolutions4Action);
		chooseSize8Button.onClick(showSolutions8Action);
		chooseSize10Button.onClick(showSolutions10Action);
		chooseUserSizeButton.onClick(enterUserSizeAction);
		chooseUserInputButton.onClick(showSolutionsUserAction);

		alertNoSolution = new ProcessingAlert(this, width / 2 - 187, height / 2 - 50, 375, 100,
				"There are no sulotions available\r\nfor this queen configuration!\r\nChange the position of your queens.");
		alertSolutionCount = new ProcessingAlert(ProcessingApplication.this, width / 2 - 45, SQUARE_SIZE * SIZE + 200,
				100, 100, " Solutions!");

		sizeInputTextField = new ProcessingTextField(this, width / 2 - 50, height / 2 - 50, "Enter Board-Size");
		sizeInputTextField.onEnter(new IProcessingAction() {

			@Override
			public void doAction() {

				try {
					SIZE = Integer.valueOf(ProcessingApplication.this.sizeInputTextField.getTextFieldUserText());
				} catch (Exception e) {
					System.out.println("Could not parse!");
				}
				disableQueenDeletion = true;
				ProcessingApplication.this.goToBoard();
			}
		});

		userInputTextField = new ProcessingTextField(this, width / 2 - 50, height / 2 - 50, "Enter Board-Size");
		userInputTextField.onEnter(new IProcessingAction() {

			@Override
			public void doAction() {

				try {
					SIZE = Integer.valueOf(ProcessingApplication.this.userInputTextField.getTextFieldUserText());
				} catch (Exception e) {
					System.out.println("Could not parse!");
				}

				showUserInputTextField = false;
				// TODO: some resetAction if 'SOLVE' was already clicked
			}
		});
	}

	public void draw() {
		background(255);
		fullscreenButton.setButtonX(width - 25);
		fullscreenButton.draw();
		if (mainMenu) {
			drawMainMenu();
		} else {
			drawField();
			drawQueenTrail();
			prevButton.draw();
			nextButton.draw();
			backButton.draw();

			if (allowUserInput) {
				solveButton.draw();
				sizeButton.draw();
				if (showUserInputTextField) {
					userInputTextField.draw();
				}
			}

			// Draw queens
			int squareSize = SQUARE_SIZE + squareScale > 5 ?  SQUARE_SIZE + squareScale : 5;
			for (List<Integer> index : queens) {
				image(queenImg, index.get(0) * squareSize + X_OFF, index.get(1) * squareSize + Y_OFF, squareSize,
						squareSize);
			}
			alertNoSolution.draw();
			alertSolutionCount.draw();
		}
	}

	private void drawMainMenu() {
		if (mainMenuChooseSize) {
			chooseLabel.draw();
			chooseSize4Button.draw();
			chooseSize8Button.draw();
			chooseSize10Button.draw();
			chooseUserSizeButton.draw();
			chooseUserInputButton.draw();
			quitButton.setButtonY(400);
			if (showTextField) {
				sizeInputTextField.draw();
			}
		} else {
			startButton.draw();
			quitButton.setButtonY(300);
		}
		quitButton.draw();
	}

	private void drawQueenTrail() {
		int squareSize = SQUARE_SIZE + squareScale > 5 ?  SQUARE_SIZE + squareScale : 5;
		
		for (List<Integer> index : queens) {
			List<QueenPosition> tempLines = new ArrayList<QueenPosition>();
			// Horizontal und vertikal
			for (int i = 0; i < SIZE; i++) {
				fill(64);
				int tempX = index.get(0) * squareSize + X_OFF;
				int tempY = index.get(1) * squareSize + Y_OFF;
				tempLines.add(new QueenPosition(i, index.get(1)));
				tempLines.add(new QueenPosition(index.get(0), i));
				rect(i * squareSize + X_OFF, tempY, squareSize, squareSize);
				rect(tempX, i * squareSize + Y_OFF, squareSize, squareSize);
			}
			// diagonal
			fill(64);
			final int size_official = SIZE - 1;
			int temp_ix = index.get(0);
			int temp_iy = index.get(1);
			int x_cord_diaPosStart = temp_ix;
			int x_cord_diaNegStart = temp_ix;
			int y_cord_diaPosStart = temp_iy;
			int y_cord_diaNegStart = temp_iy;

			// get first positive coordinate for diagonal
			while (y_cord_diaPosStart > 0 && x_cord_diaPosStart > 0) {
				x_cord_diaPosStart--;
				y_cord_diaPosStart--;
			}

			// get first negative coordinate for diagonal
			while (y_cord_diaNegStart < size_official && x_cord_diaNegStart > 0) {
				x_cord_diaNegStart--;
				y_cord_diaNegStart++;
			}

			// show positive diagonal
			while (y_cord_diaPosStart >= 0 && x_cord_diaPosStart <= size_official
					&& y_cord_diaPosStart <= size_official) {
				int tempX = x_cord_diaPosStart * squareSize + X_OFF;
				int tempYPos = y_cord_diaPosStart * squareSize + Y_OFF;
				tempLines.add(new QueenPosition(x_cord_diaPosStart, y_cord_diaPosStart));
				rect(tempX, tempYPos, squareSize, squareSize);
				y_cord_diaPosStart++;
				x_cord_diaPosStart++;
			}
			// show negative diagonal
			while (y_cord_diaNegStart <= size_official && x_cord_diaNegStart <= size_official
					&& y_cord_diaNegStart >= 0) {
				int tempX = x_cord_diaNegStart * squareSize + X_OFF;
				int tempYNeg = y_cord_diaNegStart * squareSize + Y_OFF;
				tempLines.add(new QueenPosition(x_cord_diaNegStart, y_cord_diaNegStart));
				rect(tempX, tempYNeg, squareSize, squareSize);
				y_cord_diaNegStart--;
				x_cord_diaNegStart++;
			}
			queenLines.put(index, tempLines);
		}

	}

	private List<QueenPosition> getAllLines(List<Integer> index) {
		List<QueenPosition> tempList = new ArrayList<QueenPosition>();

		// Horizontal und vertikal
		for (int i = 0; i < SIZE; i++) {
			tempList.add(new QueenPosition(i, index.get(1)));
			tempList.add(new QueenPosition(index.get(0), i));
		}
		// diagonal
		final int size_official = SIZE - 1;
		int x_cord_diaPosStart = index.get(0);
		int x_cord_diaNegStart = index.get(0);
		int y_cord_diaPosStart = index.get(1);
		int y_cord_diaNegStart = index.get(1);

		// get first positive coordinate for diagonal
		while (y_cord_diaPosStart > 0 && x_cord_diaPosStart > 0) {
			x_cord_diaPosStart--;
			y_cord_diaPosStart--;
		}

		// get first negative coordinate for diagonal
		while (y_cord_diaNegStart < size_official && x_cord_diaNegStart > 0) {
			x_cord_diaNegStart--;
			y_cord_diaNegStart++;
		}

		// show positive diagonal
		while (y_cord_diaPosStart >= 0 && x_cord_diaPosStart <= size_official && y_cord_diaPosStart <= size_official) {
			tempList.add(new QueenPosition(x_cord_diaPosStart, y_cord_diaPosStart));
			y_cord_diaPosStart++;
			x_cord_diaPosStart++;
		}
		// show negative diagonal
		while (y_cord_diaNegStart <= size_official && x_cord_diaNegStart <= size_official && y_cord_diaNegStart >= 0) {
			tempList.add(new QueenPosition(x_cord_diaNegStart, y_cord_diaNegStart));
			y_cord_diaNegStart--;
			x_cord_diaNegStart++;
		}

		return tempList;
	}

	private void drawField() {
		int squareSize = SQUARE_SIZE + squareScale > 5 ?  SQUARE_SIZE + squareScale : 5;
		
		
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				int tempX = i * squareSize + X_OFF;
				int tempY = j * squareSize + Y_OFF;
				if ((i + j) % 2 == 0) {
					fill(0);
					rect(tempX, tempY, squareSize, squareSize);
				} else {
					fill(255);
					rect(tempX, tempY, squareSize, squareSize);
				}
			}
		}
	}

	private void goToBoard() {
		solver = new SolutionsWithProlog(SIZE, queens);
		solutions = solver.solve();
		queens = getProcessingIndexFromPrologIndex(solutions.get(solutionIndex));
		mainMenu = false;
		mainMenuChooseSize = false;
	}

	// INPUT FUNKTIONEN
	public void keyPressed() {
		if (this.showTextField) {
			this.sizeInputTextField.keyPressed(key);
		}
		if (this.showUserInputTextField) {
			this.userInputTextField.keyPressed(key);
		}
	}

	public void mouseClicked() {
		System.out.println(System.lineSeparator() + "MouseX: " + mouseX + ", " + " MouseY: " + mouseY);
		fullscreenButton.mousePressed();
		if (mainMenu) {
			startButton.mousePressed();
			quitButton.mousePressed();
			if (mainMenuChooseSize) {
				if (showTextField) {
					if (!this.sizeInputTextField.overTextField()) {
						showTextField = false;
						this.sizeInputTextField.setFocus(false);
					}
				} else {
					chooseSize4Button.mousePressed();
					chooseSize8Button.mousePressed();
					chooseSize10Button.mousePressed();
					chooseUserSizeButton.mousePressed();
					chooseUserInputButton.mousePressed();

				}
			}
		} else {
			List<Integer> index = getChessTileFromMouse(mouseX, mouseY);

			System.out.println("Chess tile index " + Arrays.toString(index.toArray()));
			if (!disableQueenDeletion) {
				if (index.get(0) != -1 && index.get(1) != -1) {

					List<Integer> tempIndex = new ArrayList<Integer>();
					boolean queenRemoved = false;
					for (List<Integer> indexOfQueens : queens) {
						if (index.get(0) == indexOfQueens.get(0) && index.get(1) == indexOfQueens.get(1)) {
							tempIndex = indexOfQueens;
						}
					}
					if (!tempIndex.isEmpty()) {
						queens.remove(tempIndex);
						List<Integer> tempQueen = new ArrayList<Integer>();
						for (List<Integer> qu : queenLines.keySet()) {
							if (tempIndex.get(0) == qu.get(0) && tempIndex.get(1) == qu.get(1)) {
								tempQueen = qu;
							}
						}
						queenLines.remove(tempQueen);
						queenRemoved = true;
					}
					if (!queenRemoved) {
						boolean freeSpace = true;
						for (Map.Entry<List<Integer>, List<QueenPosition>> pair : queenLines.entrySet()) {
							for (QueenPosition position : pair.getValue()) {
								if (index.get(0) >= 0 && index.get(1) >= 0) {
									if (position.getxPosition() == index.get(0)
											&& position.getyPosition() == index.get(1)) {
										freeSpace = false;
									}
								}
							}
						}
						if (freeSpace) {
							queens.add(index);
						} else {
							System.out.println("Not a free index for a queen " + Arrays.toString(index.toArray()));
						}
					}
				}
			}
			nextButton.mousePressed();
			prevButton.mousePressed();
			backButton.mousePressed();
			if (allowUserInput) {
				solveButton.mousePressed();
				sizeButton.mousePressed();
			}
		}
	}

	public void mouseWheel(MouseEvent event) {
		if (!mainMenu) {
			squareScale += event.getCount();
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

}
