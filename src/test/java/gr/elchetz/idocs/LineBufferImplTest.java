/**
 * 
 */
package gr.elchetz.idocs;

import static org.junit.Assert.assertEquals;
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
		int i =0;
		while(testMe.hasMore()){
			testMe.getContents();
			testMe.next();
			i++;
		}
		assertEquals(11, i);
	}

	/**
	 * Test method for
	 * {@link gr.elchetz.idocs.LineBufferImpl#contains(java.lang.String)}.
	 * @throws IOException 
	 */
	@Test
	public void testContains() throws IOException {
		assertTrue(testMe.contains("a"));
		assertTrue(testMe.contains("b"));
		assertTrue(testMe.contains("c"));
		testMe.next();
		assertTrue(testMe.contains("d"));
		assertTrue(testMe.contains("e"));
		assertTrue(testMe.contains("f"));
		
	}

	/**
	 * Test method for {@link gr.elchetz.idocs.LineBufferImpl#next()}.
	 * @throws IOException 
	 */
	@Test
	public void testNext() throws IOException {
		testMe.next();
		assertEquals("f", testMe.last());
	}

	/**
	 * Test method for {@link gr.elchetz.idocs.LineBufferImpl#last()}.
	 */
	@Test
	public void testLast() {
		assertEquals("c", testMe.last());
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
		assertTrue(b1.compareTo(b2)<0);
		assertTrue(b2.compareTo(b1)>0);
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
		assertEquals(3, testMe.getContents().size());
		for(int i=0;i<9;i++){
			testMe.next();
		}
		assertNotNull(testMe.getContents());
		assertEquals(1, testMe.getContents().size());
	}

}
