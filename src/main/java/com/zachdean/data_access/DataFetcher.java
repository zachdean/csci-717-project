package com.zachdean.data_access;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zachdean.data_access.DataFetcher;
import com.zachdean.debt_snowball.Debt;
import com.zachdean.investment.Investment;
import com.zachdean.major_expense.Expense;

public class DataFetcher {
    private String baseAddress = "";

    public void setBaseAddess(URI uri){
        String schema = uri.getScheme();
        String host = uri.getHost();

        baseAddress = String.format("%s://%s", schema, host);
    }

    public List<Debt> fetchDebts(String userId) {
        try {
            URL url = new URL(String.format("%s/api/snowball/%s", this.baseAddress, userId));

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("accept", "application/json");
            
            InputStream responseStream = con.getInputStream();
                        
            ObjectMapper mapper = new ObjectMapper();
            
            return mapper.readValue(responseStream, new TypeReference<List<Debt>>(){});

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return null;
    }

    public List<Investment> fetchInvestments(String userId) {
        try {
            URL url = new URL(String.format("%s/api/investments/%s", this.baseAddress, userId));
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("accept", "application/json");
            
            InputStream responseStream = con.getInputStream();
            
            ObjectMapper mapper = new ObjectMapper();
            
            return mapper.readValue(responseStream, new TypeReference<List<Investment>>(){});

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return null;
    }

    public List<Expense> fetchExpenses(String userId) {
        try {
            URL url = new URL(String.format("%s/api/expenses/%s", this.baseAddress, userId));
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("accept", "application/json");
            
            InputStream responseStream = con.getInputStream();
            
            ObjectMapper mapper = new ObjectMapper();
            
            return mapper.readValue(responseStream, new TypeReference<List<Expense>>(){});

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
}
