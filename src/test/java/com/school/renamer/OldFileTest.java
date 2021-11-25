package com.school.renamer;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.jupiter.api.BeforeEach;
import java.nio.file.StandardCopyOption;

public class OldFileTest {
    OldFile bobFile;
    File srcFile;
    String output_folder = "src/test/java/com/school/renamer/output/";

    public OldFileTest() {
    }

    @BeforeEach
    public void initializeOldFileObjects() throws IOException {
        Files.createDirectories(Paths.get(output_folder));
        srcFile = new File("1234567890-123456_Bob_Flounder_999999_bla bla.pdf");
        bobFile = new OldFile(srcFile);
    }

    // Test for getID()
    @Test
    public void testGetID() {
        String expResult = "999999";
        String result = bobFile.getID(bobFile.getOname());
        assertEquals(expResult, result);
    }

    // Test getID() for No Macth
    @Test
    public void testGetID_No_Match() {
        String expResult = "nomatch";
        String result = bobFile.getID("456789");
        assertEquals(expResult, result);
    }

    // Test for getCSV()
    @Test
    public void testGetCSV() {
        CorrectName bob = new CorrectName("Bob Flounder", "999999");
        CorrectName frank = new CorrectName("Frank Jameson", "888888");
        bobFile.correctNames.add(bob);
        bobFile.correctNames.add(frank);
        System.out.println(bobFile.getID(bobFile.oldName));
        String expResult = bob.getFullName() + "_" + bob.getCCC() + "_" + "assignsubmission_file_";
        String result = bobFile.getCSV(bobFile.oldName);
        assertEquals(expResult, result);
    }

    // Test for getCSV() for ID not Found
    @Test
    public void testGetCSV_ID_NOT_FOUND() {
        String expected_value = "ID NOT FOUND!";
        String actual_value = bobFile.getCSV(bobFile.oldName);
        assertEquals(expected_value, actual_value);
    }

    // Test for correctFile()
    @Test
    public void testCorrectFile() {
        String expResult = bobFile.getCSV(bobFile.oldName) + bobFile.oldName;
        String result = bobFile.correctFile();
        assertEquals(expResult, result);
    }
    
    //Creates a folder /TestFolder at workspace root, creates txt file, then CopyFile into a new folder "/output"
    @Test
    public void testCopyFile() throws IOException {
        File folder = new File("TestFolder");
        folder.mkdir();
        System.out.println(folder.getAbsolutePath());
        File testFile = new File(folder, "testFile.txt");
        testFile.createNewFile();
        System.out.println("Copying file to /output..."); 
        CopyFile copyFile = new CopyFile();  
        FileOps copycommand = new CopyFileCommand(copyFile, Paths.get(testFile.getPath()), Paths.get(output_folder + testFile.getName()), StandardCopyOption.REPLACE_EXISTING);
        copycommand.execute();
        int exist = 0;
        if (testFile.exists()) {
            exist = 1;
            System.out.println("Success"); 
        }
        else {
            System.out.println("Fail"); 
        }
        int expResult = 1;
        assertEquals(expResult, exist);
    }
}
