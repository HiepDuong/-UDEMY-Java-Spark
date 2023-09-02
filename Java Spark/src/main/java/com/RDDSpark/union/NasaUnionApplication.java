package com.RDDSpark.union;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

public class NasaUnionApplication {
       /* "in/nasa_19950701.tsv" file contains 10000 log lines from one of NASA's apache server for July 1st, 1995.
           "in/nasa_19950801.tsv" file contains 10000 log lines for August 1st, 1995

           Create a Spark program to generate a new RDD which contains the log lines from both July 1st and August 1st,
           take a 0.1 sample of those log lines and save it to "out/sample_nasa_logs.tsv" file.

           Keep in mind, that the original log files contains the following header lines.
           host	logname	time	method	url	response	bytes

           Make sure the head lines are removed in the resulting RDD.
         */

    public static void main(String[] args) throws Exception {
        SparkConf conf = new SparkConf().setAppName("airports").setMaster("local[2]");

        JavaSparkContext sc = new JavaSparkContext(conf);

        JavaRDD<String> nasaJuly = sc.textFile("in/nasa_19950701.tsv");
        JavaRDD<String> nasaAug = sc.textFile("in/nasa_19950801.tsv");

        JavaRDD<String> unionNasaLog = nasaJuly.union(nasaAug);
        JavaRDD<String> cleanUnionNasaLog =unionNasaLog.filter(line -> isNotHeader(line));

        JavaRDD<String> result = cleanUnionNasaLog.sample(true, 0.1);
        result.saveAsTextFile("out/union_sample_nasa.text");

    }
    private static boolean isNotHeader(String line){
        return !(line.startsWith("host") && line.contains("byte"));
    }
}
