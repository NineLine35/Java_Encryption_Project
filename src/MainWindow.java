import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class MainWindow {
    private JPanel MainWindow;
    private JRadioButton decryptRadio;
    private JRadioButton encryptRadio;
    private JPanel buttonPanel;
    private JButton processButton;
    private JButton saveButton;
    private JTextArea userInputOutput;
    private JButton loadTxtButton;
    private JButton button4;
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
        });
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Run UserOutput "Save" method
                    op.Save();

                    // Clear text area
                    userInputOutput.selectAll();
                    userInputOutput.replaceSelection("");

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

                    //TODO Add loading of file to CryptKey variable

                    JOptionPane.showMessageDialog(new JFrame(),
                            "Load Successful");


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
    }
}
