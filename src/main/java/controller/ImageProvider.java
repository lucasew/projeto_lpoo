/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;

/**
 *
 * @author lucasew
 */
public class ImageProvider {
    private static Map<String, ImageIcon> icons;
    
    public static ImageIcon getImage(String path) {
        if (ImageProvider.icons == null) {
            ImageProvider.icons = new HashMap<>();
        }
        synchronized(ImageProvider.icons) {
            ImageIcon ico = ImageProvider.icons.get(path);
            if (ico == null) {
                ico = new ImageIcon(ImageProvider.class.getResource(path));
                ImageProvider.icons.put(path, ico);
            }
            return ico;
        }
    }
}
