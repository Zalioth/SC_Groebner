import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
	static int minVars;
	static int maxVars;
	static int minPoly;
	static int maxPoly;
	static int minDegr;
	static int maxDegr;

	static int increment;

	static int minFixed;
	static int maxFixed;

	static int incrementFixed;

	static int repetitions;

	static int fixedVars;
	static int fixedPoly;
	static int fixedDegr;
	
	public static void setLinearTestValues()
	{
		// Linear test
		minVars = 20;
		maxVars = 200;
		minPoly = 20;
		maxPoly = 200;
		minDegr = 20;
		maxDegr = 200;

		increment = 1;

		fixedVars = 80;
		fixedPoly = 80;
		fixedDegr = 80;

		repetitions = 10;
	}
	
	public static void setQuadraticTestValues()
	{
		// Quadratic test
		minVars = 20;
		maxVars = 200;
		minPoly = 20;
		maxPoly = 200;
		minDegr = 20;
		maxDegr = 200;

		increment = 5;

		minFixed = 80;
		maxFixed = 81;

		incrementFixed = 10;

		repetitions = 10;
	}
	
	public static void setCubicTestValues()
	{
		// Cubic test
		minVars = 20;
		maxVars = 200;
		minPoly = 20;
		maxPoly = 200;
		minDegr = 20;
		maxDegr = 200;

		increment = 1;

		repetitions = 40;
	}
	
	public static void main(String[] args) throws IOException
	{
		// Menu variables
		boolean end = false;
		Scanner scan = new Scanner(System.in);
		try
		{
			while (!end)
			{
				System.out.println("0 -> Exit.");
				System.out.println("1 -> Test 1. Cubic test, v-p-d.");
				System.out.println("2 -> Test 2. Quadratic test, v-p");
				System.out.println("3 -> Test 3. Quadratic test, v-d");
				System.out.println("4 -> Test 4. Quadratic test, p-d");
				System.out.println("5 -> Test 5. Linear test, p");
				System.out.println("6 -> Test 6. Linear test, v");
				System.out.println("7 -> Test 7. Linear test, d");
				System.out.println("Where:");
				System.out.println("\t'v' stands for number of variables,");
				System.out.println("\t'p' stands for number of polynomials");
				System.out.println("\t'd' stands for maximum degree of each polynomial.");
				System.out.println("Choose an option:");
				int selection = scan.nextInt();

				// Suggest the Garbage Collector to run, so all the resources
				// taken by former executions can be freed and reused.
				System.gc();

				switch (selection)
				{
				case 0:
					end = true;
					System.out.println("Exiting...");
					break;
				case 1:
					test1();
					break;
				case 2:
					test2();
					break;
				case 3:
					test3();
					break;
				case 4:
					test4();
					break;
				case 5:
					test5();
					break;
				case 6:
					test6();
					break;
				case 7:
					test7();
					break;
				default:
					System.out
							.println("Please pick an option (number) among the specified above.");
					break;
				}
				if (selection != 0)
				{
					System.out.println("Press ENTER to continue...");
					scan.nextLine();
					scan.nextLine();
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
			System.exit(1);
		}
		scan.close();
	}

	public static void test1() throws IOException
	{
		setCubicTestValues();
		
		FileWriter fw1;
		PrintWriter pw1;

		Time timer = new Time();

		// Test 1, full cubic test.
		System.out.println("\tTest 1 starting...\n");
		fw1 = new FileWriter("./test1.txt", true);
		pw1 = new PrintWriter(fw1);

		try
		{
			for (int v = minVars; v < maxVars; ++v)
			{
				for (int p = minPoly; p < maxPoly; ++p)
				{
					for (int d = minDegr; d < maxDegr; ++d)
					{
						double sum = 0;
						for (int r = 0; r < repetitions; ++r)
						{
							// Execute the method #calculateGroebnerBase and measure its
							// time
							timer.start();
							calculateGroebnerBase(v, p, d);
							timer.stop();
							// Suggest the Garbage Collector to run, so all the resources
							// taken by
							// the Gröbner base calculation can be freed.
							System.gc();

							//	System.out.println(timer.getTime());
							sum += timer.getTime();
						}

						double average = (double)sum / (double)repetitions;

						// Write the time in a file
						System.out.println(("v: " + v + "\tp: " + p + "\td: " + d
								+ "\ttime: " + average));
						pw1.println((v + "\t" + p + "\t" + d + "\t" + average));
					}
				}
			}
		} catch (Throwable e)
		{
			System.out.println("An exception occured in test 1:");
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally
		{
			pw1.close();
			fw1.close();
			System.out.println("\tTest 1 ended.\n");
		}
	}

	public static void test2() throws IOException
	{
		setQuadraticTestValues();
		
		FileWriter fw2;
		PrintWriter pw2;

		Time timer = new Time();

		// Test 2, quadratic test vars-poly.
		System.out.println("\tTest 2 starting...\n");
		fw2 = new FileWriter("./test2.txt", true);
		pw2 = new PrintWriter(fw2);

		try
		{
			pw2.println("");
			pw2.println("Test 2");
			pw2.println("Quadratic test, Vars - Poly");
			pw2.println("minVars: " + minVars + " maxVars: " + maxVars);
			pw2.println("minPoly: " + minPoly + " maxPoly: " + maxPoly);
			pw2.println("minDegr: " + minDegr + " maxDegr: " + maxDegr);
			pw2.println("With an increment of " + increment);
			pw2.println("And a time average calculated from " + repetitions
					+ " repetitions");
			pw2.println("Fixed value varies as:");
			pw2.println("minFixed: " + minFixed + " maxFixed: " + maxFixed);
			pw2.println("With an increment of " + incrementFixed);
			pw2.println("");

			for (int f = minFixed; f < maxFixed; f += incrementFixed)
			{
				pw2.println("fixed: " + f);
				pw2.print("v-p");
				for (int p = minPoly; p < maxPoly; p += increment)
				{
					pw2.print("\t" + p);
				}
				pw2.print("\n");
				for (int v = minVars; v < maxVars; v += increment)
				{
					pw2.print(v);
					for (int p = minPoly; p < maxPoly; p += increment)
					{
						int d = f;
						double sum = 0;
						for (int r = 0; r < repetitions; ++r)
						{
							// Execute the method #calculateGroebnerBase and measure
							// its time
							timer.start();
							calculateGroebnerBase(v, p, d);
							timer.stop();
							// Suggest the Garbage Collector to run, so all the
							// resources taken by
							// the Gröbner base calculation can be freed.
							System.gc();

							//	System.out.println(timer.getTime());
							sum += timer.getTime();
						}

						double average = (double)sum / (double)repetitions;

						// Write the time in a file
						System.out.println(("v: " + v + "\tp: " + p + "\td: " + d
								+ "\ttime: " + average));
						pw2.print(("\t" + average));
				
					}
					pw2.print("\n");
				}
				pw2.println("");
			}
		} catch (Throwable e)
		{
			System.out.println("An exception occured in test 2:");
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally
		{
			pw2.close();
			fw2.close();
			System.out.println("\tTest 2 ended.\n");
		}
	}

	public static void test3() throws IOException
	{
		setQuadraticTestValues();
		
		FileWriter fw3;
		PrintWriter pw3;

		Time timer = new Time();

		// Test 3, quadratic test vars-degr.
		System.out.println("\tTest 3 starting...\n");
		fw3 = new FileWriter("./test3.txt", true);
		pw3 = new PrintWriter(fw3);

		try
		{
			pw3.println("");
			pw3.println("Test 3");
			pw3.println("Quadratic test, Vars - Degr");
			pw3.println("minVars: " + minVars + " maxVars: " + maxVars);
			pw3.println("minPoly: " + minPoly + " maxPoly: " + maxPoly);
			pw3.println("minDegr: " + minDegr + " maxDegr: " + maxDegr);
			pw3.println("With an increment of " + increment);
			pw3.println("And a time average calculated from " + repetitions
					+ " repetitions");
			pw3.println("Fixed value varies as:");
			pw3.println("minFixed: " + minFixed + " maxFixed: " + maxFixed);
			pw3.println("With an increment of " + incrementFixed);
			pw3.println("");

			for (int f = minFixed; f < maxFixed; f += incrementFixed)
			{
				pw3.println("fixed: " + f);
				pw3.print("v-d");
				for (int d = minDegr; d < maxDegr; d += increment)
				{
					pw3.print("\t" + d);
				}
				pw3.print("\n");
				for (int v = minVars; v < maxVars; v += increment)
				{
					pw3.print(v);
					for (int d = minDegr; d < maxDegr; d += increment)
					{
						int p = f;
						double sum = 0;
						for (int r = 0; r < repetitions; ++r)
						{
							// Execute the method #calculateGroebnerBase and measure
							// its time
							timer.start();
							calculateGroebnerBase(v, p, d);
							timer.stop();
							// Suggest the Garbage Collector to run, so all the
							// resources taken by
							// the Gröbner base calculation can be freed.
							System.gc();

							sum += timer.getTime();
						}

						double average = sum / repetitions;

						// Write the time in a file
						System.out.println(("v: " + v + "\tp: " + p + "\td: " + d
								+ "\ttime: " + average));
						pw3.print(("\t" + average));
					}
					pw3.print("\n");
				}
				pw3.println("");
			}
		} catch (Throwable e)
		{
			System.out.println("An exception occured in test 3:");
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally
		{
			pw3.close();
			fw3.close();
			System.out.println("\tTest 3 ended.\n");
		}
	}

	public static void test4() throws IOException
	{
		setQuadraticTestValues();
		
		FileWriter fw4;
		PrintWriter pw4;

		Time timer = new Time();

		// Test 4, quadratic test poly-degr.
		System.out.println("\tTest 4 starting...\n");
		fw4 = new FileWriter("./test4.txt", true);
		pw4 = new PrintWriter(fw4);

		try
		{
			pw4.println("");
			pw4.println("Test 4");
			pw4.println("Quadratic test, Poly - Degr");
			pw4.println("minVars: " + minVars + " maxVars: " + maxVars);
			pw4.println("minPoly: " + minPoly + " maxPoly: " + maxPoly);
			pw4.println("minDegr: " + minDegr + " maxDegr: " + maxDegr);
			pw4.println("With an increment of " + increment);
			pw4.println("And a time average calculated from " + repetitions
					+ " repetitions");
			pw4.println("Fixed value varies as:");
			pw4.println("minFixed: " + minFixed + " maxFixed: " + maxFixed);
			pw4.println("With an increment of " + incrementFixed);
			pw4.println("");

			for (int f = minFixed; f < maxFixed; f += incrementFixed)
			{
				pw4.println("fixed: " + f);
				pw4.print("p-d");
				for (int d = minDegr; d < maxDegr; d += increment)
				{
					pw4.print("\t" + d);
				}
				pw4.print("\n");
				for (int p = minPoly; p < maxPoly; p += increment)
				{
					pw4.print(p);
					for (int d = minDegr; d < maxDegr; d += increment)
					{
						int v = f;
						double sum = 0;
						for (int r = 0; r < repetitions; ++r)
						{
							// Execute the method #calculateGroebnerBase and measure
							// its time
							timer.start();
							calculateGroebnerBase(v, p, d);
							timer.stop();
							// Suggest the Garbage Collector to run, so all the
							// resources taken by
							// the Gröbner base calculation can be freed.
							System.gc();

							sum += timer.getTime();
						}

						double average = sum / repetitions;

						// Write the time in a file
						System.out.println(("v: " + v + "\tp: " + p + "\td: " + d
								+ "\ttime: " + average));
						pw4.print(("\t" + average));
					}
					pw4.print("\n");
				}
				pw4.println("");
			}
		} catch (Throwable e)
		{
			System.out.println("An exception occured in test 4:");
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally
		{
			pw4.close();
			fw4.close();
			System.out.println("\tTest 4 ended.\n");
		}
	}

	public static void test5() throws IOException
	{
		setLinearTestValues();
		
		FileWriter fw5;
		PrintWriter pw5;

		Time timer = new Time();

		// Test 5, linear test poly.
		System.out.println("\tTest 5 starting...\n");
		fw5 = new FileWriter("./test5.txt", true);
		pw5 = new PrintWriter(fw5);

		try
		{
			pw5.println("");
			pw5.println("Test 5");
			pw5.println("Linear test, Poly");
			pw5.println("minVars: " + minVars + " maxVars: " + maxVars);
			pw5.println("minPoly: " + minPoly + " maxPoly: " + maxPoly);
			pw5.println("minDegr: " + minDegr + " maxDegr: " + maxDegr);
			pw5.println("With an increment of " + increment);
			pw5.println("And a time average calculated from " + repetitions
					+ " repetitions");
			pw5.println("Fixed values vary as:");
			pw5.println("Vars: " + fixedVars);
			pw5.println("Poly: " + fixedPoly);
			pw5.println("Degr: " + fixedDegr);
			pw5.println("");

			pw5.println("");
			pw5.println("Poly\tTime");
			for (int p = minPoly; p < maxPoly; p += increment)
			{
				int v = fixedVars;
				int d = fixedDegr;
				double sum = 0;
				for (int r = 0; r < repetitions; ++r)
				{
					// Execute the method #calculateGroebnerBase and measure its
					// time
					timer.start();
					calculateGroebnerBase(v, p, d);
					timer.stop();
					// Suggest the Garbage Collector to run, so all the resources
					// taken by
					// the Gröbner base calculation can be freed.
					System.gc();

					sum += timer.getTime();
				}

				double average = sum / repetitions;

				// Write the time in a file
				System.out.println(("v: " + v + "\tp: " + p + "\td: " + d
						+ "\ttime: " + average));
				pw5.println(p + "\t" + average);
			}
		} catch (Throwable e)
		{
			System.out.println("An exception occured in test 5:");
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally
		{
			pw5.close();
			fw5.close();
			System.out.println("\tTest 5 ended.\n");
		}
	}

	public static void test6() throws IOException
	{
		setLinearTestValues();
		
		FileWriter fw6;
		PrintWriter pw6;

		Time timer = new Time();

		// Test 6, linear test vars.
		System.out.println("\tTest 6 starting...\n");
		fw6 = new FileWriter("./test6.txt", true);
		pw6 = new PrintWriter(fw6);

		try
		{
			pw6.println("");
			pw6.println("Test 6");
			pw6.println("Linear test, Vars");
			pw6.println("minVars: " + minVars + " maxVars: " + maxVars);
			pw6.println("minPoly: " + minPoly + " maxPoly: " + maxPoly);
			pw6.println("minDegr: " + minDegr + " maxDegr: " + maxDegr);
			pw6.println("With an increment of " + increment);
			pw6.println("And a time average calculated from " + repetitions
					+ " repetitions");
			pw6.println("Fixed values vary as:");
			pw6.println("Vars: " + fixedVars);
			pw6.println("Poly: " + fixedPoly);
			pw6.println("Degr: " + fixedDegr);
			pw6.println("");

			pw6.println("");
			pw6.println("Vars\tTime");
			for (int v = minVars; v < maxVars; v += increment)
			{
				int p = fixedPoly;
				int d = fixedDegr;
				double sum = 0;
				for (int r = 0; r < repetitions; ++r)
				{
					// Execute the method #calculateGroebnerBase and measure its
					// time
					timer.start();
					calculateGroebnerBase(v, p, d);
					timer.stop();
					// Suggest the Garbage Collector to run, so all the resources
					// taken by
					// the Gröbner base calculation can be freed.
					System.gc();

					sum += timer.getTime();
				}

				double average = sum / repetitions;

				// Write the time in a file
				System.out.println(("v: " + v + "\tp: " + p + "\td: " + d
						+ "\ttime: " + average));
				pw6.println(v + "\t" + average);
			}
		} catch (Throwable e)
		{
			System.out.println("An exception occured in test 6:");
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally
		{
			pw6.close();
			fw6.close();
			System.out.println("\tTest 6 ended.\n");
		}
	}

	public static void test7() throws IOException
	{
		setLinearTestValues();
		
		FileWriter fw7;
		PrintWriter pw7;

		Time timer = new Time();

		// Test 7, linear test degr.
		System.out.println("\tTest 7 starting...\n");
		fw7 = new FileWriter("./test7.txt", true);
		pw7 = new PrintWriter(fw7);

		try
		{
			pw7.println("");
			pw7.println("Test 7");
			pw7.println("Linear test, Degr");
			pw7.println("minVars: " + minVars + " maxVars: " + maxVars);
			pw7.println("minPoly: " + minPoly + " maxPoly: " + maxPoly);
			pw7.println("minDegr: " + minDegr + " maxDegr: " + maxDegr);
			pw7.println("With an increment of " + increment);
			pw7.println("And a time average calculated from " + repetitions
					+ " repetitions");
			pw7.println("Fixed values vary as:");
			pw7.println("Vars: " + fixedVars);
			pw7.println("Poly: " + fixedPoly);
			pw7.println("Degr: " + fixedDegr);
			pw7.println("");

			pw7.println("");
			pw7.println("Degr\tTime");
			for (int d = minDegr; d < maxDegr; d += increment)
			{
				int v = fixedVars;
				int p = fixedPoly;
				double sum = 0;
				for (int r = 0; r < repetitions; ++r)
				{
					// Execute the method #calculateGroebnerBase and measure its
					// time
					timer.start();
					calculateGroebnerBase(v, p, d);
					timer.stop();
					// Suggest the Garbage Collector to run, so all the resources
					// taken by
					// the Gröbner base calculation can be freed.
					System.gc();

					sum += timer.getTime();
				}

				double average = sum / repetitions;

				// Write the time in a file
				System.out.println(("v: " + v + "\tp: " + p + "\td: " + d
						+ "\ttime: " + average));
				pw7.println(d + "\t" + average);
			}
		} catch (Throwable e)
		{
			System.out.println("An exception occured in test 7:");
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally
		{
			pw7.close();
			fw7.close();
			System.out.println("\tTest 7 ended.\n");
		}
	}

	public static void calculateGroebnerBase(int numberOfVariables,
			int numberOfPolynomials, int maxDegreeOfPolynomials)
	{
		String vars[] = new String[numberOfVariables];
		for (int i = 0; i < numberOfVariables; ++i)
		{
			vars[i] = ("v" + i);
		}

		// Coefficient Factory = Z65537
		ModIntegerRing coefficientFactory = new ModIntegerRing(65537);
		GenPolynomialRing<ModInteger> polynomialFactory = new GenPolynomialRing<ModInteger>(
				coefficientFactory, vars.length, new TermOrder(TermOrder.INVLEX),
				vars);

		List<GenPolynomial<ModInteger>> polynomials = new ArrayList<GenPolynomial<ModInteger>>();
		for (int i = 0; i < numberOfPolynomials; ++i)
		{
			GenPolynomial<ModInteger> p = polynomialFactory
					.random(maxDegreeOfPolynomials);
			polynomials.add(p);
		}

		GroebnerBase<ModInteger> gb = GBFactory
				.getImplementation(coefficientFactory);
		// List<GenPolynomial<ModInteger>> G = gb.GB(numberOfVariables, polynomials);
		// G holds the Gröbner base corresponding to the polynomials generated.

		// Executing this way to save Java from having to allocate memory for the
		// returning
		// Gröbner base that will not be used.
		
		//Other functions used
		gb.GB(numberOfVariables, polynomials);
	}
}
