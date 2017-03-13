/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package XYG_BASIC;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JPanel;

/**
 *
 * @author KOCMOC
 */
public class LegendElement extends JPanel {

    private final String name;
    private final Color color;
    private final JPanel container = new JPanel(new GridBagLayout());
    private final Font font = new Font("Serif", Font.PLAIN, 15);
    
    public LegendElement(String name, Color color) {
        this.setPreferredSize(new Dimension(100, 50));
        this.name = name;
        this.color = color;
    }
    
    @Override
    public void paintComponent(Graphics g) {
//            super.paintComponent(g);
//            super.setBackground(Color.BLUE);

        Graphics2D g2 = (Graphics2D) g;
        g2.setPaint(color);
        g2.fillRect(0, this.getWidth() / 10, this.getWidth() / 4, this.getHeight() / 10);
        g2.setFont(font);
        g2.drawString(name, 45, 15);
    }
    
    public JPanel getLegendElement(){
        container.add(this);
        return container;
    }
}
