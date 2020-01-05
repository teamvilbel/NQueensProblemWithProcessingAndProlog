/**
 * 
 */
package thm.vilbel.com.prolog;

import java.util.*;

import org.jpl7.Atom;
import org.jpl7.JPL;
import org.jpl7.Query;
import org.jpl7.Term;

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
		this.queenPositionMap = new HashMap<>();
		// Init JPL to access Prolog
		JPL.init();

		// Load knowledge base from file.
		Query knowledgeBase = new Query("consult", new Term[] { new Atom("NQueensProblemWithProcessingAndProlog/resources/n_queens.pl") });
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
		String result=prepareResultStringAndContainer(this.N,queens);
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

	// method to prepare queenPlaceholders in resultString and set queenPositions
	private String prepareResultStringAndContainer(int queenNumber, Set<List<Integer>> queens){
		StringBuilder result= new StringBuilder("[");
		//loop to build resultString and use alphabetical characters as placeholders dependent on N
		for(int i = 0; i < queenNumber; i++){
			if(i < 26){
				result.append((char) (65 + i)).append(",");
				queenPositions.add(Character.toString((char)(65 + i)));
			}
			if(i>=26 && i < 52){
				result.append(((char) (65 + i - 26))).append(((char) (65 + i - 26))).append(",");
				queenPositions.add((char) (65 + i - 26) +String.valueOf((char)(65 + i-26)));
			}
			if(i>=52 && i<78){
				result.append((char) (65 + i - 52)).append((char) (65 + i - 52)).append((char) (65 + i - 52)).append(",");
				queenPositions.add((char) (65 + i - 52) +String.valueOf((char)(65 + i-52))+ (char) (65 + i - 52));
			}
			if(i>=78 && i<104){
				result.append((char) (65 + i - 78)).append((char) (65 + i - 78)).append((char) (65 + i - 78)).append((char) (65 + i - 78)).append(",");
				queenPositions.add((char) (65 + i - 78) +String.valueOf((char)(65 + i-78))+ (char) (65 + i - 78) + (char) (65 + i - 78));
			}
		}

		//replace placeholders with actual queenPosition if available
		for (List<Integer> index : queens) {
			int y = index.get(1);
			y++;
			queenPositionMap.put(queenPositions.get(index.get(0)), String.valueOf(y));
			if(result.toString().contains(queenPositions.get(index.get(0)))){
				result = new StringBuilder(result.toString().replaceFirst(queenPositions.get(index.get(0)), String.valueOf(y)));
			}
		}
		//System.out.println(result);
		result = new StringBuilder(result.substring(0, result.length() - 1));
		result.append("]");
		return result.toString();
	}
}
