package com.example.carservice.Components;

import java.io.*;
import java.util.UUID;

public class FileUtility {

    public static class Paths{
        public static final String portraitsPath = "D:\\PG\\SEM 7\\JEE\\CarService\\src\\main\\resources\\portraits\\";
    }

    private void save(byte[] data, String fileName) throws IOException {
        File outputFile = new File(fileName);
        outputFile.createNewFile();
        try(FileOutputStream os = new FileOutputStream(outputFile)){
            os.write(data);
        }
    }

    private void delete(String fileName){
        new File(fileName).delete();
    }

    public void savePortrait(byte[] data, UUID uuid) throws IOException {
        save(data, Paths.portraitsPath + uuid.toString() + ".png");
    }

    public void deletePortrait(UUID uuid){
        delete(Paths.portraitsPath + uuid.toString() + ".png");
    }
}