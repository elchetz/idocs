/**
 * 
 */
package gr.elchetz.idocs;

import java.io.IOException;
import java.util.Set;

/**
 * @author elchetz
 * 
 */
public interface LineBuffer extends Comparable<LineBuffer>{

	boolean hasMore();

	boolean contains(String word);

	void next() throws IOException;
	
	String last();
	
	Set<String> getContents();

}
