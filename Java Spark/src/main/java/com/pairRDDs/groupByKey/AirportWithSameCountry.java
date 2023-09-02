package com.pairRDDs.groupByKey;

import com.RDDSpark.utils.Utils;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.PairFunction;

import scala.Tuple2;

import java.util.Map;
public class AirportWithSameCountry {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName("airPortSameCountry").setMaster("local[2]");

        JavaSparkContext sc = new JavaSparkContext(conf);

        JavaRDD<String> lines = sc.textFile("in/airports.text");
        JavaPairRDD<String, String> pairOfCountryAndAirport = lines.
                mapToPair((PairFunction<String,String, String>) line ->
                        new Tuple2<String, String>(line.split(Utils.COMMA_DELIMITER)[3], line.split(Utils.COMMA_DELIMITER)[1]));

        JavaPairRDD<String, Iterable<String>> pairOfCountryAndListAirPort = pairOfCountryAndAirport.groupByKey();
        //mapValues wihtout changing Key

        for(Map.Entry<String, Iterable<String>> entry : pairOfCountryAndListAirPort.collectAsMap().entrySet()){
            System.out.println(entry.getKey() + " " + entry.getValue());
        }

    }



}

