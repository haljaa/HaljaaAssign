package com.assign.aljaafar_hassan_S1902221.util;

import com.assign.aljaafar_hassan_S1902221.modelclasses.DataModel;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

public class ExtractXmlData {
    private Boolean itemTagStart = false;
    private ArrayList<DataModel> arrayList = new ArrayList<>();

    public ArrayList<DataModel> extractData(String xmlString) throws XmlPullParserException, IOException {
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XmlPullParser xpp = factory.newPullParser();

        DataModel dataModel = null;

        xpp.setInput(new StringReader(xmlString));
        int eventType = xpp.getEventType();
        String tag = "", text = "";
        while (eventType != XmlPullParser.END_DOCUMENT) {
            tag = xpp.getName();
            switch (eventType) {
                case XmlPullParser.START_TAG:
                    if (tag.equals("item")) {
                        itemTagStart = true;
                        dataModel = new DataModel();
                    }
                    break;
                case XmlPullParser.TEXT:
                    text = xpp.getText();
                    break;
                case XmlPullParser.END_TAG:
                    if (itemTagStart) {
                        switch (tag) {
                            case "title":
                                dataModel.setTitle(text);
                                break;
                            case "description":
                                dataModel.setDescription(text);
                                break;
                            case "location":
                                dataModel.setLink(text);
                                break;
                            case "pubDate":
                                dataModel.setPubDate(text);
                                break;
                            case "point":
                                dataModel.setLocPoints(text);
                                break;
                            case "item":
                                DataModel dataModel1 = new DataModel();
                                dataModel1.setTitle(dataModel.getTitle());
                                dataModel1.setDescription(dataModel.getDescription());
                                dataModel1.setLink(dataModel.getLink());
                                dataModel1.setPubDate(dataModel.getPubDate());
                                dataModel1.setLocPoints(dataModel.getLocPoints());
                                arrayList.add(dataModel1);
                                itemTagStart = false;
                                break;
                        }
                    }
                    break;
            }
            eventType = xpp.next();
        }
        return arrayList;
    }
}
