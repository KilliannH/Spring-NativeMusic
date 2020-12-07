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
    public int runYtDownload() {
        String[] command = {"/bin/bash", "-c", "youtube-dl " + this.ytUrl + " " + "--extract-audio --audio-format mp3 --audio-quality 0"};
        String originalFilename = "";
        try {
            ProcessBuilder pb = new ProcessBuilder(command);
            System.out.print("" + pb.environment().toString());
            pb.directory(new File(songDirPath));
            Process p = pb.start();

            InputStream inputStream = p.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String line;
            System.out.printf("Output of running %s is:\n",
                    Arrays.toString(command));
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
                if (line.contains("Deleting original file")) {

                    originalFilename = line.split("Deleting original file ")[1];
                    originalFilename = originalFilename.split(".webm")[0];
                    originalFilename += ".mp3";
                }
            }

            //Wait to get exit value
            try {
                int exitValue = p.waitFor();
                System.out.println("\n\nExit Value is " + exitValue);
                if (exitValue == 0) { // Success
                    p.destroy();
                    return renameAndFinalize(originalFilename);
                } else {
                    p.destroy();
                }
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                p.destroy();
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private int renameAndFinalize(String originalFilename) {
        String[] command = {"/bin/bash", "-c", "mv ./" + '\"' + originalFilename + "\" " + this.filename};
        try {
            ProcessBuilder pb = new ProcessBuilder(command);
            pb.directory(new File(songDirPath));
            Process p = pb.start();

            System.out.printf("Output of running %s is:\n",
                    Arrays.toString(command));

            try {
                int exitValue = p.waitFor();
                System.out.println("\n\nExit Value is " + exitValue);
                p.destroy();
                return exitValue;
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                p.destroy();
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
