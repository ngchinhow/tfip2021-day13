package com.tfip2021.module2;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import org.slf4j.Logger;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

public class ContactTests {
    private static final Logger logger = LoggerFactory.getLogger(ContactTests.class);

    @Test
    void testSave() throws IOException {
        Contact contact = new Contact();
        contact.setName("tester 1");
        contact.setEmail("test@test.com");
        contact.setPhoneNumber("12345");
        contact.save();

        logger.info(contact.getName());
        File file = new File(
            contact.getDataDir() + File.separator + contact.getId()
        );
        StringBuffer fileContent = new StringBuffer();
        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                fileContent.append(sc.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        assertEquals(
            "id=" + contact.getId() +
            "name=tester 1" +
            "email=test@test.com" +
            "phoneNumber=12345",
            fileContent.toString()
        );
    }

    @Test
    void testRead() throws FileNotFoundException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
        // Testing positive reading
        Contact contact = new Contact("TESTINGDONOTDELETE");
        int responseCode = contact.read();
        assertEquals(200, responseCode);
        assertEquals("TESTINGDONOTDELETE", contact.getId());
        assertEquals("tester 2", contact.getName());
        assertEquals("test2@test2.com", contact.getEmail());
        assertEquals("93939393", contact.getPhoneNumber());

        // Testing negative reading
        Contact contact2 = new Contact("IMPOSSIBLETOREACHID");
        int responseCode2 = contact2.read();
        assertEquals(404, responseCode2);
    }
}
