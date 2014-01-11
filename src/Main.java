import java.util.ArrayList;
import java.util.List;

import edu.jas.arith.ModInteger;
import edu.jas.arith.ModIntegerRing;
import edu.jas.gb.GroebnerBase;
import edu.jas.gbufd.GBFactory;
import edu.jas.poly.GenPolynomial;
import edu.jas.poly.GenPolynomialRing;
import edu.jas.poly.TermOrder;



public class Main
{
	public static void main(String[] args)
	{
		example3();
	}
	
	/**
    * example3. Coefficients in Boolean ring and additional idempotent
    * generators.
    * 
    */
   public static void example3() {
       String[] vars = { "v3", "v2", "v1" };
       
       ModIntegerRing z2 = new ModIntegerRing(2);
       GenPolynomialRing<ModInteger> z2p = new GenPolynomialRing<ModInteger>(z2, vars.length, new TermOrder(
               TermOrder.INVLEX), vars);
       List<GenPolynomial<ModInteger>> fieldPolynomials = new ArrayList<GenPolynomial<ModInteger>>();

       //add v1^2 + v1, v2^2 + v2, v3^2 + v3 to fieldPolynomials
       for (int i = 0; i < vars.length; i++) {
           GenPolynomial<ModInteger> var = z2p.univariate(i);
           fieldPolynomials.add(var.multiply(var).sum(var));
       }


       List<GenPolynomial<ModInteger>> polynomials = new ArrayList<GenPolynomial<ModInteger>>();

       GenPolynomial<ModInteger> v1 = z2p.univariate(0);
       GenPolynomial<ModInteger> v2 = z2p.univariate(1);
       GenPolynomial<ModInteger> v3 = z2p.univariate(2);
       GenPolynomial<ModInteger> notV1 = v1.sum(z2p.ONE);
       GenPolynomial<ModInteger> notV2 = v2.sum(z2p.ONE);
       GenPolynomial<ModInteger> notV3 = v3.sum(z2p.ONE);

       //v1*v2
       GenPolynomial<ModInteger> p1 = v1.multiply(v2);

       //v1*v2 + v1 + v2 + 1
       GenPolynomial<ModInteger> p2 = notV1.multiply(notV2);

       //v1*v3 + v1 + v3 + 1
       GenPolynomial<ModInteger> p3 = notV1.multiply(notV3);

       polynomials.add(p1);
       polynomials.add(p2);
       polynomials.add(p3);

       polynomials.addAll(fieldPolynomials);

       //GroebnerBase<ModInteger> gb = new GroebnerBaseSeq<ModInteger>();
       GroebnerBase<ModInteger> gb = GBFactory.getImplementation(z2);

       List<GenPolynomial<ModInteger>> G = gb.GB(polynomials);

       System.out.println(G);
   }
}
