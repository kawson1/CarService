package com.example.carservice.Components;

import jakarta.enterprise.context.Dependent;
import lombok.SneakyThrows;

import java.io.*;

@Dependent
public class CloningUtility {

    /**
     In Java, the sneaky throw concept allows us to throw any checked exception without defining it
     explicitly in the method signature. This allows the omission of the throws declaration,
     effectively imitating the characteristics of a runtime exception.

     PL: skraca pisownie, nie wymaga pisania catch {throw...}
     */
    @SneakyThrows
    @SuppressWarnings("unchecked")
    public <T extends Serializable> T clone(T object) {
        try (ByteArrayInputStream is = new ByteArrayInputStream(writeObject(object).toByteArray());
             ObjectInputStream ois = new ObjectInputStream(is)) {
            return (T) ois.readObject();
        }
    }

    private <T extends Serializable> ByteArrayOutputStream writeObject(T object) throws IOException {
        try (ByteArrayOutputStream os = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(os)) {
            oos.writeObject(object);
            return os;
        }
    }


}
