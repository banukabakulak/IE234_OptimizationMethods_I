package Lab4;

import ilog.concert.*;
import ilog.cplex.*;

public class Model {
	
	public Model() {}
	
	public void SolveModel(int[] profit, int[][] A, int[] resources)
	{
		int nVariables = profit.length;
		int nConstraints = resources.length;
		
		try {
			
			IloCplex cplexModel = new IloCplex();
			
			// define the decision variables 
			
			IloNumVar[] x = new IloNumVar[nVariables];
			
			for(int i = 0; i < nVariables; i++)
				x[i] = cplexModel.numVar(0, Double.MAX_VALUE, "x(" + i + ")");  // x(i)
			
			// define the objective function
			
			IloLinearNumExpr objective = cplexModel.linearNumExpr();
			
			for(int i = 0; i < nVariables; i++)
				objective.addTerm(profit[i], x[i]);
			
			cplexModel.addMaximize(objective);
			
			// define the constraints
			
			for(int j = 0; j < nConstraints; j++)
			{
				IloLinearNumExpr constraint = cplexModel.linearNumExpr();
				
				for(int i = 0; i < nVariables; i++)
					constraint.addTerm(A[j][i], x[i]);
				
				cplexModel.addLe(constraint, resources[j]);					
			}
			
			cplexModel.exportModel("productionModel.lp");
			
			// solve the model 
			
			Boolean success = cplexModel.solve();
			
			if(success)
			{
				System.out.println("The problem status is " + cplexModel.getStatus());
				
				System.out.println("The objective value is " + cplexModel.getObjValue());
				
				System.out.println("The production amounts are ");
				
				for(int i = 0; i < nVariables; i++)
					System.out.print(cplexModel.getValue(x[i]) + " ");				
			}
			else {
				System.out.println("The problem status is " + cplexModel.getStatus());
			}
			
			
		}catch(IloException e)
		{
			System.out.println(e.getStackTrace());
		}
		
	}

}
