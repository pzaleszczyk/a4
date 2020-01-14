package nowy;


public interface Box<T>{
	T add(T t,T x); 		//  +
	T subtract(T t,T x);	//  -
	T set(int t);				//  =
	T set(Double t);
	T init();				//  0
	T divide(T t, T x);		//  /
	T multiply(T t, T x);	//  *
	T abs(T t);				// |x|
	boolean compare(T t, T x);
	void print(T t);		//printf
	
	T[] newArray(int n);
	T[][] new2Array(int n);
}