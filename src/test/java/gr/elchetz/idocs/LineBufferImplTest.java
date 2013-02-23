/**
 * 
 */
package gr.elchetz.idocs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author elchetz
 * 
 */
public class LineBufferImplTest {
	private static final String TEST_FILE1_TXT = "test-file1.txt";
	private File file1;
	private LineBufferImpl testMe;
	private int bufferSize = 3;

	@Before
	public void setUp() throws Exception {
		file1 = new File(this.getClass().getClassLoader()
				.getResource(TEST_FILE1_TXT).toURI());
		testMe = new LineBufferImpl(file1, bufferSize);
	}

	@After
	public void tearDown() throws Exception {
		this.testMe = null;
		this.file1 = null;
	}

	/**
	 * Test method for {@link gr.elchetz.idocs.LineBufferImpl#hasMore()}.
	 * @throws IOException 
	 */
	@Test
	public void testHasMore() throws IOException {
		assertTrue(testMe.hasMore());	
		testMe.next();
		assertTrue(testMe.hasMore());
		testMe.getContents();
		assertFalse(testMe.hasMore());
	}

	/**
	 * Test method for
	 * {@link gr.elchetz.idocs.LineBufferImpl#contains(java.lang.String)}.
	 * @throws IOException 
	 */
	@Test
	public void testContains() throws IOException {
		assertTrue(testMe.contains("a-word"));
		assertTrue(testMe.contains("c-word"));
		assertTrue(testMe.contains("f-word"));
		testMe.next();
		assertTrue(testMe.contains("e-word"));
		assertTrue(testMe.contains("b-word"));
		
	}

	/**
	 * Test method for {@link gr.elchetz.idocs.LineBufferImpl#next()}.
	 * @throws IOException 
	 */
	@Test
	public void testNext() throws IOException {
		testMe.next();
		assertEquals("e-word", testMe.last());
	}

	/**
	 * Test method for {@link gr.elchetz.idocs.LineBufferImpl#last()}.
	 */
	@Test
	public void testLast() {
		assertEquals("f-word", testMe.last());
	}

	/**
	 * Test method for
	 * {@link gr.elchetz.idocs.LineBufferImpl#compareTo(gr.elchetz.idocs.LineBuffer)}
	 * .
	 * @throws IOException 
	 */
	@Test
	public void testCompareTo() throws IOException {
		LineBuffer b1 = testMe;
		LineBuffer b2 = new LineBufferImpl(file1, bufferSize);
		assertEquals(0, b1.compareTo(b2));
		b2.next();
		assertEquals(1, b1.compareTo(b2));
		assertEquals(-1, b2.compareTo(b1));
	}

	/**
	 * Test method for {@link gr.elchetz.idocs.LineBufferImpl#getContents()}.
	 * @throws IOException 
	 */
	@Test
	public void testGetContents() throws IOException {
		assertNotNull(testMe.getContents());
		assertEquals(3, testMe.getContents().size());
		testMe.next();
		assertNotNull(testMe.getContents());
		assertEquals(2, testMe.getContents().size());
	}

}
