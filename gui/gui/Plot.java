package gui;

import ExtraFunctions.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;

@SuppressWarnings("serial")
public class Plot extends JPanel {
    // --- POLYMORPHISM IN ACTION: generic 'PolygonShape' can hold Triangles OR Quadrilaterals ---
    private PolygonShape polygon = new Quadrilateral(); 
    private int verticesSize = 22;

    public Color c5 = new Color(0, 0, 0);
    public Color c6 = new Color(255, 215, 0);
    private Color polygonColor = c5;

    private JLabel areaLabel = new JLabel();
    private JButton reset = new JButton("RESET");
    private JButton GOBack = new JButton("GO BACK");
    private JButton colorButton = new JButton("CHANGE COLOR");
    private JButton saveButton = new JButton("SAVE SHAPE");
    private JButton loadButton = new JButton("LOAD SHAPE");
    private JButton exportButton = new JButton("EXPORT PNG");
    
    // --- NEW: Shape Switcher ---
    private String[] shapeOptions = { "Quadrilateral", "Triangle" };
    private JComboBox<String> shapeSelector = new JComboBox<>(shapeOptions);

    private JTextArea angleArea = new JTextArea();
    private DrawingCanvas canvas;

    public Plot(JFrame jFrame, int scale) {
        setSize(1500, 700);
        setLayout(new BorderLayout(10, 10));
        setBackground(c6);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // --- NORTH Area ---
        areaLabel.setFont(new Font("Sans-serif", Font.BOLD, 30));
        areaLabel.setForeground(c5);
        areaLabel.setHorizontalAlignment(JLabel.CENTER);
        add(areaLabel, BorderLayout.NORTH);

        // --- EAST Area ---
        angleArea.setFont(new Font("Sans-serif", Font.BOLD, 30));
        angleArea.setForeground(c6);
        angleArea.setBackground(c5);
        angleArea.setEditable(false);
        angleArea.setPreferredSize(new Dimension(300, 0));
        add(angleArea, BorderLayout.EAST);

        // --- WEST Area ---
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.setBackground(c6);
        controlPanel.setOpaque(true);

        styleButton(GOBack);
        styleButton(reset);
        styleButton(colorButton);
        styleButton(saveButton);
        styleButton(loadButton);
        styleButton(exportButton);
        
        // Style the new dropdown
        shapeSelector.setMaximumSize(new Dimension(250, 50));
        shapeSelector.setFont(new Font("Sans-serif", Font.BOLD, 20));

        controlPanel.add(GOBack);
        controlPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        controlPanel.add(new JLabel("Select Shape:")).setFont(new Font("Sans-serif", Font.BOLD, 16));
        controlPanel.add(shapeSelector); // Add shape selector
        controlPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        controlPanel.add(reset);
        controlPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        controlPanel.add(colorButton);
        controlPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        controlPanel.add(saveButton);
        controlPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        controlPanel.add(loadButton);
        controlPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        controlPanel.add(exportButton);

        add(controlPanel, BorderLayout.WEST);

        // --- CENTER Area ---
        canvas = new DrawingCanvas();
        add(canvas, BorderLayout.CENTER);

        updateTextDisplays();

        // --- Listeners ---
        reset.addActionListener(e -> {
            jFrame.getContentPane().removeAll();
            Start info = new Start(jFrame, 0, 250);
            jFrame.getContentPane().add(info);
        });

        GOBack.addActionListener(e -> {
            jFrame.getContentPane().removeAll();
            Start info = new Start(jFrame, 0, 250);
            jFrame.getContentPane().add(info);
            jFrame.revalidate();
            jFrame.repaint();
        });

        colorButton.addActionListener(e -> {
            Color newColor = JColorChooser.showDialog(Plot.this, "Choose Polygon Color", polygonColor);
            if (newColor != null) {
                polygonColor = newColor;
                canvas.repaint();
            }
        });

        // --- SHAPE SELECTOR LISTENER ---
        shapeSelector.addActionListener(e -> {
            String selected = (String) shapeSelector.getSelectedItem();
            if (selected.equals("Triangle")) {
                polygon = new Triangle();
            } else {
                polygon = new Quadrilateral();
            }
            // Re-initialize canvas for new shape
            canvas.initializeVertices();
            canvas.syncWithModel();
            updateTextDisplays();
            canvas.repaint();
        });

        saveButton.addActionListener(e -> saveShape());
        loadButton.addActionListener(e -> loadShape());
        exportButton.addActionListener(e -> exportImage());
    }

