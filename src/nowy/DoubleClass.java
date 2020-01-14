package nowy;


import java.math.BigInteger;
import java.util.Arrays;

public class DoubleClass implements Box<Double>{
	
	@Override
	public Double add(Double t,Double x) {
		return t+x;
	}
	@Override
	public Double set(int t) {
		return (double) t;
	}
	@Override
	public Double set(Double t) {
		return t;
	}
	@Override
	public Double init() {
		return 0.0;
	}
	@Override
	public void print(Double t) {
		System.out.print(t);	
	}
	@Override
	public Double subtract(Double t, Double x) {
		return t-x;
	}
	@Override
	public Double divide(Double t, Double x) {
		return t/x;
	}
	@Override
	public Double multiply(Double t, Double x) {
		return t*x;
	}
	@Override
	public Double abs(Double t) {
		return Math.abs(t);
	}
	@Override
	public Double[] newArray(int n) {
		Double[] result = new Double[n];
		for(int i=0;i<n;i++) {
			result[i] = init();
		}
		return result;
	}
	@Override
	public Double[][] new2Array(int n) {
		Double[][] result = new Double[n][n];
		for(int j=0;j<n;j++) {
			for(int i=0;i<n;i++) {
				result[i][j] = init();
			}
		}
		return result;
	}
	@Override
	public boolean compare(Double t, Double x) {
		if(abs(t) > abs(x))
			return true;
		else 
			return false;
	}	
	
}


