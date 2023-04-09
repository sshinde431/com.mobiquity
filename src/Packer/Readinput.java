package Packer;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import Exception.APIException;
public class Readinput {
    private boolean isCompleted = false;
    private final Path path;

    /** Reading input file */
    public Readinput(String filePath)
    {
        this.path = Paths.get(filePath);
    }

    public void inputTest() throws IOException
    {
        // Adding filepath
        Scanner input = new Scanner(this.path);

        while (!isCompleted)
        {
            while (input.hasNext())
            {
                String line = input.nextLine();
                Package individualPackage;
                //System.out.println(testCase);
                if (line.length() == 0)
                {
                    continue;
                }
                try
                {
                    // creating the package using class constructor
                    individualPackage = new Package(line);
                    // Find package using findPackage method
                    individualPackage = individualPackage.findPackage();

                    // Retrieve the package no.s of optimum package
                    System.out.println(individualPackage.getNumberOfRecords());
                }
                catch (APIException e)
                {
                    System.out.println(e.getMessage());
                }
            }
            //ending the loop as processing completed
            isCompleted = true;
        }
    }


}
