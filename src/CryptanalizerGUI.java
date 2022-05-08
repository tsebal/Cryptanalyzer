import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Map;
import javax.swing.*;

/**
 * Cryptanalizer GUI
 * Encrypts and decrypts the provided text with Caesar's algorithm from the provided ALPHABET.
 * Selects an encryption key for the encrypted text based on the provided ALPHABET.
 *
 * @author Tsebal
 * @since 0.12b
 */

public class CryptanalizerGUI {
    private static String inputFile;
    private static String outputFile;

    public static void main(String[] args) {
        createWindow();
    }

    private static void createWindow() {
        JFrame frame = new JFrame("Cryptanalizer v.0.11b by Tsebal (Encrypts/decrypts text with Caesar's algorithm)");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        createUI(frame);
        frame.setSize(570, 200);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static void createUI(final JFrame frame) {
        //open file dialog button, label open file info
        JPanel panel1 = new JPanel();
        panel1.setLayout(new FlowLayout(FlowLayout.LEFT));
        JButton btnOpenFile = new JButton("Open File");
        btnOpenFile.setPreferredSize(new Dimension(100, 25));
        final JLabel labelBtnOpen = new JLabel();
        labelBtnOpen.setText("-> specify source file for encryption/decryption");
        //initialize action for btnOpenFile
        btnOpenFileInitializer(btnOpenFile, labelBtnOpen, frame);

        //Save to file dialog button, label save to file info
        JPanel panel2 = new JPanel();
        panel2.setLayout(new FlowLayout(FlowLayout.LEFT));
        JButton btnSaveFile = new JButton("Save to File");
        btnSaveFile.setPreferredSize(new Dimension(100, 25));
        final JLabel labelBtnSave = new JLabel();
        labelBtnSave.setText("-> specify the file to save the encryption/decryption results");
        //initialize action for btnSaveFile
        btnSaveFileInitializer(btnSaveFile, labelBtnSave, frame);

        //contains label and JTextField for cipher key value entered by user
        JPanel panel3 = new JPanel();
        panel3.setLayout(new FlowLayout(FlowLayout.RIGHT));
        final JLabel labelKey = new JLabel("Cipher KEY = ");
        JTextField keyField = new JTextField("0");
        keyField.setPreferredSize(new Dimension(50, 25));
        setNumericOnly(keyField);           //checks only digits are entered by user

        //contains 4 buttons for encryption/decryption/bruteforcing
        JPanel panelFourBtns = new JPanel();
        panelFourBtns.setLayout(new FlowLayout(FlowLayout.CENTER));
        JButton btnEncrypt = new JButton("Encrypt");
        btnEncrypt.setPreferredSize(new Dimension(100, 25));
        JButton btnDecrypt = new JButton("Decrypt");
        btnDecrypt.setPreferredSize(new Dimension(100, 25));
        JButton btnBruteForce = new JButton("BruteForce");
        btnBruteForce.setPreferredSize(new Dimension(100, 25));
        JButton btnStatAnalysis = new JButton("StatAnalysis");
        btnStatAnalysis.setPreferredSize(new Dimension(105, 25));
        //initialize action for btnEncrypt (method =0 for encryption)
        btnEncryptInitializer(btnEncrypt, keyField, frame, 0);
        //initialize action for btnDecrypt (method =1 for decryption)
        btnEncryptInitializer(btnDecrypt, keyField, frame, 1);
        //initialize action for btnBruteForce (BruteForce method)
        btnBruteForceInitializer(btnBruteForce, keyField, frame);
        //initialize action for btnStatAnalysis (Statistical Analyze method)
        btnStatAnalysisInitializer(btnStatAnalysis, keyField, frame);

        //prepare panels and frame
        panel1.add(btnOpenFile);
        panel1.add(labelBtnOpen);
        panel2.add(btnSaveFile);
        panel2.add(labelBtnSave);
        panel3.add(labelKey);
        panel3.add(keyField);
        panelFourBtns.add(btnEncrypt);
        panelFourBtns.add(btnDecrypt);
        panelFourBtns.add(btnBruteForce);
        panelFourBtns.add(btnStatAnalysis);

        frame.getContentPane().add(panel1, BorderLayout.NORTH);
        frame.getContentPane().add(panel2, BorderLayout.BEFORE_LINE_BEGINS);
        frame.getContentPane().add(panel3);
        frame.getContentPane().add(panelFourBtns, BorderLayout.SOUTH);
    }

    //initializes action for btnOpenFile
    public static void btnOpenFileInitializer(JButton jButton, JLabel jLabel, JFrame frame) {
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int option = fileChooser.showOpenDialog(frame);
                if (option == JFileChooser.APPROVE_OPTION) {
                    File fileToOpen = fileChooser.getSelectedFile();
                    jLabel.setText("File Selected: " + fileToOpen.getName());
                    inputFile = fileToOpen.getAbsolutePath();      //saves path to input file
                } else {
                    jLabel.setText("Open command canceled");
                }
            }
        });
    }

    //initializes action for btnSaveFile
    public static void btnSaveFileInitializer(JButton jButton, JLabel jLabel, JFrame frame) {
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Specify a file to save");

                int option = fileChooser.showSaveDialog(frame);
                if (option == JFileChooser.APPROVE_OPTION) {
                    File fileToSave = fileChooser.getSelectedFile();
                    jLabel.setText("File to save: " + fileToSave.getName());
                    outputFile = fileToSave.getAbsolutePath();     //saves path to output result
                } else {
                    jLabel.setText("Save command canceled");
                }
            }
        });
    }

    //initializes action for btnEncrypt, btnDecrypt (method =0 for encryption, =1 for decryption)
    public static void btnEncryptInitializer(JButton jButton, JTextField keyField, JFrame frame, int method) {
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //if the file paths has choosen
                if (inputFile != null && outputFile != null) {
                    String result = Cryptanalizer.encryptorDecryptor(
                            Paths.get(inputFile),
                            Paths.get(outputFile),
                            Integer.parseInt(keyField.getText()),
                            method);
                    JOptionPane.showMessageDialog(frame, result, "Result", 1);
                } else {
                    JOptionPane.showMessageDialog(frame,
                            "Please select the source file and the file where to save result.",
                            "Warning", 2);
                }
            }
        });
    }

    //initializes action for btnBruteForce (BruteForce method)
    public static void btnBruteForceInitializer(JButton jButton, JTextField keyField, JFrame frame) {
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (inputFile != null) {
                    BruteForceCipher bruteForceCipher = new BruteForceCipher(
                            Paths.get(inputFile),
                            Cryptanalizer.ALPHABET);
                    try {
                        int key = bruteForceCipher.findKey();
                        keyField.setText(String.valueOf(key));      //sets cipher key into keyField
                        JOptionPane.showMessageDialog(frame,
                                "Encryption key found = " + key +
                                        "\n Please press \"Save to file\" button and \"Decrypt\" button to save result.",
                                "BruteForce method result",
                                1);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(frame,
                            "Please select the source file to brute force them.\n" +
                                    "Or the information encoded in the input file is not logical text.",
                            "Warning",
                            2);
                }
            }
        });
    }

    //initialize action for btnStatAnalysis (Statistical Analyze method)
    public static void btnStatAnalysisInitializer(JButton jButton, JTextField keyField, JFrame frame) {
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //if the input file is present, initialize statisticalAnalyzer
                //pass the path to the encrypted file and the alphabet
                if (inputFile != null) {
                    StatisticalAnalyzer statisticalAnalyzer = new StatisticalAnalyzer(
                            Paths.get(inputFile),
                            Cryptanalizer.ALPHABET);
                    try {
                        //add the analysis results to the map
                        Map<Integer, String> resultMap = statisticalAnalyzer.analyzeText();
                        String showResult = "";
                        for (Map.Entry<Integer, String> key : resultMap.entrySet()) {
                            showResult += "Cipher key = " + key.getKey() + " Decrypted text snippet: " + key.getValue() + "\n";
                        }
                        JOptionPane.showMessageDialog(frame,
                                showResult +
                                        "\nPlease press \"Save to file\" button then \"Decrypt\" button " +
                                        "and enter preferred Cipher key to save result.",
                                "Statistical analysis result",
                                1);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(frame,
                            "Please specify the input file for key matching by statistical analysis.\n" +
                                    "Or the information encoded in the input file is not logical text.",
                            "Warning",
                            2);
                }
            }
        });
    }

    //checks user entered numeric value of cipher key
    public static void setNumericOnly(JTextField jTextField) {
        jTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if ((!Character.isDigit(c)) ||
                        (c == KeyEvent.VK_BACK_SPACE) ||
                        (c == KeyEvent.VK_DELETE)) {
                    e.consume();
                }
            }
        });
    }
}