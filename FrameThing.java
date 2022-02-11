import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;
import java.util.ArrayList;
import java.util.Random;

/**
 * This class extends off of the JFrame
 */
public class Shapes extends JFrame {

    /**  The canvas JPanel for this Frame  */
    private CustomCanvas customCanvas = new CustomCanvas();

    /**  The Width of the Frame  */
    private int w = 500;
    /**  The height of the Frame */
    private int h = 500;

    /**
     * The default constructor, handles initialization of the window
     */
    public Shapes() {
        // Setting the size of the window
        setPreferredSize(new Dimension(w, h));
        setSize(new Dimension(w, h));
        
        // Passes the size of the Canvas drawing area for use inside of internal methods
        this.customCanvas.setSizeInternal(w, h - 100);
        
        // Creating the Frame Buttons
        JButton create_shape = new JButton("Create");
        JButton clear_shape = new JButton("Clear");
        JToggleButton toggle_fill = new JToggleButton("Outline");
        
        // Inserting and sizing the Buttons
        create_shape.setBounds(75, 400, 100, 40);
        clear_shape.setBounds(200 - 8, 400, 100, 40);
        toggle_fill.setBounds(385 - 75, 400, 100, 40);
        
        // Each Button gets its respective ActionListener which is implemented off of the Basic ActionListener Contract
        //   --- See below for implementations
        create_shape.addActionListener(new AddListener(customCanvas));
        clear_shape.addActionListener(new ClearListener(customCanvas));
        toggle_fill.addActionListener(new ToggleListener(customCanvas));
        
        // Adds the three buttons directly to this Frame
        add(create_shape);
        add(clear_shape);
        add(toggle_fill);

        // Adding the Canvas Panel to the Frame
        // Canvas Panels CANNOT be added directly to the Frame
        Container cp = getContentPane();
        cp.add(customCanvas);
        
        pack();
        
        // Sets the window to exit the program on closing
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        // Setting the title
        setTitle("Shapes");
        // Disables layout autoformatting so we can manually position elements
        setLayout(null);
        // Shows the window
        setVisible(true);
    }

    /**
     * Program entry point
     * @param args The arguments from command line (ignored)
     */
    public static void main(String[] args) {
        // Instantiates the window
        new Shapes();
    }
}

/**
 * This class serves the purpose of a canvas, it extends directly off of the JPanel class
 */
class CustomCanvas extends JPanel {

    /**  Internal flag, used to tell whether shapes need to be filled or outlined  */
    private boolean fill = false;
    
    /**  The width sizing of this Canvas panel  */
    private int width;
    /**  The height sizing of this Canvas panel  */
    private int height;
    
    /**  Stores common subclasses of shapes, used to store all the shapes on screen  */
    private ArrayList<RectangularShape> Shapes = new ArrayList<>();
    /**  Stores Color objects, relates to the color of Objects stored in Shapes  */
    private ArrayList<Color> colors = new ArrayList<>();

    /**
     * This Method overrides a superclass method
     * 
     * Paints the components on the screen, used to initialize the canvas
     * @param g The graphics object for this Canvas
     */
    @Override
    public void paintComponent(Graphics g) {
        // Calls the super method counterpart
        super.paintComponent(g);
        // ^^ Effective. . 
    }

    /**
     * Method that is tied to the addShape button
     * Adds a random colored shape (randomly either a circle or a square) with a random color
     * @param g The Graphics object for this Canvas
     */
    public void addShape(Graphics g) {
        // Casts Graphics to Graphics2D
        Graphics2D g2d = (Graphics2D) g;
        
        // Initializing Random Object
        Random rand = new Random();
        
        // Sets both to separate random numbers between 10 and 60
        int size_x = rand.nextInt(50) + 10;
        int size_y = rand.nextInt(50) + 10;
        
        
        // Reference for below two numbers, Coords + SHAPE SIZE cannot exceed CANVAS SIZE
        // sets x location to random number between 0 and WIDTH - SHAPE WIDTH
        int loc_x = rand.nextInt(this.width - size_x);
        // sets y location to random number between 0 and HEIGHT - SHAPE HEIGHT
        int loc_y = rand.nextInt(this.height - size_y);
        
        // Gets a random color of a manually limited format
        // Hue = 0 to 255
        // Saturation = 55 to 255 (to avoid white shapes)
        // Brightness = 0 to 200 (to avoid white shapes)
        Color color = Color.
                getHSBColor(rand.nextFloat() * 255, rand.nextFloat() * 250 + 55, rand.nextFloat() * 200);
        
        // Adds this color to ArrayList of stored colors
        this.colors.add(color);
        // Sets the drawing color to this color
        g2d.setColor(color);

        // Deciding which shape to draw
        if (rand.nextBoolean()) {
            // Creates Rectangle of generated number values
            Rectangle2D rect = new Rectangle2D.Float(loc_x, loc_y, size_x, size_y);

            // If shape is to be filled or outlined
            if (fill) {
                // Fill shape
                g2d.fill(rect);
            }
            else {
                // Outline shape
                g2d.draw(rect);
            }

            // Adds Rectangle2D (Extension of RectangularShape) to Shapes ArrayList
            this.Shapes.add(rect);
        }
        else {
            // Creates Ellipse of generated number values
            Ellipse2D ellipse = new Ellipse2D.Float(loc_x, loc_y, size_x, size_y);

            // If shape is to be filled or outlined
            if (fill) {
                // Fill shape
                g2d.fill(ellipse);
            }
            else {
                // Outline shape
                g2d.draw(ellipse);
            }

            // Adds Ellipse2D (Extension of RectangularShape) to Shapes ArrayList
            this.Shapes.add(ellipse);
        }
    }

