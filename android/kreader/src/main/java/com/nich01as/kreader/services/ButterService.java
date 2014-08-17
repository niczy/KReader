package com.nich01as.kreader.services;

import android.util.Log;

import com.alchemyapi.api.AlchemyAPI;
import com.alchemyapi.api.AlchemyAPI_TextParams;
import com.nich01as.kreader.data.Butter;
import com.nich01as.kreader.data.Tag;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

/**
 * Created by nicholaszhao on 8/16/14.
 */
@Singleton
public class ButterService {

    private final AlchemyAPI alchemyApi;
    private final Map<String, Butter> cache = new HashMap<String, Butter>();

    @Inject
    public ButterService() {
        alchemyApi = AlchemyAPI.GetInstanceFromString("d43c5b81d9688475282ed73ad738981f7c007a3f");
    }

    public Butter loadButter(String url) throws SAXException, ParserConfigurationException, XPathExpressionException, IOException {
        if (cache.containsKey(url)) {
            return cache.get(url);
        }
        AlchemyAPI_TextParams param = new AlchemyAPI_TextParams();
        Document doc = alchemyApi.URLGetText(url, param);
        Butter butter = new Butter();
        butter.setContent(doc.getElementsByTagName("text").item(0).getTextContent());
        Document keywords = alchemyApi.TextGetRankedNamedEntities(butter.getContent());
        NodeList keywordNodes = keywords.getElementsByTagName("entity");
        for (int i = 0; i < keywordNodes.getLength(); i++) {
            Element element = (Element) keywordNodes.item(i);

            double relevance = Double.parseDouble(element.getElementsByTagName("relevance").item(0).getTextContent());

            if (relevance > 0.6) {
                butter.addTag(new Tag(element.getElementsByTagName("text").item(0).getTextContent(), relevance));
            }
        }

        keywords = alchemyApi.TextGetRankedConcepts(butter.getContent());
        keywordNodes = keywords.getElementsByTagName("concept");
        for (int i = 0; i < keywordNodes.getLength(); i++) {
            Element element = (Element) keywordNodes.item(i);

            double relevance = Double.parseDouble(element.getElementsByTagName("relevance").item(0).getTextContent());

            if (relevance > 0.6) {
                butter.addTag(new Tag(element.getElementsByTagName("text").item(0).getTextContent(), relevance));
            }
        }
        Log.d("butter", keywords.toString());
        butter.sortTags();
        cache.put(url, butter);
        return butter;
    }
    

}
