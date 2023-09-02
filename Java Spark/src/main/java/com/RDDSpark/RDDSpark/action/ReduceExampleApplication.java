package com.RDDSpark.RDDSpark.action;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.Arrays;
import java.util.List;

public class ReduceExampleApplication {
    public static void main(String[] args) throws Exception {
        Logger.getLogger("org").setLevel(Level.OFF);
        SparkConf conf = new SparkConf().setAppName("take").setMaster("local[*]");
        JavaSparkContext sc = new JavaSparkContext(conf);

        List<Integer> inputNums = Arrays.asList(1, 2, 3, 4, 5, 6, 7);
        JavaRDD<Integer> numsRdd = sc.parallelize(inputNums);

        Integer product = numsRdd.reduce((x, y) -> x * y);
        Integer sum = numsRdd.reduce(Integer::sum);
        System.out.println(product);
        System.out.println(sum);

        JavaRDD<String> lines = sc.textFile("in/prime_nums.text");
        //split when whitespace
        JavaRDD<String> numbersInString = lines.flatMap(line -> Arrays.asList(line.split("\\s+")).iterator());
        JavaRDD<Integer> validateNumbers = numbersInString.filter(number -> !number.isEmpty()).map(Integer::valueOf);
        Integer sumFromInputFile = validateNumbers.reduce(Integer::sum);
        System.out.println(sumFromInputFile);
    }
}
