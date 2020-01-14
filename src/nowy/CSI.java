package nowy;

import java.io.IOException;

import org.json.simple.parser.ParseException;

public class CSI {
	double[][] matrix;
	double[] vY;
	double[] vX;
	double[] vMi;
	double[] vLambda;
	double[] vDelta;
	double[] vH;
	double[] vM;

	int n;
	CSI(double[] vY, double[] vX){

		n = vY.length;
		System.out.println("N size "+n);
		this.vY = vY;
		this.vX = vX;
		matrix = new double[n][n];
		vMi = new double[n];
		vLambda = new double[n];
		vDelta = new double[n];
		vH = new double[n];
		vM = new double[n];
	}
	
	void newv(Double[] a) {
		for(int i = 0 ; i < n ; i ++) {
			vM[i] = a[i];
		}
	}
	Double[][] doublecast() {
		Double[][] result = new Double[n][n];
		for(int i = 0; i < vY.length; i ++) {
			for(int j = 0; j < vY.length; j ++) {
				result[i][j] = matrix[i][j];
			}
		}
		return result;
	}
	Double[] doublecastvector() {
		Double[] result = new Double[n];
		for(int i = 0; i < vY.length; i ++) {
				result[i] = vDelta[i];
		}
		return result;
	}
	void calculateDelta(int i){
		double a = (6/(vH[i]+vH[i+1]));
		double b = ((vY[i+1]-vY[i])/vH[i+1]);
		double c = ((vY[i]-vY[i-1])/vH[i]);
		vDelta[i] = a*(b-c);
	}
	double findA(int i) {return vY[i];}
	double findB(int i) {
		double result = ((vY[i+1]-vY[i])/vH[i+1]);
		result -= ((2*vM[i]+vM[i+1])/6)*vH[i+1];
		return result;
	}
	double findC(int i) {return vM[i]/2;}
	double findD(int i) { return (vM[i+1]-vM[i])/6*vH[i+1];}
	void calculateLambda(int i) {vLambda[i] = vH[i+1]/(vH[i]+vH[i+1]);}
	void calculateH(int i) {vH[i+1] = vX[i+1]-vX[i];}
	void calculateMi(int i) {	vMi[i] = vH[i]/(vH[i]+vH[i+1]);}
	
	void calculate() {
		for(int i = 0; i < vY.length-1; i ++) {
			calculateH(i);
		}
		for(int i = 1; i < vY.length-1; i ++) {
			calculateDelta(i);
			calculateMi(i);
			calculateLambda(i);
		}
		creatematrix();
	}
	void printv(double[] a) {
		for(int i = 0; i < a.length ; i++) {
			System.out.println(":"+a[i]);
		}
	}
	void creatematrix() {
		for(int y = 0 ; y < vY.length; y ++) {
			for(int x = 0 ; x < vY.length; x ++) {
				if(y==x)
					matrix[x][y] = 2.0;
				if(x==y+1)
					matrix[x][y] = vLambda[y];
				if(x+1==y)
					matrix[x][y] = vMi[y];
			}
		}
		matrix[0][0] = 1.0;
		matrix[n-1][n-1] = 1.0;
		transpose();
		for(int y = 0 ; y < vY.length; y ++) {
			for(int x = 0 ; x < vY.length; x ++) {
				System.out.print(matrix[y][x]+", ");
			}
			System.out.println();
		}
	}
	
	void transpose() {
		double[][] result = new double[n][n];
		double temp;
		for(int i = 0 ; i < n; i ++) {
			for(int j = 0 ; j < n; j ++) {				
				result[i][j] = matrix[j][i];
				result[j][i] = matrix[i][j];
				
			}
		}
		matrix = result;
	}
	
	int getIndexX(double value) {
		for(int i = 1 ;i < vX.length; i++){
			if(value <= vX[i] && value > vX[i-1])
				return i-1;
		}
		return -1;
	}
	
