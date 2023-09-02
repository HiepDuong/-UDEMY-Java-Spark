package com.RDDSpark.RDDSpark.distinct;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.SparkConf;

import java.util.Arrays;

public class DistinctExample {
    public static void main(String[] args) {


        SparkConf sparkConf = new SparkConf().setAppName("airports").setMaster("local[3]");
        JavaSparkContext jsc = new JavaSparkContext(sparkConf);

        JavaRDD<Integer> rdd = jsc.parallelize(Arrays.asList(1, 2, 3, 1, 2, 3, 4, 5));
        JavaRDD<Integer> distinctRDD = rdd.distinct();
        System.out.println(distinctRDD.collect().toString());
    }
}
