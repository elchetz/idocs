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
import java.io.Writer;
import java.util.Comparator;
import java.util.List;

import com.google.code.externalsorting.ExternalSort;

/**
 * @author elchetz
 * 
 */
public class FindCommonWords {

	private static final String SORTED_SUFFIX = ".sorted";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length < 3) {
			System.out
					.println(String.format("Usage: java %s file1 file2 outFile", FindCommonWords.class.getName()));
		} else {

			long start = System.currentTimeMillis();

			Comparator<String> comparator = new Comparator<String>() {
				public int compare(String r1, String r2) {
					return r1.compareTo(r2);
				}
			};

			List<File> tempFiles;
			File[] inputFiles = {new File(args[0] + SORTED_SUFFIX), new File(args[1] + SORTED_SUFFIX)};

			try {
				for (int i = 0; i < 2; i++) {
					tempFiles = ExternalSort.sortInBatch(new File(args[i]), comparator);
					ExternalSort.mergeSortedFiles(tempFiles, inputFiles[i] , comparator, true);
					inputFiles[i].deleteOnExit();
				}
			} catch (IOException e1) {
				e1.printStackTrace();
				System.exit(1);
			}

			System.out.println("File Sorting Time: "
					+ (System.currentTimeMillis() - start) + "ms");

			start = System.currentTimeMillis();

			FindCommonWords findCommonWords = new FindCommonWords();
			try {
				BufferedWriter out = new BufferedWriter(new FileWriter(args[2]));
				findCommonWords.findCommonWords(inputFiles[0], inputFiles[1], out);
				out.close();
			} catch (IOException e) {
				System.err.println("Ooops!");
				e.printStackTrace();
			}

			System.out.println("File Processing: "
					+ (System.currentTimeMillis() - start) + "ms");
			
			System.out.println(String.format("Common words in files %s and %s are saved in file %s", args[0], args[1], args[2]));

		}

	}

	/**
	 * Finds common words in two files which contain one word per line.
	 * 
	 * @param file1
	 *            The first file.
	 * @param file2
	 *            The second file.
	 * @param bufferSize
	 *            The size of the {@link LineBuffer}s used.
	 * @param writer
	 *            The writer to use in order to write common words.
	 * @throws IOException
	 *             In case reading the files fails.
	 * @since v0.1.0
	 */
	public void findCommonWords(File file1, File file2, Writer writer)
			throws IOException {

		String lineSep = System.getProperty("line.separator");
		String lineFile1;
		String lineFile2;

		BufferedReader r1 = new BufferedReader(new FileReader(file1));
		BufferedReader r2 = new BufferedReader(new FileReader(file2));
		lineFile1 = r1.readLine();
		lineFile2 = r2.readLine();

		while (lineFile1 != null && lineFile2 != null) {
			if (lineFile1.equals(lineFile2)) {
				writer.write(lineFile1 + lineSep);
			}

			int order = lineFile1.compareTo(lineFile2);

			if (order == 0) {
				lineFile1 = r1.readLine();
				lineFile2 = r2.readLine();
			} else if (order < 0) {
				lineFile1 = r1.readLine();
			} else {
				lineFile2 = r2.readLine();
			}
		}

		r1.close();
		r2.close();
	}
}
