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
import java.util.Collections;
import java.util.List;

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
			File file1 = new File(args[0]);
			File file2 = new File(args[1]);
			FindCommonWords findCommonWords = new FindCommonWords();
			try {
				findCommonWords.writeFile(
						findCommonWords.parseFileTwo(file2,
								findCommonWords.readFileOne(file1)), args[2]);
			} catch (IOException e) {
				System.err.println("Oops!");
				e.printStackTrace();
			}
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
	protected List<String> parseFileTwo(File file, List<String> words)
			throws IOException {
		List<String> matches = new ArrayList<String>();
		BufferedReader buffer = new BufferedReader(new FileReader(file));
		String line;
		while ((line = buffer.readLine()) != null) {
			if (words.contains(line)) {
				matches.add(line);
			}
		}
		buffer.close();
		Collections.sort(matches);
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
	protected void writeFile(List<String> commonWords, String outFile)
			throws IOException {
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(
				outFile)));
		for (String word : commonWords) {
			out.println(word);
		}
		out.close();
	}
}
