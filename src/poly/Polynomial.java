package poly;

import java.io.IOException;
import java.util.Scanner;

/**
 * This class implements evaluate, add and multiply for polynomials.
 * 
 * @author runb-cs112
 *
 */
public class Polynomial {

	/**
	 * Reads a polynomial from an input stream (file or keyboard). The storage
	 * format of the polynomial is:
	 * 
	 * <pre>
	 *     <coeff> <degree>
	 *     <coeff> <degree>
	 *     ...
	 *     <coeff> <degree>
	 * </pre>
	 * 
	 * with the guarantee that degrees will be in descending order. For example:
	 * 
	 * <pre>
	 *      4 5
	 *     -2 3
	 *      2 1
	 *      3 0
	 * </pre>
	 * 
	 * which represents the polynomial:
	 * 
	 * <pre>
	 * 4 * x ^ 5 - 2 * x ^ 3 + 2 * x + 3
	 * </pre>
	 * 
	 * @param sc
	 *            Scanner from which a polynomial is to be read
	 * @throws IOException
	 *             If there is any input error in reading the polynomial
	 * @return The polynomial linked list (front node) constructed from coefficients
	 *         and degrees read from scanner
	 */
	public static Node read(Scanner sc) throws IOException {
		Node poly = null;
		while (sc.hasNextLine()) {
			Scanner scLine = new Scanner(sc.nextLine());
			poly = new Node(scLine.nextFloat(), scLine.nextInt(), poly);
			scLine.close();
		}
		return poly;
	}

	/**
	 * Returns the sum of two polynomials - DOES NOT change either of the input
	 * polynomials. The returned polynomial MUST have all new nodes. In other words,
	 * none of the nodes of the input polynomials can be in the result.
	 * 
	 * @param poly1
	 *            First input polynomial (front of polynomial linked list)
	 * @param poly2
	 *            Second input polynomial (front of polynomial linked list
	 * @return A new polynomial which is the sum of the input polynomials - the
	 *         returned node is the front of the result polynomial
	 */
	
	public static Node add(Node poly1, Node poly2) {
		/** COMPLETE THIS METHOD **/
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE THIS METHOD COMPILE
		// CHANGE IT AS NEEDED FOR YOUR IMPLEMENTATION
		Node ptr1 = poly1;
		Node ptr2 = poly2;
		Node front = null;
		Float coef;
		int deg;
		if (ptr1 == null && ptr2 == null) {
			return null;
		}
		if (ptr1 == null && ptr2 != null) {
			while (ptr2 != null) {
				front = addToBack(front, ptr2.term.coeff, ptr2.term.degree);
				ptr2 = ptr2.next;
			}
			return front;
		}
		if (ptr2 == null && ptr1 != null) {
			while (ptr1 != null) {
				front = addToBack(front, ptr1.term.coeff, ptr1.term.degree);
				ptr1 = ptr1.next;
			}
			return front;
		}
		// Creating the new head node
		if (ptr1.term.degree == ptr2.term.degree) {
			coef = ptr1.term.coeff + ptr2.term.coeff;
			deg = ptr1.term.degree;
			if (coef != 0) {
			front = new Node(coef, deg, null);
			ptr1 = ptr1.next;
			ptr2 = ptr2.next;
			}else {
				ptr1 = ptr1.next;
				ptr2 = ptr2.next;
			}
		} else if (ptr1.term.degree > ptr2.term.degree) {
			front = new Node(ptr2.term.coeff, ptr2.term.degree, null);
			ptr2 = ptr2.next;
		} else if (ptr2.term.degree > ptr1.term.degree) {
			front = new Node(ptr1.term.coeff, ptr1.term.degree, null);
			ptr1 = ptr1.next;
		}

		while (ptr1 != null || ptr2 != null) {
			if (ptr1 == null) {
				while (ptr2 != null) {
					front = addToBack(front, ptr2.term.coeff, ptr2.term.degree);
					ptr2 = ptr2.next;
				}
				break;
			}
			if (ptr2 == null) {
				while (ptr1 != null) {
					front = addToBack(front, ptr1.term.coeff, ptr1.term.degree);
					ptr1 = ptr1.next;
				}
				break;
			}
			if (ptr1.term.degree == ptr2.term.degree) {
				coef = ptr1.term.coeff + ptr2.term.coeff;
				if (coef != 0) {
					front = addToBack(front, coef, ptr1.term.degree);
				}

				ptr1 = ptr1.next;
				ptr2 = ptr2.next;
			} else if (ptr1.term.degree > ptr2.term.degree) {
				front = addToBack(front, ptr2.term.coeff, ptr2.term.degree);
				front = addToBack(front, ptr1.term.coeff, ptr1.term.degree);
				ptr1 = ptr1.next;
				ptr2 = ptr2.next;
			} else if (ptr2.term.degree > ptr1.term.degree) {
				front = addToBack(front, ptr1.term.coeff, ptr1.term.degree);
				front = addToBack(front, ptr2.term.coeff, ptr2.term.degree);

				ptr1 = ptr1.next;
				ptr2 = ptr2.next;
			}
		}
		int count = countSimplify(front);
		for (int i = 0; i < count; i++) {
			front = simplify(front);
		}
		return simplify(front);
	}

	private static Node addToBack(Node head, float co, int deg) {

		Node a = head;

		if (head == null) {
			return new Node(co, deg, null);
		}

		while ((head.next != null)) {
			head = head.next;
		}

		head.next = new Node(co, deg, null);
		return a;
	}

	

