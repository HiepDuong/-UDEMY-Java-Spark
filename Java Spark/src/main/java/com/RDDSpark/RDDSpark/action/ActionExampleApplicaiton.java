package com.RDDSpark.RDDSpark.action;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.Arrays;
import java.util.List;

public class ActionExampleApplicaiton {
    public static void main(String[] args) throws Exception {
        Logger.getLogger("org").setLevel(Level.OFF);
        SparkConf conf = new SparkConf().setAppName("take").setMaster("local[*]");
        JavaSparkContext sc = new JavaSparkContext(conf);

        List<String> inputWords = Arrays.asList("spark", "hadoop", "spark", "hive", "pig", "cassandra", "hadoop");
        JavaRDD<String> wordRdd = sc.parallelize(inputWords);

        List<String> words = wordRdd.take(6);

        for (String  word : words) {
            System.out.println(word);
        }
    }
}
