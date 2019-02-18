package com.test.unit9;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


import javafx.scene.layout.Border;
public class PermissionTest {

    public static void main(String[] args) {
        System.setProperty("java.security.policy", "src/com/test/unit9/PermissionTest.policy");
        System.setSecurityManager(new SecurityManager());
        EventQueue.invokeLater(()->
        {
            JFrame frame = new PermissionTestFrame();
            frame.setTitle("PermissionTest");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }

}
class PermissionTestFrame extends JFrame{
    private JTextField textField;
    private WordCheckTextArea textArea;
    private static final int EXCT_ROWS = 20;
    private static final int TEXT_COLUMNS = 60;
    
    public PermissionTestFrame() {
        textField = new JTextField(20);
        JPanel panel = new JPanel();
        panel.add(textField);
        JButton openButton = new JButton("Insert");
        panel.add(openButton);
        openButton.addActionListener(e->insertWords(textField.getText()));
        
        add(panel, BorderLayout.NORTH);
        
        textArea = new WordCheckTextArea();
        textArea.setRows(EXCT_ROWS);
        textArea.setColumns(TEXT_COLUMNS);
        add(new JScrollPane(textArea),BorderLayout.CENTER);
        pack();
    }

    private void insertWords(String text) {
        try {
            textArea.append(text + System.lineSeparator());
        } catch (Exception e) {
            // 当没有权限时
            JOptionPane.showMessageDialog(this, "I am sorry, but I cannot do that.");
            e.printStackTrace();
        }
    }
}
class WordCheckTextArea extends JTextArea{
    @Override
    public void append(String str) {
        WordCheckPermission p = new WordCheckPermission(str.replace("\r\n", ""), "insert");
        SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            sm.checkPermission(p);
        }
        super.append(str);
    }
}