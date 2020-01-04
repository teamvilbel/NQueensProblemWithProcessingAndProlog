/**
 * 
 */
package thm.vilbel.com.prolog;

import java.util.*;

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
	//list to handle queenpositions
	private List<String> queenPositions;
	//map to save position, queenvalue
	private HashMap<String, String> queenPositionMap;


	
	public SolutionsWithProlog(final int N, Set<List<Integer>> queens) {
		super();
		this.N = N;
		this.queens = new HashSet<List<Integer>>();
		this.queenPositions = new ArrayList<String>();
		this.queenPositions.add("A");
		this.queenPositions.add("B");
		this.queenPositions.add("C");
		this.queenPositions.add("D");
		this.queenPositions.add("E");
		this.queenPositions.add("F");
		this.queenPositions.add("G");
		this.queenPositions.add("H");
		this.queenPositions.add("I");
		this.queenPositions.add("J");
		this.queenPositions.add("K");

		this.queenPositionMap = new HashMap<>();
		// Init JPL to access Prolog
		JPL.init();
		
		// Load knowledge base from file.  
		Query knowledgeBase = new Query("consult", new Term[] { new Atom("./resources/n_queens.pl") });
		
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
					}
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
		//bulletproof till 11 queens
		String a = "A";
		String b = "B";
		String c = "C";
		String d = "D";
		String e = "E";
		String f = "F";
		String g = "G";
		String h = "H";
		String i = "I";
		String j = "J";
		String k = "K";

		for (List<Integer> index : queens) {
			int x = index.get(0);
			int y = index.get(1);
			y++;
			switch (x) {
			case 0:
				a = String.valueOf(y);
				queenPositionMap.put("A", a);
				break;
			case 1:
				b = String.valueOf(y);
				queenPositionMap.put("B", b);
				break;
			case 2:
				c = String.valueOf(y);
				queenPositionMap.put("C", c);
				break;
			case 3:
				d = String.valueOf(y);
				queenPositionMap.put("D", d);
				break;
			case 4:
				e = String.valueOf(y);
				queenPositionMap.put("E", e);
				break;
			case 5:
				f = String.valueOf(y);
				queenPositionMap.put("F", f);
				break;
			case 6:
				g = String.valueOf(y);
				queenPositionMap.put("G", g);
				break;
			case 7:
				h = String.valueOf(y);
				queenPositionMap.put("H", h);
				break;
			case 8:
				i = String.valueOf(y);
				queenPositionMap.put("I", i);
				break;
			case 9:
				j = String.valueOf(y);
				queenPositionMap.put("J", j);
				break;
			case 10:
				k = String.valueOf(y);
				queenPositionMap.put("K", k);
				break;
			default:
				break;
			}
			
		}

		//for each boardSize the resultString
		String result="";
		switch (this.N) {
			case 0:
				result = "["+"]";
				break;
			case 1:
				result = "[" + a +  "]";
				break;
			case 2:
				result = "[" + a + "," + b + "]";
				break;
			case 3:
				result = "[" + a + "," + b + "," + c + "]";
				break;
			case 4:
				result = "[" + a + "," + b + "," + c + "," + d+"]";
				break;
			case 5:
				result = "[" + a + "," + b + "," + c + "," + d+"," + e+"]";
				break;
			case 6:
				result = "[" + a + "," + b + "," + c + "," + d+"," + e + "," + f +"]";
				break;
			case 7:
				result = "[" + a + "," + b + "," + c + "," + d + "," + e + "," + f + "," + g +"]";
				break;
			case 8:
				result = "[" + a + "," + b + "," + c + "," + d + "," + e + "," + f + "," + g + "," + h + "]";
				break;
			case 9:
				result = "[" + a + "," + b + "," + c + "," + d + "," + e + "," + f + "," + g + "," + h +"," + i + "]";
				break;
			case 10:
				result = "[" + a + "," + b + "," + c + "," + d + "," + e + "," + f + "," + g + "," + h +"," + i +"," + j + "]";
				break;
			case 11:
				result = "[" + a + "," + b + "," + c + "," + d + "," + e + "," + f + "," + g + "," + h +"," + i +"," + j +"," + k + "]";
				break;

			default:
				break;
		}
		String query = String.format(SOLUTIONS_QUERRY, this.N, result, result);
		System.out.println(query);
		ArrayList<List<Integer>> solutions = new ArrayList<List<Integer>>();
		if (Query.hasSolution(query)) {
			Map<String,Term>[] allSolutions = Query.allSolutions(query);
			System.out.println("This has "+ allSolutions.length + " solutions");
			
			for (Map<String, Term> map : allSolutions) {
				Iterator queensIterator = queens.iterator();
				List<Integer> results = new ArrayList<Integer>();
				for (int l = 0; l < this.N ; l++) {
					if(map.get(this.queenPositions.get(l)) != null)
					{
						results.add(map.get(this.queenPositions.get(l)).intValue());
					}
					else
					{
						results.add( Integer.valueOf(this.queenPositionMap.get(this.queenPositions.get(l))));
					}
				}
				System.out.println(results);
				solutions.add(results);
			}
			//clear container to avoid anomalies
			queenPositions.clear();
			queenPositionMap.clear();
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
