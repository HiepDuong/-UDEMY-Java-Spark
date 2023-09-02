package com.sqlSparks;

public class ResponseDTO {
    private String country;
    private Integer age_midpoint;
    private String occupation;
    private Integer salary_midpoint;

    public ResponseDTO(String country, Integer age_midpoint, String occupation, Integer salary_midpoint) {
        this.country = country;
        this.age_midpoint = age_midpoint;
        this.occupation = occupation;
        this.salary_midpoint = salary_midpoint;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getAge_midpoint() {
        return age_midpoint;
    }

    public void setAge_midpoint(Integer age_midpoint) {
        this.age_midpoint = age_midpoint;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public Integer getSalary_midpoint() {
        return salary_midpoint;
    }

    public void setSalary_midpoint(Integer salary_midpoint) {
        this.salary_midpoint = salary_midpoint;
    }
}
