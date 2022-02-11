import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrameThing extends JFrame {

    private CustomCanvas customCanvas = new CustomCanvas();

    public FrameThing() {
        setPreferredSize(new Dimension(500, 500));
        setSize(new Dimension(500, 500));

        JButton create_shape = new JButton("Create");
        JButton clear_shape = new JButton("Clear");
        JButton toggle_fill = new JButton("Toggle");

        create_shape.setBounds(75, 400, 100, 40);
        clear_shape.setBounds(200 - 8, 400, 100, 40);
        toggle_fill.setBounds(385 - 75, 400, 100, 40);

        create_shape.addActionListener(new AddListener(customCanvas));
        clear_shape.addActionListener(new ClearListener(customCanvas));
        toggle_fill.addActionListener(new ToggleListener(customCanvas));

        add(create_shape);
        add(clear_shape);
        add(toggle_fill);

        Container cp = getContentPane();
        cp.add(customCanvas);

        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("My Window");
        setLayout(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new FrameThing();
    }
}

class CustomCanvas extends JPanel {

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    public void addShape(Graphics g) {

    }
    public void clearShapes(Graphics g) {
        g.setColor(getBackground());
        g.fillRect(0, 0, 500, 400);
    }
    public void toggleShapes(Graphics g) {

    }
}

class AddListener implements ActionListener {

    CustomCanvas canvas;
    public AddListener(CustomCanvas canvas) {
        super();
        this.canvas = canvas;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        canvas.addShape(canvas.getGraphics());
    }
}
class ClearListener implements ActionListener {

     CustomCanvas canvas;

    public ClearListener(CustomCanvas canvas) {
        this.canvas = canvas;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        canvas.clearShapes(canvas.getGraphics());
    }

}
class ToggleListener implements ActionListener {

    CustomCanvas canvas;

    public ToggleListener(CustomCanvas canvas) {
        this.canvas = canvas;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        canvas.toggleShapes(canvas.getGraphics());
    }
}
