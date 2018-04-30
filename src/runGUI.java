
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class runGUI extends JPanel {

    public runGUI (String imgPath) {

        this.setBackground (Color.lightGray);
        Dimension d1 = new Dimension(250, 150);
        this.setPreferredSize(d1);

        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();

        panel2.setSize(100,100);

        ImageIcon image = new ImageIcon(imgPath);
        JLabel label = new JLabel(image);

        JButton resize = new JButton();
        resize.setText("Click to Carve Image Again");

        resize.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {

                int newWidth = panel1.getWidth();
                int newHeight = panel1.getHeight();
                ImageIcon newImage = new ImageIcon(image.getImage().getScaledInstance(newWidth,newHeight,0));
                JLabel newLabel = new JLabel(newImage);
                panel1.remove(label);
                panel1.validate();
                panel1.repaint();

                panel1.remove(newLabel);
                panel1.validate();
                panel1.repaint();

                panel1.add(newLabel);
                panel1.validate();
                panel1.repaint();

                System.out.println("MADE IT");

            }
        });

        panel1.add(label);
        panel2.add(resize);

        JFrame frame = new JFrame ();
        frame.setTitle ("Image Retargeting");
        frame.setResizable (true);
        frame.setSize (700, 500);
        frame.setLayout(new GridLayout(1,2));
        frame.add(panel1);
        //frame.add(panel2);

        frame.setVisible (true);

    }

    public void paintComponent (Graphics g)

    {
        super.paintComponent(g);

    }


}


