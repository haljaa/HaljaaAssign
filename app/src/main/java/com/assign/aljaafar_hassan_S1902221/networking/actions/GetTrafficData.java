//aljaafar hassan S1902221
package com.assign.aljaafar_hassan_S1902221.networking.actions;

import com.assign.aljaafar_hassan_S1902221.modelclasses.DataModel;
import com.assign.aljaafar_hassan_S1902221.util.ExtractXmlData;

import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.concurrent.Callable;

public class GetTrafficData implements Callable<ArrayList<DataModel>> {
    private final String urlString;

    public GetTrafficData(String url) {
        this.urlString = url;
    }

    @Override
    public ArrayList<DataModel> call() throws IOException, XmlPullParserException {
        URLConnection urlConnection;
        BufferedReader in;
        String input;
        URL url = new URL(urlString);
        urlConnection = url.openConnection();
        in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        StringBuilder result = new StringBuilder();
        while ((input = in.readLine()) != null) {
            result.append(input);
        }
        in.close();

        ExtractXmlData extractXmlData = new ExtractXmlData();

        return extractXmlData.extractData(result.toString());
    }
}