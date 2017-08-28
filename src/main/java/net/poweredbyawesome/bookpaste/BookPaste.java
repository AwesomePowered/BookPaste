package net.poweredbyawesome.bookpaste;

import org.bukkit.plugin.java.JavaPlugin;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class BookPaste extends JavaPlugin {

    public static BookPaste instance;

    @Override
    public void onEnable() {
        instance = this;
        getCommand("bookpaste").setExecutor(new BookPasteCommand());
    }

    public String paste(String s) {
        try {
            URL youareell = new URL("https://hasteb.in/documents");
            HttpsURLConnection con = (HttpsURLConnection) youareell.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            con.setDoOutput(true);
            try (BufferedOutputStream B = new BufferedOutputStream(con.getOutputStream())) {
                B.write(s.getBytes("utf8"));
                B.flush();
            }
            int i = con.getResponseCode();
            final Reader reader = new InputStreamReader(con.getInputStream());
            final BufferedReader br = new BufferedReader(reader);
            String datLine = br.readLine();
            return parseData(datLine);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Something went wrong. Please check the console";
    }

    public String parseData(String data) {
        Matcher m = Pattern.compile("^\\{\"key\":\"(.*)\"}$").matcher(data);
        if (m.matches()) {
            return "https://hasteb.in/"+m.group(1)+".book";
        } else return "unknownPaste";
    }
}
