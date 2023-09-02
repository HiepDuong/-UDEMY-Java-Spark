package com.RDDSpark.RDDSpark.broadcast;

import com.RDDSpark.utils.Utils;
import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.broadcast.Broadcast;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

public class BroadcastExampple {
    public static void main(String[] args) throws FileNotFoundException {
        SparkConf conf = new SparkConf().setAppName("BroadcastExample").setMaster("local[2]");
        SparkContext sc = new SparkContext(conf);
        JavaSparkContext javaSparkContext = new JavaSparkContext(sc);

        final Broadcast<Map<String, String>> postCodeMap = javaSparkContext.broadcast(loadPostCodeMap());
        JavaRDD<String> markerSpaceRdd = javaSparkContext.textFile("in/uk-makerspaces-identifiable-data.csv");
        //map into a String
        JavaRDD<String> regions = markerSpaceRdd.filter(line -> !line.split(Utils.COMMA_DELIMITER, -1)[0].equals("Timestamp"))//remove headerr
                .map(line -> {
                    //get abbreviation
                    Optional<String> postPrefix = getPostPrefix(line);
                    if (postPrefix.isPresent() && postCodeMap.value().containsKey(postPrefix.get())) { //check if the Abbreviation (AB1, CA exisit){
                        return postCodeMap.value().get(postPrefix.get());// }// this return value based on Key
                    }
                    return "unknown";
                });
        for (Map.Entry<String, Long> regionsCounts: regions.countByValue().entrySet()){
            System.out.println(regionsCounts.getKey() + " " + regionsCounts.getValue());
        }
    }

    private static Optional<String> getPostPrefix(String line) {
        String[] splits = line.split(Utils.COMMA_DELIMITER, -1);
        String postcode = splits[4];
        if (postcode.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(postcode.split(" ") [0]);
    }
    private static Map<String, String> loadPostCodeMap() throws FileNotFoundException{
        Scanner postCode = new Scanner(new File("in/uk-postcode.csv"));
        Map<String, String> postCodeMap = new HashMap<>();
        while (postCode.hasNextLine()){
            String line = postCode.nextLine();
            String[] splits = line.split(Utils.COMMA_DELIMITER, -1);
            postCodeMap.put(splits[0], splits[7]);
        }
        return  postCodeMap;
    }
}

