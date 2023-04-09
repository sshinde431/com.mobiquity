import Packer.Readinput;

import java.io.IOException;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
      //  System.out.println("Hello world!");
            try
            {
                // Get path to current working directory
                String path = Paths.get(".").toAbsolutePath().normalize().toString();
                //System.out.println(path);
                // Read Input File
                Readinput sortPackage = new Readinput(path+"/example_input.txt");
                // Process the input file
                sortPackage.inputTest();
            }
            catch (IOException ex)
            {
                System.err.println("Input Error");
                System.exit(1);
            }
        }

}