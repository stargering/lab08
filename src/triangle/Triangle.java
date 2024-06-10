package triangle;

import resizable.ResizableImage;

import java.awt.*;
import java.awt.image.BufferedImage;

import static resizable.Debug.print;

/**
 * Implement your Sierpinski Triangle here.
 *
 *
 * You only need to change the drawTriangle
 * method!
 *
 *
 * If you want to, you can also adapt the
 * getResizeImage() method to draw a fast
 * preview.
 *
 */
public class Triangle implements ResizableImage {
    int drawTriangle = 0;

    /**
     * change this method to implement the triangle!
     * @param size the outer bounds of the triangle
     * @return an Image containing the Triangle
     */
    private BufferedImage drawTriangle(Dimension size) {
        print("drawTriangle: " + ++drawTriangle + " size: " + size);
        BufferedImage bufferedImage = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D gBuffer = (Graphics2D) bufferedImage.getGraphics();

        // Original content
        gBuffer.setColor(Color.black);
        int border = 2;
        gBuffer.drawRect(border, border, size.width - 2 * border, size.height - 2 * border);
        gBuffer.setColor(Color.darkGray);
        border = 8;
        gBuffer.drawRect(border, border, size.width - 2 * border, size.height - 2 * border);
        gBuffer.drawString("Triangle goes here", border * 2, border * 4);

        // Draw Sierpinski Triangle
        int width = size.width;
        int height = size.height;
        Point p1 = new Point(width / 2, border);
        Point p2 = new Point(border, height - border);
        Point p3 = new Point(width - border, height - border);

        Color[] colors = {
                Color.red, Color.green, Color.blue, Color.yellow,
                Color.cyan, Color.magenta, Color.orange
        }; // Define an array of colors

        drawSierpinski(gBuffer, p1, p2, p3, 6, colors); // Adjust the recursion depth as needed

        return bufferedImage;
    }

    private void drawSierpinski(Graphics2D g, Point p1, Point p2, Point p3, int depth, Color[] colors) {
        // Ensure that we cycle through colors based on depth
        g.setColor(colors[depth % colors.length]); // Use different color for each level
        int[] xPoints = { p1.x, p2.x, p3.x };
        int[] yPoints = { p1.y, p2.y, p3.y };
        g.drawPolygon(xPoints, yPoints, 3);

        if (depth > 0) {
            Point mid1 = midpoint(p1, p2);
            Point mid2 = midpoint(p2, p3);
            Point mid3 = midpoint(p1, p3);

            drawSierpinski(g, p1, mid1, mid3, depth - 1, colors);
            drawSierpinski(g, mid1, p2, mid2, depth - 1, colors);
            drawSierpinski(g, mid3, mid2, p3, depth - 1, colors);
        }
    }

    private Point midpoint(Point p1, Point p2) {
        return new Point((p1.x + p2.x) / 2, (p1.y + p2.y) / 2);
    }

    BufferedImage bufferedImage;
    Dimension bufferedImageSize;

    @Override
    public Image getImage(Dimension triangleSize) {
        if (triangleSize.equals(bufferedImageSize))
            return bufferedImage;
        bufferedImage = drawTriangle(triangleSize);
        bufferedImageSize = triangleSize;
        return bufferedImage;
    }

    @Override
    public Image getResizeImage(Dimension size) {
        BufferedImage bufferedImage = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D gBuffer = (Graphics2D) bufferedImage.getGraphics();
        gBuffer.setColor(Color.pink);
        int border = 2;
        gBuffer.drawRect(border, border, size.width - 2 * border, size.height - 2 * border);
        return bufferedImage;
    }
}
