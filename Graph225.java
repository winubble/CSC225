/*
 * University of Victoria
 * CSC 225 - Fall 2016
 * Code template for assignment 4
 */
import java.io.IOException;
import java.io.File;
import java.util.Scanner;
import java.io.PrintWriter;
import java.util.Random;
import java.io.BufferedReader;
import java.util.Stack;
import java.io.FileReader;
// DO NOT CHANGE THE CLASS NAME OR PACKAGE
public class Graph225 {
	
	public Graph225(){
		
	}
	/**
	 * Simple representation of an undirected graph, using a square, symmetric
	 * adjacency matrix.
	 * <p>
	 * An adjacency matrix M represents a graph G=(V,E) where V is a set of n
	 * vertices and E is a set of m edges. The size of the matrix is {@code n},
	 * where {@code n} is in the range {@code [4, 15]} only. Thus, the rows and
	 * columns of the matrix are in the range {@code [0, n-1]} representing
	 * vertices. The elements of the matrix are 1 if the edge exists in the
	 * graph and 0 otherwise. Since the graph is undirected, the matrix is
	 * symmetric and contains 2m 1â€™s.
	 */
	public static class Graph {

		/**
		 * An adjacency matrix representation of this graph
		 */
		private int[][] adjacencyMatrix;
		private int size;
		private int edge;
		private int row;
		private int column;
		/*
		 * You are free to add constructors, but the empty constructor is the
		 * only one invoked during marking.
		 */
		public Graph() {
			adjacencyMatrix = new int[15][15];
			size = 0;
			edge = 0;
			row = 0;
			column = 0;
		}

		/**
		 * Generate a random graph as specified in the assignment statement.
		 * 
		 * @param n
		 *            The size of the graph
		 * @param density
		 *            The density of the graph
		 */
		public void generate(int n, int density) {

			size = n;
			Random ran = new Random();

			adjacencyMatrix = new int[n][n];
			if(density==1){
				edge = 7*n/5;
			}else if(density==2){
				edge = n*n/4;
			}else if(density==3){
				edge = 2*n*n/5;
			}
			int range = size;

			for(int i=0; i<edge; i++){
				row = ran.nextInt(range);
				column = ran.nextInt(range);
				adjacencyMatrix[row][column] = 1;
				adjacencyMatrix[column][row] = 1;
			}
		}

		/**
		 * Reads an adjacency matrix from the specified file, and updates this
		 * graph's data. For the file structure please refer to the sample input
		 * file {@code testadjmat.txt}).
		 * 
		 * @param file
		 *            The input file
		 * @throws IOException
		 *             If something bad happens while reading the input file.
		 */
		public void read(String file) throws IOException {

			File input = new File(file);
			BufferedReader bfr = new BufferedReader(new FileReader(input));
			String line = bfr.readLine();
			String[] num_list = line.split(" ");
			int num = num_list.length;
			bfr.close();
			size = num;

			Scanner reader = new Scanner(input);
			for(int i=0;i<num;i++){
				for(int j=0;j<num;j++){
					adjacencyMatrix[i][j]=reader.nextInt();
				}
			}
			
		}

		/**
		 * Writes the adjacency matrix representing this graph in the specified
		 * file.
		 * 
		 * @param file
		 *            The path of the output file
		 * @throws IOException
		 *             If something bad happens while writing the file.
		 */
		public void write(String file) throws IOException {

			File input = new File(file);
			PrintWriter output = new PrintWriter(input);
			
			for(int i=0;i<size;i++){
				for(int j=0;j<size;j++){
					if(j==size-1){
						output.print(adjacencyMatrix[i][j]+"\n");
					}else{
						output.print(adjacencyMatrix[i][j]+" ");
					}
				}
			}
			output.close();
		}

		/**
		 * @return an adjacency matrix representation of this graph
		 */
		public int[][] getAdjacencyMatrix() {
			return this.adjacencyMatrix;
		}

		/**
		 * Updates this graph's adjacency matrix
		 * 
		 * @param m
		 *            The adjacency matrix representing the new graph
		 */
		public void setAdjacencyMatrix(int[][] m) {
			this.adjacencyMatrix = m;
			size = m[0].length;
		}
		
		public int getSize(){
			return size;
		}
		
		public int getEdges(){
			return edge;
		}
	}

