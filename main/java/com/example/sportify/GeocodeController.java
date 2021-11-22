package com.example.sportify;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RestController
public class GeocodeController {

    @RequestMapping(path = "/geocode", method = RequestMethod.GET )
    public String getGeocode(@RequestParam String address) throws IOException {
        OkHttpClient client = new OkHttpClient();
        String encodedAddress = URLEncoder.encode(address, StandardCharsets.UTF_8);
        Request request = new Request.Builder()
                .url("https://google-maps-geocoding.p.rapidapi.com/geocode/json?language=en&address=" + encodedAddress)
                .get()
                .addHeader("x-rapidapi-host", "google-maps-geocoding.p.rapidapi.com")
                .addHeader("x-rapidapi-key", "99a8cb3dcamsh472eef55fcace1fp1c7d8bjsn3e1c047f1005"/*  Use your API Key here */)
                .build();

        ResponseBody responseBody = client.newCall(request).execute().body();
        assert responseBody != null;
        return responseBody.string();
    }

}
