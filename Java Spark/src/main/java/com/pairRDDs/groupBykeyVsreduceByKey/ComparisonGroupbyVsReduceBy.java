package com.pairRDDs.groupBykeyVsreduceByKey;

import com.google.common.collect.Iterables;
import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.PairFunction;
import scala.Tuple2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ComparisonGroupbyVsReduceBy {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName("comparing").setMaster("local[2]");
        JavaSparkContext sc = new JavaSparkContext(conf);
        List<String> list = Arrays.asList("one","one","two", "two", "two", "three");
        JavaRDD<String> inputs = sc.parallelize(list);
        JavaPairRDD<String, Integer> javaPair = inputs.mapToPair(line -> new Tuple2<>(line, 1));
        //reduce by Key
        List<Tuple2<String, Integer>> reduceByKey = javaPair.reduceByKey((x,y) -> x+y).collect();

        //groupByKey
        JavaPairRDD<String, Iterable<Integer>> groupByKey = javaPair.groupByKey();
        List<Tuple2<String, Integer>> groupByKeyResult= groupByKey.mapValues(count -> Iterables.size(count)).collect();
        System.out.println(reduceByKey);
        System.out.println(groupByKeyResult);

    }
}
