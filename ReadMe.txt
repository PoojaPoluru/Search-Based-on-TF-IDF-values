                                              Search-Based-on-TF-IDF-values
------------------------------------------------------------------------------------------------
Compiler  :Java Compiler
Language :Java
IDE          :Eclipse
--------------------------------------------------------------------------------------------------------

Compilation and execution using the Terminal
Step to create folder in HDFS: Commands to create input folder in hdfs
1.	sudo su hdfs
2.	Hadoop fs -mkdir /user/cloudera
3.	Hadoop fs -chown cloudera /user/cloudera
4.	Exit
5.	Sudo su cloudera
6.	Hadoop fs -mkdir /user/cloudera/input
 Adding files to HDFS
Step 1: Place the input files in HDFS.
Command: hadoop fs -put < CanterburyFile folder Path> <Path in HDFS>
Example: Hadoop fs -put /home/cloudera/Downloads/cantrbry/Canterbury/ /user/cloudera/input
--------------------------------------------------------------------------------------------------------------------------------------

DocWordCount

Step 1: Compile the java file	
Command: 
1.	mkdir -p build
2.	Java -cp /usr/lib/hadoop/*:/usr/lib/hadoop-mapreduce/* <JAVA FILE PATH> -d build -Xlint
Example: Java -cp /usr/lib/hadoop/*:/usr/lib/hadoop-mapreduce/* /home/cloudera/workspace/training/src/DocWordCount.java -d build -Xlint
Step 2: JAR Generation
Command:  jar -cvf <JAR PATH> -C build/ .
Example: jar -cvf DocWordCount.jar -C build/ .
Step 3: Running JAR
Command: Hadoop jar <JAR PATH> <Package Name of JAVA CLASS> <Input Folder Path in HDFS> <Output Folder Path in HDFS>
Example: Hadoop jar DocWordCount.jar DocWordCount  /user/cloudera/input /user/cloudera/DocWordCoun.out
*Delete the output Folder or change the Output folder Name during Execution.
-Command to delete output folder: Hadoop fs -rm -r <Output Folder Path>
-Example: Hadoop fs -rm -r /home/cloudera/DocWordCount.out
--------------------------------------------------------------------------------------------------------------------------------------
TermFrequency
Executing Commands: <Input File Folder Path> <Output Folder Name Path>
Example: /home/cloudera/Downloads/cantrbry/ /home/cloudera/Downloads/Output/TermFrequency
Step 1: Compile java file
Command: 
1.	mkdir -p build
2.	Java -cp /usr/lib/hadoop/*:/usr/lib/hadoop-mapreduce/* <JAVA FILE PATH> -d build -Xlint
 Java -cp /usr/lib/hadoop/*:/usr/lib/hadoop-mapreduce/* /home/cloudera/workspace/training/src/TermFrequency.java -d build -Xlint
Step 2: JAR Generation
Command:  jar -cvf <JAR PATH> -C build/ .
Example: jar -cvf TermFrequency.jar -C build/ .
Step 3: Running JAR
Command: Hadoop jar <JAR PATH> <Package Name of JAVA CLASS> <Input Folder Path in HDFS> <Output Folder Path in HDFS>
Example: Hadoop jar TermFrequency.jar TermFrequency/user/cloudera/input /user/cloudera/TermFrequency.out
*Delete the output Folder or change the Output folder Name as mentioned above.
--------------------------------------------------------------------------------------------------------------------------------------		
TFIDF

Step 1: Compile java file
Command: 
1.	mkdir -p build
2.	Java -cp /usr/lib/hadoop/*:/usr/lib/hadoop-mapreduce/* <JAVA FILE PATH> -d build -Xlint
Example: Java -cp /usr/lib/hadoop/*:/usr/lib/hadoop-mapreduce/* /home/cloudera/workspace/training/src/TFIDF.java -d build -Xlint
Step 2: JAR Generation
Command:  jar -cvf <JAR PATH> -C build/ .
Example: jar -cvf TFIDF.jar -C build/ .
Step 3: Running JAR
Command: Hadoop jar <JAR PATH> <Package Name of JAVA CLASS> <Input Folder Path in HDFS> <Intermediate Folder Path> <Output Folder Path in HDFS>
Example: Hadoop jar TFIDF.jar org.myorg.TFIDF/ user/cloudera/input user/cloudera/Intermediate.out /user/cloudera/TFIDF.out
*Delete the output Folder or change the Output folders Name as mentioned above.
--------------------------------------------------------------------------------------------------------------------------------------

Search:

Step 1: Compile java file	
Command: 
1.	mkdir -p build
2.	Java -cp /usr/lib/hadoop/*:/usr/lib/hadoop-mapreduce/* <JAVA FILE PATH> -d build -Xlint
Example: Java -cp /usr/lib/hadoop/*:/usr/lib/hadoop-mapreduce/* /home/cloudera/workspace/training/src/Search.java -d build -Xlint
Step 2: JAR Generation
Command:  jar -cvf <JAR PATH> -C build/ .
Example: jar -cvf Search.jar -C build/ .
Step 3: Running JAR
Command: Hadoop jar <JAR PATH> <Package Name of JAVA CLASS> <Input Folder Path in HDFS> <Output Folder Path in HDFS> <QUERY>
Example 1: Hadoop jar Search.jar org.myorg.Search/ user/cloudera/input /user/cloudera/Search.out computer science
Delete the output Folder or change the Output folder Name during Execution as mentioned above.

