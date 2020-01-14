package nowy;


public class MojaMacierz<T> {
	Box<T> box;
	T oldmacierz[][];
	T macierzA[][]; // Randomowa macierz
	T oldwektor[];
	T wektorX[]; // Randomowy wektor
	T wektorB[];
	int[] var;
	int[] index = new int[2];
	int n = 30;

	
	MojaMacierz(int n, Box<T> box, Double[][] matrix, Double[] vectorY){
		this.box = box;
		this.n = n;
		macierzA = box.new2Array(n);
		oldmacierz = box.new2Array(n);
		wektorX = box.newArray(n);
		wektorB = box.newArray(n);
		oldwektor = box.newArray(n);
		var = new int[n];
		
		for(int i=0; i<n; i++) {
			var[i] = i;
		}
		
		for(int i=0; i < n; i++) {
			wektorX[i] = box.set(vectorY[i]);
			oldwektor[i] = wektorX[i];
			for(int j = 0 ; j < n; j++) {
				macierzA[i][j] = box.set(matrix[i][j]);
				oldmacierz[i][j] = macierzA[i][j];
			}
		}
	}
	
	MojaMacierz(int n, Box<T> box, int[][] random){
		this.box = box;
		this.n = n;
		macierzA = box.new2Array(n);
		oldmacierz = box.new2Array(n);
		wektorX = box.newArray(n);
		wektorB = box.newArray(n);
		oldwektor = box.newArray(n);
		var = new int[n];
		
		for(int i=0; i<n; i++) {
			var[i] = i;
		}
		
	}

	void getGaussMain(int value) {
		T multiplier;
		int j;
		boolean flag = false;
		//Petla po wierszach
		for(int i = 1+value ; i < n ; i++) {
			
			multiplier = box.divide(macierzA[i][value],macierzA[value][value]);
				if(multiplier.equals(0)) break;
			macierzA[i][value] = box.subtract(macierzA[i][value], box.multiply(multiplier,macierzA[value][value]));

			for(j = 1+value; j < n; j++) {
				macierzA[i][j] = box.subtract(macierzA[i][j],box.multiply(multiplier,macierzA[value][j]));
			}
			
			if(macierzA[i][i].equals(0) && i == value+1) {// multiplier*macierzA[value][--j]) {
				flag = true;
			}
			wektorX[i] = box.subtract(wektorX[i], box.multiply(multiplier,wektorX[value]));
			
			if (flag && i == n-1) { //Jesli ostatni to nie ma z czym zamieniac
				System.out.println("\\\\\nBRAK ROZWIAZANIA\n\\\\");
				flag = false;
				return;
			}
			else if(flag) {
				//System.out.println("switchRow dla "+i+" ; "+(i+1));
				switchRow(i,i+1);
				i--;
				flag = false;
			}
		}
		//Print.printresult(this);
		
	}
	void getGauss() {
		//Petla przesuwajaca kolumne o 1
		for(int value = 0; value < n-1 ; value++) {
			//System.out.println(value);
			index[1] = value;
			getGaussMain(value);
		}
	}
	void getGaussPart() {
		
		for(int value = 0; value < n-1 ; value++) {
			
			if(!box.set(findMax(value)).equals(macierzA[value][value]))  
				switchRow(value,findMax(value));
			getGaussMain(value);
		
		}
	}
	void getGaussFull() {
		
		for(int value = 0; value < n-1 ; value++) {
			//if(findFullMax(value)[0] != macierzA[value][value]) {
				switchRow(value,findFullMax(value)[0]);
				switchColumn(value, findFullMax(value)[1]);
			//}
			getGaussMain(value);
		}
		
	}
	int findMax(int column) {
		T max = box.init();
		index[0] = column;index[1] = column;
		for(int i = column; i < n; i++) {
			if(box.compare(macierzA[i][column], max)) {
				max = macierzA[i][column];
				index[0] = i;
			}
		}
		return index[0];
	}
	int[] findFullMax(int column) {
		T max = box.init();
		index[0] = column; index[1] = column;
		for(int i = column; i < n; i++)
			for(int j = column; j < n; j++) {
				if(box.compare(macierzA[i][j], max)) {
					max = macierzA[i][j];
					index[0] = i;
					index[1] = j;
				}
			}
		return index;
	}
	void switchRow(int source, int target) {
		if(source == target) return;

		T[] temp = box.newArray(n);
		//Zamiana lini z tym o wyzszym numerze
		for(int j = 0 ; j < n ; j++) {
			temp[j] = macierzA[source][j];
			macierzA[source][j] = macierzA[target][j];
			macierzA[target][j] = temp[j];
		}
		temp[0] = wektorX[source];
		wektorX[source] = wektorX[target];
		wektorX[target] = temp[0];
	}
	void switchColumn(int source, int target) {
		if(source == target) return;
		int tempo;
		
		tempo = var[source];
		var[source] = var[target];
		var[target] = tempo;
		
		T[] temp = box.newArray(n);
		//Zamiana lini z tym o wyzszym numerze  
		for(int j = 0 ; j < n ; j++) {
			temp[j] = macierzA[j][source];
			macierzA[j][source] = macierzA[j][target];
			macierzA[j][target] = temp[j];
		}

	}
	void getNewVector() {
		wektorB[n-1] = box.divide(wektorX[n-1], macierzA[n-1][n-1]); //Wartosc dolnego wektoraB
		for(int i = n-2; i >= 0; i--) {
			wektorB[i] = wektorX[i];
			for(int j = n-1; j > i; j-- ) {
				wektorB[i] = box.subtract(wektorB[i], box.multiply(macierzA[i][j],wektorB[j]));
			}
			wektorB[i] = box.divide(wektorB[i], macierzA[i][i]);	
		}	
	}
	
	T getVectorError() {
		T[] result = box.newArray(n);
		T suma = box.init();
		for(int i=0;i<n;i++) {
			for(int j=0; j<n;j++) {
				result[i] = box.add(result[i], box.multiply(oldmacierz[i][var[j]],wektorB[j]));
			}
			suma = box.add(suma, box.abs((box.subtract(oldwektor[i],result[i]))));
		}	
		return suma;
		
	}
	
	void printNewVector() {
		for(int i = 0; i < n; i++) {
			if(i%4==0) System.out.println();
			System.out.print("["+i+"] ");
			box.print(wektorB[i]);
			
		}
		System.out.println();
	}
	
	void printresult() {
		System.out.println();
		for(int i=0; i < n; i++) {
			for(int j = 0 ; j < n; j++) {
				System.out.print("["+i+","+j+"]");
				box.print(macierzA[i][j]);
			}
			System.out.print(" = ");
			box.print(wektorX[i]);
			System.out.println();
		}
	}

}
