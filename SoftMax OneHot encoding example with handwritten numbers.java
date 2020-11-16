import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Random;
import java.util.Scanner;

public class main {
	public static void main(String[] args) {
		//BufferedImage myPicture = new BufferedImage();
		// TODO Auto-generated method stub
		String filename = "train.csv";
		String stringArray[] = new String[785];
		File file = new File(filename);
		int count = 0;
		int tempVal = 0;
		int currentY = 0;
		int sample = 100;
		double weightDataL1[][] = new double[784][784];
		double weightDataL2[][] = new double[784][10];
		double biasL1[] =new double[784];
		double biasL2[] =new double[10];
		//double tempWeightSumL2[] = new double[10];
		double tempWeightSumL1 = 0.0;
		double tempSum = 0.0;
		Long numIter = 0L;
		int epoch = 0;
        double updateaiL2[] = new double[10];
		double updateaiL1[] = new double[784];
		boolean flag = true;
		//BufferedImage newImage = new BufferedImage(SMALL_SIZE, SMALL_SIZE2, BufferedImage.TYPE_INT_RGB);
		//Graphics g = newImage.createGraphics();
		double cost = 0.0;
		double cChange = 0.0;
		double cPrev = 0.0;
        //double xDataTrain[][] = new double[80][936];
        //double yDataTrain[] = new double[80];
        double alpha = 0.001;
        int tempi = 0;
        try{
            // read with Scanner class
            Scanner S = new Scanner(file);
            while (S.hasNextLine()){
            	String line = S.nextLine();
            	count++;
            }
            S.close();
        }catch (FileNotFoundException e){

            e.printStackTrace();
        }
        count = count;
        int randomArray[] = new int[count];
        double aiL1Array[][] = new double [count][784];
        double aiL2Array[][] = new double[count][10];
        double masterArray[][] = new double[count][785];
		double xDataOrig[][] = new double[count][784];
        double yDataOrig[][] = new double[count][10];
        try{
            Scanner S = new Scanner(file);
            while (S.hasNextLine()){
            	String line = S.nextLine();
            	stringArray = line.split(",");
            	for (int i = 0; i<785; i++) {
            		if (i==0) {
            			yDataOrig[currentY][Integer.parseInt(stringArray[i])]=1;
            		}
            		else {
            			xDataOrig[currentY][i-1]=Double.parseDouble(stringArray[i])/255.0;
            		}
            	}
            	currentY++;
            }
            S.close();	
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
		for (int j = 0; j<784; j ++) {
			System.out.print(xDataOrig[358][j] + ",");
		}
		System.out.println();
		for (int j = 0; j<784; j ++) {
			System.out.print(xDataOrig[359][j] + ",");
		}
		System.out.println();
		//initialize masterarray
		for (int n = 0; n<count;n++) {
			for (int m = 0;m<785;m++) {
				if (m ==0) {
					for (int p = 0; p <10; p++) {
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
		for (int j = 0; j<784;j++) {
        	for (int j2 = 0; j2<784; j2++) {
        		weightDataL1[j][j2] = -1 + Math.random()*2;
        	}
        	for (int j3 = 0; j3<10; j3++) {
        		//System.out.println(j);
        		weightDataL2[j][j3] = -1 + Math.random()*2;
        	}
        	biasL1[j]= -1 + Math.random()*2;
        }
		for (int j = 0; j<10; j++) {
			biasL2[j] = -1 + Math.random()*2;
		}
		for (int j = 0; j<count; j++) {
			randomArray[j]=j;
		}
		while(flag) {
			//shuffle masterArray
			double yData[][] = new double[count][10];
			double xData[][] = new double[count][784];
			RandomizeArrayInteger(randomArray);
			for (int n = 0; n<count;n++) {
				tempVal = randomArray[n];
				for (int m = 0;m<785;m++) {
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
				double tempWeightSumL2[] = new double[10];
		    	for (int j =0; j<784; j++) {
		    		tempWeightSumL1 = 0;
		    		for (int jprime = 0; jprime<784; jprime++) {
		    			tempWeightSumL1 += weightDataL1[jprime][j]*xData[i][jprime];
		    		}
		    		updateaiL1[j] = 1.0/(1+Math.exp(-tempWeightSumL1-biasL1[j]));
		    		aiL1Array[i][j] = updateaiL1[j];
		    		for (int k = 0; k<10; k++) {
		    			tempWeightSumL2[k] += weightDataL2[j][k]*aiL1Array[i][j];
		    		}
		    	}
		    	for (int t = 0; t<10; t++) {
		    		tempWeightSumL2[t] += biasL2[t];
		    	}
				updateaiL2 = softMax(tempWeightSumL2);
				//System.out.println(updateaiL2);
				//aiL2 is final layer outputs array[10]
				//need softMax function
				for (int y = 0; y<10; y++) {
					aiL2Array[i][y] = updateaiL2[y];
				}
				//calculate C
				for (int z = 0; z<10; z++) {
					cost+=(yData[i][z])*Math.log(aiL2Array[i][z]);
				}
	        	//System.out.println(cost);
				//update L2 Weights
				//difference between predicted and actual x activation previous layer
				double ohDif[] = new double[10];
				for (int b = 0;b<10;b++) {
					ohDif[b] = updateaiL2[b]-yData[i][b];
				}
				for (int j = 0; j<784;j++) {
					for (int b = 0;b<10;b++) {
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
				for (int b = 0;b<10;b++) {
					tempSum = updateaiL2[b]-yData[i][b];
					tempSum = alpha*tempSum;
					biasL2[b] = tempSum;
				}
//		    	tempSum =(updateaiL2-yData[i])*updateaiL2*(1-updateaiL2);
//		    	tempSum = alpha*tempSum;
//		    	biasL2 -= tempSum;
		    	//update L1 Weights
		    	for (int jprime = 0; jprime<784;jprime++) {
		    		for (int j = 0; j<784; j++) {
		    			tempSum = 0;
						for (int b = 0;b<10;b++) {
							tempSum += ohDif[b]*weightDataL2[j][b];
						}
						tempSum = tempSum*Sigmoid_derivative(updateaiL1[j])*xData[i][jprime];
		    			//tempSum=(updateaiL2-yData[i])*updateaiL2*(1-updateaiL2)*weightDataL2[j]*updateaiL1[j]*(1-updateaiL1[j])*xData[i][jprime];
		    			tempSum=alpha*tempSum;
			    		weightDataL1[jprime][j] -= tempSum;
		    		}
		        }
		    	//update L1 Bias
		    	for (int j = 0; j<784; j++) {
		    		tempSum = 0;
			    	for (int b = 0;b<10;b++) {
						tempSum += ohDif[b]*weightDataL2[j][b];
					}
		    		tempSum = tempSum*Sigmoid_derivative(updateaiL1[j]);
	    			//tempSum=(updateaiL2-yData[i])*updateaiL2*(1-updateaiL2)*weightDataL2[j]*updateaiL1[j]*(1-updateaiL1[j]);
	    			tempSum = alpha*tempSum;
			    	biasL1[j] -= tempSum;
		    	}
			}
			System.out.println(cost+" C Value");
	    	cChange = cost-cPrev;
	    	cPrev=cost;
	    	if (Math.abs(cost)<5){
	    		flag = false;
	    	}
	    	if (Math.abs(cost)<100){
	    		sample += 100;
	    		if (sample >= count) {
	    			sample = count;
	    		}
	    	}
	    	cost=0;
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
				for (int i = 0; i<784;i++) {
					for (int j = 0; j<784; j++) {
						writerL1.println(weightDataL1[i][j]);
					}
					for (int k = 0; k<10; k++) {
						writerL2.println(weightDataL2[i][k]);
					}
					biasL1F.println(biasL1[i]);
		    	}
		    	for (int i = 0; i<10; i++) {
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
			for (int i = 0; i<784;i++) {
				for (int j = 0; j<784; j++) {
					pL1.println(weightDataL1[i][j]);
				}
				for (int k = 0; k<10; k++) {
					pL2.println(weightDataL2[i][k]);
				}
				pB1.println(biasL1[i]);
	    	}
	    	for (int i = 0; i<10; i++) {
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
		double softMaxResult[] = new double[10];
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

