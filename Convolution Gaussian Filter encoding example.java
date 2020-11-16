import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;

import javax.imageio.ImageIO;

public class main {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String[] allImagePath = new String[6];
		allImagePath[0]="C:\\Users\\jiegu\\eclipse-workspace\\MLHW4\\picture\\Barford_Paul-2010-150x150.jpg";
		allImagePath[1]="C:\\Users\\jiegu\\eclipse-workspace\\MLHW4\\picture\\fawaz_kassem-e1542051579561-150x150.jpg";
		allImagePath[2]="C:\\Users\\jiegu\\eclipse-workspace\\MLHW4\\picture\\Koutris_Paris-for-Web-e1542043418584-150x150.jpg";
		allImagePath[3]="C:\\Users\\jiegu\\eclipse-workspace\\MLHW4\\picture\\luedtke-photo-e1542051953793-150x150.jpg";
		allImagePath[4]="C:\\Users\\jiegu\\eclipse-workspace\\MLHW4\\picture\\Robinson-S-M-Photo_CROP-e1542053126161-150x150.jpg";
		allImagePath[5]="C:\\Users\\jiegu\\eclipse-workspace\\MLHW4\\picture\\shivaram-headshot-feb-2017-color-e1543416047189-150x150.jpg";
		
		//String imagePath = allImagePath[2];
		//String imagePath = "C:\\Users\\jiegu\\eclipse-workspace\\MLHW4\\picture\\picTest.jpg";
		BufferedImage myPicture = null;
		//double cMagnitude[][] = new double[150][150];
		//double cDirection[][] = new double[150][150];
		double[][] filterArrayX = new double[3][3];
		double[][] filterArrayY = new double[3][3];
		double tempSumX = 0;
		double tempSumY = 0;
		double[][] imageArray = new double[150][150];
		double[][] derivativeX = new double[150][150];
		double[][] derivativeY = new double[150][150];
		double[][] gradiantData = new double[150][150];
		double[][] angleData = new double[150][150];
		double[][][] finalBins = new double[15][15][8];
		filterArrayX[0][0] = -1;
		filterArrayX[0][1] = 0;
		filterArrayX[0][2] = 1;
		filterArrayX[1][0] = -2;
		filterArrayX[1][1] = 0;
		filterArrayX[1][2] = 2;
		filterArrayX[2][0] = -1;
		filterArrayX[2][1] = 0;
		filterArrayX[2][2] = 1;
		
		filterArrayY[0][0] = -1;
		filterArrayY[0][1] = -2;
		filterArrayY[0][2] = -1;
		filterArrayY[1][0] = 0;
		filterArrayY[1][1] = 0;
		filterArrayY[1][2] = 0;
		filterArrayY[2][0] = 1;
		filterArrayY[2][1] = 2;
		filterArrayY[2][2] = 1;
		File test = null;
		PrintStream test2 = null;
		test = new File("output.txt");
		try {
			test2 = new PrintStream(test);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int allPic = 0; allPic<6; allPic++) {
			for (int removeX=0;removeX<150;removeX++) {
				for (int removeY=0;removeY<150;removeY++) {
					imageArray[removeX][removeY] = 0;
					derivativeX[removeX][removeY] = 0;
					derivativeY[removeX][removeY] = 0;
					gradiantData[removeX][removeY] = 0;
					angleData[removeX][removeY] = 0;
				}
			}
			for (int removeX=0;removeX<15;removeX++) {
				for (int removeY=0;removeY<15;removeY++) {
					for (int removeZ=0; removeZ<8; removeZ++) {
						finalBins[removeX][removeY][removeZ] = 0;
					}
				}
			}
			myPicture = null;
			tempSumX = 0;
			tempSumY = 0;
			String imagePath = allImagePath[allPic];
			try {
				myPicture = ImageIO.read(new File(imagePath));
			} catch (IOException e) {
				e.printStackTrace();
			}
			for (int j = 0; j<150; j++ ) {
				for (int k = 0; k<150; k++) {
					int rgb = myPicture.getRGB(k ,j);
					Color c = new Color(rgb);
					double intensity = (double)((c.getRed()+c.getBlue()+c.getGreen())/3.0);
					imageArray[j][k] = intensity;
				}
			}
			//File testA = null;
			//PrintStream test2A = null;
			//testA = new File("testA.txt");
			//try {
			//	test2A = new PrintStream(testA);
			//} catch (FileNotFoundException e) {
			//	// TODO Auto-generated catch block
			//	e.printStackTrace();
			//}
			for (int j = 0; j<150; j++ ) {
				for (int k = 0; k<150; k++) {
					//test2.print(imageArray[j][k] + ",");
					tempSumX =0;
					tempSumY =0;
					for (int m = -1; m<2; m++) {
						for (int n = -1; n<2; n++) {
							if (j>=m&&k>=n&&j<=m+149&&k<=n+149) {
								//System.out.println(filterArrayX[m+1][n+1] + " * " + imageArray[j-m][k-n]);
								tempSumX+=filterArrayX[m+1][n+1]*imageArray[j-m][k-n];
								tempSumY+=filterArrayY[m+1][n+1]*imageArray[j-m][k-n];
							}
						}
					}
					derivativeX[j][k] = tempSumX;
					//test2A.println(imageArray[j][k] + ":" + tempSumY);
					//test2A.println(tempSumX);
					derivativeY[j][k] = tempSumY;
				}
			}
			for (int j = 0; j<150; j++ ) {
				for (int k = 0; k<150; k++) {
					//System.out.println(derivativeX[j][k]);
					gradiantData[j][k] = Math.sqrt(Math.pow(derivativeX[j][k],2)+Math.pow(derivativeY[j][k], 2));
					angleData[j][k] = Math.atan2(derivativeY[j][k], derivativeX[j][k]);
				}
			}
	//		for (int j = 0; j<150; j++ ) {
	//			for (int k = 0; k<150; k++) {
	//				System.out.println("Gradiant: " + gradiantData[j][k] + "| Angle: " + angleData[j][k]);
	//			}
	//		}
			double tempAngleData = 0;
			double tempGradiantData = 0;
			int tempBin = 0;
			for (int i = 0; i<15; i++) {
				for (int j = 0; j<15; j++) {
					for (int k = 0; k<10; k++) {
						for (int m = 0; m<10; m++) {
							tempAngleData = angleData[i*10+k][j*10+m];
							tempGradiantData = gradiantData[i*10+k][j*10+m];
							if(tempAngleData<0) {
								tempAngleData+=Math.PI;
							}
							tempBin = (int)Math.floor(tempAngleData/(Math.PI/8));
							tempBin += 3;
							if (tempBin >=8) {
								tempBin -= 8;
							}
							//System.out.println(Math.floor(tempAngleData/(Math.PI/8)));
							finalBins[i][j][tempBin] += tempGradiantData;
						}
					}
				}
			}
			for (int i = 0; i <15; i ++) {
				for (int j = 0; j < 15; j++) {
					for (int k = 0; k <8; k ++) {
						if(i==14 && j==14 && k==7) {
							test2.print(finalBins[i][j][k]);
						}
						else{
							test2.print(finalBins[i][j][k] + ",");
						}
					}
				}
			}
			test2.println();
		}
	}

}
