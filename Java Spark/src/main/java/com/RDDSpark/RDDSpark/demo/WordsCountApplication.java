package com.RDDSpark.RDDSpark.demo;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.Arrays;
import java.util.Map;

public class WordsCountApplication {

    public static void main(String[] args) throws Exception {
        Logger.getLogger("org").setLevel(Level.ERROR);
        SparkConf conf = new SparkConf().setAppName("airports").setMaster("local[3]");

        JavaRDD<String> lines;
       JavaSparkContext sc = new JavaSparkContext(conf);

       lines = sc.textFile("in/word_count.text");


        JavaRDD<String> wordsProcess = lines.flatMap(line -> Arrays.asList(line.split(" ")).iterator());

        Map<String, Long> wordsCount = wordsProcess.countByValue();
        for (Map.Entry<String, Long> entry : wordsCount.entrySet()){
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
    }
}
