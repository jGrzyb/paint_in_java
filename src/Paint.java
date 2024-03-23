import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.rmi.ssl.SslRMIClientSocketFactory;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Paint implements ActionListener {

    public static void main(String[] args) {
        new Paint();
    }
    JMenuItem it1, it2, it3, it4;
    JRadioButtonMenuItem it5, it6, it7;
    JMenuBar bar;
    JMenu menu, size;
    JFrame frame;
    TestPane tp = new TestPane();

    public Paint() {
        frame = new JFrame("Testing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(tp);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setSize(500, 500);
        frame.setResizable(false);

        bar = new JMenuBar();
        menu = new JMenu("Menu");
        size = new JMenu("size");

        it1 = new JMenuItem("Save");
        it2 = new JMenuItem("Open");
        it3 = new JMenuItem("Info");
        it4 = new JMenuItem("Exit");
        it5 = new JRadioButtonMenuItem("Small");
        it6 = new JRadioButtonMenuItem("Medium");
        it7 = new JRadioButtonMenuItem("Large");
        it5.setSelected(true);
        ButtonGroup bg = new ButtonGroup();
        bg.add(it5);
        bg.add(it6);
        bg.add(it7);

        menu.add(it1);
        menu.add(it2);
        menu.add(it3);
        menu.add(size);
        menu.add(it4);
        size.add(it5);
        size.add(it6);
        size.add(it7);

        bar.add(menu);
        frame.setJMenuBar(bar);
        frame.setVisible(true);

        it1.addActionListener(this);
        it2.addActionListener(this);
        it3.addActionListener(this);
        it4.addActionListener(this);
        it5.addActionListener(this);
        it6.addActionListener(this);
        it7.addActionListener(this);

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                }
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == it1) {
            save();
        }
        else if(e.getSource() == it2) {
            open();
        }
        else if(e.getSource() == it3) {
            info();
        }
        else if(e.getSource() == it4) {
            System.out.println("exit");
            exit();
        }
        else if(e.getSource() == it5) {
            frame.setSize(500, 500);
        }
        else if(e.getSource() == it6) {
            frame.setSize(700, 700);
        }
        else if(e.getSource() == it7) {
            frame.setSize(1000, 1000);
        }
    }

    private void save() {
        try {
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                BufferedImage bi = this.tp.paintPane.background;
                ImageIO.write(bi, "png", file);
            }

        } catch(Exception e) {
            System.out.println("Błąd");
        }
    }
        private void open() {
        try {
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                BufferedImage img = ImageIO.read(file);
                BufferedImage convertedImg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
                convertedImg.getGraphics().drawImage(img, 0, 0, null);
                this.tp.paintPane.background = convertedImg;
                int ww = this.frame.getWidth() - this.tp.paintPane.getWidth();
                int hh = this.frame.getHeight() - this.tp.paintPane.getHeight();
                frame.setSize(convertedImg.getWidth() + ww,convertedImg.getHeight() + hh);
                this.tp.paintPane.repaint();
            }


        } catch(Exception e) {
            System.out.println();
        }

    }
    private void info() {
        JPanel p = new JPanel();
        JTextArea t = new JTextArea();
        t.setEditable(false);
        t.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 20));
        p.add(t);
        t.setText("""
                Jakub Grzyb
                EAIiIB ISI
                rok 2 grupa 3
                """);

        JFrame info = new JFrame("Info");
        info.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        info.setLayout(new BorderLayout());
        info.setLocationRelativeTo(null);
        info.add(p);
        info.setSize(200, 200);
        info.setVisible(true);
    }
    private void exit() {
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }


    public class TestPane extends JPanel {

        private PaintPane paintPane;

        public TestPane() {
            setLayout(new BorderLayout());
            add((paintPane = new PaintPane()));
            add(new ColorsPane(paintPane), BorderLayout.SOUTH);
        }
    }

    public class ColorsPane extends JPanel {
        public static JSlider s = new JSlider();

        public ColorsPane(PaintPane paintPane) {
            setLayout(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();
            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridy = 0;
            c.gridx = 0;
            JButton b1 = new JButton(new ColorAction(paintPane, "B", Color.BLACK));
            b1.setBackground(Color.BLACK);
            add(b1, c);
            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridy = 0;
            c.gridx = 1;
            JButton b2 = new JButton(new ColorAction(paintPane, "R", Color.RED));
            b2.setBackground(Color.RED);
            add(b2, c);
            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridy = 0;
            c.gridx = 2;
            JButton b3 = new JButton(new ColorAction(paintPane, "G", Color.GREEN));
            b3.setBackground(Color.GREEN);
            add(b3, c);
            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridy = 0;
            c.gridx = 3;
            JButton b4 = new JButton(new ColorAction(paintPane, "B", Color.BLUE));
            b4.setBackground(Color.BLUE);
            add(b4, c);
            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridy = 0;
            c.gridx = 4;
            JButton b5 = new JButton(new ColorAction(paintPane, "Y", Color.YELLOW));
            b5.setBackground(Color.YELLOW);
            add(b5, c);
            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridy = 0;
            c.gridx = 5;
            JButton b6 = new JButton(new ColorAction(paintPane, "O", Color.ORANGE));
            b6.setBackground(Color.ORANGE);
            add(b6, c);
            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridy = 0;
            c.gridx = 6;
            JButton b7 = new JButton(new ColorAction(paintPane, "P", Color.PINK));
            b7.setBackground(Color.PINK);
            add(b7, c);
            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridy = 0;
            c.gridx = 7;
            JButton b8 = new JButton(new ColorAction(paintPane, "W", Color.WHITE));
            b8.setBackground(Color.WHITE);
            add(b8, c);
            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridy = 0;
            c.gridx = 8;
            JButton b9 = new JButton(new ColorAction(paintPane, "G", Color.GRAY));
            b9.setBackground(Color.GRAY);
            add(b9, c);
            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridy = 0;
            c.gridx = 9;
            JButton b10 = new JButton(new ColorAction(paintPane, "C", Color.CYAN));
            b10.setBackground(Color.CYAN);
            add(b10, c);
            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridy = 1;
            c.gridx = 0;
            c.gridwidth = 2;
            JButton bs1 = new JButton(new ShapeAction(paintPane, 1, "Square"));
            add(bs1, c);
            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridy = 1;
            c.gridx = 2;
            JButton bs2 = new JButton(new ShapeAction(paintPane, 2, "Triangle"));
            add(bs2, c);
            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridy = 1;
            c.gridx = 4;
            JButton bs3 = new JButton(new ShapeAction(paintPane, 0, "Circle"));
            add(bs3, c);


            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridy = 2;
            c.gridx = 0;
            c.gridwidth = 10;
            s.setMinimum(2);
            s.setMaximum(20);
            add(s, c);
        }

        public class ShapeAction extends AbstractAction {
            private PaintPane pa;
            private int shape=0;
            public ShapeAction(PaintPane pa, int sh, String n) {
                putValue(NAME, n);
                this.pa = pa;
                this.shape = sh;
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                pa.shape = shape;
            }
        }

        public class ColorAction extends AbstractAction {

            private PaintPane paintPane;
            private Color color;

            private ColorAction(PaintPane paintPane, String name, Color color) {
                putValue(NAME, name);
                this.paintPane = paintPane;
                this.color = color;
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                paintPane.setForeground(color);
            }

        }

    }

    public class PaintPane extends JPanel implements ChangeListener {
        int size = 10;
        int shape = 0;

        private BufferedImage background;

        public PaintPane() {
            setBackground(Color.WHITE);
            setForeground(Color.BLACK);
            MouseAdapter handler = new MouseAdapter() {

                @Override
                public void mousePressed(MouseEvent e) {
                    drawDot(e.getPoint());
                }

                @Override
                public void mouseDragged(MouseEvent e) {
                    drawDot(e.getPoint());
                }

            };
            addMouseListener(handler);
            addMouseMotionListener(handler);
            ColorsPane.s.addChangeListener(this);


        }

        protected void drawDot(Point p) {
            if (background == null) {
                updateBuffer();
            }

            if (background != null && shape == 0) {
                Graphics2D g2d = background.createGraphics();
                g2d.setColor(getForeground());
                g2d.fillOval(p.x - size/2, p.y - size/2, size, size);
                g2d.dispose();
            }
            else if (background != null && shape == 1) {
                Graphics2D g2d = background.createGraphics();
                g2d.setColor(getForeground());
                g2d.fillRect(p.x - size/2, p.y - size/2, size, size);
                g2d.dispose();
            }
            else if (background != null){
                Graphics2D g2d = background.createGraphics();
                g2d.setColor(getForeground());
                int[] xp = {p.x - size/2, p.x, p.x + size/2};
                int[] yp = {p.y, p.y - size, p.y};
                Polygon po = new Polygon(xp, yp, 3);
                g2d.fillPolygon(po);
                g2d.dispose();
            }
            repaint();
        }

        @Override
        public void invalidate() {
            super.invalidate();
            updateBuffer();
        }

        protected void updateBuffer() {

            if (getWidth() > 0 && getHeight() > 0) {
                BufferedImage newBuffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
                Graphics2D g2d = newBuffer.createGraphics();
                g2d.setColor(Color.WHITE);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                if (background != null) {
                    g2d.drawImage(background, 0, 0, this);
                }
                g2d.dispose();
                background = newBuffer;
            }

        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(200, 200);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();
            if (background == null) {
                updateBuffer();
            }
            g2d.drawImage(background, 0, 0, this);
            g2d.dispose();
        }

        @Override
        public void stateChanged(ChangeEvent e) {
            if(e.getSource() == ColorsPane.s) {
                size = ColorsPane.s.getValue();
            }
        }
    }
}