	/**
	 * Traverses the given graph starting at the specified vertex, using the
	 * depth first search graph traversal algorithm.
	 * <p>
	 * <b>NOTICE</b>: adjacent vertices must be visited in strictly increasing
	 * order (for automated marking)
	 * 
	 * @param graph
	 *            The graph to traverse
	 * @param vertex
	 *            The starting vertex (as per its position in the adjacency
	 *            matrix)
	 * @return a vector R of n elements where R[j] is 1 if vertex j can be
	 *         reached from {@code vertex} and 0 otherwise
	 */
	public int[] reach(Graph graph, int vertex) {

		int[] R = new int[graph.getSize()];
		Stack stack= new  Stack();  
  		stack.push(new Integer(vertex)); 
  		boolean[] visited = new boolean[graph.getSize()];
  		visited[vertex-1]=true;  
  		while (!stack.isEmpty()){  
   			int node = (int)stack.pop(); 
			R[node-1]=1;
  			//System.out.print(element.data + "\t");  
  			int[] neighbours=findNeighbours(graph,node);  
   			for (int i = 0; i < neighbours.length; i++) {  
    			int n=neighbours[i];  
    			if(!visited[n-1]){  
     				stack.push(new Integer(n));  
     				visited[n-1]=true;  
				}  
   			}  
  		}
		return R;
	}
	
	public int[] findNeighbours(Graph graph, int vertex){
		int[][] matrix = graph.getAdjacencyMatrix();
		int[] temp = new int[matrix.length];
		int cnt = 0;
		for(int i=0; i<matrix.length; i++){
			if(matrix[vertex-1][i]==1){
				temp[cnt]=i+1;
				cnt++;
			}
		}
		int[] m = new int[cnt]; 
		for(int i=0; i<cnt; i++){
			m[i]=temp[i];
		}
		return m;
	}
	
	
	/**
	 * Computes the number connected components of a given graph.
	 * <p>
	 * <b>NOTICE</b>: adjacent vertices must be visited in strictly increasing
	 * order (for automated marking)
	 * 
	 * @param graph
	 *            The graph
	 * @return The number of connected component in {@code graph}
	 */
	public int connectedComponents(Graph graph) {

		int[][] check = new int[graph.getSize()][graph.getSize()];
		int count = 0;
		for(int i=0; i<graph.getSize();i++){
			int[] result= reach(graph,i+1);
			check[i]=result;
		}
		
		boolean[] visited = new boolean[graph.getSize()];
		for(int i=0; i<check.length; i++){
			int cnt = 0;
			if(!visited[i]){
				for(int j=0; j<check[i].length; j++){
					if(check[i][j]==1){
						visited[j] = true;
						cnt = 1;
					}
				}
			}
			if(cnt==1){
				count++;
			}
		}
		return count;
		
		
		
		
	}

	/**
	 * Determines whether a given graph contains at least one cycle.
	 * <p>
	 * <b>NOTICE</b>: adjacent vertices must be visited in strictly increasing
	 * order (for automated marking)
	 * 
	 * @param graph
	 *            The graph
	 * @return whether or not {@code graph} contains at least one cycle
	 */
	public boolean hasCycle(Graph graph) {

		
		for(int m=0; m<graph.getSize(); m++){
			Stack stack= new  Stack();
			int vertex = m+1;
		
			stack.push(new Integer(vertex));  
			boolean[] visited = new boolean[graph.getSize()];
			visited[vertex-1]=true; 
		
			while (!stack.isEmpty()){  
				int node = (int)stack.pop();  
				int check = 0;
				int[] neighbours=findNeighbours(graph,node);  
				for (int i = 0; i < neighbours.length; i++) {  
					int n=neighbours[i]; 
					
					if(!visited[n-1]){  
						stack.push(new Integer(n));  
						visited[n-1]=true;  
					}
					else{
						check++;
					}
				}
		
				if(check>=2&&check==neighbours.length){
					return true;
				}
			}

		}
		return false;
	}


