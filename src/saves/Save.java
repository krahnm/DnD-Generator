package saves;

import elementclasses.Chamber;

import java.io.*;
import java.util.ArrayList;

public class Save {

    public Save(ArrayList<Chamber> chambers) {


        try {
            FileOutputStream fileOut =
                    new FileOutputStream("C:\\Users\\krahn\\Desktop\\CIS2430\\a4\\bin/employee.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(chambers.get(0).getDescription());
            out.close();
            fileOut.close();
            System.out.printf("Serialized data is saved in C:\\Users\\krahn\\Desktop\\CIS2430\\a4\\bin/employee.ser");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }
}
