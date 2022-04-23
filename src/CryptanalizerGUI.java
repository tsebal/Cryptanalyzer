import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CryptanalizerGUI {
    public static void main(String[] args) {
        createWindow();
    }

    private static void createWindow() {
        JFrame frame = new JFrame("Cryptanalizer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        createUI(frame);
        frame.setSize(560, 200);
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

        //Save to file dialog button, label save to file info
        JPanel panel2 = new JPanel();
        panel2.setLayout(new FlowLayout(FlowLayout.LEFT));
        JButton btnSaveFile = new JButton("Save to File");
        btnSaveFile.setPreferredSize(new Dimension(100, 25));
        final JLabel labelBtnSave = new JLabel();

        JPanel panel3 = new JPanel();
        panel3.setLayout(new FlowLayout(FlowLayout.LEFT));
        final JLabel labelKey = new JLabel("Cipher KEY = ");



        btnOpenFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int option = fileChooser.showOpenDialog(frame);
                if (option == JFileChooser.APPROVE_OPTION) {
                    File fileToOpen = fileChooser.getSelectedFile();
                    labelBtnOpen.setText("File Selected: " + fileToOpen.getName());
                } else {
                    labelBtnOpen.setText("Open command canceled");
                }
            }
        });

        btnSaveFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Specify a file to save");

                int option = fileChooser.showSaveDialog(frame);
                if (option == JFileChooser.APPROVE_OPTION) {
                    File fileToSave = fileChooser.getSelectedFile();
                    labelBtnSave.setText("File to save: " + fileToSave.getName());
                } else {
                    labelBtnSave.setText("Save command canceled");
                }
            }
        });

        panel1.add(btnOpenFile);
        panel1.add(labelBtnOpen);
        panel2.add(btnSaveFile);
        panel2.add(labelBtnSave);
        panel3.add(labelKey);

        frame.getContentPane().add(panel1, BorderLayout.NORTH);
        frame.getContentPane().add(panel2, BorderLayout.BEFORE_LINE_BEGINS);
        frame.getContentPane().add(panel3, BorderLayout.SOUTH);
    }
}