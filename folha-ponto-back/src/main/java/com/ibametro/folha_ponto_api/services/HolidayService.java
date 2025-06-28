package com.ibametro.folha_ponto_api.services;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ibametro.folha_ponto_api.domain.enums.Mes;



@Service
public class HolidayService {
    @Value("${calendarific.api.url}")
    private String apiUrl;

    @Value("${calendarific.api.key}")
    private String apiKey;

    @Value("${calendarific.country}")
    private String country;

    public List<Integer> getHolidays(int year, String mes) {
        int month = Mes.getMesIndex(mes);
        String urlString = String.format("%s?api_key=%s&country=%s&year=%d&month=%d", 
                                        apiUrl, apiKey, country, year, month);

        List<Integer> holidays = new ArrayList<>();

        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            int responseCode = connection.getResponseCode();
            if (responseCode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            }

            StringBuilder response = new StringBuilder();
            try (Scanner scanner = new Scanner(url.openStream())) {
                while (scanner.hasNext()) {
                    response.append(scanner.nextLine());
                }
            }

            JSONObject jsonObject = new JSONObject(response.toString());
            JSONArray holidaysArray = jsonObject.getJSONObject("response").getJSONArray("holidays");
            
            for (int i = 0; i < holidaysArray.length(); i++) {
                JSONObject holiday = holidaysArray.getJSONObject(i);
                int day = holiday.getJSONObject("date").getJSONObject("datetime").getInt("day");
                holidays.add(day);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return holidays;
    }
}
