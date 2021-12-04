package com.zachdean.functions;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zachdean.data_access.DataFetcher;
import com.zachdean.debt_snowball.Debt;
import com.zachdean.investment.Investment;
import com.zachdean.major_expense.Expense;

@SuppressWarnings("unchecked")
public class AzureDataFetcher implements DataFetcher {
    private String baseAddress = "";

    public void setBaseAddess(URI uri){
        String schema = uri.getScheme();
        String host = uri.getHost();
        int port = uri.getPort();

        baseAddress = String.format("%s://%s:%s", schema, host, port);
    }
    
    @Override
    public List<Debt> fetchDebts(String userId) {
        try {
            URL url = new URL(String.format("%s/debts/%s", this.baseAddress, userId));
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("accept", "application/json");
            
            InputStream responseStream = con.getInputStream();
            
            ObjectMapper mapper = new ObjectMapper();
            
            return mapper.readValue(responseStream, List.class);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return null;
    }

    @Override
    public List<Investment> fetchInvestments(String userId) {
        try {
            URL url = new URL(String.format("%s/investments/%s", this.baseAddress, userId));
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("accept", "application/json");
            
            InputStream responseStream = con.getInputStream();
            
            ObjectMapper mapper = new ObjectMapper();
            
            return mapper.readValue(responseStream, List.class);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return null;
    }

    @Override
    public List<Expense> fetchExpenses(String userId) {
        try {
            URL url = new URL(String.format("%s/expenses/%s", this.baseAddress, userId));
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("accept", "application/json");
            
            InputStream responseStream = con.getInputStream();
            
            ObjectMapper mapper = new ObjectMapper();
            
            return mapper.readValue(responseStream, List.class);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
}
