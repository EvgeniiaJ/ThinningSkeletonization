import java.io.FileWriter;
import java.util.Scanner;

public class ThinningSkeleton {

	private int imageRows;
	private int imageCols;
	private int imageMin;
	private int imageMax;
	public int changeFlag;
	public int cycleCounter;
	
	public int[][] firstArray;
	public int[][] secondArray;
	public int[] neighborsArray;
	
	public ThinningSkeleton(Scanner input) {
		
		if(input.hasNext()) {
			imageRows = input.nextInt();
		}
		if(input.hasNext()) {
			imageCols = input.nextInt();
		}
		if(input.hasNext()) {
			imageMax = input.nextInt();
		}
		if(input.hasNext()) {
			imageMin = input.nextInt();
		}
		changeFlag = 1;
		cycleCounter = 0;
		initializeArrays();
	
	}
	
	public void initializeArrays() {
		firstArray = new int[imageRows + 2][imageCols + 2];
		secondArray = new int[imageRows + 2][imageCols + 2];
		neighborsArray = new int[9];
		
		for(int i = 0; i < (imageRows + 2); i++) {
			for(int j = 0; j < (imageCols + 2); j++) {
				
				firstArray[i][j] = 0;
				secondArray[i][j] = 0;
				
			}
		}
		
		for(int i = 0; i < 9; i++) {
			neighborsArray[i] = 0;
		}
	}
	
	public void makeFrameZero(int[][] array) {
		
		for(int i = 0; i < (imageRows + 2); i++) {
			array[i][0] = 0;
			array[i][imageCols + 1] = 0;
		}
		
		for(int i = 0; i < (imageCols + 2); i++) {
			array[0][i] = 0;
			array[imageRows + 1][i] = 0;
		}
	}
	
	public void loadImage(Scanner input) {
		while(input.hasNext()) {
			for (int i = 1; i < (imageRows + 1); i++) {
				for (int j = 1; j < (imageCols + 1); j++) {
					firstArray[i][j] = input.nextInt();
				}
			}
		}
	}
	
	public void copyArrays(int[][] first, int[][] second) {
		
		for(int i = 0; i < (imageRows + 2); i++) {
			for(int j = 0; j < (imageCols + 2); j++) {
				
				first[i][j] = second[i][j];
				
			}
		}
	}
	
	public void performThinning(int[][] firstArray, int[][] secondArray) {
		performNorthThinning(firstArray, secondArray);
		copyArrays(firstArray, secondArray);
		
		performSouthThinning(firstArray, secondArray);
		copyArrays(firstArray, secondArray);
		
		performWestThinning(firstArray, secondArray);
		copyArrays(firstArray, secondArray);
		
		performEastThinning(firstArray, secondArray);
		copyArrays(firstArray, secondArray);
	}
	
	public void performNorthThinning(int[][] firstArray, int[][] secondArray) {
		for (int i = 1; i < (imageRows + 1); i++) {
			for (int j = 1; j < (imageCols + 1); j++) {
				secondArray[i][j] = firstArray[i][j];
				
				if (firstArray[i][j] > 0 && firstArray[i - 1][j] <= 0) {
					if (conditionsChecked(firstArray, i, j, 1)) {
						secondArray[i][j] = 0;
						changeFlag++;
					}
				}
			}
		}
	}
	
	public void performSouthThinning(int[][] firstArray, int[][] secondArray) {
		for (int i = 1; i < (imageRows + 1); i++) {
			for (int j = 1; j < (imageCols + 1); j++) {
				secondArray[i][j] = firstArray[i][j];
				
				if (firstArray[i][j] > 0 && firstArray[i + 1][j] <= 0) {
					if (conditionsChecked(firstArray, i, j, 1)) {
						secondArray[i][j] = 0;
						changeFlag++;
					}
				}
			}
		}
	}
	
