/**
 * 
 */
package tem.vilbel.com.prolog;

import java.util.Collection;
import java.util.Map;

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
public class Prolog {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JPL.init();
		Query q1 = new Query("consult",
				new Term[] { new Atom("./resources/n_queens.pl") });

		System.out.println(q1.hasSolution());

		/*
		 * H = 1, N = 8, n_queens(N, [A, B, C, D, E, F, G, H]), labeling([ff], [A, B, C,
		 * D, E, F, G]).
		 */

		Variable N = new Variable("N");

		Variable A = new Variable();
		Variable B = new Variable();
		Variable C = new Variable();
		Variable D = new Variable();
		Variable E = new Variable();
		Variable F = new Variable();
		Variable G = new Variable();
		Variable H = new Variable();

		Query goal = new Query(
				"H = 1, N = 8, n_queens(N, [A, B, C, D, E, F, G, H]), labeling([ff], [A, B, C, D, E, F, G]).");
//		Query goal = new Query("H = 1, N = 8, n_queens(N, [A, B, C, D, E, F, G, H]), labeling([ff], [A, B, C, D, E, F, G]).");

		String t1 = "n_queens(8, Qs), labeling([ff], Qs).";
		String t2 = "H = 1, N = 8, n_queens(N, [A, B, C, D, E, F, G, H]), labeling([ff], [A, B, C, D, E, F, G]).";

		System.out.println(Query.hasSolution(t1));
		Map<String, Term>[] allSolutions = Query.allSolutions(t1);

		for (Map<String, Term> map : allSolutions) {
			Collection<Term> values = map.values();
			for (Term term : values) {
				Term[] terms = term.toTermArray();
				for (Term ints : terms) {
					
					System.out.print(ints + ", ");
				}
				System.out.println("");
			}
		}

	}

}
