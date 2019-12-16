/**
 * 
 */
package tem.vilbel.com.prolog;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jpl7.Atom;
import org.jpl7.JPL;
import org.jpl7.Query;
import org.jpl7.Term;
import org.jpl7.Variable;

/**
 * @author Noah Ruben
 *
 *
 * @created 12.12.2019
 */
public class SolutionsWithProlog {

	
	/*
	 * H = 1, N = 8, n_queens(N, [A, B, C, D, E, F, G, H]), labeling([ff], [A, B, C,
	 * D, E, F, G]).
	 */
	
	public final int N;
	public Set<List<Integer>> queens;
	
	private final String ALL_SOLUTIONS_QUERRY = "distinct(n_queens(%d, Qs)), labeling([ff], Qs).";
	
	private final String SOLUTIONS_QUERRY = "distinct(n_queens(%d, %s)), labeling([ff], %s).";
	
	public SolutionsWithProlog(final int N, Set<List<Integer>> queens) {
		super();
		this.N = N;
		this.queens = new HashSet<List<Integer>>();
		
		// Init JPL to access Prolog
		JPL.init();
		
		// Load knowledge base from file.  
		Query knowledgeBase = new Query("consult", new Term[] { new Atom("./NQueensProblemWithProcessingAndProlog/resources/n_queens.pl") });
		
		System.out.println("Loaded file: " + knowledgeBase.hasSolution());
	}
	
	

	public List<List<Integer>> solve() {
		String query = String.format(ALL_SOLUTIONS_QUERRY, this.N);
		ArrayList<List<Integer>> solutions = new ArrayList<List<Integer>>();
		if (Query.hasSolution(query)) {
			Map<String,Term>[] allSolutions = Query.allSolutions(query);
			System.out.println("This has "+ allSolutions.length + " solutions");
			
			
			for (Map<String, Term> map : allSolutions) {
				Collection<Term> values = map.values();
				for (Term term : values) {
					Term[] terms = term.toTermArray();
					ArrayList<Integer> solution = new ArrayList<Integer>();
					for (Term ints : terms) {
						solution.add(ints.intValue());
//						System.out.print(ints.intValue() + ", ");
					}
//					System.out.println("");
					solutions.add(solution);
				}
			}
			return solutions;
		}else {
			System.out.println("This has no solutions");
			return solutions;
		}
	}
	
	/*
	 * H = 1, N = 8, n_queens(N, [A, B, C, D, E, F, G, H]), labeling([ff], [A, B, C,
	 * D, E, F, G]).
	 */
	public List<List<Integer>> solve(Set<List<Integer>> queens) {
		
		String a = "A";
		String b = "B";
		String c = "C";
		String d = "D";
		String e = "E";
		String f = "F";
		String g = "G";
		String h = "H";
		for (List<Integer> index : queens) {
			int x = index.get(0);
			int y = index.get(1);
			y++;
			switch (x) {
			case 0:
				a = String.valueOf(y);
				break;
			case 1:
				b = String.valueOf(y);
				break;
			case 2:
				c = String.valueOf(y);
				break;
			case 3:
				d = String.valueOf(y);
				break;
			case 4:
				e = String.valueOf(y);
				break;
			case 5:
				f = String.valueOf(y);
				break;
			case 6:
				g = String.valueOf(y);
				break;
			case 7:
				h = String.valueOf(y);
				break;
			default:
				break;
			}
			
		}
		
		String result = "[" + a + "," + b + "," + c + "," + d + "," + e + "," + f + "," + g + "," + h + "]";
		String query = String.format(SOLUTIONS_QUERRY, this.N, result, result);
		System.out.println(query);
		ArrayList<List<Integer>> solutions = new ArrayList<List<Integer>>();
		if (Query.hasSolution(query)) {
			Map<String,Term>[] allSolutions = Query.allSolutions(query);
			System.out.println("This has "+ allSolutions.length + " solutions");
			
			for (Map<String, Term> map : allSolutions) {
				Iterator queensIterator = queens.iterator();
				List<Integer> results = new ArrayList<Integer>();
				if(map.get("A") != null)
				{
					results.add(map.get("A").intValue());
				}
				else
				{
					results.add( Integer.valueOf(a) );
				}
				if(map.get("B") != null)
				{
					results.add(map.get("B").intValue());
				}
				else
				{
					results.add( Integer.valueOf(b) );
				}
				if(map.get("C") != null)
				{
					results.add(map.get("C").intValue());
				}
				else
				{
					results.add( Integer.valueOf(c) );
				}
				if(map.get("D") != null)
				{
					results.add(map.get("D").intValue());
				}
				else
				{
					results.add( Integer.valueOf(d) );
				}
				if(map.get("E") != null)
				{
					results.add(map.get("E").intValue());
				}
				else
				{
					results.add( Integer.valueOf(e) );
				}
				if(map.get("F") != null)
				{
					results.add(map.get("F").intValue());
				}
				else
				{
					results.add( Integer.valueOf(f) );
				}
				if(map.get("G") != null)
				{
					results.add(map.get("G").intValue());
				}
				else
				{
					results.add( Integer.valueOf(g) );
				}
				if(map.get("H") != null)
				{
					results.add(map.get("H").intValue());
				}
				else
				{
					results.add( Integer.valueOf(h) );
				}
				System.out.println(results);
				solutions.add(results);
			}
			return solutions;
		}else {
			System.out.println("This has no solutions");
			return solutions;
		}
	}
	
	/**
	 * @return the queens
	 */
	public Set<List<Integer>> getQueens() {
		return queens;
	}

	/**
	 * @param queens the queens to set
	 */
	public void setQueens(Set<List<Integer>> queens) {
		this.queens = queens;
	}



	/**
	 * @return the n
	 */
	public int getN() {
		return N;
	}

}
