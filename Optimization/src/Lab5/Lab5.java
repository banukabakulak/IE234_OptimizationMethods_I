package Lab5;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import ilog.concert.*;
import ilog.cplex.*;

public class Lab5 {
	
	final static int full_time = 2;
	final static int part_time = 9;
	
	final static int nVariables = 11;
	final static int nConstraints = 14;

	public static void main(String[] args) {
	
		try {
			
			IloCplex scheduleModel = new IloCplex();
			
			// define decision variables 
			
			IloNumVar[] F = new IloNumVar[full_time];
			
			for(int i = 0; i < full_time; i++)
				F[i] = scheduleModel.numVar(0, Double.MAX_VALUE, "F(" + i + ")");
			
			IloNumVar[] P = new IloNumVar[part_time];
			
			for(int i = 0; i < part_time; i++)
				P[i] = scheduleModel.numVar(0, Double.MAX_VALUE, "P(" + i + ")");
			
			// define objective function
			
			IloLinearNumExpr objective = scheduleModel.linearNumExpr();
			
			objective.addTerm(30, F[0]);
			objective.addTerm(35, F[1]);
			
			for(int i = 0; i < part_time; i++)
				objective.addTerm(20, P[i]);
			
			scheduleModel.addMinimize(objective);
			
			// define A_matrix and rightHandSide
			
			double[][] A_matrix = new double[nConstraints][nVariables];
			
			double[] rightHandSide = new double[nConstraints];
			
			
			try {
				
				Scanner sc = new Scanner(new File("Coefficients.txt"));
				
				sc.nextLine();
				
				for(int j = 0; j < nConstraints; j++)
				{
					String[] scan = sc.nextLine().split("\t");
					
					for(int i = 0; i < nVariables; i++)
						A_matrix[j][i] = Double.parseDouble(scan[i]);
					
					rightHandSide[j] = Double.parseDouble(scan[nVariables]);			
				}
				
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			// define constraints 
			for(int j = 0; j < nConstraints; j++)
			{
				IloLinearNumExpr constraint = scheduleModel.linearNumExpr();
				
				
				for(int i = 0; i < full_time; i++)
					constraint.addTerm(A_matrix[j][i], F[i]);
				
				for(int i = 0; i < part_time; i++)
					constraint.addTerm(A_matrix[j][i + full_time], P[i]);
				
				scheduleModel.addGe(constraint, rightHandSide[j]);				
			}
			
			scheduleModel.exportModel("scheduleModel.lp");
			
			// solve Model
			
			Boolean success = scheduleModel.solve();
			
			if(success) 
			{
				System.out.println("The objective value is " + scheduleModel.getObjValue());
				
				System.out.println("The personel numbers are");
				
				for(int i = 0; i < full_time; i++)
					System.out.print(scheduleModel.getValue(F[i]) + " ");			
				
				for(int i = 0; i < part_time; i++)
					System.out.print(scheduleModel.getValue(P[i]) + " ");
				
			}
			else 
				System.out.println("The problem status is " + scheduleModel.getStatus());
			
			System.out.println();
			
		} catch (IloException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
