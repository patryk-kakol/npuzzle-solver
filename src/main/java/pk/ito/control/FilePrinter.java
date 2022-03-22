package pk.ito.control;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/* class used to print data to .txt files */
public class FilePrinter {

  /* creates file, fill it with given data and returns its name */
  public static String printToFile(String solutionDescription) {
    LocalDateTime dateTime = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy_HH_mm_ss");
    String now = dateTime.format(formatter);
    String fileName = "solution_" + now + ".txt";
    try {
      FileOutputStream fos = new FileOutputStream(fileName);
      fos.write(solutionDescription.getBytes(StandardCharsets.UTF_8));
      fos.close();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return fileName;
  }
}
