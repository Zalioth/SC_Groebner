import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import util.Time;
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
		int maxVars = 6;
		int minPoly = 1;
		int maxPoly = 6;
		int minDegr = 1;
		int maxDegr = 6;
		
		int increment = 1;
		
		int minFixed = 1;
		int maxFixed = 6;
		
		int incrementFixed = 2;
		
		int repetitions = 10;
		
		try
		{
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
						// Suggest the Garbage Collector to run, so all the resources taken by 
						// the Gröbner base calculation can be freed.
						System.gc();
						
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
		}
		catch(Throwable e)
		{
			System.out.println("An exception occured in test 1:");
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		
		
		
		
		
		try
		{
			// Test 2, quadratic test vars-poly.
			System.out.println("\tTest 2 starting...\n");
			FileWriter fw2 = new FileWriter("./test2.txt", true);
	      PrintWriter pw2 = new PrintWriter(fw2);
	      
	      pw2.println("");
	      pw2.println("Test 2");
	      pw2.println("Quadratic test, Vars - Poly");
	      pw2.println("minVars: " + minVars + " maxVars: " + maxVars);
	      pw2.println("minPoly: " + minPoly + " maxPoly: " + maxPoly);
	      pw2.println("minDegr: " + minDegr + " maxDegr: " + maxDegr);
	      pw2.println("With an increment of " + increment);
	      pw2.println("And a time average calculated from " + repetitions + " repetitions");
	      pw2.println("Fixed value varies as:");
	      pw2.println("minFixed: " + minFixed + " maxFixed: " + maxFixed);
	      pw2.println("With an increment of " + incrementFixed);
	      pw2.println("");
	      
	      for(int f=minFixed; f<maxFixed; f+=incrementFixed)
	      {
	      	pw2.println("fixed: " + f);
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
	   				int d = f;
	   				long sum = 0;
	   				for(int r=0; r<repetitions; ++r)
	   				{
	   					// Execute the method #calculateGroebnerBase and measure it's time
	   					timer.start();
	   					calculateGroebnerBase(v, p, d);
	   					timer.stop();
	   					// Suggest the Garbage Collector to run, so all the resources taken by 
							// the Gröbner base calculation can be freed.
							System.gc();
	   					
	   					sum += timer.getTime();
	   				}
	   				
	   				long average = sum / repetitions;
	   				
	   				// Write the time in a file
	   				System.out.println(("v: " + v + "\tp: " + p + "\td: " + d + "\ttime: " + average ));
	   				pw2.print( ( "\t" + average ) );
	   			}
	   			pw2.print("\n");
	   		}
	   		pw2.println("");
	      }
			
			pw2.close();
			fw2.close();
			System.out.println("\tTest 2 ended.\n");
		}
		catch(Throwable e)
		{
			System.out.println("An exception occured in test 2:");
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		
		
		
		
		
		
		
		
		
		
		try
		{
			// Test 3, quadratic test vars-degr.
			System.out.println("\tTest 3 starting...\n");
			FileWriter fw3 = new FileWriter("./test3.txt", true);
	      PrintWriter pw3 = new PrintWriter(fw3);
	      
	      pw3.println("");
	      pw3.println("Test 3");
	      pw3.println("Quadratic test, Vars - Degr");
	      pw3.println("minVars: " + minVars + " maxVars: " + maxVars);
	      pw3.println("minPoly: " + minPoly + " maxPoly: " + maxPoly);
	      pw3.println("minDegr: " + minDegr + " maxDegr: " + maxDegr);
	      pw3.println("With an increment of " + increment);
	      pw3.println("And a time average calculated from " + repetitions + " repetitions");
	      pw3.println("Fixed value varies as:");
	      pw3.println("minFixed: " + minFixed + " maxFixed: " + maxFixed);
	      pw3.println("With an increment of " + incrementFixed);
	      pw3.println("");
	      
	      for(int f=minFixed; f<maxFixed; f+=incrementFixed)
	      {
	      	pw3.println("fixed: " + f);
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
	   				int p = f;
	   				long sum = 0;
	   				for(int r=0; r<repetitions; ++r)
	   				{
	   					// Execute the method #calculateGroebnerBase and measure it's time
	   					timer.start();
	   					calculateGroebnerBase(v, p, d);
	   					timer.stop();
	   					// Suggest the Garbage Collector to run, so all the resources taken by 
							// the Gröbner base calculation can be freed.
							System.gc();
	   					
	   					sum += timer.getTime();
	   				}
	   				
	   				long average = sum / repetitions;
	   				
	   				// Write the time in a file
	   				System.out.println(("v: " + v + "\tp: " + p + "\td: " + d + "\ttime: " + average ));
	   				pw3.print( ( "\t" + average ) );
	   			}
	   			pw3.print("\n");
	   		}
	      	pw3.println("");
	      }
	      
			pw3.close();
			fw3.close();
			System.out.println("\tTest 3 ended.\n");
		}
		catch(Throwable e)
		{
			System.out.println("An exception occured in test 3:");
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		
		
		
		
		
		
		
		
		try
		{
			// Test 4, quadratic test poly-degr.
			System.out.println("\tTest 4 starting...\n");
			FileWriter fw4 = new FileWriter("./test4.txt", true);
	      PrintWriter pw4 = new PrintWriter(fw4);
	      
	      pw4.println("");
	      pw4.println("Test 4");
	      pw4.println("Quadratic test, Poly - Degr");
	      pw4.println("minVars: " + minVars + " maxVars: " + maxVars);
	      pw4.println("minPoly: " + minPoly + " maxPoly: " + maxPoly);
	      pw4.println("minDegr: " + minDegr + " maxDegr: " + maxDegr);
	      pw4.println("With an increment of " + increment);
	      pw4.println("And a time average calculated from " + repetitions + " repetitions");
	      pw4.println("Fixed value varies as:");
	      pw4.println("minFixed: " + minFixed + " maxFixed: " + maxFixed);
	      pw4.println("With an increment of " + incrementFixed);
	      pw4.println("");
	      
	      for(int f=minFixed; f<maxFixed; f+=incrementFixed)
	      {
	      	pw4.println("fixed: " + f);
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
	   				int v = f;
	   				long sum = 0;
	   				for(int r=0; r<repetitions; ++r)
	   				{
	   					// Execute the method #calculateGroebnerBase and measure it's time
	   					timer.start();
	   					calculateGroebnerBase(v, p, d);
	   					timer.stop();
	   					// Suggest the Garbage Collector to run, so all the resources taken by 
							// the Gröbner base calculation can be freed.
							System.gc();
	   					
	   					sum += timer.getTime();
	   				}
	   				
	   				long average = sum / repetitions;
	   				
	   				// Write the time in a file
	   				System.out.println(("v: " + v + "\tp: " + p + "\td: " + d + "\ttime: " + average ));
	   				pw4.print( ( "\t" + average ) );
	   			}
	   			pw4.print("\n");
	   		}
	      	pw4.println("");
	      }
	      
			pw4.close();
			fw4.close();
			System.out.println("\tTest 4 ended.\n");
		}
		catch(Throwable e)
		{
			System.out.println("An exception occured in test 4:");
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		
		
		
		
		
		// Linear tests
		minVars = 1;
		maxVars = 10;
		minPoly = 1;
		maxPoly = 10;
		minDegr = 1;
		maxDegr = 10;
		
		increment = 1;
		
		int fixedVars = 5;
		int fixedPoly = 5;
		int fixedDegr = 5;
		
		
		
		
		
		
		
		try
		{
			// Test 5, linear test poly.
			System.out.println("\tTest 5 starting...\n");
			FileWriter fw5 = new FileWriter("./test5.txt", true);
	      PrintWriter pw5 = new PrintWriter(fw5);
	      
	      pw5.println("");
	      pw5.println("Test 5");
	      pw5.println("Linear test, Poly");
	      pw5.println("minVars: " + minVars + " maxVars: " + maxVars);
	      pw5.println("minPoly: " + minPoly + " maxPoly: " + maxPoly);
	      pw5.println("minDegr: " + minDegr + " maxDegr: " + maxDegr);
	      pw5.println("With an increment of " + increment);
	      pw5.println("And a time average calculated from " + repetitions + " repetitions");
	      pw5.println("Fixed values vary as:");
	      pw5.println("Vars: " + fixedVars);
	      pw5.println("Poly: " + fixedPoly);
	      pw5.println("Degr: " + fixedDegr);
	      pw5.println("");
	      
	      pw5.println("");
	      pw5.println("Poly\tTime");
			for(int p=minPoly; p<maxPoly; p+=increment)
			{
				int v = fixedVars;
				int d = fixedDegr;
				long sum = 0;
				for(int r=0; r<repetitions; ++r)
				{
					// Execute the method #calculateGroebnerBase and measure it's time
					timer.start();
					calculateGroebnerBase(v, p, d);
					timer.stop();
					// Suggest the Garbage Collector to run, so all the resources taken by 
					// the Gröbner base calculation can be freed.
					System.gc();
					
					sum += timer.getTime();
				}
				
				long average = sum / repetitions;
				
				// Write the time in a file
				System.out.println(("v: " + v + "\tp: " + p + "\td: " + d + "\ttime: " + average ));
				pw5.println(p + "\t" + average);
			}
	      
			pw5.close();
			fw5.close();
			System.out.println("\tTest 5 ended.\n");
		}
		catch(Throwable e)
		{
			System.out.println("An exception occured in test 5:");
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		try
		{
			// Test 6, linear test vars.
			System.out.println("\tTest 6 starting...\n");
			FileWriter fw6 = new FileWriter("./test6.txt", true);
	      PrintWriter pw6 = new PrintWriter(fw6);
	      
	      pw6.println("");
	      pw6.println("Test 6");
	      pw6.println("Linear test, Vars");
	      pw6.println("minVars: " + minVars + " maxVars: " + maxVars);
	      pw6.println("minPoly: " + minPoly + " maxPoly: " + maxPoly);
	      pw6.println("minDegr: " + minDegr + " maxDegr: " + maxDegr);
	      pw6.println("With an increment of " + increment);
	      pw6.println("And a time average calculated from " + repetitions + " repetitions");
	      pw6.println("Fixed values vary as:");
	      pw6.println("Vars: " + fixedVars);
	      pw6.println("Poly: " + fixedPoly);
	      pw6.println("Degr: " + fixedDegr);
	      pw6.println("");
	      
	      pw6.println("");
	      pw6.println("Vars\tTime");
			for(int v=minVars; v<maxVars; v+=increment)
			{
				int p = fixedPoly;
				int d = fixedDegr;
				long sum = 0;
				for(int r=0; r<repetitions; ++r)
				{
					// Execute the method #calculateGroebnerBase and measure it's time
					timer.start();
					calculateGroebnerBase(v, p, d);
					timer.stop();
					// Suggest the Garbage Collector to run, so all the resources taken by 
					// the Gröbner base calculation can be freed.
					System.gc();
					
					sum += timer.getTime();
				}
				
				long average = sum / repetitions;
				
				// Write the time in a file
				System.out.println(("v: " + v + "\tp: " + p + "\td: " + d + "\ttime: " + average ));
				pw6.println(v + "\t" + average);
			}
	      
			pw6.close();
			fw6.close();
			System.out.println("\tTest 6 ended.\n");
		}
		catch(Throwable e)
		{
			System.out.println("An exception occured in test 6:");
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		
		
		
		
		
		
		
		
		
		try
		{
			// Test 7, linear test degr.
			System.out.println("\tTest 7 starting...\n");
			FileWriter fw7 = new FileWriter("./test7.txt", true);
		   PrintWriter pw7 = new PrintWriter(fw7);
		   
		   pw7.println("");
		   pw7.println("Test 7");
		   pw7.println("Linear test, Degr");
		   pw7.println("minVars: " + minVars + " maxVars: " + maxVars);
		   pw7.println("minPoly: " + minPoly + " maxPoly: " + maxPoly);
		   pw7.println("minDegr: " + minDegr + " maxDegr: " + maxDegr);
		   pw7.println("With an increment of " + increment);
		   pw7.println("And a time average calculated from " + repetitions + " repetitions");
		   pw7.println("Fixed values vary as:");
		   pw7.println("Vars: " + fixedVars);
		   pw7.println("Poly: " + fixedPoly);
		   pw7.println("Degr: " + fixedDegr);
		   pw7.println("");
		   
		   pw7.println("");
		   pw7.println("Degr\tTime");
			for(int d=minDegr; d<maxDegr; d+=increment)
			{
				int v = fixedVars;
				int p = fixedPoly;
				long sum = 0;
				for(int r=0; r<repetitions; ++r)
				{
					// Execute the method #calculateGroebnerBase and measure it's time
					timer.start();
					calculateGroebnerBase(v, p, d);
					timer.stop();
					// Suggest the Garbage Collector to run, so all the resources taken by 
					// the Gröbner base calculation can be freed.
					System.gc();
					
					sum += timer.getTime();
				}
				
				long average = sum / repetitions;
				
				// Write the time in a file
				System.out.println(("v: " + v + "\tp: " + p + "\td: " + d + "\ttime: " + average ));
				pw7.println(d + "\t" + average);
			}
		   
			pw7.close();
			fw7.close();
			System.out.println("\tTest 7 ended.\n");
		}
		catch(Throwable e)
		{
			System.out.println("An exception occured in test 7:");
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		
	}
	
	
	
	
	
	
	
	
	public static void calculateGroebnerBase(int numberOfVariables, int numberOfPolynomials, int maxDegreeOfPolynomials)
	{
		String vars[] = new String[numberOfVariables];
		for(int i=0; i<numberOfVariables; ++i)
		{
			vars[i] = ("v"+i);
		}
		
		// Coefficient Factory = Z65537
		ModIntegerRing coefficientFactory = new ModIntegerRing(65537);
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
