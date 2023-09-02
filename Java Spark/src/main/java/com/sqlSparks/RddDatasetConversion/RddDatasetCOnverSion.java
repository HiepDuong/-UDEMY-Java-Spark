package com.sqlSparks.RddDatasetConversion;

import com.sqlSparks.ResponseDTO;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.SparkSession;

import static com.RDDSpark.utils.Utils.COMMA_DELIMITER;

public class RddDatasetCOnverSion {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName("RddConversion").setMaster("local[1]");
        JavaSparkContext sc = new JavaSparkContext(conf);
        SparkSession session =SparkSession.builder().appName("RddConversion").master("local[1]").getOrCreate();
        JavaRDD<String> lines = sc.textFile("in/2016-stack-overflow-survey-responses.csv");

        JavaRDD<ResponseDTO> responseRDD = lines
                .filter(line -> !line.split(COMMA_DELIMITER, -1)[2].equals("country"))
                .map(line -> {
                    String[] splits = line.split(COMMA_DELIMITER, -1);
                    return new ResponseDTO(splits[2], toInt(splits[6]), splits[9], toInt(splits[14]));
                });
        //this convert from RDD spark to Spark SQL
        Dataset<ResponseDTO> responseDataset = session.createDataset(responseRDD.rdd(), Encoders.bean(ResponseDTO.class));

        System.out.println("=== Print out schema ===");
        responseDataset.printSchema();

        System.out.println("=== Print 20 records of responses table ===");
        responseDataset.show(20);

        JavaRDD<ResponseDTO> responseJavaRDD = responseDataset.toJavaRDD();
        for (ResponseDTO response : responseJavaRDD.collect()) {
            System.out.println(response);
        }
    }
    private static Integer toInt(String split) {
        return split.isEmpty() ? null : Math.round(Float.valueOf(split));
    }

}
