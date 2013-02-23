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

