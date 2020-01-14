package nowy;

import java.util.Arrays;

public class Seidel{
	int iterations;
	Double[] newVector;
	Double[] vector;// = {6.0,25.0,-11.0,15.0};
	Double[][] matrix;// = {{10.0,-1.0,2.0,0.0},{-1.0,11.0,-1.0,3.0},{2.0,-1.0,10.0,-1.0},{0.0,3.0,-1.0,8.0}};
	double epsilon = 0.0000000001;
	
	double sum() {
		double sum = 0;
		for(double a : newVector) {
			sum += Math.abs(a);
		}
		return sum;
	}
		Seidel(Double[][] matrixr, Double[] vectorY){
			this.vector = vectorY;
			this.matrix = matrixr;
			newVector = new Double[vector.length];
			Arrays.fill(newVector,0.0);
			
			
			iterations = 0;
			double old_sum = 10;
			double sum = 1;
			
			
			while(old_sum/sum > 1+epsilon || old_sum/sum < 1) {
				if(iterations > 0)
					old_sum = sum;
				for(int i = 0; i < vector.length; i++) {
					newVector[i] = calculateValue(matrix[i],vector[i], i);
					//newVector[i] = calculateValue(test[i], testv[i], i);
				}
				iterations++;
				sum = sum();
			}
//			for(int i = 0; i < vector.length; i++) {
//				if(i%4==0)
//					System.out.println();
//				//System.out.println(newVector[i]);
//			}
		}

Double calculateValue(Double[] vec, Double y, int iteration) {
	Double sum = y;

	for(int i = 0; i < vec.length; i++) {
		if(i!=iteration) {
			if(Math.abs(vec[i]) != 0.0 && Math.abs(newVector[i]) != 0.0) {
				sum += -1*vec[i]*newVector[i];
			}
		}
	}
	//System.out.println("/" + vec[iteration] +"= " + sum/vec[iteration]);
	return (sum /= vec[iteration]);
}
}
