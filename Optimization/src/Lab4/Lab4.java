package Lab4;

public class Lab4 {
	
	final static int nVariables = 5;
	
	final static int nConstraints = 3;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		System.out.println("Hello world!");
		
		// declaration of the input parameters, profits, A matrix, resources
		
		int[] profit = new int[nVariables];
		
		profit[0] = 55; profit[1] = 60; profit[2] = 35; profit[3] = 40; profit[4] = 20;

		int[][] A = new int[nConstraints][nVariables];
		
		A[0][0] = 12;  A[0][1] = 20; A[0][2] = 0; A[0][3] = 25; A[0][4] = 15;
		A[1][0] = 10;  A[1][1] = 8; A[1][2] = 16; A[1][3] = 0; A[1][4] = 0;
		A[2][0] = 20;  A[2][1] = 20; A[2][2] = 20; A[2][3] = 20; A[2][4] = 20;
	
	    int[] resources = new int[nConstraints];
	    
	    resources[0] = 17280;  resources[1] = 11520;  resources[2] = 23040; 
	    
	   Model cplexModel = new Model();
	   cplexModel.SolveModel(profit, A, resources);
	   
	}

}
