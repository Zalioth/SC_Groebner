import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import util.Time;
import edu.jas.arith.BigComplex;
import edu.jas.arith.ModInteger;
import edu.jas.arith.ModIntegerRing;
import edu.jas.gb.GroebnerBase;
import edu.jas.gbufd.GBFactory;
import edu.jas.poly.GenPolynomial;
import edu.jas.poly.GenPolynomialRing;
import edu.jas.poly.TermOrder;



public class Main
{
	public static void main(String[] args) throws IOException
	{
		Time timer = new Time();
		
		int minVars = 1;
		int maxVars = 5;
		int minPoly = 1;
		int maxPoly = 5;
		int minDegr = 1;
		int maxDegr = 5;
		
		int fixed = 3;
		int increment = 1;
		
		int repetitions = 5;
		
		/*
		// Test 1, full cubic test.
		System.out.println("\tTest 1 starting...\n");
		FileWriter fw1 = new FileWriter("./test1.txt", true);
      PrintWriter pw1 = new PrintWriter(fw1);
		for(int v=minVars; v<maxVars; ++v)
		{
			for(int p=minPoly; p<maxPoly; ++p)
			{
				for(int d=minDegr; d<maxDegr; ++d)
				{
					// Execute the method #calculateGroebnerBase and measure it's time
					timer.start();
					calculateGroebnerBase(v, p, d);
					timer.stop();
					// Write the time in a file
					System.out.println(("v: " + v + "\tp: " + p + "\td: " + d + "\ttime: " + timer.getTime() ));
					pw1.println( ( v + "\t" + p + "\t" + d + "\t" + timer.getTime() ) );
				}
			}
		}
		pw1.close();
		fw1.close();
		System.out.println("\tTest 1 ended.\n");
		*/
		
		// Test 2, cuadratic test vars-poly.
		System.out.println("\tTest 2 starting...\n");
		FileWriter fw2 = new FileWriter("./test2.txt", true);
      PrintWriter pw2 = new PrintWriter(fw2);
      
      pw2.print( "v-p" );
      for(int p=minPoly; p<maxPoly; p+=increment)
		{
      	pw2.print( "\t" + p );
		}
      pw2.print("\n");
		for(int v=minVars; v<maxVars; v+=increment)
		{
			pw2.print(v);
			for(int p=minPoly; p<maxPoly; p+=increment)
			{
				int d = fixed;
				long sum = 0;
				for(int r=0; r<repetitions; ++r)
				{
					// Execute the method #calculateGroebnerBase and measure it's time
					timer.start();
					calculateGroebnerBase(v, p, d);
					timer.stop();
					
					sum += timer.getTime();
				}
				
				long average = sum / repetitions;
				
				// Write the time in a file
				System.out.println(("v: " + v + "\tp: " + p + "\td: " + d + "\ttime: " + average ));
				pw2.print( ( "\t" + average ) );
			}
			pw2.print("\n");
		}
		pw2.close();
		fw2.close();
		System.out.println("\tTest 2 ended.\n");
		
		// Test 3, cuadratic test vars-degr.
		System.out.println("\tTest 3 starting...\n");
		FileWriter fw3 = new FileWriter("./test3.txt", true);
      PrintWriter pw3 = new PrintWriter(fw3);
      
      pw3.print( "v-d" );
      for(int d=minDegr; d<maxDegr; d+=increment)
		{
      	pw3.print( "\t" + d );
		}
      pw3.print("\n");
		for(int v=minVars; v<maxVars; v+=increment)
		{
			pw3.print(v);
			for(int d=minDegr; d<maxDegr; d+=increment)
			{
				int p = fixed;
				long sum = 0;
				for(int r=0; r<repetitions; ++r)
				{
					// Execute the method #calculateGroebnerBase and measure it's time
					timer.start();
					calculateGroebnerBase(v, p, d);
					timer.stop();
					
					sum += timer.getTime();
				}
				
				long average = sum / repetitions;
				
				// Write the time in a file
				System.out.println(("v: " + v + "\tp: " + p + "\td: " + d + "\ttime: " + average ));
				pw3.print( ( "\t" + average ) );
			}
			pw3.print("\n");
		}
		pw3.close();
		fw3.close();
		System.out.println("\tTest 3 ended.\n");
		
		// Test 4, cuadratic test poly-degr.
		System.out.println("\tTest 4 starting...\n");
		FileWriter fw4 = new FileWriter("./test4.txt", true);
      PrintWriter pw4 = new PrintWriter(fw4);
      
      pw4.print( "p-d" );
      for(int d=minDegr; d<maxDegr; d+=increment)
		{
      	pw4.print( "\t" + d );
		}
      pw4.print("\n");
		for(int p=minPoly; p<maxPoly; p+=increment)
		{
			pw4.print(p);
			for(int d=minDegr; d<maxDegr; d+=increment)
			{
				int v = fixed;
				long sum = 0;
				for(int r=0; r<repetitions; ++r)
				{
					// Execute the method #calculateGroebnerBase and measure it's time
					timer.start();
					calculateGroebnerBase(v, p, d);
					timer.stop();
					
					sum += timer.getTime();
				}
				
				long average = sum / repetitions;
				
				// Write the time in a file
				System.out.println(("v: " + v + "\tp: " + p + "\td: " + d + "\ttime: " + average ));
				pw4.print( ( "\t" + average ) );
			}
			pw4.print("\n");
		}
		pw4.close();
		fw4.close();
		System.out.println("\tTest 4 ended.\n");
	}
	
	public static void calculateGroebnerBase(int numberOfVariables, int numberOfPolynomials, int maxDegreeOfPolynomials)
	{
		String vars[] = new String[numberOfVariables];
		for(int i=0; i<numberOfVariables; ++i)
		{
			vars[i] = ("v"+i);
		}
		
		// Coefficient Factory = Z2
		ModIntegerRing coefficientFactory = new ModIntegerRing(2);
      GenPolynomialRing<ModInteger> polynomialFactory = new GenPolynomialRing<ModInteger>(coefficientFactory, vars.length, new TermOrder(TermOrder.INVLEX), vars);
      
      List<GenPolynomial<ModInteger>> polynomials = new ArrayList<GenPolynomial<ModInteger>>();
      for(int i=0; i<numberOfPolynomials; ++i)
      {
      	GenPolynomial<ModInteger> p = polynomialFactory.random(maxDegreeOfPolynomials);
      	polynomials.add(p);
      }
      
      GroebnerBase<ModInteger> gb = GBFactory.getImplementation(coefficientFactory);
      List<GenPolynomial<ModInteger>> G = gb.GB(polynomials);
      
      // G holds the Gröbner base corresponding to the polynomials generated.
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
