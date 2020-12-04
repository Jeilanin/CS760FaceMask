import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Random;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class main {
	private static final int SMALL_SIZE = 20;
	private static final int SMALL_SIZE2 = 20;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int count = 0;
		int tempVal = 0;
		int currentY = 0;
		int sample = 500;
		int totalpix=SMALL_SIZE*SMALL_SIZE2;
		int data = totalpix;
		int totalpic = 1700;
		double weightDataL1[][] = new double[data][data];
		double weightDataL2[][] = new double[data][5];
		double biasL1[] =new double[data];
		double biasL2[] =new double[5];
		//double tempWeightSumL2[] = new double[10];
		double tempWeightSumL1 = 0.0;
		double tempSum = 0.0;
		Long numIter = 0L;
		int epoch = 0;
        double updateaiL2[] = new double[5];
		double updateaiL1[] = new double[data];
		boolean flag = true;
		//BufferedImage newImage = new BufferedImage(SMALL_SIZE, SMALL_SIZE2, BufferedImage.TYPE_INT_RGB);
		//Graphics g = newImage.createGraphics();
		double cost = 0.0;
		double cChange = 0.0;
		double cPrev = 0.0;
        //double xDataTrain[][] = new double[80][936];
        //double yDataTrain[] = new double[80];
        double alpha = 0.005;
        int tempi = 0;
        int randomArray[] = new int[totalpic];
        double aiL1Array[][] = new double [totalpic][data];
        double aiL2Array[][] = new double[totalpic][5];
        double masterArray[][] = new double[totalpic][data+1];
		double xDataOrig[][] = new double[totalpic][data];
        double yDataOrig[][] = new double[totalpic][5];
        for (int i = 1; i <=500; i++) {
			BufferedImage newImage = new BufferedImage(SMALL_SIZE, SMALL_SIZE2, BufferedImage.TYPE_INT_RGB);
			Graphics g = newImage.createGraphics();
			String imagePath = "C:\\Users\\jiegu\\eclipse-workspace\\ML760FaceMask\\Correct Mask Fixed\\No Mask\\nomask" + i + ".jpg";
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
					yDataOrig[tempi][0]=1;
				}
			}
			g.dispose();
		}
		for (int i = 1; i <=500; i++) {
			BufferedImage newImage = new BufferedImage(SMALL_SIZE, SMALL_SIZE2, BufferedImage.TYPE_INT_RGB);
			Graphics g = newImage.createGraphics();
			String imagePath = "C:\\Users\\jiegu\\eclipse-workspace\\ML760FaceMask\\Correct Mask Fixed\\Good\\mask" + i + ".jpg";
			try {
				BufferedImage myPicture = ImageIO.read(new File(imagePath));
				g.drawImage(myPicture, 0, 0, SMALL_SIZE, SMALL_SIZE2, null);
			} catch (IOException e) {
				System.out.println("Picture: "+i);
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
					tempi = i+499;
					xDataOrig[tempi][index]=(double)greyscale/255;
					//xDataOrig[tempi][index]=red;
					//xDataOrig[tempi][index+totalpix]=green;
					//xDataOrig[tempi][index+(2*totalpix)]=blue;
					yDataOrig[tempi][1]=1;
				}
			}
			g.dispose();
		}
		for (int i = 1; i <=100; i++) {
			BufferedImage newImage = new BufferedImage(SMALL_SIZE, SMALL_SIZE2, BufferedImage.TYPE_INT_RGB);
			Graphics g = newImage.createGraphics();
			String imagePath = "C:\\Users\\jiegu\\eclipse-workspace\\ML760FaceMask\\Correct Mask Fixed\\Bad Nose Uncovered\\badnose" + i + ".jpg";
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
					tempi = i+999;
					xDataOrig[tempi][index]=(double)greyscale/255;
					//xDataOrig[tempi][index]=red;
					//xDataOrig[tempi][index+totalpix]=green;
					//xDataOrig[tempi][index+(2*totalpix)]=blue;
					yDataOrig[tempi][2]=1;
				}
			}
			g.dispose();
		}
		for (int i = 1; i <=400; i++) {
			BufferedImage newImage = new BufferedImage(SMALL_SIZE, SMALL_SIZE2, BufferedImage.TYPE_INT_RGB);
			Graphics g = newImage.createGraphics();
			String imagePath = "C:\\Users\\jiegu\\eclipse-workspace\\ML760FaceMask\\Correct Mask Fixed\\CGI Bad Nose Uncovered\\badnose" + i + ".jpg";
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
					tempi = i+1099;
					xDataOrig[tempi][index]=(double)greyscale/255;
					//xDataOrig[tempi][index]=red;
					//xDataOrig[tempi][index+totalpix]=green;
					//xDataOrig[tempi][index+(2*totalpix)]=blue;
					yDataOrig[tempi][2]=1;
				}
			}
			g.dispose();
		}
		for (int i = 1; i <=100; i++) {
			BufferedImage newImage = new BufferedImage(SMALL_SIZE, SMALL_SIZE2, BufferedImage.TYPE_INT_RGB);
			Graphics g = newImage.createGraphics();
			String imagePath = "C:\\Users\\jiegu\\eclipse-workspace\\ML760FaceMask\\Correct Mask Fixed\\CGI Bad Nose and Mouth Uncovered\\badnosemouth" + i + ".jpg";
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
					tempi = i+1499;
					xDataOrig[tempi][index]=(double)greyscale/255;
					//xDataOrig[tempi][index]=red;
					//xDataOrig[tempi][index+totalpix]=green;
					//xDataOrig[tempi][index+(2*totalpix)]=blue;
					yDataOrig[tempi][3]=1;
				}
			}
			g.dispose();
		}
		for (int i = 1; i <=100; i++) {
			BufferedImage newImage = new BufferedImage(SMALL_SIZE, SMALL_SIZE2, BufferedImage.TYPE_INT_RGB);
			Graphics g = newImage.createGraphics();
			String imagePath = "C:\\Users\\jiegu\\eclipse-workspace\\ML760FaceMask\\Correct Mask Fixed\\CGI Bad Chin Uncovered\\badchin" + i + ".jpg";
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
					tempi = i+1599;
					xDataOrig[tempi][index]=(double)greyscale/255;
					//xDataOrig[tempi][index]=red;
					//xDataOrig[tempi][index+totalpix]=green;
					//xDataOrig[tempi][index+(2*totalpix)]=blue;
					yDataOrig[tempi][4]=1;
				}
			}
			g.dispose();
		}
		//initialize masterarray
		for (int n = 0; n<totalpic;n++) {
			for (int m = 0;m<data+1;m++) {
				if (m == 0) {
					for (int p = 0; p < 5; p++) {
						if (yDataOrig[n][p] == 1) {
							masterArray[n][m] = p;
						}
					}
				}
				else {
					masterArray[n][m] = xDataOrig[n][m-1];
				}
			}
		}
		for (int j = 0; j<data;j++) {
        	for (int j2 = 0; j2<data; j2++) {
        		weightDataL1[j][j2] = -1 + Math.random()*2;
        	}
        	for (int j3 = 0; j3<5; j3++) {
        		//System.out.println(j);
        		weightDataL2[j][j3] = -1 + Math.random()*2;
        	}
        	biasL1[j]= -1 + Math.random()*2;
        }
		for (int j = 0; j<5; j++) {
			biasL2[j] = -1 + Math.random()*2;
		}
		for (int j = 0; j<totalpic; j++) {
			randomArray[j]=j;
		}
		while(flag) {
			//shuffle masterArray
			double yData[][] = new double[totalpic][5];
			double xData[][] = new double[totalpic][data];
			RandomizeArrayInteger(randomArray);
			for (int n = 0; n<totalpic;n++) {
				tempVal = randomArray[n];
				for (int m = 0;m<data+1;m++) {
					if (m ==0) {
						int y = (int)masterArray[tempVal][m];
						yData[n][y]= 1;
					}
					else {
						xData[n][m-1] = masterArray[tempVal][m];
					}
				}
			}
			//Calculate Ai
			epoch ++;
			for (int i = 0; i<sample;i++) {
			//for (int i = 0; i<100;i++) {
				//Forward Prop
				numIter ++;
				double tempWeightSumL2[] = new double[5];
		    	for (int j =0; j<data; j++) {
		    		tempWeightSumL1 = 0;
		    		for (int jprime = 0; jprime<data; jprime++) {
		    			tempWeightSumL1 += weightDataL1[jprime][j]*xData[i][jprime];
		    		}
		    		updateaiL1[j] = 1.0/(1+Math.exp(-tempWeightSumL1-biasL1[j]));
		    		aiL1Array[i][j] = updateaiL1[j];
		    		for (int k = 0; k<5; k++) {
		    			tempWeightSumL2[k] += weightDataL2[j][k]*aiL1Array[i][j];
		    		}
		    	}
		    	for (int t = 0; t<5; t++) {
		    		tempWeightSumL2[t] += biasL2[t];
		    	}
				updateaiL2 = softMax(tempWeightSumL2);
				//System.out.println(updateaiL2);
				//aiL2 is final layer outputs array[10]
				//need softMax function
				for (int y = 0; y<5; y++) {
					aiL2Array[i][y] = updateaiL2[y];
				}
				//calculate C
				for (int z = 0; z<5; z++) {
					if(yData[i][z]!=0) {
						if(Math.abs(aiL2Array[i][z])>(Math.pow(10, -4))) {
							cost+=(yData[i][z])*Math.log(aiL2Array[i][z]);
		        		}
						else {
							cost+=1;
						}
					}
				}
	        	//System.out.println(cost);
				//update L2 Weights
				//difference between predicted and actual x activation previous layer
				double ohDif[] = new double[5];
				for (int b = 0;b<5;b++) {
					ohDif[b] = updateaiL2[b]-yData[i][b];
				}
				for (int j = 0; j<data;j++) {
					for (int b = 0;b<5;b++) {
						tempSum=updateaiL1[j]*ohDif[b];
						tempSum=alpha*tempSum;
						weightDataL2[j][b] -= tempSum;
					}
//					tempSum=updateaiL1[j]*ohDif[b];
//		    		tempSum=(updateaiL2-yData[i])*updateaiL2*(1-updateaiL2)*updateaiL1[j];
//		    		tempSum=alpha*tempSum;
//		    		//if (tempSum !=0){System.out.println(tempSum);}
//		    		weightDataL2[j][10] -= tempSum;
		        }
				//update L2 Bias
				for (int b = 0;b<5;b++) {
					tempSum = updateaiL2[b]-yData[i][b];
					tempSum = alpha*tempSum;
					biasL2[b] = tempSum;
				}
//		    	tempSum =(updateaiL2-yData[i])*updateaiL2*(1-updateaiL2);
//		    	tempSum = alpha*tempSum;
//		    	biasL2 -= tempSum;
		    	//update L1 Weights
		    	for (int jprime = 0; jprime<data;jprime++) {
		    		for (int j = 0; j<data; j++) {
		    			tempSum = 0;
						for (int b = 0;b<5;b++) {
							tempSum += ohDif[b]*weightDataL2[j][b];
						}
						tempSum = tempSum*Sigmoid_derivative(updateaiL1[j])*xData[i][jprime];
		    			//tempSum=(updateaiL2-yData[i])*updateaiL2*(1-updateaiL2)*weightDataL2[j]*updateaiL1[j]*(1-updateaiL1[j])*xData[i][jprime];
		    			tempSum=alpha*tempSum;
			    		weightDataL1[jprime][j] -= tempSum;
		    		}
		        }
		    	//update L1 Bias
		    	for (int j = 0; j<data; j++) {
		    		tempSum = 0;
			    	for (int b = 0;b<5;b++) {
						tempSum += ohDif[b]*weightDataL2[j][b];
					}
		    		tempSum = tempSum*Sigmoid_derivative(updateaiL1[j]);
	    			//tempSum=(updateaiL2-yData[i])*updateaiL2*(1-updateaiL2)*weightDataL2[j]*updateaiL1[j]*(1-updateaiL1[j]);
	    			tempSum = alpha*tempSum;
			    	biasL1[j] -= tempSum;
		    	}
			}
			System.out.println("Epoch: "+epoch+" Cost: " +cost + " Samples: " + sample);
	    	cChange = cost-cPrev;
	    	cPrev=cost;
	    	if (Math.abs(cost)<5){
	    		flag = false;
	    	}
	    	if (Math.abs(cost)<(sample/2)){
	    		sample += 100;
	    		if (sample >= totalpic) {
	    			sample = totalpic;
	    		}
	    	}
	    	cost=0;
	    	if (epoch%30==0) {
	    		if(alpha>0.0001) {
	    			alpha=alpha-0.000001;
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
					for (int i = 0; i<data;i++) {
						for (int j = 0; j<data; j++) {
							writerL1.println(weightDataL1[i][j]);
						}
						for (int k = 0; k<5; k++) {
							writerL2.println(weightDataL2[i][k]);
						}
						biasL1F.println(biasL1[i]);
			    	}
			    	for (int i = 0; i<5; i++) {
			    		biasL2F.println(biasL2[i]);
			    	}
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					writerL1.close();
					writerL2.close();
					biasL1F.close();
					biasL2F.close();
				}
	    	}
		}
		File wL1 = null;
		PrintStream pL2 = null;
		PrintStream pL1 = null;
		PrintStream pB1 = null;
		PrintStream pB2 = null;
		wL1 = new File("weightL1Final.txt");
		File wL2 = null;
		wL2 = new File("weightL2Final.txt");
		File bL1 = null;
		File bL2 = null;
		bL1 = new File("biasL1Final.txt");
		bL2 = new File("biasL2Final.txt");
		try {
			pL2 = new PrintStream(wL2);
			pL1 = new PrintStream(wL1);
			pB1 = new PrintStream(bL1);
			pB2 = new PrintStream(bL2);
			for (int i = 0; i<data;i++) {
				for (int j = 0; j<data; j++) {
					pL1.println(weightDataL1[i][j]);
				}
				for (int k = 0; k<5; k++) {
					pL2.println(weightDataL2[i][k]);
				}
				pB1.println(biasL1[i]);
	    	}
	    	for (int i = 0; i<5; i++) {
	    		pB2.println(biasL2[i]);
	    	}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			pL1.close();
			pL2.close();
			pB1.close();
			pB2.close();
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