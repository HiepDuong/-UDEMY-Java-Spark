package com.RDDSpark.RDDSpark.accumulators;

import com.RDDSpark.utils.Utils;
import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.util.LongAccumulator;
import scala.Option;

public class AccumulatorsExampleApplication {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName("Survey").setMaster("local[2]");
        SparkContext sc = new SparkContext(conf);
        JavaSparkContext javaSparkContext = new JavaSparkContext(conf);

        final LongAccumulator total = new LongAccumulator();
        final LongAccumulator missingSalaryMidPoint = new LongAccumulator();
        //total of entry
        total.register(sc, Option.apply("total"), false);
        missingSalaryMidPoint.register(sc, Option.apply("missing salary mid point"), false);
        JavaRDD<String> responseRDD = javaSparkContext.textFile("in/2016-stack-overflow-survey-responses.csv");
        JavaRDD<String> responseFilterFromCanda = responseRDD.filter(response ->{
            String[] splits = response.split(Utils.COMMA_DELIMITER,-1);
            total.add(1);
            if (splits[14].isEmpty()){
                missingSalaryMidPoint.add(1);
            }
            return splits[2].equals("Canada");
                }
        );
        System.out.println("Total count of response " + total.value());
        System.out.println("Response from Canda" + responseFilterFromCanda.count());
        System.out.println("Count of missing response " + missingSalaryMidPoint.value());
    }
}
