/* Henry Pratama Suryadi
A0105182R Lab 1 Submission*/
import java.awt.Point;
import java.util.LinkedList;
import java.util.Queue;

public class ConnectedComponentAlgorithm {
    
    private byte[][] img;
    private int height;
    private int width;
    private int componentAmount = 0;
    int[][] labels;
    Queue<Point> scanComponent = new LinkedList<Point>();
    
    /**
     * Find all connected components of the image
     * @param m_img "byte" type of black and white image
     * @return 2D array with labels of different components
     * This current method does not work, it just copies all the values from the input
     * image into the labels!!!  
     */
    public int[][] findConnectedComponents(byte[][] m_img){
        img = m_img;
        height = img.length;
        width = img[0].length;
        Point cur = new Point();
        Point side = new Point();
        
        labels = new int[height][width];
        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++) {
                cur.setLocation(i, j);
                if (checkComponents(cur))
                {
                    componentAmount++;
                    labels[cur.x][cur.y] = componentAmount;
                    scanComponent.add(new Point(i, j));
                    
                    while (!scanComponent.isEmpty()){
                        cur = scanComponent.remove();
                        labels[cur.x][cur.y] = componentAmount;
                        
                        for (int k = 0; k < 4; k++) {
                            side = new Point(cur.x + (int)Math.sin(k * Math.PI/2), cur.y + (int)Math.cos(k * Math.PI/2));
                            if (checkComponents(side)) {
                                labels[side.x][side.y] = componentAmount;
                                scanComponent.add(side);
                            }
                        }
                    }
                }
            }
        
        return labels;
    }
    
    private boolean checkComponents(Point coor){
        if (coor.x < height && coor.x >= 0 && coor.y < width && coor.y >= 0) {
            return ((img[coor.x][coor.y] == 1) && (labels[coor.x][coor.y] == 0));
        }
        return false;
    }
    /**
     * get the number of connected components
     * @return number of connected components
     */
    public int getComponentAmount() {
        return componentAmount;
    }
        
}