	/**
	 * Returns the product of two polynomials - DOES NOT change either of the input
	 * polynomials. The returned polynomial MUST have all new nodes. In other words,
	 * none of the nodes of the input polynomials can be in the result.
	 * 
	 * @param poly1
	 *            First input polynomial (front of polynomial linked list)
	 * @param poly2
	 *            Second input polynomial (front of polynomial linked list)
	 * @return A new polynomial which is the product of the input polynomials - the
	 *         returned node is the front of the result polynomial
	 */
	public static Node multiply(Node poly1, Node poly2) {
		/** COMPLETE THIS METHOD **/
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE THIS METHOD COMPILE
		// CHANGE IT AS NEEDED FOR YOUR IMPLEMENTATION
		Node ptr1 = poly1;
		Node ptr2 = poly2;

		Float coef;
		int deg;
		Node front = null;
		
		if (ptr1 == null && ptr2 == null) {
			return null;
		}
		if (ptr1 == null && ptr2 != null) {
			return null;
		}
		if (ptr2 == null && ptr1 != null) {
			return null;
		}

		// Creating new front node
		coef = ptr1.term.coeff * ptr2.term.coeff;
		deg = ptr1.term.degree + ptr2.term.degree;
		front = new Node(coef, deg, null);
		ptr1 = ptr1.next;

		while (ptr2 != null) {
			while (ptr1 != null) {
				coef = ptr1.term.coeff * ptr2.term.coeff;
				deg = ptr1.term.degree + ptr2.term.degree;
				front = addToBack(front, coef, deg);
				ptr1 = ptr1.next;
				//front = add(ptr1, ptr2);

			}
			ptr2 = ptr2.next;
			ptr1 = poly1;
		}
		// newFront = simplify(front);
	
		front = mergesort(front);
		
		int count = countSimplify(front);
		for (int i = 0; i < count; i++) {
			front = simplify(front);
		}
		return simplify(front);

	}

	// counts the amount of dup terms 
	private static int countSimplify(Node head) {
		Node ptr = head;
		int count = 0;
		while (ptr != null) {
			count++;
			ptr = ptr.next;
		}
		return count;

	}
	
//Sorting algo is used to sort the polynomial by degrees 
	private static Node mergesort(Node head) {
		if (head == null || head.next == null) {
			return head;
		}
		Node middleNode = getMid(head); 
		Node split = middleNode.next;
		middleNode.next = null; 

		return mergeList(mergesort(head), mergesort(split)); 
	}



// Merges the linked lists together once they are sorted 
	private static Node mergeList(Node L1, Node L2) {
		Node tempNode = new Node(0, 0, null);
		Node ptr = tempNode;

		while ( L1 != null && L2 != null) {
			if (L1.term.degree <= L2.term.degree) {
				ptr.next = L1;
				L1 = L1.next;
			} else {
				ptr.next = L2;
				L2 = L2.next;
			}
			ptr = ptr.next;
		}
		if (L1 == null) {
			ptr.next = L2;
		} else {
			ptr.next = L1;
		}

		return tempNode.next;
	}
// Simplifies like terms in the polynomial LL 
	private static Node simplify(Node front) {
		Node ptr = front;
		Node newFront = null;
		Float coeff;
		while (ptr != null) {
			if (ptr.next == null) {
				
				newFront = addToBack(newFront, ptr.term.coeff, ptr.term.degree);
				break;
			}
			if (ptr.term.degree == ptr.next.term.degree) {
				coeff = ptr.term.coeff + ptr.next.term.coeff;
				if (coeff == 0) {
					ptr = ptr.next.next; // added here
					continue;
				} else {
					newFront = addToBack(newFront, coeff, ptr.term.degree);
					ptr = ptr.next.next;
				}
			} else {
				newFront = addToBack(newFront, ptr.term.coeff, ptr.term.degree);
				ptr = ptr.next;
			}

		}
		return newFront;

	}
	//Acquires the middle of the LL
	private static Node getMid(Node front) {
		
		if (front == null) {
			return front;
		}
		Node ptr1 = front;
		
		Node ptr2 = ptr1;
		while (ptr2.next != null && ptr2.next.next != null) {
			
			ptr1 = ptr1.next;
			
			ptr2 = ptr2.next.next;
		}

		return ptr1;
	}

	/**
	 * Evaluates a polynomial at a given value.
	 * 
	 * @param poly
	 *            Polynomial (front of linked list) to be evaluated
	 * @param x
	 *            Value at which evaluation is to be done
	 * @return Value of polynomial p at x
	 */
	public static float evaluate(Node poly, float x) {
		/** COMPLETE THIS METHOD **/
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE THIS METHOD COMPILE
		// CHANGE IT AS NEEDED FOR YOUR IMPLEMENTATION

		// IF NULL CHECK
		double value = 0;
		float ptrcoeff;
		int ptrdeg;
		Node ptr = poly;

		while (ptr != null) {

			ptrcoeff = ptr.term.coeff;
			ptrdeg = ptr.term.degree;
			value = value + (ptrcoeff * (Math.pow(x, ptrdeg)));

			ptr = ptr.next;
		}

		return (float) value;
	}

	/**
	 * Returns string representation of a polynomial
	 * 
	 * @param poly
	 *            Polynomial (front of linked list)
	 * @return String representation, in descending order of degrees
	 */
	public static String toString(Node poly) {
		if (poly == null) {
			return "0";
		}

		String retval = poly.term.toString();
		for (Node current = poly.next; current != null; current = current.next) {
			retval = current.term.toString() + " + " + retval;
		}
		return retval;
	}

}
