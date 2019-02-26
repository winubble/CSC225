import java.util.Random;
import java.io.File;
import java.io.PrintWriter;
import java.io.FileNotFoundException;

public class PQ225{
	public int[] heapArray;
	public int count;
	public int comparison;
	
	public PQ225(){
		heapArray = new int[100000];
		count = 0;
		comparison = 0;
	}
	
	//double the size if the array is full
	public void resize(){
		int[] newArr = new int[2*heapArray.length];
		for(int p =0; p<heapArray.length;p++){
			newArr[p]=heapArray[p];
		}
		heapArray = newArr;
	}
		
	public void ranGen(int N,int LOW,int HIGH){
		long seed = System.currentTimeMillis();
		Random ran = new Random(seed);
		int k = 0; 
		int rn;
		while(k<N){
			do{
				rn = ran.nextInt();
			}while(rn<LOW || rn>HIGH);
			count++;
			if(count>=heapArray.length){
				resize();
			}
			heapArray[k] = rn;
			k++;
		}
	}
	public int size(){
		return count;
	}
	
	public void insert(int i){
		if(count>=heapArray.length){
			resize();
		}
		heapArray[count] = i;
		count++;
		int pos = count;
		if(pos>1){
			while(i<heapArray[pos/2-1]){
				int temp = heapArray[pos/2-1];
				heapArray[pos/2-1] = i;
				heapArray[pos-1] = temp;
				pos = pos/2;
				if(pos<=1){
					break;
				}
			}
			
		}
	}
	
	public int deleteMin(){
		int num = 0;
		int length = count;
		if(length!=0){
			num = heapArray[0];
			heapArray[0]=heapArray[length-1];
			count--;
			heapArray = heapRebuild(heapArray, 1,heapArray[0]);
		}
		int[] temp = new int[heapArray.length];
		for(int i=0; i<count; i++){
			temp[i] = heapArray[i];
		}
		heapArray = temp;
		return num;
	}
	
	//Helper method for deleteMin
	public int[] heapRebuild(int[] arr, int pos, int num){
		if(2*pos>count){
			return arr;
		}else{
			if(2*pos+1>count){
				if(num>arr[2*pos-1]){
					arr[pos-1]=arr[2*pos-1];
					arr[2*pos-1]=num;
					return arr;
				}else{
					return arr;
				}
			}else{
				if(num>arr[2*pos-1]||num>arr[2*pos]){
					if(arr[2*pos-1]<arr[2*pos]){
						arr[pos-1]=arr[2*pos-1];
						arr[2*pos-1]=num;
						return heapRebuild(arr, 2*pos, num);
						}else{
						arr[pos-1]=arr[2*pos];
						arr[2*pos]=num;
						return heapRebuild(arr, 2*pos+1, num);
					}
				}else{
					return arr;
				}
			}
			
		}
	
	}
	//To make the array into a heap array
	public void makeHeap(){
		int pos = count/2-1;
		while(pos>=0){
			minMake(pos);
			pos--;
		}
	}
	
	//Helper method for makeHeap
	public void minMake(int pos){
		
		int left = 2*pos+1;
		int right = 2*pos+2;
		int min = pos;
		
		if(left<count && heapArray[left]<heapArray[min]){
			min = left;
		}
		if(right<count && heapArray[right]<heapArray[min]){
			min = right;
		}
		if(min!=pos){
			comparison++;
			int temp = heapArray[pos];
			heapArray[pos]=heapArray[min];
			heapArray[min]=temp;
			minMake(min);
		}		
	}
	
	public int heapsort(){
		int pos = count;
		makeHeap();
		int i = pos-1;
		while(i>0){
			int temp = heapArray[i];
			heapArray[i]=heapArray[0];
			heapArray[0]=temp;
			minMake(0);
			i--;
		}
		return 0;
	}
	
	public int get(int pos){
		return heapArray[pos];
	}
	