	/**
	 * Computes the pre-order for each vertex in the given graph.
	 * Empty spots in the array, if any, are to be filled with -1.
	 * <p>
	 * <b>NOTICE</b>: adjacent vertices must be visited in strictly increasing
	 * order (for automated marking)
	 * 
	 * @param graph
	 *            The graph
	 * @return a vector R of n elements of each vertex, representing the pre-order of
	 *         {@code graph}
	 */
	public int[][] preOrder(Graph graph) {

		int[][] o = new int[graph.getSize()][graph.getSize()];
		for(int i=0; i<o.length; i++){
			for(int j=0; j<o[i].length; j++){
				o[i][j]=-1;
			}
		}

		for(int m=0; m<graph.getSize(); m++){
			int cnt = 0;
			Stack stack= new  Stack();  
			int vertex = m+1;
			stack.push(new Integer(vertex));  
			boolean[] visited = new boolean[graph.getSize()];
			visited[vertex-1]=true;  
			while (!stack.isEmpty()){  
				int node = (int)stack.pop();
				int dupcnt = 0;
				for(int i=0; i<o[m].length;i++){
					if(o[m][i]==node){
						dupcnt = 1;
					}
	
					
				}

				if(dupcnt==1){
					continue;
				}
				visited[node-1] = true;
				if(node!=vertex){
		
					o[m][cnt]=node;
					cnt++;

				}
				
				int[] neighbours=findNeighbours(graph,node);  
				for (int i = neighbours.length-1; i>=0; i--) {  
					int n=neighbours[i];  
					if(!visited[n-1]){  
						stack.push(new Integer(n));  

						
					}  
				}

			}
	
		}
		return o;
	}

	/**
	 * Computes the post-order for each vertex in the given graph.
	 * Empty spots in the array, if any, are to be filled with -1.
	 * <p>
	 * <b>NOTICE</b>: adjacent vertices must be visited in strictly increasing
	 * order (for automated marking)
	 * 
	 * @param graph
	 *            The graph
	 * @return a vector R of n elements of each vertex, representing the post-order of
	 *         {@code graph}
	 */
	public int[][] postOrder(Graph graph) {
		int[][] o = new int[graph.getSize()][graph.getSize()];
		for(int i=0; i<o.length; i++){
			for(int j=0; j<o[i].length; j++){
				o[i][j]=-1;
			}
		}
		for(int m=0; m<graph.getSize(); m++){
			Stack orderstack = new Stack();
			Stack stack = new  Stack();
			int cnt = 0;
			int vertex = m+1;
			stack.push(new Integer(vertex));  
			boolean[] visited = new boolean[graph.getSize()];
			visited[vertex-1]=true;  
			while (!stack.isEmpty()){  
				int node = (int)stack.pop(); 
				visited[node-1] = true;
				int dupcnt = 0;
				for(int i=0; i<o[m].length;i++){
					if(o[m][i]==node){
						dupcnt = 1;
					}

					
				}
	
				if(dupcnt==1){
					continue;
				}

				if(node!=vertex){
					orderstack.push(new Integer(node));
				}
	 
				int[] neighbours=findNeighbours(graph,node);
				int visitcnt = 0;
				for(int i=0; i<neighbours.length; i++){

					if(visited[neighbours[i]-1]){
						visitcnt++;
					}
				}
				if(visitcnt==neighbours.length&&!orderstack.empty()){

					o[m][cnt]=(int)orderstack.pop();
	
					cnt++;
					
					while(!orderstack.empty()){
						int visitedcnt = 0;
						int[] neigh = findNeighbours(graph,(int)orderstack.peek());
						for (int i = 0; i <neigh.length; i++) {  
							int n=neigh[i];  
							if(visited[n-1]){    
								visitedcnt++;  
							}  
						}
						if(visitedcnt!=neigh.length){
							break;
						}else{
							o[m][cnt]=(int)orderstack.pop();
							cnt++;
						}
					}
				}  
				for (int i = neighbours.length-1; i >= 0; i--) {  
					int n=neighbours[i];  
					if(!visited[n-1]){  
						stack.push(new Integer(n));  
						 		
					}  
				}  
				
			}

		}
		return o;

	}

	/**
	 * test and exercise the algorithms and data structures developed for the
	 * first five parts of this assignment extensively. The output generated by
	 * this method must convince the marker that the algorithms and data
	 * structures are implemented as specified. For example:
	 * <ul>
	 * <li>Generate graphs of different sizes and densities
	 * <li>Test the algorithms for different graphs
	 * <li>Test your algorithms using the sample input file testadjmat.txt
	 * 
	 * @throws Exception
	 *             if something bad happens!
	 */
	public void test() throws Exception {
		
		if(testGraph()){
			System.out.println("Testing for the graph method: SUCCEED.");
		}else{
			System.out.println("Testing for the graph method: FAILED.");
		}
		
		if(testReach()){
			System.out.println("Testing for the reach method: SUCCEED.");
		}else{
			System.out.println("Testing for the reach method: FAILED.");
		}
		
		if(testConnectedComponents()){
			System.out.println("Testing for the ConnectedComponents method: SUCCEED.");
		}else{
			System.out.println("Testing for the ConnectedComponents method: FAILED.");
		}
		
		if(testHasCycle()){
			System.out.println("Testing for the hasCycle method: SUCCEED.");
		}else{
			System.out.println("Testing for the hasCycle method: FAILED.");
		}
		
		if(testPreOrder()){
			System.out.println("Testing for the preOrder method: SUCCEED.");
		}else{
			System.out.println("Testing for the preOrder method: FAILED.");
		}
		
		if(testPostOrder()){
			System.out.println("Testing for the postOrder method: SUCCEED.");
		}else{
			System.out.println("Testing for the postOrder method: FAILED.");
		}


	}
	
