import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
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
    private JButton closeButton;
    private JButton decryptButton;

    public MainWindow() {
        // Create a UserOutput object to store encrypted strings and keys
        UserOutput op = new UserOutput();

        // Set decrypt button to disabled a launch
        decryptButton.setEnabled(false);

        // Action to read text field and return encrypted text to the text area
        this.processButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // Encrypt process
                if (userInputOutput.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(new JFrame(),
                            "Text field blank");
                } else {
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
                    userInputOutput.setText(output);

                }

            }

        });

        //Button to kick off the saving of the encrypted text and key file
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

        // Radio buttons that change the functions of the app between encrypting & decrypting
        encryptRadio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Set decrypt radio button to false
                decryptRadio.setSelected(false);

                // Enable text area if previously disabled
                userInputOutput.setEnabled(true);
                userInputOutput.setText("");

                // Disable load text and load key buttons (these are used only for decryption)
                loadTxtButton.setEnabled(false);
                loadKeyButton.setEnabled(false);
                decryptButton.setEnabled(false);

                saveButton.setEnabled(true);
                processButton.setEnabled(true);
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

                decryptButton.setEnabled(true);
                saveButton.setEnabled(false);
                processButton.setEnabled(false);

                op.setUserEncryptOut(null);
                op.setUserEncryptKey(null);
            }
        });

        // Button to kick off load text action
        loadTxtButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Create a JFileChooser object
                JFileChooser loadFile = new JFileChooser("C:\\");
                // Set filter to only allow txt files to be loaded (can be expanded in the future)
                FileNameExtensionFilter txtFilter = new FileNameExtensionFilter("Text Files", "txt");
                // Set filter on FileChooser object
                loadFile.setFileFilter(txtFilter);

                op.setUserEncryptOut("");

                //Create value to hold return variable of open dialog box to compare
                int validReturn = loadFile.showOpenDialog(null);

                if (validReturn == JFileChooser.APPROVE_OPTION) {

                    //Variable to hold the file selected by the user
                    File selectedFile = loadFile.getSelectedFile();

                    // Check to ensure that file loaded is an encrypted text file, and not an encrypted text file
                    String correctPath = "encryptTXT";

                    if (selectedFile.toString().contains(correctPath)) {
                        //Create a variable to hold the output from the text loading
                        String encryptText = "";

                        //Create a scanner object to read the file
                        try {
                            Scanner reader = new Scanner(selectedFile);

                            // Read the text from the file into a variable
                            while (reader.hasNextLine()) {
                                encryptText = encryptText + reader.nextLine();
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

                    } else {
                        JOptionPane.showMessageDialog(new JFrame(),
                                "Incorrect Text File");
                    }

                }

            }
        });

        // Button to load key file action
        loadKeyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //Create a JFileChooser object
                JFileChooser loadFile = new JFileChooser("C:\\");
                // Set filter to only allow txt files to be loaded (can be expanded in the future)
                FileNameExtensionFilter txtFilter = new FileNameExtensionFilter("Text Files", "txt");
                // Set filter on FileChooser object
                loadFile.setFileFilter(txtFilter);

                op.setUserEncryptKey(null);

                //Create value to hold return variable of open dialog box to compare
                int validReturn = loadFile.showOpenDialog(null);


                if (validReturn == JFileChooser.APPROVE_OPTION) {
                    //Variable to hold the file selected by the user
                    File selectedFile = loadFile.getSelectedFile();

                    // Check to ensure that file loaded is a key file, and not an encrypted text file
                    String correctPath = "keyTXT";

                    if (selectedFile.toString().contains(correctPath)) {
                        // Create a string variable to hold the key string
                        String encryptKey = "";


                        // Read the selected text file
                        try {
                            Scanner reader = new Scanner(selectedFile);

                            while (reader.hasNextLine()) {

                                encryptKey = encryptKey + reader.nextLine();
                            }

                            reader.close();

                        } catch (FileNotFoundException ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(new JFrame(),
                                    "Error reading file");
                        }

                        // Code to remove the brackets from the key string and pull values from string array
                        // to an int array.  This is required for the decrypt method.

                        encryptKey = encryptKey.replaceAll("\\s", "");


                        String[] rmvBrackets = encryptKey.substring(1, encryptKey.length() - 1).split(",");

                        int[] finalKey = new int[rmvBrackets.length];

                        for (int x = 0; x < rmvBrackets.length; x++) {
                            finalKey[x] = Integer.valueOf(rmvBrackets[x]);
                        }

                        // Set finalKey variable to encryptKey value
                        op.setUserEncryptKey(finalKey);

                        // Confirmation of successful load
                        JOptionPane.showMessageDialog(new JFrame(),
                                "Load Successful");
                    } else {
                        JOptionPane.showMessageDialog(new JFrame(),
                                "Incorrect Key File");
                    }


                }

            }
        });

        // Button to process a decryption of the loaded txt and key files
        decryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //Throw a notification if the text area is blank and the user tries to decrypt
                if (userInputOutput.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(new JFrame(),
                            "Please load text & key file");
                }
                else
                {
                    String encryptTxt = op.getUserEncryptOut();
                    int[] key = op.getUserEncryptKey();

                    //Create a CryptKey object
                    CryptKey unscramble = new CryptKey();

                    String output = unscramble.decrypt(encryptTxt, key);

                    userInputOutput.setText(output);
                    userInputOutput.setEnabled(true);
                }

            }
        });

        // Closes the program
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Java Text Encryption");
        frame.setContentPane(new MainWindow().MainWindow);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        //Center the JFrame
        frame.setLocationRelativeTo(null);
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        MainWindow = new JPanel();
        MainWindow.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(5, 5, new Insets(0, 0, 0, 0), -1, -1));
        MainWindow.setPreferredSize(new Dimension(400, 200));
        MainWindow.setRequestFocusEnabled(true);
        encryptRadio = new JRadioButton();
        encryptRadio.setSelected(true);
        encryptRadio.setText("Encrypt");
        MainWindow.add(encryptRadio, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
        MainWindow.add(buttonPanel, new com.intellij.uiDesigner.core.GridConstraints(1, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        processButton = new JButton();
        processButton.setText("Encrypt");
        buttonPanel.add(processButton, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_SOUTH, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        saveButton = new JButton();
        saveButton.setLabel("Save");
        saveButton.setText("Save");
        buttonPanel.add(saveButton, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        decryptButton = new JButton();
        decryptButton.setText("Decrypt");
        buttonPanel.add(decryptButton, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        userInputOutput = new JTextArea();
        userInputOutput.setLineWrap(true);
        userInputOutput.setWrapStyleWord(true);
        MainWindow.add(userInputOutput, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        loadKeyButton = new JButton();
        loadKeyButton.setEnabled(false);
        loadKeyButton.setText("Load Key");
        MainWindow.add(loadKeyButton, new com.intellij.uiDesigner.core.GridConstraints(2, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        decryptRadio = new JRadioButton();
        decryptRadio.setText("Decrypt");
        MainWindow.add(decryptRadio, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        closeButton = new JButton();
        closeButton.setText("Close");
        MainWindow.add(closeButton, new com.intellij.uiDesigner.core.GridConstraints(3, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        loadTxtButton = new JButton();
        loadTxtButton.setEnabled(false);
        loadTxtButton.setText("Load Text");
        MainWindow.add(loadTxtButton, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer1 = new com.intellij.uiDesigner.core.Spacer();
        MainWindow.add(spacer1, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer2 = new com.intellij.uiDesigner.core.Spacer();
        MainWindow.add(spacer2, new com.intellij.uiDesigner.core.GridConstraints(1, 4, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer3 = new com.intellij.uiDesigner.core.Spacer();
        MainWindow.add(spacer3, new com.intellij.uiDesigner.core.GridConstraints(4, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return MainWindow;
    }

}
