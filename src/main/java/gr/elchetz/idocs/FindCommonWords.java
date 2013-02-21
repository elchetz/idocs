/**
 * workshop for i-docs. 
 */
package gr.elchetz.idocs;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author elchetz
 * 
 */
public class FindCommonWords {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length < 3) {
			System.out
					.println("Usage: java FindCommonWords file1 file2 outFile");
		} else {

			File shortFile = new File(args[0]);
			File longFile = new File(args[1]);

			if (shortFile.length() > longFile.length()) {
				File tmpFile = shortFile;
				shortFile = longFile;
				longFile = tmpFile;
			}

			long start = System.currentTimeMillis();
			FindCommonWords findCommonWords = new FindCommonWords();
			try {
				findCommonWords.writeFile(
						findCommonWords.parseFileTwo(longFile,
								findCommonWords.readFileOne(shortFile)),
						args[2]);
			} catch (IOException e) {
				System.err.println("Oops!");
				e.printStackTrace();
			}
			long end = System.currentTimeMillis();
			
			System.out.println("Execution Time: " + (end-start) + "ms");
			
		}

	}

	/**
	 * Reads a text file line by line and adds the lines to a list as strings
	 * which are then returned sorted.
	 * 
	 * @param file
	 *            The text file to read from.
	 * @return A list of strings sorted alphabetically.
	 * @throws IOException
	 *             If it fails to access the file.
	 */
	protected List<String> readFileOne(File file) throws IOException {
		List<String> contents = new ArrayList<String>();

		BufferedReader buffer = new BufferedReader(new FileReader(file));
		String line;
		while ((line = buffer.readLine()) != null) {
			contents.add(line);
		}
		buffer.close();
		return contents;
	}

	/**
	 * Parses the file passed, and the lines found in the <code>words</code>
	 * List are returned.
	 * 
	 * @param file
	 *            The file to parse.
	 * @param words
	 *            The words to lookup.
	 * @return A List of the common words found in <code>file</code> and
	 *         <code>words</code>.
	 * @throws IOException
	 *             If it fails to access the file.
	 */
	protected Set<String> parseFileTwo(File file, List<String> words)
			throws IOException {
		//List<String> matches = new ArrayList<String>();
		Set<String> matches = new TreeSet<String>(); 
		BufferedReader buffer = new BufferedReader(new FileReader(file));
		String line;
		while ((line = buffer.readLine()) != null) {
			if (words.contains(line)) {
				matches.add(line);
			}
		}
		buffer.close();
		return matches;
	}

	/**
	 * Writes the contents of the String List to a text file.
	 * 
	 * @param commonWords
	 *            The List to write.
	 * @param outFile
	 *            The file to write to.
	 * @throws IOException
	 *             If writing to file fails.
	 */
	protected void writeFile(Set<String> commonWords, String outFile)
			throws IOException {
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(
				outFile)));
		for (String word : commonWords) {
			out.println(word);
		}
		out.close();
	}
}