	public boolean testGraph(){
		Graph g1 = new Graph();

		int size = 6;
		int density = 2;
		g1.generate(size, density);
		if(g1.getSize()!=6){
			return false;
		}
		if(g1.getEdges()!=9){
			return false;
		}
		Graph g2 = new Graph();
		int size2 = 7;
		int density2 = 1;
		g2.generate(size2,density2);
		if(g2.getSize()!=7){
			return false;
		}
		if(g2.getEdges()!=9){
			return false;
		}
		Graph g3 = new Graph();
		int size3 = 15;
		int density3 = 3;
		g3.generate(size3,density3);
		if(g3.getSize()!=15){
			return false;
		}
		if(g3.getEdges()!=90){
			return false;
		}
		return true;
	}
	
	public boolean testReach(){
		Graph g = new Graph();
		Graph225 g225 = new Graph225();
	
		int[][] test = {
			{0,1,0,0},
			{1,0,1,0},
			{0,1,0,0},
			{0,0,0,0}
		};
		g.setAdjacencyMatrix(test);
		
		int[] testArr = g225.reach(g, 1);
			
		for(int i=0; i<3; i++){
			if(testArr[i]!=1){
				return false;
			}
		}
		if(testArr[3]!=0){
			return false;
		}
		return true;
	}
	
	public boolean testConnectedComponents(){
		Graph g = new Graph();
		Graph225 g225 = new Graph225();
		
		int[][] test = {
			{0,1,0,0},
			{1,0,1,0},
			{0,1,0,0},
			{0,0,0,0}
		};
		g.setAdjacencyMatrix(test);
		
		if(g225.connectedComponents(g)!=2){
			return false;
		}
		return true;
	}
	
	public boolean testHasCycle(){
		Graph g1 = new Graph();
		Graph g2 = new Graph();
		Graph225 g225 = new Graph225();
		
		int[][] test1 = {
			{0,1,0,0},
			{1,0,1,0},
			{0,1,0,0},
			{0,0,0,0}
		};
		g1.setAdjacencyMatrix(test1);
		if(g225.hasCycle(g1)){
			return false;
		}
		
		int[][] test2 = {
			{0,1,1,0},
			{1,0,0,1},
			{1,0,0,1},
			{0,1,1,0}
		};
		g2.setAdjacencyMatrix(test2);
		if(!g225.hasCycle(g2)){
			return false;
		}
		
		return true;
	}
	
	public boolean testPreOrder(){
		Graph g = new Graph();
		Graph225 g225 = new Graph225();

		int[][] test = {
			{0,1,0,0},
			{1,0,1,0},
			{0,1,0,0},
			{0,0,0,0}
		};
		g.setAdjacencyMatrix(test);
		int[][] testArr = g225.preOrder(g);
		
		int[][] resultArr = {
			{2,3,-1,-1},
			{1,3,-1,-1},
			{2,1,-1,-1},
			{-1,-1,-1,-1}
		};
		
		for(int m=0;m<test.length;m++){
			for(int n=0;n<test[m].length;n++){
				if(testArr[m][n]!=resultArr[m][n]){
					return false;
				}
			}
		}
		
		return true;
	}
	
	public boolean testPostOrder(){
		Graph g = new Graph();
		Graph225 g225 = new Graph225();
		
		int[][] test = {
			{0,1,0,0},
			{1,0,1,0},
			{0,1,0,0},
			{0,0,0,0}
		};
		g.setAdjacencyMatrix(test);
		int[][] testArr = g225.postOrder(g);
		
		int[][] resultArr = {
			{3,2,-1,-1},
			{1,3,-1,-1},
			{1,2,-1,-1},
			{-1,-1,-1,-1}
		};
			
		for(int m=0;m<test.length;m++){
			for(int n=0;n<test[m].length;n++){
				if(testArr[m][n]!=resultArr[m][n]){
					return false;
				}
			}
		}
		return true;
	}
	public static void main(String[] args){

		Graph225 graph = new Graph225();
		
		try{
			graph.test();
		}catch(Exception e){
			System.out.println(e);
	 	}
	}
}
