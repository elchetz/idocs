1. The limitation of the current implementation is due to the fact that both file1 and common words found are kept in String Lists, 
hence if the file1 is huge in size we might get into out of memory issues.

2. If ram is not an issue then we could allocate more memory to the JVM. Otherwise I would go to 3 and 4 below.

3. I wouldn't say that my program is efficient, probably using some more advances Libraries could improve performance and memory usage 
significantly.

4. One idea in order to increase the performance of the program would be to sort the two input files first, and then parse them as streams.
Without loading any of them into memory. Also an idea would be to do this using multiple threads to read parts of the files in parallel.


Notes:
The program currently does not care about:
- file encoding 
- whitespace
- proper exception handling


Building the project:
The project can be build using manven2 or 3 with:
> mvn clean package

Running the project:
> java -jar target/idocs-workshop-0.0.1-SNAPSHOT.jar inFile1 inFile2 outResultsFile


Changelog:

v0.0.2
------
- always load in memory the smallest file
- keep matches to TreeSet in order to eliminate duplicates
- printing execution time info

v0.1.0
------
- sorting the input files before processing 
 (used open source implementation of sort-merge algorithm, from http://code.google.com/p/externalsortinginjava/)
- no input files are loaded in memory, just buffered from input stream
- matching results are not kept in memory, written to out-put stream directly

- there is still a known issue: 
	some common words are saved in the outfile two or more times, this is because they 
	are multiple copies of them found in the input files and they spread across multiple buffered reads. 
	A possible solution to this could be keeping track of the last word written to the output and if the 
	new word found is the same, skip it.

- to run this version:
	> mvn clean package
	> cp lib/externalsortinginjava-0.1.1.jar target/
	> java -jar target/idocs-workshop-0.1.0-SNAPSHOT.jar inFile1 inFile2 outResultsFile
	
v0.1.1
------
- Fixed the bug that was known in v0.1.0, common words are now written to the output file only once

v0.1.2
------
- Contributed to externalsortinginjava project with a sort/distinct feature, and used that instead
	(currently in feature branch http://externalsortinginjava.googlecode.com/svn/branches/sort_distinct/)
- Simplified the code since there are no duplicates expected anymore (and other over-engineered crap).
- Improved dependency management 
	(since externalsortinginjava is not on a public repository at the moment a system dependency is used pointing to the jar in lib/)
- Added classpath reference to manifest file, pointing to ../lib/externalsortinginjava-0.1.2.jar in order to be able to execute the jar directly from target/ 
	(this is not recommended for normal projects, where a nice packaging should be done)
- Performance has improved more (from ~800ms to ~25ms) for the sample files (sorting remains around the same ~500ms)
 
- to run this version:
	
	build:
	> mvn clean package
	
	run with the sample input files:
	> ./runSample.sh  
	
	or run manually:
	> java -jar target/idocs-workshop-0.1.2-SNAPSHOT.jar inFile1 inFile2 outResultsFile

v0.1.3
------
- Removed system dependency to "../lib/externalsortinginjava-0.1.2.jar"
- Removed lib/
- Added dependency to com.google.code:externalsortinginjava:0.1.2-SNAPSHOT
- Added repository snapshots.externalsortinginjava.googlecode.com in order 
  to resolve the above dependency
 
- to run this version:
	
	build:
	> mvn clean package 
	
	run with the sample input files:
	> ./runSample.sh  
	
	or run manually:
	> java -jar target/idocs-workshop-0.1.3-SNAPSHOT.jar inFile1 inFile2 outResultsFile
  
	
