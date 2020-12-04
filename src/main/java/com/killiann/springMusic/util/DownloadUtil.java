package com.killiann.springMusic.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ResourceBundle;

public class DownloadUtil {

    String filename;
    String ytUrl;
    ResourceBundle rb = ResourceBundle.getBundle("config");
    private final String path = rb.getString("song.base.dir");

    DownloadUtil(String filename, String ytUrl) {
        this.filename = filename;
        this.ytUrl = ytUrl;
    }

    void callYtDownload() throws IOException {
        try {
            ProcessBuilder pb =
                    new ProcessBuilder("youtube-dl " + this.ytUrl, "--extract-audio", "--audio-format mp3", "--audio-quality 0");
            pb.directory(new File(path));
            Process p = pb.start();
            // this reads from the subprocess's output stream
            BufferedReader subProcessInputReader =
                    new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line;
            while ((line = subProcessInputReader.readLine()) != null)
                System.out.println(line);

            subProcessInputReader.close();
        }
        catch (IOException e)
        { e.printStackTrace(); }
    }
}
