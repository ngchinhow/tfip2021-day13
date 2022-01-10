package com.tfip2021.module2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

import org.springframework.stereotype.Component;

@Component
public class Contact {
    private static final int ID_LENGTH = 8;
    private static final ArrayList<String> fieldsToIgnore = new ArrayList<String> (
        Arrays.asList("ID_LENGTH", "fieldsToIgnore")
    );

    private String id;
    private String name;
    private String email;
    private String phoneNumber;

    public Contact() {
        Random r = new Random();
        StringBuffer sb = new StringBuffer();
        while(sb.length() < ID_LENGTH){
            sb.append(Integer.toHexString(r.nextInt()));
        }
        this.id = sb.toString().substring(0, ID_LENGTH);
    }

    public Contact(String id) {
        this.id = id;
    }

    public ArrayList<String> getFieldsToIgnore() { return fieldsToIgnore; }
    public String getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhoneNumber() { return phoneNumber; }

    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public void save(String dataDir) throws IOException {
        File file = new File(
            dataDir + File.separator + getId()
        );
        file.createNewFile();
        
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(file), "UTF-8")
        )) {
            for (Field field : this.getClass().getDeclaredFields()) {
                if (!getFieldsToIgnore().contains(field.getName())) {
                    bw.write(
                        field.getName() + "=" + 
                        field.get(this) + System.lineSeparator()
                    );
                }
            }
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public int read(String dataDir) throws FileNotFoundException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
        File file = new File(
            dataDir + File.separator + getId()
        );
        if (!file.exists()) return 404;

        try (Scanner sc = new Scanner(file, "UTF-8")) {
            while (sc.hasNextLine()) {
                String[] kv = sc.nextLine().split("=");
                this.getClass().getDeclaredField(kv[0]).set(this, kv[1]);
            }
        }
        return 200;
    }
}
