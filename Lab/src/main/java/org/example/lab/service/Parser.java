package org.example.lab.service;

import org.apache.commons.lang3.StringUtils;
import org.example.lab.model.HryvniaExchangeItem;
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;

import java.io.IOException;
import java.util.*;
import java.text.ParseException;

public class Parser {
    public List<HryvniaExchangeItem> Parse() throws ParseException, IOException {
        var a = new ArrayList<HryvniaExchangeItem>();
        String userAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36 OPR/108.0.0.0";
        String url = "https://bank.gov.ua/ua/markets/exchangerates";
        Document doc = Jsoup.connect(url).userAgent(userAgent).get();
        var table = doc.selectFirst("table#exchangeRates");
        int rowNum = 0;
        assert table != null;
        for (Element row : table.select("> tbody > tr")) {
            a.add(parseRow(rowNum++, row));
        }
        return  a;
    }

    private HryvniaExchangeItem parseRow(int rowNum, Element row) {
        var a = new HryvniaExchangeItem();
        a.SetID(rowNum);
        int columnNum = 0;
        for (Element cell : row.select("> td")) {
            for( Attribute attribute : cell.attributes() ){
                if(attribute.getValue().equalsIgnoreCase("Код цифровий"))
                {
                    a.DigitalCode=cell.text();
                } else if (attribute.getValue().equalsIgnoreCase("Код літерний")) {
                    a.LetterCode=cell.text();
                } else if (attribute.getValue().equalsIgnoreCase("Назва валюти")) {
                    a.Name=cell.text();
                } else if (attribute.getValue().equalsIgnoreCase("Офіційний курс")) {
                    a.OfficialCourse=cell.text();
                }
            }
            columnNum++;
        }
        return a;
    }
}
