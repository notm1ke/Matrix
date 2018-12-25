package co.m1ke.matrix.util;

import co.m1ke.matrix.error.json.JsonExplodeException;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

public class JsonUtils {

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }


    public static JSONObject getFromUrl(String url) throws Exception {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            return new JSONObject(jsonText);
        } finally {
            is.close();
        }
    }

    public static JSONObject getFromFile(File file) {
        try {
            InputStream is = new FileInputStream(file);
            String text = IOUtils.toString(is, "UTF-8");

            if (!Comparables.isJson(text)) {
                return null;
            }

            JSONObject obj = new JSONObject(text);
            is.close();

            return obj;
        } catch (IOException e) {
            return null;
        }
    }

    public static JSONObject explode(JSONObject object) throws JsonExplodeException {
        throw new JsonExplodeException("stop");
    }

}
