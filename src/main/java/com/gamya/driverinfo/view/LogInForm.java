/*
 * Copyright 2014 Samuel Franklyn <sfranklyn at gmail.com>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gamya.driverinfo.view;

import com.gamya.driverinfo.controller.LogIn;
import static java.awt.EventQueue.invokeLater;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import net.java.dev.designgridlayout.DesignGridLayout;

/**
 *
 * @author Samuel Franklyn <sfranklyn at gmail.com>
 */
public class LogInForm extends JFrame {

    private final JTextField textSystem;
    private final JTextField textDatabase;
    private final JTextField textUser;
    private final JPasswordField textPassword;
    private final JButton buttonOk;
    private final JButton buttonCancel;
    private final LogIn logIn;
    private Connection con;

    public LogInForm() {
        logIn = new LogIn();

        setTitle("Log In");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        DesignGridLayout layout = new DesignGridLayout(panel);

        textSystem = new JTextField("", 10);
        textDatabase = new JTextField("", 10);
        textUser = new JTextField("", 10);
        textPassword = new JPasswordField("", 10);

        buttonOk = new JButton("Ok");
        buttonOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                buttonOkAction(evt);
            }
        });

        buttonCancel = new JButton("Cancel");
        buttonCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                buttonCancelAction(evt);
            }
        });

        layout.row().grid(new JLabel("System")).add(textSystem);
        layout.row().grid(new JLabel("Database")).add(textDatabase);
        layout.row().grid(new JLabel("User")).add(textUser);
        layout.row().grid(new JLabel("Password")).add(textPassword);
        layout.row().grid().add(buttonOk).add(buttonCancel);

        add(panel);
        rootPane.setDefaultButton(buttonOk);
        pack();
        setLocationRelativeTo(null);
    }

    private void buttonOkAction(ActionEvent evt) {
        con = logIn.logIn(textSystem.getText(), textDatabase.getText(),
                textUser.getText(), new String(textPassword.getPassword()));
        if (con == null) {
            JOptionPane.showMessageDialog(null, logIn.getMessage());
            return;
        }
        setVisible(false);
        dispose();
        invokeLater(new Runnable() {
            @Override
            public void run() {
                DriverInfoForm driverInfoForm = new DriverInfoForm();
                driverInfoForm.setCon(con);
                driverInfoForm.setVisible(true);
            }
        });
    }

    private void buttonCancelAction(ActionEvent evt) {
        dispose();
        System.exit(0);
    }

    public static void main(String args[]) {
        invokeLater(new Runnable() {
            @Override
            public void run() {
                new LogInForm().setVisible(true);
            }
        });
    }

}
