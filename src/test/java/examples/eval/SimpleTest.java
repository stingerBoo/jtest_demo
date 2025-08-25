/**
 * 
 */
package examples.eval;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Parasoft Jtest UTA: Test class for Simple
 *
 * @see examples.eval.Simple
 * @author sgrabinski
 */
public class SimpleTest {

	/**
	 * Parasoft Jtest UTA: Test for map(int)
	 *
	 * @see examples.eval.Simple#map(int)
	 * @author sgrabinski
	 */
	@Test(timeout = 5000)
	public void testMap() throws Throwable {
		// When
		int index = 0; // UTA: configured value
		int result = Simple.map(index);

		// Then - assertions for result of method map(int)
		assertEquals(-1, result);

	}

	/**
	 * Parasoft Jtest UTA: Test for map(int)
	 *
	 * @see examples.eval.Simple#map(int)
	 * @author sgrabinski
	 */
	@Test(timeout = 5000)
	public void testMap2() throws Throwable {
		// When
		int index = 2; // UTA: configured value
		int result = Simple.map(index);

		// Then - assertions for result of method map(int)
		assertEquals(0, result);

	}

	/**
	 * Parasoft Jtest UTA: Test for map(int)
	 *
	 * @see examples.eval.Simple#map(int)
	 * @author sgrabinski
	 */
	@Test(timeout = 5000)
	public void testMap3() throws Throwable {
		// When
		int index = 1; // UTA: configured value
		int result = Simple.map(index);

		// Then - assertions for result of method map(int)
		assertEquals(-2, result);

	}
}
