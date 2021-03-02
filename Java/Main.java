import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {

		String imageFileName;
		String thinningFileName;
		String debugingFileName;

		if (args.length != 3) {
			System.out.println("Invalid Number of Arguments.");
			System.out.println("Enter names of image file, file for thinning results, and file for debuging output.");
			System.exit(0);
		}

		try {
			imageFileName = args[0];
			thinningFileName = args[1];
			debugingFileName = args[2];

			Scanner image = new Scanner(new File(imageFileName));
			FileWriter thinning = new FileWriter(new File(thinningFileName));
			FileWriter debuging = new FileWriter(new File(debugingFileName));

			ThinningSkeleton skeleton = new ThinningSkeleton(image);
			skeleton.loadImage(image);

			debuging.write("Output of the array before Thinning:\n");
			skeleton.prettyPrint(skeleton.firstArray, debuging);

			while (skeleton.changeFlag > 0) {
				skeleton.changeFlag = 0;
				skeleton.performThinning(skeleton.firstArray, skeleton.secondArray);
				skeleton.cycleCounter++;

				debuging.write("\nResult of Thinning: Cycle #" + skeleton.cycleCounter + "\n");
				skeleton.prettyPrint(skeleton.firstArray, debuging);
			}

			thinning.write(skeleton.getImageRows() + " " + skeleton.getImageCols() + " " + skeleton.getImageMin() + " "
					+ skeleton.getImageMax() + "\n");
			skeleton.prettyPrint(skeleton.firstArray, thinning);

			image.close();
			thinning.close();
			debuging.close();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

}
