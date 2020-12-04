import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class test {
	private static final int SMALL_SIZE = 20;
	private static final int SMALL_SIZE2 = 20;
	public static void main(String[] args) {
		System.out.println("Testing!");
		// TODO Auto-generated method stub
		String filename11 = "1weightL1.txt";
		String filename12 = "1weightL2.txt";
		String filename13 = "1biasL1.txt";
		String filename14 = "1biasL2.txt";
		String filename21 = "2weightL1.txt";
		String filename22 = "2weightL2.txt";
		String filename23 = "2biasL1.txt";
		String filename24 = "2biasL2.txt";
		String filename31 = "3weightL1.txt";
		String filename32 = "3weightL2.txt";
		String filename33 = "3biasL1.txt";
		String filename34 = "3biasL2.txt";
		File file11 = new File(filename11);
		File file12 = new File(filename12);
		File file13 = new File(filename13);
		File file14 = new File(filename14);
		File file21 = new File(filename21);
		File file22 = new File(filename22);
		File file23 = new File(filename23);
		File file24 = new File(filename24);
		File file31 = new File(filename31);
		File file32 = new File(filename32);
		File file33 = new File(filename33);
		File file34 = new File(filename34);
		//String stringArray[] = new String[785];
		int totalpix=SMALL_SIZE*SMALL_SIZE2;
		int data = totalpix;
		int totalpic = 500;
		//int count = 0;
		//int tempVal = 0;
		int currentY = 0;
		int currentJ = 0;
		int currentK = 0;
		int numCorrect1 = 0;
		int numCorrect2 = 0;
		int numCorrect3 = 0;
		double weightData1L1[][] = new double[data][data];
		double weightData1L2[][] = new double[data][5];
		double bias1L1[] =new double[data];
		double bias1L2[] =new double[5];
		double weightData2L1[][] = new double[data][data];
		double weightData2L2[][] = new double[data][5];
		double bias2L1[] =new double[data];
		double bias2L2[] =new double[5];
		double weightData3L1[][] = new double[data][data];
		double weightData3L2[][] = new double[data][5];
		double bias3L1[] =new double[data];
		double bias3L2[] =new double[5];
		//double tempWeightSumL2[] = new double[10];
		double tempWeightSum1L1 = 0.0;
		double tempWeightSum2L1 = 0.0;
		double tempWeightSum3L1 = 0.0;
		double tempSum1 = 0.0;
		double tempSum2 = 0.0;
		double tempSum3 = 0.0;
        double updateai1L2[] = new double[5];
        double updateai2L2[] = new double[5];
        double updateai3L2[] = new double[5];
		double updateai1L1[] = new double[data];
		double updateai2L1[] = new double[data];
		double updateai3L1[] = new double[data];
		//boolean flag = true;
		//BufferedImage newImage = new BufferedImage(SMALL_SIZE, SMALL_SIZE2, BufferedImage.TYPE_INT_RGB);
		//Graphics g = newImage.createGraphics();
		//double cost = 0.0;
		//double cChange = 0.0;
		//double cPrev = 0.0;
        //double xDataTrain[][] = new double[80][936];
        //double yDataTrain[] = new double[80];
        //double alpha = 0.001;
        int tempi = 0;
        double ai1L1Array[][] = new double [totalpic][data];
        double ai2L1Array[][] = new double [totalpic][data];
        double ai3L1Array[][] = new double [totalpic][data];
        double ai1L2Array[][] = new double[totalpic][5];
        double ai2L2Array[][] = new double[totalpic][5];
        double ai3L2Array[][] = new double[totalpic][5];
        //double masterArray[][] = new double[totalpic][data+1];
		double xDataOrig[][] = new double[totalpic][data];
        double yDataOrig[][] = new double[totalpic][5];
        try{
            Scanner S = new Scanner(file11);
            currentK = 0;
            currentJ = 0;
            while (S.hasNextLine()){
            	String line = S.nextLine();
            	weightData1L1[currentJ][currentK] = Double.parseDouble(line);
            	currentK++;
            	if (currentK == data) {
            		currentK = 0;
            		currentJ++;
            	}
            }
            S.close();	
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
        try{
            Scanner S = new Scanner(file12);
            currentK = 0;
            currentJ = 0;
            while (S.hasNextLine()){
            	String line = S.nextLine();
            	weightData1L2[currentJ][currentK] = Double.parseDouble(line);
            	currentK++;
            	if (currentK == 5) {
            		currentK = 0;
            		currentJ++;
            	}
            }
            S.close();	
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
        try{
            Scanner S = new Scanner(file13);
            currentJ = 0;
            while (S.hasNextLine()){
            	String line = S.nextLine();
            	bias1L1[currentJ] = Double.parseDouble(line);
            	currentJ++;
            }
            S.close();	
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
        try{
            Scanner S = new Scanner(file14);
            currentJ = 0;
            while (S.hasNextLine()){
            	String line = S.nextLine();
            	bias1L2[currentJ] = Double.parseDouble(line);
            	currentJ++;
            }
            S.close();	
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
        try{
            Scanner S = new Scanner(file21);
            currentK = 0;
            currentJ = 0;
            while (S.hasNextLine()){
            	String line = S.nextLine();
            	weightData2L1[currentJ][currentK] = Double.parseDouble(line);
            	currentK++;
            	if (currentK == data) {
            		currentK = 0;
            		currentJ++;
            	}
            }
            S.close();	
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
        try{
            Scanner S = new Scanner(file22);
            currentK = 0;
            currentJ = 0;
            while (S.hasNextLine()){
            	String line = S.nextLine();
            	weightData2L2[currentJ][currentK] = Double.parseDouble(line);
            	currentK++;
            	if (currentK == 5) {
            		currentK = 0;
            		currentJ++;
            	}
            }
            S.close();	
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
        try{
            Scanner S = new Scanner(file23);
            currentJ = 0;
            while (S.hasNextLine()){
            	String line = S.nextLine();
            	bias2L1[currentJ] = Double.parseDouble(line);
            	currentJ++;
            }
            S.close();	
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
        try{
            Scanner S = new Scanner(file24);
            currentJ = 0;
            while (S.hasNextLine()){
            	String line = S.nextLine();
            	bias2L2[currentJ] = Double.parseDouble(line);
            	currentJ++;
            }
            S.close();	
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
        try{
            Scanner S = new Scanner(file31);
            currentK = 0;
            currentJ = 0;
            while (S.hasNextLine()){
            	String line = S.nextLine();
            	weightData3L1[currentJ][currentK] = Double.parseDouble(line);
            	currentK++;
            	if (currentK == data) {
            		currentK = 0;
            		currentJ++;
            	}
            }
            S.close();	
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
        try{
            Scanner S = new Scanner(file32);
            currentK = 0;
            currentJ = 0;
            while (S.hasNextLine()){
            	String line = S.nextLine();
            	weightData3L2[currentJ][currentK] = Double.parseDouble(line);
            	currentK++;
            	if (currentK == 5) {
            		currentK = 0;
            		currentJ++;
            	}
            }
            S.close();	
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
        try{
            Scanner S = new Scanner(file33);
            currentJ = 0;
            while (S.hasNextLine()){
            	String line = S.nextLine();
            	bias3L1[currentJ] = Double.parseDouble(line);
            	currentJ++;
            }
            S.close();	
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
        try{
            Scanner S = new Scanner(file34);
            currentJ = 0;
            while (S.hasNextLine()){
            	String line = S.nextLine();
            	bias3L2[currentJ] = Double.parseDouble(line);
            	currentJ++;
            }
            S.close();	
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
        File finalFile = null;
		PrintStream pFinalFile = null;
		finalFile = new File("Final.txt");
		try {
			pFinalFile = new PrintStream(finalFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i = 1; i <=500; i++) {
			BufferedImage newImage = new BufferedImage(SMALL_SIZE, SMALL_SIZE2, BufferedImage.TYPE_INT_RGB);
			Graphics g = newImage.createGraphics();
			String imagePath = "C:\\Users\\jiegu\\eclipse-workspace\\ML760FaceMask\\Correct Mask Fixed\\Testing\\test" + i + ".jpg";
			try {
				BufferedImage myPicture = ImageIO.read(new File(imagePath));
				g.drawImage(myPicture, 0, 0, SMALL_SIZE, SMALL_SIZE2, null);
			} catch (IOException e) {
				e.printStackTrace();
			}
			for (int j = 0; j<SMALL_SIZE; j++ ) {
				for (int k = 0; k<SMALL_SIZE2; k++) {
					int rgb = newImage.getRGB(k ,j);
					Color c = new Color(rgb);
					int red = c.getRed();
					int green = c.getGreen();
					int blue = c.getBlue();
					int greyscale = (red+green+blue)/3;
					int index = j*SMALL_SIZE+k;
					tempi = i-1;
					xDataOrig[tempi][index]=(double)greyscale/255;
					//xDataOrig[tempi][index]=red;
					//xDataOrig[tempi][index+totalpix]=green;
					//xDataOrig[tempi][index+(2*totalpix)]=blue;
					if(i<=100) {
						yDataOrig[tempi][0]=1;
					}
					else if(i<=200) {
						yDataOrig[tempi][1]=1;
					}
					else if(i<=300) {
						yDataOrig[tempi][2]=1;
					}
					else if(i<=400) {
						yDataOrig[tempi][3]=1;
					}
					else {
						yDataOrig[tempi][4]=1;
					}
				}
			}
			g.dispose();
		}
		for (int i = 0; i<totalpic; i++) {
			int yResultTemp=0;
			String printString1 = " Training 1 Guess";
			String printString2 = " Training 2 Guess";
			String printString3 = " Training 3 Guess";
			//Calculate Ai
			double tempWeightSum1L2[] = new double[5];
			double tempWeightSum2L2[] = new double[5];
			double tempWeightSum3L2[] = new double[5];
		    for (int j =0; j<data; j++) {
		    	tempWeightSum1L1 = 0;
		    	tempWeightSum2L1 = 0;
		    	tempWeightSum3L1 = 0;
		    	for (int jprime = 0; jprime<data; jprime++) {
		    		tempWeightSum1L1 += weightData1L1[jprime][j]*xDataOrig[i][jprime];
		    		tempWeightSum2L1 += weightData2L1[jprime][j]*xDataOrig[i][jprime];
		    		tempWeightSum3L1 += weightData3L1[jprime][j]*xDataOrig[i][jprime];
		    	}
		    	updateai1L1[j] = 1.0/(1+Math.exp(-tempWeightSum1L1-bias1L1[j]));
		    	updateai2L1[j] = 1.0/(1+Math.exp(-tempWeightSum2L1-bias2L1[j]));
		    	updateai3L1[j] = 1.0/(1+Math.exp(-tempWeightSum3L1-bias3L1[j]));
		    	ai1L1Array[i][j] = updateai1L1[j];
		    	ai2L1Array[i][j] = updateai2L1[j];
		    	ai3L1Array[i][j] = updateai3L1[j];
		    	for (int k = 0; k<5; k++) {
		    		tempWeightSum1L2[k] += weightData1L2[j][k]*ai1L1Array[i][j];
		    		tempWeightSum2L2[k] += weightData2L2[j][k]*ai2L1Array[i][j];
		    		tempWeightSum3L2[k] += weightData3L2[j][k]*ai3L1Array[i][j];
		    	}
		    }
		    for (int t = 0; t<5; t++) {
		    	tempWeightSum1L2[t] += bias1L2[t];
		    	tempWeightSum2L2[t] += bias2L2[t];
		    	tempWeightSum3L2[t] += bias3L2[t];
		    	if(yDataOrig[i][t]==1) {
		    		yResultTemp=t;
		    	}
		    }
			updateai1L2 = softMax(tempWeightSum1L2);
			updateai2L2 = softMax(tempWeightSum2L2);
			updateai3L2 = softMax(tempWeightSum3L2);
				//System.out.println(updateaiL2);
				//aiL2 is final layer outputs array[10]
				//need softMax function
			for (int y = 0; y<5; y++) {
				ai1L2Array[i][y] = updateai1L2[y];
				ai2L2Array[i][y] = updateai2L2[y];
				ai3L2Array[i][y] = updateai3L2[y];
			}
			printString1 ="Picture "+i+" - Correct Result:"+yResultTemp+printString1+":";
			printString2 ="Picture "+i+" - Correct Result:"+yResultTemp+printString2+":";
			printString3 ="Picture "+i+" - Correct Result:"+yResultTemp+printString3+":";
			double tempMax1 = 0;
			double tempMax2 = 0;
			double tempMax3 = 0;
			int yMax1 = 0;
			int yMax2 = 0;
			int yMax3 = 0;
			for (int y = 0; y<5; y++) {
				if (ai1L2Array[i][y]>tempMax1) {
					tempMax1 = ai1L2Array[i][y];
					yMax1 = y;
				}
				if (ai2L2Array[i][y]>tempMax2) {
					tempMax2 = ai2L2Array[i][y];
					yMax2 = y;
				}
				if (ai3L2Array[i][y]>tempMax3) {
					tempMax3 = ai3L2Array[i][y];
					yMax3 = y;
				}
			}
			printString1 = printString1 + yMax1 + ":";
			printString2 = printString2 + yMax2 + ":";
			printString3 = printString3 + yMax3 + ":";
			for (int y = 0; y<5;y++) {
				int chance1 = (int)(100*ai1L2Array[i][y]);
				int chance2 = (int)(100*ai2L2Array[i][y]);
				int chance3 = (int)(100*ai3L2Array[i][y]);
				printString1 = printString1 + "[" + y +"-" + chance1 + "%]";
				printString2 = printString2 + "[" + y +"-" + chance2 + "%]";
				printString3 = printString3 + "[" + y +"-" + chance3 + "%]";
			}
			if (yMax1 == yResultTemp) {
				numCorrect1++;
				printString1 = "    " + printString1;
			}
			else {
				printString1 = "[I] " + printString1;
			}
			if (yMax2 == yResultTemp) {
				numCorrect2++;
				printString2 = "    " + printString2;
			}
			else {
				printString2 = "[I] " + printString2;
			}
			if (yMax3 == yResultTemp) {
				numCorrect3++;
				printString3 = "    " + printString3;
			}
			else {
				printString3 = "[I] " + printString3;
			}
			pFinalFile.println(printString1);
			pFinalFile.println(printString2);
			pFinalFile.println(printString3);
		}
		System.out.println("1:11050Epoch 1.5 days 310 Error - "+numCorrect1 + "/" + totalpic);
		System.out.println("2:10320Epoch 1.2 days 320 Error - "+numCorrect2 + "/" + totalpic);
		System.out.println("3:2690Epoch 0.3 days 210 Error - "+numCorrect3 + "/" + totalpic);
	}
	public static double[] softMax(double[] array) {
		double z_exp = 0.0;
		double sum_z_exp = 0.0;
		double softMaxResult[] = new double[5];
		for (int i = 0; i <array.length; i++) {
			z_exp = Math.exp(array[i]);
			sum_z_exp = sum_z_exp + z_exp;
		}
		for (int i = 0; i <array.length; i++) {
			softMaxResult[i] = Math.exp(array[i])/sum_z_exp;
		}
		return softMaxResult;
	}
	public static double Sigmoid(double x) {
		return 1/(1+Math.exp(x));
	}
	public static double Sigmoid_derivative(double x) {
		return Sigmoid(x)*(1-Sigmoid(x));
	}

}
