/*
 * University of Victoria
 * CSC 225 - Fall 2016
 * Assignment 2 - Template for ChunkMergesort
 * 
 * This template includes some testing code to help verify the implementation.
 * To interactively provide test inputs, run the program with:
 * 
 *     java ChunkMergesort
 * 
 * To conveniently test the algorithm with a large input, create a text file
 * containing space-separated integer values and run the program with:
 * 
 *     java ChunkMergesort file.txt
 * 
 * where file.txt is replaced by the name of the text file.
 * 
 * Miguel Jimenez
 * 
 */
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Do not change the name of the ChunkMergesort class
public final class ChunkMergesort {
	
	/**
	 * Use this class to return two lists.
	 * 
	 * Example of use:
	 * 
	 * Chunks p = new Chunks(S1, S2); // where S1 and S2 are lists of integers
	 */
	public static final class Chunks {
		
		private final List<Integer> left;
		private final List<Integer> right;
		
		public Chunks(List<Integer> left, List<Integer> right) {
			this.left = left;
			this.right = right;
		}
		
		public List<Integer> left() {
			return this.left;
		}
		
		public List<Integer> right() {
			return this.right;
		}
	}

	/**
	 * The list containing the integer comparisons in order of occurrence. Use
	 * this list to persist the comparisons; the method report will be invoked
	 * to write a text file containing this information.
	 * 
	 * Example of use (when comparing 1 and 9):
	 * 
	 * Integer[] d = new Integer[2];
	 * d[0] = 1;
	 * d[1] = 9;
	 * this.comparisons.add(d);
	 * 
	 * or just:
	 * 
	 * this.comparisons.add(new Integer[]{1, 9});
	 */
	private final List<Integer[]> comparisons;

	public ChunkMergesort() {
		this.comparisons = new ArrayList<Integer[]>();
	}

	public List<Integer> chunkMergesort(List<Integer> S) {
		
		if(S.size()< 2){
			return S;
		}
		
		int num = 1;
		for(int i=0; i<S.size()-1; i++){
			if(S.get(i+1)<S.get(i)){
				this.comparisons.add(new Integer[]{S.get(i), S.get(i+1)});
				num++;
			}
		}
		if(num ==1){
			return S;
		}
		
		Chunks p = chunkDivide(S,num);
		
		List<Integer> S1 = chunkMergesort(p.left);
		List<Integer> S2 = chunkMergesort(p.right);
		
		S = merge(S1,S2);
		
		
		for(int i=0; i<S.size()-1; i++){
			if(S.get(i)>S.get(i+1)){
				throw new UnsupportedOperationException();
			}
		}
		return S;
		
	}

	public Chunks chunkDivide(List<Integer> S, int c) {
		
		List<Integer> S1 = new ArrayList<Integer>();
		List<Integer> S2 = new ArrayList<Integer>();
		
		int num1 = 1;
		int i =0;
		int num2 = 1;
		int j = 0;
		
		
		if(c%2 == 0){
			while(num1<=c/2){
				S1.add(S.get(i));
				if(S.get(i)>S.get(i+1)){
					this.comparisons.add(new Integer[]{S.get(i), S.get(i+1)});
					num1++;
				}
				i++;
			}
			while(i<S.size()){
				S2.add(S.get(i));
				i++;
			}		
		}
		
		if(c%2 ==1){
			while(num2<=c/2+1){
				S1.add(S.get(j));
				if(S.get(j)>S.get(j+1)){
				this.comparisons.add(new Integer[]{S.get(j), S.get(j+1)});
					num2++;
				}
				j++;
			}
			while(j<S.size()){
				S2.add(S.get(j));
				j++;
			}		
		}		
		
		
		Chunks p = new Chunks(S1, S2);
	
		int num3 =1;
		int num4 =1;
		for(int k =0; k<S1.size()-1; k++){
			if(S1.get(k+1)<S1.get(k)){
				num3++;
			}
		}
		for(int k =0; k<S2.size()-1; k++){
			if(S2.get(k+1)<S2.get(k)){
				num4++;
			}
		}
		if(num3-num4>1){
			throw new UnsupportedOperationException();
		}
		
		return p;
	}

	public List<Integer> merge(List<Integer> S1, List<Integer> S2) {
		
		List<Integer> newS = new ArrayList<Integer>() ;
		
		while(!S1.isEmpty() && !S2.isEmpty()){
			if(S1.get(0)<S2.get(0)){
				this.comparisons.add(new Integer[]{S1.get(0), S2.get(0)});
				newS.add(S1.get(0));
				S1.remove(S1.get(0));
			}else if(S1.get(0)>S2.get(0)){
				this.comparisons.add(new Integer[]{S1.get(0), S2.get(0)});
				newS.add(S2.get(0));
				S2.remove(S2.get(0));
			}else{
				newS.add(S1.get(0));
				newS.add(S2.get(0));
				S1.remove(S1.get(0));
				S2.remove(S2.get(0));
			}
		}
		
		while(!S1.isEmpty()){
			newS.add(S1.get(0));
			S1.remove(S1.get(0));
		}
		while(!S2.isEmpty()){
			newS.add(S2.get(0));
			S2.remove(S2.get(0));
		}
				
		
		if(!S1.isEmpty() || !S2.isEmpty()){
			throw new UnsupportedOperationException();
		}
		
		return newS;
	}

	/**
	 * Writes a text file containing all the integer comparisons in order of
	 * ocurrence.
	 * 
	 * @throws IOException
	 *             If an I/O error occurs
	 */
	public void report() throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter("comparisons.txt", false));
		for (Integer[] data : this.comparisons)
			writer.append(data[0] + " " + data[1] + "\n");
		writer.close();
	}

	/**
	 * Contains code to test the chunkMergesort method. Nothing in this method
	 * will be marked. You are free to change the provided code to test your
	 * implementation, but only the contents of the methods above will be
	 * considered during marking.
	 */
	public static void main(String[] args) {
		Scanner s;
		if (args.length > 0) {
			try {
				s = new Scanner(new File(args[0]));
			} catch (java.io.FileNotFoundException e) {
				System.out.printf("Unable to open %s\n", args[0]);
				return;
			}
			System.out.printf("Reading input values from %s.\n", args[0]);
		} else {
			s = new Scanner(System.in);
			System.out.printf("Enter a list of integers:\n");
		}
		List<Integer> inputList = new ArrayList<Integer>();

		int v;
		while (s.hasNextInt() && (v = s.nextInt()) >= 0)
			inputList.add(v);

		s.close();
		System.out.printf("Read %d values.\n", inputList.size());

		long startTime = System.currentTimeMillis();

		ChunkMergesort mergesort = new ChunkMergesort();
		List<Integer> sorted = mergesort.chunkMergesort(inputList);
		
		for(int i= 0; i<sorted.size(); i++){
			System.out.println(sorted.get(i) +" ");
		}
		
		long endTime = System.currentTimeMillis();
		double totalTimeSeconds = (endTime - startTime) / 1000.0;

		System.out.printf("Total Time (seconds): %.2f\n", totalTimeSeconds);
		
		try {
			mergesort.report();
			System.out.println("Report written to comparisons.txt");
		} catch (IOException e) {
			System.out.println("Unable to write file comparisons.txt");
			return;
		}
	}

}
