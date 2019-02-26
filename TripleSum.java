/* TripleSum.java
   CSC 225 - Fall 2016
   Assignment 1 - Template for TripleSum
   Lu Lu V00836042
   connex id: zoelu233

*/

import java.util.Scanner;
import java.util.Vector;
import java.util.Arrays;
import java.io.File;

public class TripleSum{
	/* TripleSum225()
		The input array A will contain non-negative integers. The function
		will search the input array A for three elements which sum to 225.
		If such a triple is found, return true. Otherwise, return false.
		The triple may contain the same element more than once.
		The function may modify the array A.

		Do not change the function signature (name/parameters).
	*/
	public static boolean TripleSum225(int[] A){
		/* 
			In the TripleSum method, create a empty int array judge which contains 0-225. 
			Loop through the array A we checked, for integers that ranged from 0-225, 
			marked 1 in the judge array. This is O(n). Then loop the judge array used nested 
			array, the index of judge represents the number between 0-225 in A. Loop through
			0-225 three times, which is O(225^3), that is O(1). If i=1,j=1,k=1, 
			means they exists in A and i+j+k=225,there is a triple sum and return true.
			Otherwise, there is no triple sum and return false. 
		
		*/
		
		int[] judge = new int[226];
		
		int size = A.length;
		for(int i=0; i<size; i++){
			if(A[i]<226){
				judge[A[i]] = 1;
			}
		}	

		for(int i=0;i<226;i++){
			for(int j=0;j<226;j++){
				for (int k=0;k<226;k++){
					if( judge[i]==1 && judge[j]==1 && judge[k]==1 && i+j+k==225){
						return true;
					}
				}
			}
		}
		return false;
		
	}
	


	/* main()
	   Contains code to test the TripleSum225 function. Nothing in this function
	   will be marked. You are free to change the provided code to test your
	   implementation, but only the contents of the TripleSum225() function above
	   will be considered during marking.
	*/
	public static void main(String[] args){
		Scanner s;
		if (args.length > 0){
			try{
				s = new Scanner(new File(args[0]));
			} catch(java.io.FileNotFoundException e){
				System.out.printf("Unable to open %s\n",args[0]);
				return;
			}
			System.out.printf("Reading input values from %s.\n",args[0]);
		}else{
			s = new Scanner(System.in);
			System.out.printf("Enter a list of non-negative integers. Enter a negative value to end the list.\n");
		}
		Vector<Integer> inputVector = new Vector<Integer>();

		int v;
		while(s.hasNextInt() && (v = s.nextInt()) >= 0)
			inputVector.add(v);

		int[] array = new int[inputVector.size()];

		for (int i = 0; i < array.length; i++)
			array[i] = inputVector.get(i);

		System.out.printf("Read %d values.\n",array.length);


		long startTime = System.currentTimeMillis();

		boolean tripleExists = TripleSum225(array);

		long endTime = System.currentTimeMillis();

		double totalTimeSeconds = (endTime-startTime)/1000.0;

		System.out.printf("Array %s a triple of values which add to 225.\n",tripleExists? "contains":"does not contain");
		System.out.printf("Total Time (seconds): %.2f\n",totalTimeSeconds);
	}
}
