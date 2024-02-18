/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.clientappliction;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author Lenovo
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Shares {
    private String companySymbol;
    private Double price;
    private String companyName;
    private String numOfShares;

    public Shares() {
    }

    public Shares(String companyName, String companySymbol) {
        this.companySymbol = companySymbol;
        //this.price = price;
        this.companyName = companyName;
        //this.numOfShares = numOfShares;
    }

    Shares(String name, String symbol, double price, String numOfShares) {
        this.companySymbol = companySymbol;
        this.price = price;
        this.companyName = companyName;
        this.numOfShares = numOfShares;
    }

    

    public String getCompanySymbol() {
        return companySymbol;
    }

    public void setCompanySymbol(String companySymbol) {
        this.companySymbol = companySymbol;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getNumOfShares() {
        return numOfShares;
    }

    public void setNumOfShares(String numOfShares) {
        this.numOfShares = numOfShares;
    }

    
}
