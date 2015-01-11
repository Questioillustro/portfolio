/*
	Purpose: Find the angle between user-input compnent vectors
	See EquationsDriver
*/

import java.util.*;

class AngleVectors{	

	public void getAngle(){
		
	Scanner scanner = new Scanner(System.in);
	double u1,u2,u3,v1,v2,v3,uDotv,uNorm,vNorm,theta,xi,xj,xk;
	
	System.out.print("Enter V1:\n");
	System.out.print("Enter i: ");
	u1 = scanner.nextDouble();
		System.out.print("Enter j: ");
		u2 = scanner.nextDouble();
			System.out.print("Enter k: ");
			u3 = scanner.nextDouble();
			
	System.out.print("Enter V2:\n");
	System.out.print("Enter i: ");
	v1 = scanner.nextDouble();
		System.out.print("Enter j: ");
		v2 = scanner.nextDouble();
			System.out.print("Enter k: ");
			v3 = scanner.nextDouble();
	
	//Finding u . v and norm products of u & v
	uDotv = u1*v1 + u2*v2 + u3*v3;
	uNorm = Math.sqrt(Math.pow(u1,2)+Math.pow(u2,2)+Math.pow(u3,2));
	vNorm = Math.sqrt(Math.pow(v1,2)+Math.pow(v2,2)+Math.pow(v3,2));
	// Cross product
	xi = u2*v3-v2*u3;
	xj = -(u1*v3-v1*u3);
	xk = u1*v2-v1*u2;
	
	System.out.println("Cross product = <" + xi + "," + xj + "," + xk + ">"); 
	System.out.println("Dot product = " + uDotv);
	System.out.println("Norm of V1 = " + uNorm);
	System.out.println("Norm of V2 = " + vNorm);

	//Finding Theta
	theta = Math.acos(uDotv/(uNorm*vNorm));
	
	System.out.println("Theta is: " + Math.toDegrees(theta) + " Degrees");
	System.out.println("Theta is: " + theta + " Radians\n");
	}
}
	
	

	