	double S(double x) {
	    int i = getIndexX(x);
	    if (i == -1) 
	    	return 0;
	    //System.out.println("Using "+i);
	    return findA(i) + findB(i) * (x - vX[i]) +
	           findC(i) * (x - vX[i]) * (x - vX[i]) +
	           findD(i) * (x - vX[i]) * (x - vX[i]) * (x - vX[i]);
	}
//	double Sd(double x) {
//		 int i = getIndexX(x);
//		    System.out.println("Using "+i);
//		    return findB(i) +
//		           2*findC(i) * (x - vX[i]) +
//		           3*findD(i) * (x - vX[i]) * (x - vX[i]);
//	}
//	double Sdd(double x) {
//		 int i = getIndexX(x);
//		    System.out.println("Using "+i);
//		    return 
//		           2*findC(i) +
//		           6*findD(i) * (x - vX[i]) ;
//	}
//	double Sddd(double x) {
//		int i = getIndexX(x);
//	    System.out.println("Using "+i);
//	    return 
//	           6*findD(i) ;
//	}
	static double[] split(int skip, double[] array) {
		
		double[] result = new double[array.length/skip];
		for(int i = 0 ; i < array.length/skip ; i ++) {
			result[i] = array[skip*i];
		}
		return result;
	}
	
	static void compareNewValues(int skip, double[] array, double[] cutarray) {
		
		
		
	}
	public static void main(String args[]) {
		int amount = 5;
		int skip = 5;
		//Wczytanie pliku
		JsonParser json = new JsonParser("3220309");
		try {
			//Znajdujemy punkty elevation/distance
			json.findPoints(amount*skip);
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		//json.distance i json.elevation - arraye do testow
		double[] x = json.distance;
		double[] y = json.elevation;
		//x i y arraye do algorytmu
		x = split(skip,x);
		y = split(skip,y);
		for(int i = 0 ; i < y.length; i ++) {
			System.out.println("JsonNew : "+x[i]+" "+y[i]);
		}
		
		//double[] x = {0.0,1.0,2.0,3.0};
		//double[] y = {0.0,0.5,2.0,1.5};
		//double[] b = {21.0,24.0,24.0,18.0,16.0};
		
		CSI mat = new CSI(y,x);
		mat.calculate();
//		System.out.println("Y");
//		mat.printv(mat.vY);
//		System.out.println("X");
//		mat.printv(mat.vX);
//		System.out.println("H");
//		mat.printv(mat.vH);
//		System.out.println("Lambda");
//		mat.printv(mat.vLambda);
//		System.out.println("Delta");
//		mat.printv(mat.vDelta);
//		System.out.println("Micro");
//		mat.printv(mat.vMi);
//		System.out.println("LIST");
//		for(int i = 0 ; i < mat.n-1; i ++) {
//			System.out.println(mat.findA(i)+" "+mat.findB(i)+" "+mat.findC(i)+" "+mat.findD(i));
//		}
		
		
//		//Gauss
		MojaMacierz<Double> T = new MojaMacierz<Double>(mat.vY.length, new DoubleClass(), mat.doublecast(), mat.doublecastvector());
		T.getGaussPart();
		T.getNewVector();
		mat.newv(T.wektorB);
		System.out.println("Gauss: "+mat.S(1505));
		
		//Jacobi
		Jacobi js = new Jacobi(mat.doublecast(), mat.doublecastvector());
		js.calculate();
		mat.newv(js.newvector);
		System.out.println("Jacob: "+mat.S(1505));
		
		//Seidel
		Seidel sei = new Seidel(mat.doublecast(), mat.doublecastvector());
		mat.newv(sei.newVector);
		System.out.println("Seidl: "+mat.S(1505));
		//System.out.println("RESULT: "+mat.S(0.96)+" "+mat.Sd(1.44)+" "+mat.Sdd(1.44)+mat.Sddd(1.44));
		
	}
}
