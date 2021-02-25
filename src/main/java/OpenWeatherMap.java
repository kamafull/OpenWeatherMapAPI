import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;


class OpenWeatherMap {

    public static void  main(String[] args) {

        String urlString = "http://api.openweathermap.org/data/2.5/forecast?q=Saint Petersburg,RU&type=like&APPID=f8833b72420a1f7796123be02a90c1dd&units=Metric";

        try{
            StringBuilder result = new StringBuilder();
            URL url = new URL(urlString);
            URLConnection conn = url.openConnection();
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null){
                result.append(line);
            }
            rd.close();

            JSONObject jsonObject = new JSONObject(result.toString());
            JSONArray jsonArray = jsonObject.getJSONArray("list");
            ArrayList<String> list = new ArrayList<String>();
            if (jsonArray != null) {
                int len = jsonArray.length();
                for (int i=0;i<len;i++){
                    list.add(jsonArray.get(i).toString());
                }
            }

            double temp = 0;
            JSONObject buf1;
            JSONObject buf2;
            for (int i = 2; i < 40; i= i + 8){
                buf1 = new JSONObject(list.get(i).toString());
                buf2 = buf1.getJSONObject("main");
                temp+= buf2.getDouble("temp");
            }

            double resultTemp = temp/5;
            System.out.println("Сердяя температура в 9 утра в СПБ за поледние 5 дней - " + String.format("%.2f", resultTemp));

        }catch (IOException e){
            System.out.println(e.getMessage());
        }

    }
}