	public int getCom(){
		return comparison;
	}
	
	
	public void test(){
		
		File f = new File("pq_test.txt");
		try{
			PrintWriter write = new PrintWriter(f);
			//Testing ranGen
			write.println("Random generate 10 numbers.");
			System.out.print("Testing the ranGen method: ");
			write.print("Testing the ranGen method: ");
			PQ225 test = new PQ225();
			test.ranGen(10,0,100);
			//System.out.print(test.size());
			if(test.size()==10){
				System.out.println("SUCCEED.");
				write.println("SUCCEED.");
			}else{
				System.out.println("FAILED.");
				write.println("FAILED.");
			}
			write.println("The current size is: "+test.size());
			write.println("---------------------------------------");
			/*for(int b=0; b<test.size(); b++){
					System.out.println(test.get(b));
			}
			*/
			//Testing size
			System.out.print("Testing the size method: ");
			write.print("Testing the size method: ");
			if(test.size()==10){
				System.out.println("SUCCEED.");
				write.println("SUCCEED.");
			}else{
				System.out.println("FAILED.");
				write.println("FAILED.");
			}
			write.println("The current size is: "+test.size());
			write.println("---------------------------------------");
			//Testing makeHeap
			write.println("Make the array into a heap array.");
			System.out.print("Testing the makeHeap method: ");
			write.print("Testing the makeHeap method: ");
			test.makeHeap();
			boolean result = true;
			int i = 0;
			while(i<test.size()){
				int left = i*2+1;
				int right = i*2+2;
				if(left<test.size() && test.get(left)<test.get(i)){
					result = false;
					//System.out.println("Here is the fault: "+test.get(i)+test.get(left));
				}
				if(right<test.size() && test.get(right)<test.get(i)){
					result = false;
					//System.out.println("Here is the fault: "+test.get(i)+test.get(right));
				}
				i++;
			}
			if(result){
				System.out.println("SUCCEED.");
				write.println("SUCCEED.");
			}else{
				System.out.println("FAILED.");
				write.println("FAILED.");
			}
			write.println("---------------------------------------");
			/*
			for(int b=0; b<test.size(); b++){
					System.out.println(test.get(b));
			}
			*/
			
			//Testing insert
			write.println("Insert two number into the heapArray.");
			System.out.print("Testing the insert method: ");
			write.print("Testing the insert method: ");
			test.insert(77);
			test.insert(1);
			boolean result2 = true;
			int j = 0;
			while(j<test.size()){
				int left2 = j*2+1;
				int right2 = j*2+2;
				if(left2<test.size() && test.get(left2)<test.get(j)){
					result2 = false;
				}
				if(right2<test.size() && test.get(right2)<test.get(j)){
					result2 = false;
				}
				j++;
			}
			if(result2){
				System.out.println("SUCCEED.");
				write.println("SUCCEED.");
			}else{
				System.out.println("FAILED.");
				write.println("FAILED.");
			}
			write.println("The current size is :"+test.size());
			write.println("---------------------------------------");
			/*
			for(int b=0; b<test.size(); b++){
					System.out.println(test.get(b));
			}
			*/
			//Testing deleteMin
			write.println("Delete the root of the heap.");
			System.out.print("Testing the deleteMin method: ");
			write.print("Testing the deleteMin method: ");
			int newCount = test.size();
			test.deleteMin();
			boolean result3 = true;
			int r = 0;
			while(r<test.size()){
				int left3 = r*2+1;
				int right3 = r*2+2;
				if(left3<test.size() && test.get(left3)<test.get(r)){
					result3 = false;
					//System.out.println("Here is the left fault: "+test.get(i)+test.get(left3));
				}
				if(right3<test.size() && test.get(right3)<test.get(r)){
					result3 = false;
					//System.out.println("Here is the right fault: "+test.get(i)+test.get(right3));
				}
				r++;
			}
			if(result3 && newCount==test.size()+1){
				System.out.println("SUCCEED.");
				write.println("SUCCEED.");
			}else{
				System.out.println("FAILED.");
				write.println("FAILED.");
			}
			write.println("The current size is: "+test.size());
			write.println("---------------------------------------");
			//Testing heapsort
			write.println("Sort the array.");
			System.out.print("Testing the heapsort method: ");
			write.print("Testing the heapsort method: ");
			test.heapsort();
			boolean result4 = true;
			for(int m = 0;i<count;i++){
				if(test.get(m)<test.get(m+1)){
					result4 = false;
				}
			} 
			if(result4){
				System.out.println("SUCCEED.");
				write.println("SUCCEED.");
			}else{
				System.out.println("FAILED.");
				write.println("FAILED.");
			}
			write.println("The current size is: "+test.size());
			write.println("Comparisons of heapsort method is: "+test.getCom());
			write.println("---------------------------------------");
			write.println("This part is used for prove the running time "); 
			write.println("of heapsort method is O(n*lgn) for larger size.");
			write.println("Random generate 100 numbers.");
			PQ225 test2 = new PQ225();
			test2.ranGen(100,0,1000);
			write.println("The current size n is: "+test2.size());
			test2.heapsort();
			write.println("Compasisons of heapsort method is: "+ test2.getCom());
			write.close();
			System.out.println("See pq_test.txt for detailed testing results.");
		}catch(FileNotFoundException e){
			System.out.println("NO FILE.");
		}
	}
	public static void main(String[] args){
		PQ225 h = new PQ225();
		h.test();
	}
	
}
