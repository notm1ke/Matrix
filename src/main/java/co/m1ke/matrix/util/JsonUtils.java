package co.m1ke.matrix.util;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

public class JsonUtils {

    private String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }


    public JSONObject readFromUrl(String url) throws Exception {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            return new JSONObject(jsonText);
        } finally {
            is.close();
        }
    }

    public static JSONObject readFromFile(File file) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file.getName()));

            if (!Comparables.isJson(IOUtils.toString(reader))) {
                return null;
            }

            JSONObject obj = new JSONObject(IOUtils.toString(reader));
            reader.close();

            return obj;
        } catch (IOException e) {
            return null;
        }
    }

}