	public void performWestThinning(int[][] firstArray, int[][] secondArray) {
		for (int i = 1; i < (imageRows + 1); i++) {
			for (int j = 1; j < (imageCols + 1); j++) {
				secondArray[i][j] = firstArray[i][j];
				
				if (firstArray[i][j] > 0 && firstArray[i][j - 1] <= 0) {
					if (conditionsChecked(firstArray, i, j, 2)) {
						secondArray[i][j] = 0;
						changeFlag++;
					}
				}
			}
		}
	}
	
	public void performEastThinning(int[][] firstArray, int[][] secondArray) {
		for (int i = 1; i < (imageRows + 1); i++) {
			for (int j = 1; j < (imageCols + 1); j++) {
				secondArray[i][j] = firstArray[i][j];
				
				if (firstArray[i][j] > 0 && firstArray[i][j + 1] <= 0) {
					if (conditionsChecked(firstArray, i, j, 2)) {
						secondArray[i][j] = 0;
						changeFlag++;
					}
				}
			}
		}
	}
	
	public boolean conditionsChecked(int[][] firstArray, int i, int j, int passId) {
		int counter = countNonZeroNeighbors(firstArray, i, j);
		boolean connected = true;
		
		if(passId == 1) {
			if(counter >= 4) {
				connected = checkConnectedness(neighborsArray, counter);
				if(connected) {
					return true;
				}
			}
		}
		else if (passId == 2){
			if(counter >= 3) {
				connected = checkConnectedness(neighborsArray, counter);
				if(connected) {
					return true;
				}
			}
		}
		return false;
	}
	
	public int countNonZeroNeighbors(int[][] array, int i, int j) {
		int index = 0;
		int counter = -1; // not to count itself
		
		for(int row = i - 1; row < i + 2; row++) {
			for(int col = j - 1; col < j + 2; col++) {
				neighborsArray[index] = array[row][col];
				if(neighborsArray[index] > 0) {
					counter++;
				}
				index++;
			}			
		}
		return counter;
	}
	
	public boolean checkConnectedness(int[] neighbors, int counter) {
		
		// a  b  c
		// d  x  e
		// f  g  h
		
		int a = neighbors[0]; int b = neighbors[1]; int c = neighbors[2];
		int d = neighbors[3]; int x = neighbors[4]; int e = neighbors[5];
		int f = neighbors[6]; int g = neighbors[7]; int h = neighbors[8];
		
		if(a == 1 && b != 1 && d != 1) {
			return false;
		}
		
		else if (c == 1 && b != 1 && e != 1) {
			return false;
		}
		else if (f == 1 && d != 1 && g != 1) {
			return false;
		}
		else if (h == 1 && e != 1 && g != 1) {
			return false;
		}
		else if ((b != 1 && g != 1) || (d != 1 && e != 1)) {
			return false;
		}

		else if (counter == 3) {
			if (a != 1 && h != 1 && ((b != 1 && d != 1) || (e != 1 && g != 1))) {
				return false;
			}
			else if (c != 1 && f != 1 && ((b != 1 && e != 1) || (d != 1 && g != 1))) {
				return false;
			}
		}
		
		return true;		
	}
	
	public void prettyPrint(int[][] array, FileWriter output) {
		try {
			for (int i = 1; i < (imageRows + 1); i++) {
				for (int j = 1; j < (imageCols + 1); j++) {
					if(array[i][j] > 0) {
						output.write(array[i][j] + " ");
					}
					else {
						output.write("  ");
					}
				}
				output.write("\n");
			}
			output.write("\n");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	public int getImageRows() {
		return imageRows;
	}

	public void setImageRows(int imageRows) {
		this.imageRows = imageRows;
	}

	public int getImageCols() {
		return imageCols;
	}

	public void setImageCols(int imageCols) {
		this.imageCols = imageCols;
	}

	public int getImageMin() {
		return imageMin;
	}

	public void setImageMin(int imageMin) {
		this.imageMin = imageMin;
	}

	public int getImageMax() {
		return imageMax;
	}

	public void setImageMax(int imageMax) {
		this.imageMax = imageMax;
	}
	
}
