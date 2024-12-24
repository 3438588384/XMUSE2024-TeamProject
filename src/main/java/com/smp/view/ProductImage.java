package com.smp.view;

import javax.swing.*;
import java.awt.*;

public class ProductImage extends JPanel{
    private JLabel idLabel;
    private JLabel nameLabel;
    private JLabel priceLabel;
    private JLabel typeLabel;
    private JLabel imageLabel;

    public ProductImage() {
        setLayout(new GridLayout(5, 1));

        idLabel = new JLabel("编号：");
        nameLabel = new JLabel("名称：");
        priceLabel = new JLabel("价格：");
        typeLabel = new JLabel("分类：");
        imageLabel = new JLabel();

        add(idLabel);
        add(nameLabel);
        add(priceLabel);
        add(typeLabel);
        add(imageLabel);
    }

    public void updateProductDetails(String id, String name, String price, String type, String imagePath) {
        idLabel.setText("编号：" + id);
        nameLabel.setText("名称：" + name);
        priceLabel.setText("价格：" + price);
        typeLabel.setText("分类：" + type);
        ImageIcon imageIcon = new ImageIcon(imagePath);
        imageLabel.setIcon(imageIcon);
    }
}