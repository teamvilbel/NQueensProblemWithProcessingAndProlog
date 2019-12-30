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
import thm.vilbel.com.processing.alert.ProcessingAlert;
import thm.vilbel.com.processing.button.ProcessingButton;
import thm.vilbel.com.processing.textfield.ProcessingTextField;
import thm.vilbel.com.processing.ProcessingLabel;
import thm.vilbel.com.prolog.SolutionsWithProlog;

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
	private Map<List<Integer>, List<QueenPosition>> queenLines;

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
	private ProcessingButton chooseUserSizeButton;
	private ProcessingButton chooseUserInputButton;

	private ProcessingAlert alertNoSolution;
	private ProcessingAlert alertSolutionCount;
	private ProcessingTextField sizeInputTextField;
	

	private boolean mainMenu;
	private boolean mainMenuChooseSize;
	private boolean disableQueenDeletion;
	private boolean showTextField;

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
			disableQueenDeletion = false;
			ProcessingApplication.this.SIZE = 8;
			solveButton = new ProcessingButton(ProcessingApplication.this, ProcessingApplication.this.width / 2 - 45,
					ProcessingApplication.this.SQUARE_SIZE * ProcessingApplication.this.SIZE + 2 * Y_OFF, "SOLVE");
			solveButton.onClick(solveAction);
			// solver = new SolutionsWithProlog(SIZE, queens);
//			solutions = solver.solve();
			mainMenu = false;
			mainMenuChooseSize = false;
			allowUserInput = true;
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
				alertSolutionCount = new ProcessingAlert(ProcessingApplication.this, width / 2 - 45, SQUARE_SIZE * SIZE + 200, 150,50,(solutions.size()) + " Solutions!");
				alertSolutionCount.resetAlert();
				queens = getProcessingIndexFromPrologIndex(solutions.get(solutionIndex));
			}
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
			disableQueenDeletion = true;
			mainMenu = true;
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

	private boolean allowUserInput = false;

	public ProcessingApplication() {
		queens = new HashSet<List<Integer>>();
		queenLines = new HashMap<List<Integer>, List<QueenPosition>>();
		mainMenu = true;
		mainMenuChooseSize = false;
		disableQueenDeletion = true;
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
		chooseUserSizeButton = new ProcessingButton(this, width / 2, 300, 150, 45, "Enter Size");
		chooseUserInputButton = new ProcessingButton(this, width / 2 + 160, 300, 150, 45, "User Input");

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

		alertNoSolution = new ProcessingAlert(this, width / 2 - 187, height/2 -50, 375, 100,
				"There are no sulotions available\r\nfor this queen configuration!\r\nChange the position of your queens.");
		alertSolutionCount = new ProcessingAlert(ProcessingApplication.this, width / 2 - 45, SQUARE_SIZE * SIZE + 200, 100 ,100," Solutions!");
		
		sizeInputTextField = new ProcessingTextField(this, width / 2 - 50, height/2 -50, "Enter Board-Size");
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

			if (allowUserInput) {
				solveButton.draw();
			}

			// Draw queens
			for (List<Integer> index : queens) {
				image(img, index.get(0) * SQUARE_SIZE + X_OFF, index.get(1) * SQUARE_SIZE + Y_OFF, SQUARE_SIZE,
						SQUARE_SIZE);
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
		for (List<Integer> index : queens) {
			List<QueenPosition> tempLines = new ArrayList<QueenPosition>();
			// Horizontal und vertikal
			for (int i = 0; i < SIZE; i++) {
				fill(64);
				int tempX = index.get(0) * SQUARE_SIZE + X_OFF;
				int tempY = index.get(1) * SQUARE_SIZE + Y_OFF;
				tempLines.add(new QueenPosition(i, index.get(1)));
				tempLines.add(new QueenPosition(index.get(0), i));
				rect(i * SQUARE_SIZE + X_OFF, tempY, SQUARE_SIZE, SQUARE_SIZE);
				rect(tempX, i * SQUARE_SIZE + Y_OFF, SQUARE_SIZE, SQUARE_SIZE);
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
				int tempX = x_cord_diaPosStart * SQUARE_SIZE + X_OFF;
				int tempYPos = y_cord_diaPosStart * SQUARE_SIZE + Y_OFF;
				tempLines.add(new QueenPosition(x_cord_diaPosStart, y_cord_diaPosStart));
				rect(tempX, tempYPos, SQUARE_SIZE, SQUARE_SIZE);
				y_cord_diaPosStart++;
				x_cord_diaPosStart++;
			}
			// show negative diagonal
			while (y_cord_diaNegStart <= size_official && x_cord_diaNegStart <= size_official
					&& y_cord_diaNegStart >= 0) {
				int tempX = x_cord_diaNegStart * SQUARE_SIZE + X_OFF;
				int tempYNeg = y_cord_diaNegStart * SQUARE_SIZE + Y_OFF;
				tempLines.add(new QueenPosition(x_cord_diaNegStart, y_cord_diaNegStart));
				rect(tempX, tempYNeg, SQUARE_SIZE, SQUARE_SIZE);
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
	public void keyPressed() {
//		49 - 57
		if (this.showTextField) {
			this.sizeInputTextField.keyPressed(key);
		}
	}

	public void mouseClicked() {
		System.out.println(System.lineSeparator() + "MouseX: " + mouseX + ", " + " MouseY: " + mouseY);
		if (mainMenu) {
			startButton.mousePressed();
			quitButton.mousePressed();
			if (mainMenuChooseSize) {
				if (showTextField) {
					if (!this.sizeInputTextField.overTextField()) {
						showTextField = false;
						this.sizeInputTextField.setFocus(false);
					}
				}else {
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
			if(!disableQueenDeletion) {
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
