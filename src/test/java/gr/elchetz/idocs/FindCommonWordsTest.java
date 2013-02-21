package gr.elchetz.idocs;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FindCommonWordsTest {

	private static final String TEST_FILE1_TXT = "test-file1.txt";
	private File file1;
	private FindCommonWords testMe;

	@Before
	public void setUp() throws Exception {

		this.testMe = new FindCommonWords();
		this.file1 = new File(this.testMe.getClass().getClassLoader()
				.getResource(TEST_FILE1_TXT).toURI());
	}

	@After
	public void tearDown() throws Exception {
		this.testMe = null;
		this.file1 = null;
	}

	@Test
	public void testReadFileOne() throws Exception {
		List<String> result = testMe.readFileOne(file1);
		assertEquals(result.size(), 5);
		assertEquals(result.get(0), "c-word");
		assertEquals(result.get(1), "a-word");
		assertEquals(result.get(2), "f-word");
		assertEquals(result.get(3), "e-word");
		assertEquals(result.get(4), "b-word");
	}
	
	@Test
	public void parseFileTwo() throws Exception {
		List<String> words = new ArrayList<String>();
		words.add("e-word");
		words.add("a-word");
		words.add("c-word");
		Set<String> result = testMe.parseFileTwo(file1, words);
		assertEquals(result.size(), 3);
		Iterator<String> iter = result.iterator();
		assertEquals(iter.next(), "a-word");
		assertEquals(iter.next(), "c-word");
		assertEquals(iter.next(), "e-word");
		
	}

}
