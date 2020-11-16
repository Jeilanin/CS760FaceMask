import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Random;

import javax.imageio.ImageIO;

public class Main {
	//Test Case is 121-140
	private static final int SMALL_SIZE = 26;
	private static final int SMALL_SIZE2 = 36;
	public static void main(String[] args) {
		//BufferedImage myPicture = new BufferedImage();
		// TODO Auto-generated method stub
		int randomArray[] = new int[360];
		int tempVal = 0;
		double weightDataL1[][] = new double[936][936];
		double weightDataL2[] = new double[936];
		double biasL1[] =new double[936];
		double biasL2 = 0.0;
		double tempWeightSumL2 = 0.0;
		double tempWeightSumL1 = 0.0;
		double tempSum = 0.0;
		Long numIter = 0L;
		int epoch = 0;
		double aiL1Array[][] = new double [360][936];
        double aiL2Array[] = new double[360];
        double updateaiL2 = 0.0;
		double updateaiL1[] = new double[936];
		boolean flag = true;
		//BufferedImage newImage = new BufferedImage(SMALL_SIZE, SMALL_SIZE2, BufferedImage.TYPE_INT_RGB);
		//Graphics g = newImage.createGraphics();
		double cost = 0.0;
		double cChange = 0.0;
		double cPrev = 0.0;
		double masterArray[][] = new double[360][937];
		double xData[][] = new double[360][936];
        double yData[] = new double[360];
        double xDataTrain[][] = new double[80][936];
        double yDataTrain[] = new double[80];
        double alpha = 0.01;
        int tempi = 0;
		for (int i = 1; i <=200; i++) {
			BufferedImage newImage = new BufferedImage(SMALL_SIZE, SMALL_SIZE2, BufferedImage.TYPE_INT_RGB);
			Graphics g = newImage.createGraphics();
			String imagePath = "C:\\Users\\jiegu\\eclipse-workspace\\ML Facial Recognition HW2\\faces\\" + i + "a.jpg";
			try {
				BufferedImage myPicture = ImageIO.read(new File(imagePath));
				g.drawImage(myPicture, 0, 0, SMALL_SIZE, SMALL_SIZE2, null);
			} catch (IOException e) {
				e.printStackTrace();
			}
			for (int j = 0; j<36; j++ ) {
				for (int k = 0; k<26; k++) {
					int rgb = newImage.getRGB(k ,j);
					Color c = new Color(rgb);
					double intensity = (double)(c.getRed())/255.0;
					int index = j*26+k;
					if (i<121) {
						tempi = 2*(i-1);
						xData[tempi][index] = intensity;
						yData[tempi] = 0;
					}
					else if (i>140) {
						tempi = 2*(i-21);
						xData[tempi][index] = intensity;
						yData[tempi] = 0;
					}
					else {
						tempi = 2*(i-121);
						xDataTrain[tempi][index] = intensity;
						yDataTrain[tempi] = 0;
					}
				}
			}
			g.dispose();
			newImage = new BufferedImage(SMALL_SIZE, SMALL_SIZE2, BufferedImage.TYPE_INT_RGB);
			g = newImage.createGraphics();
			imagePath = "C:\\Users\\jiegu\\eclipse-workspace\\ML Facial Recognition HW2\\faces\\" + i + "b.jpg";
			try {
				BufferedImage myPicture = ImageIO.read(new File(imagePath));
				g.drawImage(myPicture, 0, 0, SMALL_SIZE, SMALL_SIZE2, null);
			} catch (IOException e) {
				e.printStackTrace();
			}
			for (int j = 0; j<36; j++ ) {
				for (int k = 0; k<26; k++) {
					int rgb = newImage.getRGB(k ,j);
					Color c = new Color(rgb);
					double intensity = (double)(c.getRed())/255.0;
					int index = j*26+k;
					if (i<121) {
						tempi = 2*(i-1)+1;
						xData[tempi][index] = intensity;
						yData[tempi] = 1;
					}
					else if (i>140) {
						tempi = 2*(i-21)+1;
						xData[tempi][index] = intensity;
						yData[tempi] = 1;
					}
					else {
						tempi = 2*(i-121)+1;
						xDataTrain[tempi][index] = intensity;
						yDataTrain[tempi] = 1;
					}
				}
			}
			g.dispose();
		}
		for (int j = 0; j<936; j ++) {
			System.out.print(xData[358][j] + ",");
		}
		System.out.println();
		for (int j = 0; j<936; j ++) {
			System.out.print(xData[359][j] + ",");
		}
		System.out.println();
		//initialize masterarray
		for (int n = 0; n<360;n++) {
			for (int m = 0;m<937;m++) {
				if (m ==0) {
					masterArray[n][m] = yData[n];
				}
				else {
					masterArray[n][m] = xData[n][m-1];
				}
			}
		}
		for (int j = 0; j<936;j++) {
        	for (int j2 = 0; j2<936; j2++) {
        		weightDataL1[j][j2] = -1 + Math.random()*2;
        	}
        	//System.out.println(j);
        	weightDataL2[j] = -1 + Math.random()*2;;
        	biasL1[j]= -1 + Math.random()*2;;
        }
		for (int j = 0; j<360; j++) {
			randomArray[j]=j;
		}
		while(flag) {
			//shuffle masterArray
			RandomizeArrayInteger(randomArray);
			for (int n = 0; n<360;n++) {
				tempVal = randomArray[n];
				for (int m = 0;m<937;m++) {
					if (m ==0) {
						yData[n]= masterArray[tempVal][m];
					}
					else {
						xData[n][m-1] = masterArray[tempVal][m];
					}
				}
			}
			//Calculate Ai
			epoch ++;
			for (int i = 0; i<360;i++) {
				numIter ++;
				tempWeightSumL2 = 0;
		    	for (int j =0; j<936; j++) {
		    		tempWeightSumL1 = 0;
		    		for (int jprime = 0; jprime<936; jprime++) {
		    			tempWeightSumL1 += weightDataL1[jprime][j]*xData[i][jprime];
		    		}
		    		updateaiL1[j] = 1.0/(1+Math.exp(-tempWeightSumL1-biasL1[j]));
		    		aiL1Array[i][j] = updateaiL1[j];
		    		tempWeightSumL2 += weightDataL2[j]*aiL1Array[i][j];
		    	}
				updateaiL2 = 1.0/(1+Math.exp(-tempWeightSumL2-biasL2));
				//System.out.println(updateaiL2);
				aiL2Array[i]=updateaiL2;
				//calculate C
	        	cost+=Math.pow(yData[i]-aiL2Array[i], 2)/2;
	        	//System.out.println(cost);
				//update L2 Weights
				for (int j = 0; j<936;j++) {
		    		tempSum=(updateaiL2-yData[i])*updateaiL2*(1-updateaiL2)*updateaiL1[j];
		    		tempSum=alpha*tempSum;
		    		//if (tempSum !=0){System.out.println(tempSum);}
		    		weightDataL2[j] -= tempSum;
		        }
				//update L2 Bias
		    	tempSum =(updateaiL2-yData[i])*updateaiL2*(1-updateaiL2);
		    	tempSum = alpha*tempSum;
		    	biasL2 -= tempSum;
		    	//update L1 Weights
		    	for (int jprime = 0; jprime<936;jprime++) {
		    		for (int j = 0; j<936; j++) {
		    			tempSum=(updateaiL2-yData[i])*updateaiL2*(1-updateaiL2)*weightDataL2[j]*updateaiL1[j]*(1-updateaiL1[j])*xData[i][jprime];
		    			tempSum=alpha*tempSum;
			    		weightDataL1[jprime][j] -= tempSum;
		    		}
		        }
		    	//update L1 Bias
		    	for (int j = 0; j<936; j++) {
	    			tempSum=(updateaiL2-yData[i])*updateaiL2*(1-updateaiL2)*weightDataL2[j]*updateaiL1[j]*(1-updateaiL1[j]);
	    			tempSum = alpha*tempSum;
			    	biasL1[j] -= tempSum;
		    	}
			}
			System.out.println(cost+" C Value");
	    	cChange = cost-cPrev;
	    	cPrev=cost;
	    	if (cost<5){
	    		flag = false;
	    	}
	    	cost=0;
		}
		File weightL1File = null;
		PrintStream writerL2 = null;
		PrintStream writerL1 = null;
		PrintStream biasL1F = null;
		PrintStream biasL2F = null;
		weightL1File = new File("weightL1.txt");
		File weightL2File = null;
		weightL2File = new File("weightL2.txt");
		File biasL1File = null;
		File biasL2File = null;
		biasL1File = new File("biasL1.txt");
		biasL2File = new File("biasL2.txt");
		try {
			writerL2 = new PrintStream(weightL2File);
			writerL1 = new PrintStream(weightL1File);
			biasL1F = new PrintStream(biasL1File);
			biasL2F = new PrintStream(biasL2File);
			biasL2F.println(biasL2);
			for (int i = 0; i<936;i++) {
				for (int j = 0; j<936; j++) {
						writerL1.println(weightDataL1[i][j]);
					}
				biasL1F.println(biasL1[i]);
				writerL2.println(weightDataL2[i]);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			writerL1.close();
			writerL2.close();
		}
		for (int j = 0; j<936; j++ ) {
		}
	}
	public static String[] RandomizeArrayString(String[] array){
		Random rgen = new Random();  // Random number generator			
 
		for (int i=0; i<array.length; i++) {
		    int randomPosition = rgen.nextInt(array.length);
		    String temp = array[i];
		    array[i] = array[randomPosition];
		    array[randomPosition] = temp;
		}
 
		return array;
	}
	public static int[] RandomizeArrayInteger(int[] array){
		Random rgen = new Random();  // Random number generator			
 
		for (int i=0; i<array.length; i++) {
		    int randomPosition = rgen.nextInt(array.length);
		    int temp = array[i];
		    array[i] = array[randomPosition];
		    array[randomPosition] = temp;
		}
 
		return array;
	}
}
