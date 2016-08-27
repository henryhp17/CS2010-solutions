//Version Java 8
// Assignment #1 - CS2010, Sem 2, AY15/16
// See Assignment PDF for details on requirements
//
/* Henry Pratama Suryadi
A0105182R Lab 1 Submission
All images (1-10) will be processed together in one run */

import java.awt.image.BufferedImage;


public class A1ConnectedComponent {

    /**
     * It is the framework of the first assignment. You are recommended to 
     * finish your assignment based on this procedure. You can change any of the
     * codes of this class if you feel necessary.
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        for (int i = 1; i <= 10; i++){
        String imageName = Integer.toString(i);
        // read an image and obtain an image-sized 0-1 
        // byte array where black pixels are 0 and white pixels are 1
        // You can change the name and path of image.
        byte[][] img = ImageUtil.readBinaryImage("../images/" + imageName + ".png");
        
        // implement the algorithm described in the Assignment1.pdf to find all
        // connected components of picture "img"
        /*
            Please implement ConnectedComponentAlgorithm.java to do connected
            component analysis. You can add, change or delete any codes in that class
        */
        ConnectedComponentAlgorithm search = new ConnectedComponentAlgorithm();
        int[][] labeledImg = search.findConnectedComponents(img);
        int componentAmount = search.getComponentAmount();
        
        // assign each label a color and obtain a colorful image to visualize 
        // different connected components
        BufferedImage imgRes = ImageUtil.mapColor(labeledImg);
        
        // write the colorful image to a file
        // You can change the name and path of image
        ImageUtil.writeImage("../images/" + imageName + "_" + Integer.toString(componentAmount)+ ".png", imgRes);
        }
    }
    
}
