package com.yakos.map.service.ımpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;




import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import com.yakos.map.dto.DtoModel;
import com.yakos.map.service.ICarService;

@Service
public class CarServiceImpl implements ICarService {

   
    

    
    
    @Override
    public List<DtoModel> getModelOptions2(String ek_url,int a) throws java.io.IOException {
        List<DtoModel> modelList = new ArrayList<>();

        String url = "http://arabamkacyakar.com"+ek_url; 
        
        String divString="";
        switch (a) {
        case 1:
        	url = "http://arabamkacyakar.com/markalar";
        	divString="select:has(option:contains(Lütfen Seçiniz))";
            break; 
        case 2:
        	divString="select:has(option:contains(Model Seçiniz))";
            break;
            
        case 3:
        	divString="select:has(option:contains(Kasa Tipi Seçiniz))";
            break;
            
        case 4:
        	divString="select:has(option:contains(Motor Tipi Seçiniz))";
            break;     
       
    }

        


   
        Document document = Jsoup.connect(url).get();
         
         

        Element modelSelectElement = document.select(divString).first();

        if (modelSelectElement != null) {
            Elements options = modelSelectElement.select("option");

            for (Element option : options) {
                String value = option.attr("value"); 
                String text = option.text();


                if (!value.isEmpty()) {
                    DtoModel model = new DtoModel();
                    model.setValue(value);
                    model.setName(text);
                    modelList.add(model);
                }
            }
        } else {
            System.out.println("Model Seçiniz alanı bulunamadı!");
        }

        return modelList;
    }






    
    
    
    
    
    public List<Map<String, Object>> scrapeCarData(String url) throws IOException {
        List<Map<String, Object>> carDataList = new ArrayList<>();

    
        Document document = Jsoup.connect(url).get();

        
        Elements rows = document.select("tbody > tr");

        for (Element row : rows) {
            Map<String, Object> carData = new HashMap<>();

          
            Elements visibleXsSpans = row.select("td .visible-xs span");
            if (visibleXsSpans.size() >= 2) {
                carData.put("visibleXs_first", visibleXsSpans.get(0).text()); 
                carData.put("visibleXs_second", visibleXsSpans.get(1).text()); 
            } else if (visibleXsSpans.size() == 1) {
                carData.put("visibleXs_first", visibleXsSpans.get(0).text());
                carData.put("visibleXs_second", null); 
            }

           
            Elements otherSpans = row.select("td span:not(.label-primary):not(.label-danger)");
            for (Element span : otherSpans) {
                String className = span.className(); 
                String value = span.text();
                carData.put(className, value);
            }

      
            Element linkElement = row.selectFirst("td div.col-md-9 a");
            if (linkElement != null) {
                String hrefValue = linkElement.attr("href"); 
                String linkText = linkElement.text(); 
                carData.put("linkHref", hrefValue);
                carData.put("linkText", linkText);
            }

            // Veriyi listeye ekle
            carDataList.add(carData);
        }

        return carDataList;
    }

     
}
