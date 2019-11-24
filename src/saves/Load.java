package saves;
import java.io.*;
import elementclasses.Chamber;

public class Load {
    public Load() {
        String e = null;
        try {
            FileInputStream fileIn = new FileInputStream("C:\\Users\\krahn\\Desktop\\CIS2430\\a4\\bin/employee.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            e = (String) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
            return;
        } catch (ClassNotFoundException c) {
            System.out.println("Employee class not found");
            c.printStackTrace();
            return;
        }

        System.out.println("Deserialized Employee...");
        System.out.println("Name: " + e);

    }
}
