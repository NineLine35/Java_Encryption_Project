import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class UserOutput {

    // Private variables to hold user encrypted output and the key
    private String userEncryptOut;
    private int[] userEncryptKey;

    // Variable to bring in Data object in order to timestamp output files
    Date date = new Date();
    SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd.HH-mm");
    String timeStamp = dateFormat.format(date);

    public void Save() throws IOException {
        // Create a File object to be used to create directory
        String path = "C:\\JavaEncrypt";
        File directory = new File(path);

        // Check if directory exists, if so do not create a new one and overwrite
        if(directory.exists() == false) {
            directory.mkdir();
        }

        // Create text file to hold encrypted text
        File encryptTxt = new File("C:\\JavaEncrypt\\encryptTXT_" + timeStamp +".txt");
        encryptTxt.createNewFile();

        //Create text file to hold key
        File keyTxt = new File("C:\\JavaEncrypt\\keyTXT_" + timeStamp + ".txt");
        keyTxt.createNewFile();

        //Write encrypted text to encryptTXT file
        FileWriter txtWrite = new FileWriter("C:\\JavaEncrypt\\encryptTXT_" + timeStamp +".txt");
        try{
            txtWrite.write(userEncryptOut);
            txtWrite.close();
            JOptionPane.showMessageDialog(new JFrame(),
                    "Text Save successful");
        }
        catch (IOException e){
            JOptionPane.showMessageDialog(new JFrame(),
                    "Could not write text to file");
        }

        // Write key to keyTXT file
        FileWriter keyWrite = new FileWriter("C:\\JavaEncrypt\\keyTXT_" + timeStamp + ".txt");
        try{
            keyWrite.write(Arrays.toString(userEncryptKey));
            keyWrite.close();
            JOptionPane.showMessageDialog(new JFrame(),
                    "Key save successful");
        }
        catch (IOException e){
            JOptionPane.showMessageDialog(new JFrame(),
                    "Could not write text to file");
        }



    }

    //Getters and setters
    public String getUserEncryptOut() {
        return userEncryptOut;
    }

    public void setUserEncryptOut(String userEncryptOut) {
        this.userEncryptOut = userEncryptOut;
    }

    public int[] getUserEncryptKey() {
        return userEncryptKey;
    }

    public void setUserEncryptKey(int[] userEncryptKey) {
        this.userEncryptKey = userEncryptKey;
    }
}
