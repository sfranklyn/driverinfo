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

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import net.java.dev.designgridlayout.DesignGridLayout;

/**
 *
 * @author Samuel Franklyn <sfranklyn at gmail.com>
 */
public class DriverInfoForm extends JFrame {

    private final DesignGridLayout layout;
    private final JTextField textNomor;
    private final JTextField textNama;
    private final JTextField textPool;
    private final JTextField textPerusahaan;
    private final JTextField textGolongan;
    private final JLabel labelImage;
    private Connection con;
    private PreparedStatement selectStmt;

    public DriverInfoForm() {
        setTitle("Driver Info");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        layout = new DesignGridLayout(panel);

        textNomor = new JTextField("", 5);
        textNomor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                textNomorAction(evt);
            }
        });

        textNama = new JTextField("", 15);
        textNama.setEditable(false);
        textPool = new JTextField("", 2);
        textPool.setEditable(false);
        textPerusahaan = new JTextField("", 3);
        textPerusahaan.setEditable(false);
        textGolongan = new JTextField("", 3);
        textGolongan.setEditable(false);
        labelImage = new JLabel();

        layout.row().grid(new JLabel("Nomor")).add(textNomor);
        layout.row().grid(new JLabel("Nama")).add(textNama);
        layout.row().grid(new JLabel("Pool")).add(textPool);
        layout.row().grid(new JLabel("Perusahaan")).add(textPerusahaan);
        layout.row().grid(new JLabel("Golongan")).add(textGolongan);
        layout.row().grid().add(labelImage);

        add(panel);
        pack();
        setLocationRelativeTo(null);
    }

    private void textNomorAction(ActionEvent evt) {
        try {
            textNama.setText("");
            textPool.setText("");
            textPerusahaan.setText("");
            textGolongan.setText("");

            if (!textNomor.getText().isEmpty()) {
                selectStmt.setString(1, textNomor.getText());

                ResultSet resultSet = selectStmt.executeQuery();
                if (resultSet.next()) {
                    textNama.setText(resultSet.getString(1).trim());
                    textPool.setText(resultSet.getString(2).trim());
                    textPerusahaan.setText(resultSet.getString(3).trim());
                    textGolongan.setText(resultSet.getString(4).trim());
                } else {
                    JOptionPane.showMessageDialog(null, "Nomor tidak terdaftar");
                }
            }

            labelImage.setIcon(null);
            File fileImage = new File("images/" + textNomor.getText() + ".jpg");
            if (fileImage.isFile()) {
                BufferedImage img = ImageIO.read(fileImage);
                Image image = img.getScaledInstance(img.getWidth() / 4,
                        img.getHeight() / 4, Image.SCALE_DEFAULT);
                ImageIcon icon = new ImageIcon(image);
                labelImage.setIcon(icon);
            }
            pack();
            setLocationRelativeTo(null);

        } catch (Exception ex) {
            Logger.getLogger(DriverInfoForm.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        try {
            this.con = con;
            String selectRecord = "SELECT "
                + "UP_NAMPEG,"
                + "UP_PLPEG, "
                + "UP_PRSPEG, "
                + "UP_GOLMT "
                + "FROM MASPENG "
                + "WHERE "
                + "UP_NOPEG = ?";
            selectStmt = con.prepareStatement(selectRecord);
        } catch (Exception ex) {
            Logger.getLogger(DriverInfoForm.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

}
