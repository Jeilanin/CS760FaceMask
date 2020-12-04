import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;

import javax.imageio.ImageIO;
public class main {
	private static final int SMALL_SIZE = 100;
	private static final int SMALL_SIZE2 = 100;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Integer count = 0;
		Integer numIter = 0;
		boolean flag = true;
		double tempSum = 0.0;
		double tempWeightSum = 0.0;
		double bais = 0.0;
		double cost = 0.0;
		double cprev = 0.0;
		double ai = 0.0;
		double alpha = 0.005;
		double cChange = 0.0;
		int totalpix=SMALL_SIZE*SMALL_SIZE2;
		int data = 2*totalpix;
		int totalpic = 1000;
		double xDataOrig[][] = new double[totalpic][data];
        double yDataOrig[] = new double[totalpic];
        double aiArray[] = new double [totalpic];
        double weightData[] = new double[data];
		int tempi = 0;
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
					//xDataOrig[tempi][index]=red/255;
					//xDataOrig[tempi][index+totalpix]=green/255;
					xDataOrig[tempi][index+totalpix]=blue/255;
					yDataOrig[tempi]=0;
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
					//xDataOrig[tempi][index]=red/255;
					//xDataOrig[tempi][index+totalpix]=green/255;
					//xDataOrig[tempi][index+(2*totalpix)]=blue/255;
					xDataOrig[tempi][index+totalpix]=blue/255;
					yDataOrig[tempi]=1;
				}
			}
			g.dispose();
		}
        //Initialize Weight
        for (int i = 0; i<data;i++) {
        	weightData[i] = Math.random();
        }
        //Training
        while(flag) {
	        for (int i = 0; i<totalpic;i++) {
	        	//System.out.println(yData[i]);
	        	//calculate ai
	        	tempWeightSum = 0;
	        	for (int j = 0; j<data; j++) {
	        		tempWeightSum += weightData[j]*xDataOrig[i][j];
	        	}
	        	//System.out.println(tempWeightSum+" "+ numIter);
	        	ai = 1.0/(1+Math.exp(-tempWeightSum-bais));
	        	//System.out.println(ai);
	        	aiArray[i] = ai;
	        	//store all ai
	        }
	    	//update weights
    		numIter++;
	    	for (int j = 0; j<data;j++) {
	    		tempSum = 0;
	    		for (int i = 0; i<totalpic;i++) {
	    			if (yDataOrig[i]==0) {
	    				tempSum+=aiArray[i]*xDataOrig[i][j];
	    			}
	    			else {
	    				tempSum+=(aiArray[i]-1)*xDataOrig[i][j];
	    			}
	    		}
	    		tempSum=alpha*tempSum;
	    		weightData[j] -= tempSum;
	        }
	    	tempSum = 0;
	    	//calculate C and Bias
	    	for (int i = 0; i<totalpic;i++) {
				if (yDataOrig[i]==0) {
					tempSum+=aiArray[i];
					if(aiArray[i]<(1-Math.pow(10, -4))) {
	        			cost-=Math.log(1-aiArray[i]);
	        		}
					else {
	        			cost+=1;
	        		}
				}
				else {
					tempSum+=(aiArray[i]-1);
					if(aiArray[i]>Math.pow(10, -4)) {
		        		cost-=Math.log(aiArray[i]);
		        	}
					else {
	        			cost+=1;
	        		}
				}
			}
	    	tempSum = alpha*tempSum;
	    	bais -= tempSum;
	    	System.out.println(cost+" C Value - "+numIter);
	    	//System.out.println(cprev+"test2");
	    	//System.out.println(cChange+"test3");
	    	//System.out.println(weightData[300]);
	    	//if (Math.abs(cChange)<=0.0001 || numIter >10000){
	    	if (Math.abs(cost)<=5 || numIter >10000000){
	    		flag = false;
	    	}
	    	cChange = cost-cprev;
	    	cprev=cost;
	    	cost=0;
	    	if (numIter%100==0) {
	    		if(alpha>0.0001) {
	    			alpha=alpha-0.000001;
	    		}
		    	File weightFile = null;
				PrintStream writer = null;
				weightFile = new File("weight.txt");
				try {
					writer = new PrintStream(weightFile);
					writer.println(bais);
					for (int i = 0; i<data;i++) {
						writer.println(weightData[i]);
					}
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					writer.close();
				}
	    	}
		}
		File weight2 = null;
		PrintStream wght2 = null;
		weight2 = new File("weightFinal.txt");
		try {
			wght2 = new PrintStream(weight2);
			wght2.println(bais);
			for (int i = 0; i<data;i++) {
				wght2.println(weightData[i]);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			wght2.close();
		}
	}
}
