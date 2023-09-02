# This project is from Apache Spark 2.0 with Java -Learn Spark from a Big Data Guru by Tao. W


Here's a step-by-step guide on setting up Java 8, Hadoop, and running a Spark job using a JAR file

# 1. Setting up Java 8:
## a. Download Java 8 from Oracle:

Navigate to Oracle's Java SE Downloads. https://www.oracle.com/java/technologies/javase/javase8-archive-downloads.html

Download the appropriate version of Java SE Development Kit 8uXXX (where XXX is the latest revision number) for your operating system.

Follow the installation instructions
.
## b. Set Java Environment Variables:

Windows:

Right-click on My Computer or This PC and select Properties.
Click on Advanced system settings and then on the Environment Variables button.
Under 'System Variables', click New. Set the Variable name as JAVA_HOME and Variable value as the path to your Java JDK directory (e.g., C:\Program Files\Java\jdk1.8.0_XXX).
Edit the Path variable in 'System Variables' and add %JAVA_HOME%\bin at the end.

# 2. Setting up Hadoop:
## a. Download Hadoop from GitHub:

Navigate to the GitHub repository: https://github.com/steveloughran/winutils

Download the repository as a ZIP file and extract it to a location on your computer (e.g., C:\hadoop).

## b. Set Hadoop Environment Variables:

Windows:

Go back to the Environment Variables window.

Under 'System Variables', click New. Set the Variable name as HADOOP_HOME and Variable value as the path to your extracted Hadoop directory (e.g., C:\hadoop).

Edit the Path variable in 'System Variables' and add %HADOOP_HOME%\bin at the end.

# 3. Setting up Spark
## a. Download Spark 8:

Navigate to https://spark.apache.org/downloads.html

Download the repository as a ZIP file and extract it to a location on your computer (e.g., C:\Spark).

## b. Set Spark Environment Variables:

Windows:

Go back to the Environment Variables window.

Under 'System Variables', click New. 

Set SPARK_HOME to the path where you extracted Spark, e.g., C:\spark.

Edit the Path variable in 'System Variables' and add %SPARK_HOME%\bin at the end.

# 3. Running the application
## 1. Run with any IDEs (Eclipse, IOntelliJ)

## 2. spark submit

go to the folder of the project and open the terminal there or cd path_to_your_project_directory

spark submit com.package.classNameExample  /target/demo-0.0.1-SNAPSHOT.jar
