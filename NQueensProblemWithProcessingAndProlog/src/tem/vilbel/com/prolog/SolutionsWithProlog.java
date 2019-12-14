/**
 * 
 */
package tem.vilbel.com.prolog;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
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
	
	public SolutionsWithProlog(final int N, Set<List<Integer>> queens) {
		super();
		this.N = N;
		this.queens = new HashSet<List<Integer>>();
		
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
