package com.sqlSparks.stronglyTyped;

import com.sqlSparks.ResponseDTO;
import org.apache.spark.api.java.function.FilterFunction;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.*;
import static org.apache.spark.sql.functions.*;

public class TypedDataset {
    private static final String AGE_MIDPOINT = "age_midpoint";
    private static final String SALARY_MIDPOINT = "salary_midpoint";
    private static final String SALARY_MIDPOINT_BUCKET = "salaryMidpointBucket";

    public static void main(String[] args) {
        SparkSession session = SparkSession.builder().appName("StackOverFLowsurvey").master("local[*]").getOrCreate();
        DataFrameReader dataFrameReader = session.read();
        Dataset<Row> responses = dataFrameReader.option("header", "true").csv("in/2016-stack-overflow-survey-responses.csv");
        Dataset<Row> responsesWithSelectedColmns = responses.select(
               col("country"), col("age_midpoint").cast("integer"),
        col("occupation"), col("salary_midpoint").cast("integer"));
        // as Encoder bean with resposne DTO

        Dataset<ResponseDTO> responseDTODataset= responsesWithSelectedColmns.as(Encoders.bean(ResponseDTO.class));

        System.out.println("=== Print out schema ===");
        responseDTODataset.printSchema();

        System.out.println("=== Print the responses from Afghanistan ===");
        responseDTODataset.filter((FilterFunction<ResponseDTO>) response -> response.getCountry().equals("Brazil")).show();

        System.out.println("=== Print the count of occupations ===");
        responseDTODataset.groupBy(responseDTODataset.col("occupation")).count().show();

        System.out.println("=== Print responses with average mid age less than 20 ===");
        responseDTODataset.filter((FilterFunction<ResponseDTO>)response -> response.getAge_midpoint() !=null &&
                        response.getAge_midpoint() < 20)
                .show();

        System.out.println("=== Print the result by salary middle point in descending order ===");
        responseDTODataset.orderBy(responseDTODataset.col(SALARY_MIDPOINT ).desc()).show();

        System.out.println("=== Group by country and aggregate by average salary middle point and max age middle point ===");
        responseDTODataset.filter((FilterFunction<ResponseDTO>) response -> response.getSalary_midpoint() != null)
                .groupBy("country")
                .agg(avg(SALARY_MIDPOINT), max(AGE_MIDPOINT))
                .show();

        System.out.println("=== Group by salary bucket ===");
        responseDTODataset.map((MapFunction<ResponseDTO, Integer>) response -> response.getSalary_midpoint() == null ?
                        null :
                        Math.round(response.getSalary_midpoint()/20000) * 20000, Encoders.INT())
                .withColumnRenamed("value", SALARY_MIDPOINT_BUCKET)
                .groupBy(SALARY_MIDPOINT_BUCKET)
                .count()
                .orderBy(SALARY_MIDPOINT_BUCKET).show();
    }
}
