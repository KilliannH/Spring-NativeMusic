package com.killiann.springMusic.util;

import java.io.*;
import java.util.Arrays;
import java.util.ResourceBundle;

public class DownloadUtil {

    String filename;
    String ytUrl;
    private final ResourceBundle rb = ResourceBundle.getBundle("config");
    private final String songDirPath = rb.getString("song.base.dir");

    public DownloadUtil(String filename, String ytUrl) {
        this.filename = filename;
        this.ytUrl = ytUrl;
    }

    // not working on windows.. skip dl for dev purposes
    public void callYtDownload() {
        String [] command = {"bash", "-c", "youtube-dl " + this.ytUrl + " " + "--extract-audio --audio-format mp3 --audio-quality 0"};
        try {
            ProcessBuilder pb = new ProcessBuilder(command);
            pb.directory(new File(songDirPath));
            Process p = pb.start();

            InputStream inputStream = p.getErrorStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String line;
            System.out.printf("Output of running %s is:\n",
                    Arrays.toString(command));
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }

            //Wait to get exit value
            try {
                int exitValue = p.waitFor();
                System.out.println("\n\nExit Value is " + exitValue);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
