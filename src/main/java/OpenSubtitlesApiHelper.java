import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class OpenSubtitlesApiHelper {
    private static final String API_KEY = "IZI9l1n3dSr0LwL7SO6MxDiLRwxx0Yws";
    private static final String API_URL = "https://api.opensubtitles.com/api/v1";
    private static final Gson gson = new Gson();

    public static String searchSubtitle(String query, String language) throws Exception {
        String url = API_URL + "/subtitles?query=" + java.net.URLEncoder.encode(query, "UTF-8") + "&languages=" + language.toLowerCase();
        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setRequestProperty("Api-Key", API_KEY);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestMethod("GET");
        int responseCode = conn.getResponseCode();
        InputStream in = conn.getInputStream();
        String json = new String(in.readAllBytes(), StandardCharsets.UTF_8);
        System.out.println("[OpenSubtitles API raw response]: " + json);
        if (responseCode != 200) {
            throw new Exception("OpenSubtitles API error: " + responseCode + "\nResponse: " + json);
        }
        try {
            JsonObject obj = gson.fromJson(json, JsonObject.class);
            if (!obj.has("data") || !obj.get("data").isJsonArray()) return null;
            JsonArray data = obj.getAsJsonArray("data");
            if (data.size() == 0) return null;
            JsonObject first = data.get(0).getAsJsonObject();
            return first.get("attributes").getAsJsonObject().get("files").getAsJsonArray().get(0).getAsJsonObject().get("file_id").getAsString();
        } catch (Exception e) {
            System.out.println("[OpenSubtitles API parse error]: " + e);
            return null;
        }
    }

    public static boolean downloadSubtitle(String fileId, String outputPath) throws Exception {
        String url = API_URL + "/download";
        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setRequestProperty("Api-Key", API_KEY);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        String body = "{\"file_id\":\"" + fileId + "\"}";
        conn.getOutputStream().write(body.getBytes(StandardCharsets.UTF_8));
        int responseCode = conn.getResponseCode();
        if (responseCode != 200) {
            throw new Exception("OpenSubtitles API download error: " + responseCode);
        }
        InputStream in = conn.getInputStream();
    String json = new String(in.readAllBytes(), StandardCharsets.UTF_8);
    JsonObject obj = gson.fromJson(json, JsonObject.class);
    String link = obj.get("link").getAsString();
        // Download the actual subtitle file
        HttpURLConnection fileConn = (HttpURLConnection) new URL(link).openConnection();
        fileConn.setRequestMethod("GET");
        try (BufferedInputStream bis = new BufferedInputStream(fileConn.getInputStream());
             FileOutputStream fos = new FileOutputStream(outputPath)) {
            byte[] buffer = new byte[1024];
            int count;
            while ((count = bis.read(buffer, 0, 1024)) != -1) {
                fos.write(buffer, 0, count);
            }
        }
        return true;
    }
}
