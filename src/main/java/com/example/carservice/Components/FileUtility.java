package com.example.carservice.Components;

import jakarta.ws.rs.NotFoundException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

public class FileUtility {

    public static class Paths{
    }

    private final String portraitsPath;
    public FileUtility(){
        this.portraitsPath = "/home/student/Desktop/184948/CarService/src/main/resources/portraits/";
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


    public byte[] getPortrait(UUID uuid) throws IOException {
        Path file = Path.of(portraitsPath + uuid.toString() + ".jpeg");
        try {
            return Files.readAllBytes(file);
        } catch(Exception ex) {
            try {
                return Files.readAllBytes(Path.of(portraitsPath + "default_client.jpg"));
            } catch(Exception ex2) {
                throw new NotFoundException();
            }
        }
    }

    public void savePortrait(byte[] data, UUID uuid) throws IOException {
        save(data, portraitsPath + uuid.toString() + ".jpeg");
    }

    public void deletePortrait(UUID uuid){
        delete(portraitsPath + uuid.toString() + ".jpeg");
    }
}