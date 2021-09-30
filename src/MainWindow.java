import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class MainWindow {
    private JPanel MainWindow;
    private JRadioButton decryptRadio;
    private JRadioButton encryptRadio;
    private JPanel buttonPanel;
    private JButton processButton;
    private JButton saveButton;
    private JTextArea userInputOutput;
    private JButton loadTxtButton;
    private JButton loadKeyButton;
    private JButton button5;

    public MainWindow() {
        // Create a UserOutput object to store encrypted strings and keys
        UserOutput op = new UserOutput();

        processButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                boolean isEncryptRadioSelected = encryptRadio.isSelected();
                boolean isDecryptRadioSelected = decryptRadio.isSelected();

                if (isEncryptRadioSelected) {

                        if(userInputOutput.getText().isEmpty())
                        {
                            JOptionPane.showMessageDialog(new JFrame(),
                                    "Text field blank");
                        }
                        else
                        {
                            // Pull user text from GUI text area
                            String userInput = userInputOutput.getText();

                            // Create an output String variable for the encrypted text
                            String output;

                            //Create a CryptKey object
                            CryptKey scramble = new CryptKey();

                            //Create a key
                            int[] key = scramble.keyBuilder(userInput);

                            // Set key in UserOutput
                            op.setUserEncryptKey(key);

                            //Encrypt the user text
                            output = scramble.encrypt(userInput, key);

                            // Set encrypted text in UserOutput
                            op.setUserEncryptOut(output);

                            // Clear the text area and return the encrypted text
                            userInputOutput.selectAll();
                            userInputOutput.replaceSelection("");
                            userInputOutput.setText(output);

                        }

                    //TODO Decrypt - Add a way to have the user select txt files from system
                    if(isDecryptRadioSelected){
                        String encryptTxt = op.getUserEncryptOut();
                        int[] key = op.getUserEncryptKey();

                        //Create a CryptKey object
                        CryptKey unscramble = new CryptKey();

                        String output = unscramble.decrypt(encryptTxt,key);

                        userInputOutput.setText(output);

                    }
                }



            }
        });
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Run UserOutput "Save" method
                    op.Save();

                    // Clear text area and remove values in EncryptOut & EncryptKey
                    userInputOutput.selectAll();
                    userInputOutput.replaceSelection("");
                    op.setUserEncryptOut(null);
                    op.setUserEncryptKey(null);

                } catch (NullPointerException | IOException ex) {
                    ex.printStackTrace();

                    JOptionPane.showMessageDialog(new JFrame(),
                            "Text field blank");
                }
            }
        });
        encryptRadio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Set decrypt radio button to false
                decryptRadio.setSelected(false);

                // Enable text area if previously disabled
                userInputOutput.setEnabled(true);

                // Disable load text and load key buttons (these are used only for decryption)
                loadTxtButton.setEnabled(false);
                loadKeyButton.setEnabled(false);
            }
        });
        decryptRadio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Set encrypt radio button to false
                encryptRadio.setSelected(false);

                // Clear text area
                userInputOutput.selectAll();
                userInputOutput.replaceSelection("");

                // Deactivate text area, so user cannot enter text.  This is now read only.
                userInputOutput.setEnabled(false);

                // Enable the load text and load key buttons
                loadTxtButton.setEnabled(true);
                loadKeyButton.setEnabled(true);
            }
        });
        loadTxtButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Create a JFileChooser object
                JFileChooser loadFile = new JFileChooser();
                // Set filter to only allow txt files to be loaded (can be expanded in the future)
                FileNameExtensionFilter txtFilter = new FileNameExtensionFilter("Text Files","txt");
                // Set filter on FileChooser object
                loadFile.setFileFilter(txtFilter);

                //Create value to hold return variable of open dialog box to compare
                int validReturn = loadFile.showOpenDialog(null);

                if(validReturn == JFileChooser.APPROVE_OPTION){

                    //Variable to hold the file selected by the user
                    File selectedFile = loadFile.getSelectedFile();

                    //Create a variable to hold the output from the text loading
                    String encryptText = null;

                    //Create a scanner object to read the file
                    try {
                        Scanner reader = new Scanner(selectedFile);

                        // Read the text from the file into a variable
                        while(reader.hasNextLine()){
                            encryptText = reader.nextLine();
                        }

                        reader.close();

                    } catch (FileNotFoundException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(new JFrame(),
                                "Error reading file");
                    }

                    // Set UserOutput variable to encryptText value
                    op.setUserEncryptOut(encryptText);

                    // Write text onto JTextArea
                    userInputOutput.setText(encryptText);


                    JOptionPane.showMessageDialog(new JFrame(),
                            "Load Successful");


                }

            }
        });
        loadKeyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //Create a JFileChooser object
                JFileChooser loadFile = new JFileChooser();
                // Set filter to only allow txt files to be loaded (can be expanded in the future)
                FileNameExtensionFilter txtFilter = new FileNameExtensionFilter("Text Files","txt");
                // Set filter on FileChooser object
                loadFile.setFileFilter(txtFilter);

                //Create value to hold return variable of open dialog box to compare
                int validReturn = loadFile.showOpenDialog(null);



                if(validReturn == JFileChooser.APPROVE_OPTION){
                    //Variable to hold the file selected by the user
                    File selectedFile = loadFile.getSelectedFile();

                    // Create a string variable to hold the key string
                    String encryptKey = null;

                    //TODO Need to bring file in as a string and pull the int out into an array

                    try {
                        Scanner reader = new Scanner(selectedFile);

                        while(reader.hasNextLine()){

                            encryptKey = reader.nextLine();
                        }

                        reader.close();

                    } catch (FileNotFoundException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(new JFrame(),
                                "Error reading file");
                    }

                    // Code to remove the brackets from the key string and pull values from string array
                    // to an int array.  This is required for the decrypt method.

                    encryptKey = encryptKey.replaceAll("\\s","");


                    String[] rmvBrackets = encryptKey.substring(1,encryptKey.length() -1).split(",");

                    int[] finalKey = new int[rmvBrackets.length];

                    for(int x=0; x < rmvBrackets.length; x++)
                    {
                        finalKey[x] = Integer.valueOf(rmvBrackets[x]);
                    }

                    // Set finalKey variable to encryptKey value
                    op.setUserEncryptKey(finalKey);


                }

            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("MainWindow");
        frame.setContentPane(new MainWindow().MainWindow);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        //Center the JFrame
        frame.setLocationRelativeTo(null);
    }
}
