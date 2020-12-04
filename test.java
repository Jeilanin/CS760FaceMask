import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintStream;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.la4j.LinearAlgebra;
import org.la4j.Matrix;
import org.la4j.Vector;
import org.la4j.Vectors;
import org.la4j.matrix.dense.Basic2DMatrix;
public class test {
	private static final int ASMALL_SIZE = 75;
	private static final int ASMALL_SIZE2 = 75;
	private static final int BSMALL_SIZE = 100;
	private static final int BSMALL_SIZE2 = 100;
	public static Basic2DMatrix from2DArray(double[][] array) {
		  return new Basic2DMatrix(array);
		}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int totalpix1=BSMALL_SIZE*BSMALL_SIZE2;
		int totalpix2=ASMALL_SIZE*ASMALL_SIZE2;
		int data1 = totalpix1;
		int data2 = 2*totalpix2;
		int data3 = 2*totalpix1;
		int totalpic = 200;
		String filename1 = "wei1.txt";
		String filename2 = "wei2.txt";
		String filename3 = "wei3.txt";
		Integer count1 = 0;
		Integer count2 = 0;
		Integer count3 = 0;
		Integer currentY1 = 0;
		Integer currentY2 = 0;
		Integer currentY3 = 0;
		Integer m1 = 0;
		Integer m2 = 0;
		Integer m3 = 0;
		int numCorrect1 = 0;
		int numCorrect2 = 0;
		int numCorrect3 = 0;
		double weightData1[] = new double[data1];
		double weightData2[] = new double[data2];
		double weightData3[] = new double[data3];
		double ai1 = 0.0;
		double ai2 = 0.0;
		double ai3 = 0.0;
		double tempWeightSum1 = 0.0;
		double tempWeightSum2 = 0.0;
		double tempWeightSum3 = 0.0;
		double bias1 = 0.0;
		double bias2 = 0.0;
		double bias3 = 0.0;
		//double distributionData1[][] = new double[data1][data1];
		//double distributionData2[][] = new double[data2][data2];
		//double distributionData3[][] = new double[data3][data3];
		double tempSum = 0.0;
		File file1 = new File(filename1);
		File file2 = new File(filename2);
		File file3 = new File(filename3);
		double xData1[][] = new double[totalpic][data1];
		double xData2[][] = new double[totalpic][data2];
		double xData3[][] = new double[totalpic][data3];
		double yData1[] = new double[totalpic];
		double yData2[] = new double[totalpic];
		double yData3[] = new double[totalpic];
		double aiArray1[] = new double[totalpic];
		double aiArray2[] = new double[totalpic];
		double aiArray3[] = new double[totalpic];
		int tempi = 0;
		try{
            Scanner S = new Scanner(file1);
            while (S.hasNextLine()){
            	String line = S.nextLine();
            	if (m1==0) {
            		bias1 = Double.parseDouble(line);
            		m1++;
            	}
            	else {
	            	Double weight = Double.parseDouble(line);
	            	weightData1[m1-1]=weight;
	            	m1++;
            	}
            }
        		
        }catch (FileNotFoundException e){

            e.printStackTrace();
        }
		try{
            Scanner S = new Scanner(file2);
            while (S.hasNextLine()){
            	String line = S.nextLine();
            	if (m2==0) {
            		bias2 = Double.parseDouble(line);
            		m2++;
            	}
            	else {
	            	Double weight = Double.parseDouble(line);
	            	weightData2[m2-1]=weight;
	            	m2++;
            	}
            }
        		
        }catch (FileNotFoundException e){

            e.printStackTrace();
        }
		try{
            Scanner S = new Scanner(file3);
            while (S.hasNextLine()){
            	String line = S.nextLine();
            	if (m3==0) {
            		bias3 = Double.parseDouble(line);
            		m3++;
            	}
            	else {
	            	Double weight = Double.parseDouble(line);
	            	weightData3[m3-1]=weight;
	            	m3++;
            	}
            }
        		
        }catch (FileNotFoundException e){

            e.printStackTrace();
        }
		for (int i = 1; i <=200; i++) {
			BufferedImage newImage = new BufferedImage(BSMALL_SIZE, BSMALL_SIZE2, BufferedImage.TYPE_INT_RGB);
			Graphics g = newImage.createGraphics();
			String imagePath = "C:\\Users\\jiegu\\eclipse-workspace\\ML760FaceMaskLogRegression\\Correct Mask Fixed\\Testing\\test" + i + ".jpg";
			try {
				BufferedImage myPicture = ImageIO.read(new File(imagePath));
				g.drawImage(myPicture, 0, 0, BSMALL_SIZE, BSMALL_SIZE2, null);
			} catch (IOException e) {
				e.printStackTrace();
			}
			for (int j = 0; j<BSMALL_SIZE; j++ ) {
				for (int k = 0; k<BSMALL_SIZE2; k++) {
					int rgb = newImage.getRGB(k ,j);
					Color c = new Color(rgb);
					int red = c.getRed();
					int green = c.getGreen();
					int blue = c.getBlue();
					int greyscale = (red+green+blue)/3;
					int index = j*BSMALL_SIZE+k;
					tempi = i-1;
					xData1[tempi][index]=(double)greyscale/255;
					xData3[tempi][index]=(double)greyscale/255;
					xData3[tempi][index+totalpix1]=blue/255;
					//xDataOrig[tempi][index]=red;
					//xDataOrig[tempi][index+totalpix]=green;
					//xDataOrig[tempi][index+(2*totalpix)]=blue;
					if(i<=100) {
						yData1[tempi]=1;
						yData3[tempi]=1;
					}
					else {
						yData1[tempi]=0;
						yData3[tempi]=0;
					}
				}
			}
			g.dispose();
		}
		tempi = 0;
		for (int i = 1; i <=200; i++) {
			BufferedImage newImage = new BufferedImage(ASMALL_SIZE, ASMALL_SIZE2, BufferedImage.TYPE_INT_RGB);
			Graphics g = newImage.createGraphics();
			String imagePath = "C:\\Users\\jiegu\\eclipse-workspace\\ML760FaceMaskLogRegression\\Correct Mask Fixed\\Testing\\test" + i + ".jpg";
			try {
				BufferedImage myPicture = ImageIO.read(new File(imagePath));
				g.drawImage(myPicture, 0, 0, ASMALL_SIZE, ASMALL_SIZE2, null);
			} catch (IOException e) {
				e.printStackTrace();
			}
			for (int j = 0; j<ASMALL_SIZE; j++ ) {
				for (int k = 0; k<ASMALL_SIZE2; k++) {
					int rgb = newImage.getRGB(k ,j);
					Color c = new Color(rgb);
					int red = c.getRed();
					int green = c.getGreen();
					int blue = c.getBlue();
					int greyscale = (red+green+blue)/3;
					int index = j*ASMALL_SIZE+k;
					tempi = i-1;
					xData2[tempi][index]=(double)greyscale/255;
					xData2[tempi][index+totalpix2]=blue/255;
					//xDataOrig[tempi][index]=red;
					//xDataOrig[tempi][index+totalpix]=green;
					//xDataOrig[tempi][index+(2*totalpix)]=blue;
					if(i<=100) {
						yData2[tempi]=1;
					}
					else {
						yData2[tempi]=0;
					}
				}
			}
			g.dispose();
		}
//		for (int i = 100; i <=200; i++) {
//			BufferedImage newImage = new BufferedImage(SMALL_SIZE, SMALL_SIZE2, BufferedImage.TYPE_INT_RGB);
//			Graphics g = newImage.createGraphics();
//			String imagePath = "C:\\Users\\jiegu\\eclipse-workspace\\ML760FaceMaskLogRegression\\Correct Mask Fixed\\Testing\\test" + i + ".jpg";
//			try {
//				BufferedImage myPicture = ImageIO.read(new File(imagePath));
//				g.drawImage(myPicture, 0, 0, SMALL_SIZE, SMALL_SIZE2, null);
//			} catch (IOException e) {
//				System.out.println("Picture: "+i);
//				e.printStackTrace();
//			}
//			for (int j = 0; j<SMALL_SIZE; j++ ) {
//				for (int k = 0; k<SMALL_SIZE2; k++) {
//					int rgb = newImage.getRGB(k ,j);
//					Color c = new Color(rgb);
//					int red = c.getRed();
//					int green = c.getGreen();
//					int blue = c.getBlue();
//					int greyscale = (red+green+blue)/3;
//					int index = j*SMALL_SIZE+k;
//					tempi = i+99;
//					xData[tempi][index]=(double)greyscale/255;
//					//xDataOrig[tempi][index]=red;
//					//xDataOrig[tempi][index+totalpix]=green;
//					//xDataOrig[tempi][index+(2*totalpix)]=blue;
//					yData[tempi]=0;
//				}
//			}
//			g.dispose();
//		}
        for (int i = 0; i<totalpic;i++) {
        	//System.out.println(yData[i]);
        	//calculate ai
        	tempWeightSum1 = 0;
        	tempWeightSum2 = 0;
        	tempWeightSum3 = 0;
        	double var = 0;
        	for (int j = 0; j<data1; j++) {
        		tempWeightSum1 += weightData1[j]*xData1[i][j];
        	}
        	for (int j = 0; j<data2; j++) {
        		tempWeightSum2 += weightData2[j]*xData2[i][j];
        	}
        	for (int j = 0; j<data3; j++) {
        		tempWeightSum3 += weightData3[j]*xData3[i][j];
        	}
        	//System.out.println(tempWeightSum+" "+ numIter);
        	ai1 = 1.0/(1+Math.exp(-tempWeightSum1-bias1));
        	ai2 = 1.0/(1+Math.exp(-tempWeightSum2-bias2));
        	ai3 = 1.0/(1+Math.exp(-tempWeightSum3-bias3));
        	aiArray1[i] = ai1;
        	aiArray2[i] = ai2;
        	aiArray3[i] = ai3;
        	//if (ai>0.5&&i<200) {
        	//	System.out.println(i + ": 6 - Incorrect (" + ai+")");
        	//}
        	//else if(ai<0.5&&i>=200) {
        	//	System.out.println(i+ ": 4 - Incorrect (" + ai+")");
        	//}
        	if(ai1<0.5){
        		System.out.println(i+"-Test1-0: "+ai1);
        		if(yData1[i]==0) {
        			numCorrect1++;
        		}
        	}
        	else {
        		System.out.println(i+"-Test1-1: "+ai1);
        		if(yData1[i]==1) {
        			numCorrect1++;
        		}
        	}
        	if(ai2<0.5){
        		System.out.println(i+"-Test2-0: "+ai2);
        		if(yData2[i]==0) {
        			numCorrect2++;
        		}
        	}
        	else {
        		System.out.println(i+"-Test2-1: "+ai2);
        		if(yData2[i]==1) {
        			numCorrect2++;
        		}
        	}
        	if(ai3<0.5){
        		System.out.println(i+"-Test3-0: "+ai3);
        		if(yData3[i]==0) {
        			numCorrect3++;
        		}
        	}
        	else {
        		System.out.println(i+"-Test3-1: "+ai3);
        		if(yData3[i]==1) {
        			numCorrect3++;
        		}
        	}
        }
        System.out.println("Number Correct1:" + numCorrect1 + " - "+100*((double)numCorrect1/totalpic)+"%");
        System.out.println("Number Correct2:" + numCorrect1 + " - "+100*((double)numCorrect2/totalpic)+"%");
        System.out.println("Number Correct3:" + numCorrect1 + " - "+100*((double)numCorrect3/totalpic)+"%");
//        for (int j = 0; j<data;j++) {
//        	for(int k = 0; k<data; k++) {
//        		tempSum = 0;
//        		for (int i = 0; i<totalpic;i++) {
//        			tempSum+=aiArray[i]*xData[i][j]*xData[i][k];
//        		}
//        		distributionData[j][k] = tempSum;
//        		System.out.println(j + "," + k + ":" + distributionData[j][k]);
//        	}
//        }
//        Matrix distribMatrix = new Basic2DMatrix(distributionData);
//        Matrix inverseDistrib = distribMatrix.withInverter(LinearAlgebra.GAUSS_JORDAN).inverse();
        //for (int j = 0; j<data;j++) {
        //	for(int k = 0; k<data; k++) {
        //		//System.out.println(j + "," + k + ":" + inverseDistrib.get(j, k));
        //	}
        //}
//        double cinterval[] = new double[data];
//        for (int j = 0; j<data;j++) {
//        	tempSum = 0;
//        	for (int k = 0; k<data;k++) {
//        		tempSum+=xData[100][k]*inverseDistrib.get(j, k);
//        	}
//        	cinterval[j] = tempSum;
//        }
//        tempSum=0;
//        for (int j = 0; j<data;j++) {
//        	tempSum+=cinterval[j]*xData[100][j];
//        }
//        System.out.println(tempSum);
	}
	
}

