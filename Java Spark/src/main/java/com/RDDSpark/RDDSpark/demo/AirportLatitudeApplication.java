package com.RDDSpark.RDDSpark.demo;

import com.RDDSpark.utils.Utils;
import org.apache.commons.lang.StringUtils;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.storage.StorageLevel;

public class AirportLatitudeApplication {
        public static void main(String[] args) throws Exception {
            SparkConf conf = new SparkConf().setAppName("airports").setMaster("local[2]");

            JavaSparkContext sc = new JavaSparkContext(conf);

            JavaRDD<String> airports = sc.textFile("in/airports.text");

            JavaRDD<String> airportsInUSA = airports.filter(line -> Float.parseFloat(line.split(Utils.COMMA_DELIMITER)[6]) > 40);

            JavaRDD<String> airportsNameAndCityNames = airportsInUSA.map(line -> {
                        String[] splits = line.split(Utils.COMMA_DELIMITER);
                        return StringUtils.join(new String[]{splits[1], splits[6]}, ",");
                    }
            );
            airportsNameAndCityNames.persist(StorageLevel.MEMORY_AND_DISK());
            airportsNameAndCityNames.saveAsTextFile("out/airport_latitude.text");

        }
    }