    private void styleButton(JButton btn) {
        btn.setFont(new Font("Sans-serif", Font.BOLD, 20));
        btn.setForeground(c6);
        btn.setBackground(c5);
        btn.setAlignmentX(CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(250, 50));
    }

    // --- Helper Methods for UI Updates ---
    private void updateTextDisplays() {
        areaLabel.setText("<html><div style='text-align: center;'>Area = " + polygon.getArea() + 
                          "<br>Perimeter = " + polygon.getPerimeter() + "</div></html>");
        
        StringBuilder anglesText = new StringBuilder("\n\n\n\n    Angles\n");
        char[] labels = {'A', 'B', 'C', 'D'};
        for(int i = 0; i < polygon.getNumPoints(); i++) {
             anglesText.append("    ").append(labels[i]).append("= ").append(polygon.getAngle(i)).append("\n");
        }
        angleArea.setText(anglesText.toString());
    }

    // --- File I/O Methods ---
    private void saveShape() {
        JFileChooser fc = new JFileChooser();
        if (fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(fc.getSelectedFile()))) {
                // Save the type of shape first so we know what to load later
                bw.write(polygon.getClass().getSimpleName());
                bw.newLine();
                for (int i = 0; i < polygon.getNumPoints(); i++) {
                    bw.write(polygon.getX(i) + "," + polygon.getY(i));
                    bw.newLine();
                }
                JOptionPane.showMessageDialog(this, "Shape Saved!");
            } catch (IOException e) { e.printStackTrace(); }
        }
    }

    private void loadShape() {
        JFileChooser fc = new JFileChooser();
        if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try (BufferedReader br = new BufferedReader(new FileReader(fc.getSelectedFile()))) {
                String shapeType = br.readLine();
                int pointsToRead = 0;

                if (shapeType.equals("Triangle")) {
                    polygon = new Triangle();
                    shapeSelector.setSelectedItem("Triangle");
                    pointsToRead = 3;
                } else {
                    polygon = new Quadrilateral();
                    shapeSelector.setSelectedItem("Quadrilateral");
                    pointsToRead = 4;
                }

                int[] x = new int[pointsToRead];
                int[] y = new int[pointsToRead];
                for (int i = 0; i < pointsToRead; i++) {
                    String[] parts = br.readLine().split(",");
                    x[i] = Integer.parseInt(parts[0]);
                    y[i] = Integer.parseInt(parts[1]);
                }
                polygon.setCoordinates(x, y);
                canvas.initializeVertices();
                canvas.syncWithModel();
                updateTextDisplays();
                canvas.repaint();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error loading file. Format might be wrong.");
            }
        }
    }

    private void exportImage() {
         JFileChooser fc = new JFileChooser();
         fc.setDialogTitle("Export PNG");
         if (fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
             File f = fc.getSelectedFile();
             if (!f.getName().toLowerCase().endsWith(".png")) f = new File(f.getParentFile(), f.getName() + ".png");
             try {
                 BufferedImage img = new BufferedImage(canvas.getWidth(), canvas.getHeight(), BufferedImage.TYPE_INT_RGB);
                 Graphics2D g2d = img.createGraphics();
                 canvas.paint(g2d);
                 g2d.dispose();
                 ImageIO.write(img, "png", f);
                 JOptionPane.showMessageDialog(this, "Image Exported!");
             } catch (IOException e) { e.printStackTrace(); }
         }
    }

    // ================= Drawing Canvas =================
    private class DrawingCanvas extends JPanel implements MouseMotionListener {
        private Rectangle[] vertices;
        private Polygon poly;
        private int currentVertexIndex = -1;
        private int mouseX, mouseY;
        private boolean showCoords = false;

        public DrawingCanvas() {
            initializeVertices();
            syncWithModel();

            MouseAdapter ma = new MouseAdapter() {
                public void mousePressed(MouseEvent me) {
                    currentVertexIndex = getVertexIndex(me.getX(), me.getY());
                    if (currentVertexIndex >= 0) setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                }
                public void mouseReleased(MouseEvent me) {
                    currentVertexIndex = -1;
                    setCursor(Cursor.getDefaultCursor());
                    updateTextDisplays();
                    repaint();
                }
                public void mouseExited(MouseEvent e) {
                    showCoords = false;
                    repaint();
                }
            };
            addMouseListener(ma);
            addMouseMotionListener(this);
            setBackground(Color.WHITE);
        }

        public void initializeVertices() {
            vertices = new Rectangle[polygon.getNumPoints()];
            for (int i = 0; i < vertices.length; i++) {
                vertices[i] = new Rectangle(0, 0, verticesSize, verticesSize);
            }
        }

        public void syncWithModel() {
            for (int i = 0; i < polygon.getNumPoints(); i++) {
                vertices[i].x = (int) (polygon.getX(i) - verticesSize * 0.5);
                vertices[i].y = (int) (polygon.getY(i) - verticesSize * 0.5);
            }
            poly = new java.awt.Polygon(polygon.getXs(), polygon.getYs(), polygon.getNumPoints());
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Draw Vertices
            g2d.setColor(Color.LIGHT_GRAY);
            for (Rectangle v : vertices) g2d.draw(v);

            // Draw Polygon
            g2d.setColor(polygonColor);
            g2d.draw(poly);

            // Draw Labels & Lengths
            g2d.setColor(c5);
            g2d.setFont(new Font("courier", Font.BOLD, 15));
            char[] labels = {'A', 'B', 'C', 'D'};
            for(int i = 0; i < polygon.getNumPoints(); i++) {
                g2d.drawString(String.valueOf(labels[i]), polygon.getX(i) - 10, polygon.getY(i) - 10);
                // Draw length near midpoint of the side
                g2d.drawString(polygon.getLength(i) + "", polygon.getMidX(i), polygon.getMidY(i));
            }

            // Draw Coords Tooltip
            if (showCoords) {
                g2d.setColor(new Color(255, 255, 220));
                g2d.fillRect(mouseX + 15, mouseY - 25, 90, 25);
                g2d.setColor(Color.BLACK);
                g2d.drawRect(mouseX + 15, mouseY - 25, 90, 25);
                g2d.drawString("(" + mouseX + ", " + mouseY + ")", mouseX + 20, mouseY - 8);
            }
        }

        private int getVertexIndex(int x, int y) {
            for (int i = 0; i < vertices.length; i++) {
                if (vertices[i].contains(x, y)) return i;
            }
            return -1;
        }

        public void mouseMoved(MouseEvent me) {
            mouseX = me.getX(); mouseY = me.getY();
            int index = getVertexIndex(mouseX, mouseY);
            if (index >= 0) {
                setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
                showCoords = true;
            } else {
                setCursor(Cursor.getDefaultCursor());
                showCoords = false;
            }
            repaint();
        }

        public void mouseDragged(MouseEvent me) {
            mouseX = me.getX(); mouseY = me.getY();
            if (currentVertexIndex >= 0 && getBounds().contains(mouseX, mouseY)) {
                polygon.setVertex(currentVertexIndex, mouseX, mouseY);
                syncWithModel();
                showCoords = true;
                repaint();
            }
        }
    }
}