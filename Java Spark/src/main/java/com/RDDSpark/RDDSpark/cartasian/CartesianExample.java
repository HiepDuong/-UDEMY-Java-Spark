package com.RDDSpark.RDDSpark.cartasian;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.SparkConf;
import scala.Tuple2;

import java.util.Arrays;

public class CartesianExample {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName("CartesianExample").setMaster("local");
        JavaSparkContext sc = new JavaSparkContext(conf);

        JavaRDD<Integer> rdd1 = sc.parallelize(Arrays.asList(1, 2, 3));
        JavaRDD<String> rdd2 = sc.parallelize(Arrays.asList("A", "B"));

        JavaPairRDD<Integer, String> cartesianResult = rdd1.cartesian(rdd2);

        System.out.println(cartesianResult.collect());
    }
}
