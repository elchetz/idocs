/**
 * 
 */
package gr.elchetz.idocs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author elchetz
 * 
 */
public class LineBufferImpl implements LineBuffer {
	private BufferedReader inputBuffer;
	private int bufferSize;
	private TreeSet<String> lineBuffer;
	private boolean moreDataInFile=true;
	private boolean moreDataInBuffer;

	public LineBufferImpl(File f, int bufferSize) throws IOException {
		this.inputBuffer = new BufferedReader(new FileReader(f));
		this.bufferSize = bufferSize;
		this.lineBuffer = new TreeSet<String>();
		this.next();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gr.elchetz.idocs.LineBuffer#hasMore()
	 */
	public boolean hasMore() {
		return moreDataInFile || moreDataInBuffer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gr.elchetz.idocs.LineBuffer#contains(java.lang.String)
	 */
	public boolean contains(String word) {
		return lineBuffer.contains(word);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gr.elchetz.idocs.LineBuffer#next()
	 */
	public void next() throws IOException {
		if (!hasMore()){
			return;
		}
		String line;
		int linesRead = 0;
		lineBuffer.clear();
		moreDataInBuffer=false;
		
		while ((line = inputBuffer.readLine()) != null) {
			moreDataInBuffer=true;
			linesRead++;
			lineBuffer.add(line);
			if (linesRead == bufferSize){
				break;
			}
		}

		if (linesRead < bufferSize) {
			inputBuffer.close();
			moreDataInFile = false;
		} else {
			moreDataInFile = true;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gr.elchetz.idocs.LineBuffer#last()
	 */
	public String last() {
		return lineBuffer.last();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(LineBuffer other) {
		return this.last().compareTo(other.last());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gr.elchetz.idocs.LineBuffer#getContents()
	 */
	public Set<String> getContents() {
		moreDataInBuffer=false;
		return Collections.unmodifiableSortedSet(lineBuffer);
	}

}
