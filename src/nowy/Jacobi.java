package nowy;

public class Jacobi {
	
	Double[][] diagonal;
	Double[][] L;
	Double[][] U;
	Double[] newvector;
	Double[] vector;
	int iterations;
	double epsilon = 0.00000000000001;
	
	
	Double[][] add(Double[][] a, Double[][] b, int sign){
		Double[][] c = new Double[a.length][a.length];
		
		for(int x = 0; x < a.length; x++) {
			for(int y = 0; y < a.length; y++) {
				c[x][y] = a[x][y]+(sign*b[x][y]);
				if(c[x][y] == -0.0) c[x][y] = 0.0;
			}
		}
		return c;
	}
	
	
	Double[] add(Double[] a, Double[] b, int sign){
		Double[]c = new Double[a.length];
		for(int y = 0; y < a.length; y++) {
			c[y] = a[y]+(sign*b[y]);
			if(c[y] == -0.0) c[y] = 0.0;
		}
		return c;
	}
	Double[] multiply(Double[][] a, Double[] b) {
		Double[] c = new Double[a.length];
		for(int x = 0; x < a.length; x++) {
			c[x] = 0.0;
			for(int y = 0; y < b.length; y++) {
				
				c[x] += a[x][y] * b[y];

			}
		}
		return c;
	}
	Double[][] transpose(Double[][] a){
		Double[][] result = new Double[a.length][a.length];
		for(int i = 0; i < a.length; i++) {
			for(int j = 0; j < a.length; j ++) {
				result[i][j] = a[j][i];
			}
		}
		return result;
		
	}

	Double[][] multiply(Double[][] a, Double[][] b, int sign){
		Double[][] result = new Double[a.length][a.length];
		
		Double[] temp;
		for(int i = 0; i < a.length; i++) {
			temp = multiply(a,transpose(b)[i]);
			for(int j = 0 ; j < a.length; j++) {
				
				result[i][j] = temp[j]*sign;
			}
		}
		return transpose(result);
	}
	
	Double[][] minusDiag(Double[][] a) {
		//print(a);
		//
		Double[][] c = new Double[a.length][a.length];
		for(int x = 0; x < a.length; x++) {
			for(int y = 0; y < a.length; y++) {
				c[x][y] = 1/a[x][y];
				if(a[x][y] == 0) c[x][y] = 0.0;
			}
		}
		return c;
	}
	Double[][] findT() {
		Double[][] T = new Double[diagonal.length][diagonal.length];
		
		T = multiply(minusDiag(diagonal),add(U,L,1),-1);
	
		return T;
	}
	Double[] findC(){
		
		Double[] C = multiply(minusDiag(diagonal), vector);

		
		return C;
	}
	void calculate() {
		iterations = 0;
		double old_sum = 10;
		double sum = 1;
		
		newvector = multiply(findT(),newvector);
		newvector = add(findC(),newvector,1);

		while(old_sum/sum > 1+epsilon) {
			old_sum = sum();
			newvector = multiply(findT(),newvector);
			newvector = add(findC(),newvector,1);
			iterations++;
			sum = sum();
		}
		//print(newvector); 
		//System.out.println("Iterations "+iterations);
	}
	double sum() {
		double sum = 0;
		for(double a : newvector) {
			sum += Math.abs(a);
		}
		return sum;
	}
	
	void print(Double[][] a){
		for(int j=0; j < a.length; j++) {
			for(int i=0; i < a.length; i++) {
				System.out.print(a[j][i]+" ");
			}
			System.out.println();
		}
	}
	void print(Double[] a){
		//for(int j=0; j < a.length; j++) {
			for(int i=0; i < a.length; i++) {
				System.out.print(a[i]+" ");
			}
			System.out.println();
		//}
	}
	
	Jacobi(Double[][] matrix, Double[] vector){
		int n = vector.length;
		Double[] newvector = new Double[n];
		diagonal = new Double[n][n];
		L = new Double[n][n];
		U = new Double[n][n];
		
		this.vector = vector;
		this.newvector = newvector;
		
		
		for(int x = 0 ; x < n; x++) {
			newvector[x] = 1.0;
			for(int y = 0 ; y < n; y++) {
				if(y > x) {
					U[x][y] = matrix[x][y];
					L[x][y] = 0.0;
					diagonal[x][y] = 0.0;
				}
				if(y < x) {
					L[x][y] = matrix[x][y];
					U[x][y] = 0.0;
					diagonal[x][y] = 0.0;
				}
				if(x==y){
					diagonal[x][y] = matrix[x][y];
					L[x][y] = 0.0;
					U[x][y] = 0.0;
				}
			}
		}
//		System.out.println("Matrix");
//		print(matrix);
//		System.out.println("Vector");
//		print(vector);
//		System.out.println("nVector");
//		print(newvector);
//		System.out.println("D-1");
//		print(minusDiag(diagonal));
	}
}