    /**
     * Method that is tied to the Clear button,
     * Fills in the Canvas area with a rectangle with the same Color as the background, then removes all saved shapes from memory variables
     * @param g The Graphics object for this Canvas
     */
    public void clearShapes(Graphics g) {
        // Sets the color to background color, then fills the entire canvas with this color
        g.setColor(getBackground());
        g.fillRect(0, 0, 500, 400);

        // Clears the Shapes and colors ArrayLists
        this.Shapes.clear();
        this.colors.clear();
    }

    /**
     * Method that is tied to the ToggleButton
     * Clears off the screen without forgetting the shapes on screen, then either fills or outlines those shapes
     * @param g The Graphics object for this Canvas
     * @param fill If the shapes are to be filled (if false then shapes are outlined)
     */
    public void toggleShapes(Graphics g, boolean fill) {
        // Casts Graphics to Graphics2D
        Graphics2D g2d = (Graphics2D) g;
        
        // Sets fill to the opposite value
        this.fill = fill;
        
        // Sets the Color to the background color, then fills the entire Canvas area with this color
        g.setColor(getBackground());
        g.fillRect(0, 0, 500, 400);

        // Loops through every object in Shapes by index
        for (int i = 0; i < this.Shapes.size(); i++) {
            // Sets the Color to the color at index i of colors
            g2d.setColor(this.colors.get(i));
            
            // Either fills or outlines the shape at index i of Shapes
            if (fill)
                g2d.fill(this.Shapes.get(i));
            else
                g2d.draw(this.Shapes.get(i));
        }
    }

    /**
     * Internal method, used to set the width and height for reference Internally
     * @param x The width of the Canvas
     * @param y The height of the Canvas
     */
    public void setSizeInternal(int x, int y) {
        this.width = x;
        this.height = y;
    }
}

/**  Implementation of the Contract ActionListener  */
class AddListener implements ActionListener {
    
    /**  The Canvas to run Methods on  */
    CustomCanvas canvas;

    /**
     * Default Constructor, passes in the canvas to manipulate
     * @param canvas The Canvas to manipulate
     */
    public AddListener(CustomCanvas canvas) {
        super();
        this.canvas = canvas;
    }

    /**
     * The Method fired when this Action is performed
     * @param e The ActionEvent (includes information about the Action)
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // Fires Method on Canvas addShape
        canvas.addShape(canvas.getGraphics());
    }
}
/**  Implementation of the Contract ActionListener  */
class ClearListener implements ActionListener {

    /**  The Canvas to run Methods on  */
    CustomCanvas canvas;
    
    /**
     * Default Constructor, passes in the canvas to manipulate
     * @param canvas The Canvas to manipulate
     */
    public ClearListener(CustomCanvas canvas) {
        this.canvas = canvas;
    }

    /**
     * The Method fired when this Action is performed
     * @param e The ActionEvent (includes information about the Action)
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // Fires Method on Canvas ClearShapes
        canvas.clearShapes(canvas.getGraphics());
    }

}
/**  Implementation of the Contract ActionListener  */
class ToggleListener implements ActionListener {

    /**  The Canvas to run Methods on  */
    CustomCanvas canvas;
    
    /**
     * Default Constructor, passes in the canvas to manipulate
     * @param canvas The Canvas to manipulate
     */
    public ToggleListener(CustomCanvas canvas) {
        this.canvas = canvas;
    }

    /**
     * The Method fired when this Action is performed
     * @param e The ActionEvent (includes information about the Action)
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // gets the button target
        JToggleButton target = (JToggleButton) e.getSource();

        // Shapes are currently outlined
        if (target.getText().equalsIgnoreCase("outline")) {
            // Fires Method on Canvas toggleShapes, set to fill
            canvas.toggleShapes(canvas.getGraphics(), true);
            // Sets button text to Filled
            target.setText("Filled");
        }
        // Shapes are currently filled
        else {
            // Fires Method on Canvas toggleShapes set to outline
            canvas.toggleShapes(canvas.getGraphics(), false);
            // Sets button text to Outlined
            target.setText("Outlined");
        }
    }
}
