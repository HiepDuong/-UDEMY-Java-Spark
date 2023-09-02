package com.RDDSpark.RDDSpark.subtract;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.SparkConf;

import java.util.Arrays;

public class SubtractApplication {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName("SubtractExample").setMaster("local[1]");
        JavaSparkContext sc = new JavaSparkContext(conf);

        // Create two sample RDDs
        JavaRDD<Integer> rdd1 = sc.parallelize(Arrays.asList(1, 2, 3, 4, 5));
        JavaRDD<Integer> rdd2 = sc.parallelize(Arrays.asList(4, 5, 6, 7, 8));

        // Subtract rdd2 from rdd1
        JavaRDD<Integer> subtracted = rdd1.subtract(rdd2);

        // Output the result // 1 2 3, because 1,2,3 not include in second RDD
        System.out.println("Subtracted result");
        for (Integer num : subtracted.collect()) {
            System.out.println(num);
        }

        sc.close();
    }
}
