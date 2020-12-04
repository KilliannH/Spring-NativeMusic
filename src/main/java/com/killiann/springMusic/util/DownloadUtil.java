package com.killiann.springMusic.util;

import com.killiann.springMusic.controllers.StreamController;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Arrays;
import java.util.ResourceBundle;

public class DownloadUtil {

    org.slf4j.Logger logger = LoggerFactory.getLogger(StreamController.class);

    String filename;
    String ytUrl;
    private final ResourceBundle rb = ResourceBundle.getBundle("config");
    private final String path = rb.getString("song.base.dir");
    private boolean isWindows = System.getProperty("os.name").contains("Windows");

    public DownloadUtil(String filename, String ytUrl) {
        this.filename = filename;
        this.ytUrl = ytUrl;
        logger.info("Java running on " + System.getProperty("os.name"));
    }

    public void callYtDownload() throws IOException {
        String [] command = {isWindows ? "CMD" : "bash", isWindows ? "/C" : "-c", "youtube-dl " + this.ytUrl + " " + "--extract-audio --audio-format mp3 --audio-quality 0"};
        try {
            ProcessBuilder pb = new ProcessBuilder(command);
            pb.directory(new File(path));
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
        catch (IOException e)
        { e.printStackTrace(); }
    }
}
