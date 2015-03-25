/*
<package>
	Lab 1 - csc103
<.package>
<description>
	Utility methods for the lab 1 - csc103
<.description>
<keywords>
	quadratic, math
<.keywords>
*/

public class Quadratic {
	
	private double coefA, coefB, coefC;
	private double roots[] = new double[3];
	
	public Quadratic()
	{
		coefA = 0;
		coefB = 0;
		coefC = 0;
	}
	
	public Quadratic(double a, double b, double c)
	{
		coefA = a;
		coefB = b;
		coefC = c;
	}
	
		
	public static Quadratic sum(Quadratic q1, Quadratic q2)
	{
		Quadratic temp = new Quadratic(q1.getCoefA() + q2.getCoefA(), 
									  			 q1.getCoefB() + q2.getCoefB(), 
									 			 q1.getCoefC() + q2.getCoefC());
		return temp;
	}
	
	public static Quadratic scale(double r, Quadratic q)
	{
		Quadratic scaled = new Quadratic(r * q.getCoefA(), 
										 		   r * q.getCoefB(), 
												   r * q.getCoefC());
		return scaled;
	}
	
	public double getCoefA()
	{
		return coefA;
	}
	
	public double getCoefB()
	{
		return coefB;
	}
	
	public double getCoefC()
	{
		return coefC;
	}
	
	public void show()
	{
	System.out.println("\t(" + coefA + "x^2) + " +
						 	   "(" + coefB + "x) + " +
						  	   "(" + coefC + ")");
	}
	
	public double evalExpression(double x)
	{
		return (coefA * Math.pow(x, 2.0)) + (coefB * x) + coefC;
	}
	
	public static Quadratic clone(Quadratic q)
	{
		Quadratic clone = new Quadratic(q.getCoefA(),q.getCoefB(),q.getCoefC());
		return clone;
	}
	
	public void findRoots ( )
	{
		if (coefA == 0 && coefB == 0 && coefC == 0)
			roots[0] = 3;
		
		if (coefA == 0 && coefB == 0 && coefC != 0)
			roots[0] = 0;
			
		if (coefA == 0 && coefB != 0)
		{
			roots[0] = 1;
			roots[1] = - coefC / coefB;
		}
					
		if (coefA != 0 && (Math.pow(coefB, 2) < 4 * coefA * coefC))
			roots[0] = 0;
			
		if (coefA != 0 && (Math.pow(coefB, 2) == 4 * coefA * coefC))
		{
			roots[0] = 1;
			roots[1] = -coefB / (2 * coefA);
		}
		
		if (coefA != 0 && (Math.pow(coefB, 2) > (4 * coefA * coefC)))
		{
			roots[0] = 2;
			roots[1] = (- coefB - Math.sqrt(Math.pow(coefB, 2) - (4 * coefA * coefC)))/ (2 * coefA);
			roots[2] = (- coefB + Math.sqrt(Math.pow(coefB, 2) - (4 * coefA * coefC)))/ (2 * coefA);
		}
	}
	
	public double getNumRoots ( )
	{
		return roots[0];
	}
	
	public double getRootOne ( )
	{
		return roots[1];
	}
	
	public double getRootTwo ( )
	{
		return roots[2];
	}
}


