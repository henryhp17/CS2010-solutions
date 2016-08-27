/* Henry Pratama Suryadi
A0105182R Lab 1 Submission*/
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public class ImageUtil {
    
    /**
     * read the image from "filename" and convert to binary byte array
     * @param filename
     * @return 2D array of the gray image with only 0(background) and 1(foreground).
     */
    public static byte[][] readBinaryImage(String filename){
        BufferedImage img = null;
        try{
            img = ImageIO.read(new File(filename));
        }
        catch (IOException e){
        }
        byte[][] imgBytes = null;
        if(img != null)
        {
            imgBytes = new byte[img.getHeight()][img.getWidth()];
            for(int x=0;x<img.getWidth();x++)
                for(int y=0;y<img.getHeight();y++){
                    if(img.getRGB(x, y) == 0xff000000)
                        imgBytes[y][x] = 0;
                    else
                        imgBytes[y][x] = 1;
                }
        }
        return imgBytes;
    }
    
    
    /**
     * write image to the file named "filename"
     * @param filename the path and name of the file
     * @param img  "BufferedImage" type image data
     */
    public static void writeImage(String filename, BufferedImage img){
        try{
            File file = new File(filename);
            String[] names = filename.split("\\.");
            String imgFormat = names[names.length - 1];
            ImageIO.write(img, imgFormat, file);
        }
        catch (IOException e){
            
        }
    }
    
    
    /**
     * map different labels to different colors
     * @param labeled_img the 2D array with labels which should be non-negative integer
     * @return images with different colors for different labels
     */
    public static BufferedImage mapColor(int[][] labeledImg){
        int height = labeledImg.length;
        int width = labeledImg[0].length;
        BufferedImage color_img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for(int i=0 ;i<height;i++)
            for(int j=0;j<width;j++) {
                color_img.setRGB(j, i, colorMap[labeledImg[i][j] % 255]);
            }
        return color_img;
    }
    
    
    
    private static final int[] colorMap = 
        {0x000000, 0xFF9F00, 0x0087FF, 0x0000EB, 0x9BFF64, 0xD70000, 0x24FFDB, 0x0093FF, 
         0x0083DD, 0x0054DD, 0xCF0000, 0xFF2000, 0x0CFFF3, 0x48FFB7, 0xB3FF4C, 0x58FFA7, 
         0x0068FF, 0x8F0000, 0x0014FF, 0xFF1C00, 0x00009F, 0x00BBFF, 0xFFB700, 0xF7FF08, 
         0x0008FF, 0xB30000, 0xA3FF5C, 0x0064FF, 0xCBFF34, 0xDF0000, 0xF30000, 0x50FFAF, 
         0x28FFD7, 0x009FFF, 0x0000B7, 0xFFA300, 0x0074FF, 0x00DBFF, 0x08FFF7, 0xFF0000, 
         0x000097, 0xFF3000, 0x0080FF, 0x0060FF, 0x54FFAB, 0xFFCB00, 0x0028FF, 0x003CFF, 
         0x9F0000, 0xCFFF30, 0x0097FF, 0xAB0000, 0xEF0000, 0x001CFF, 0xFFFFFF, 0xFFDB00, 
         0xFF1000, 0x0000E3, 0x0000A7, 0xDBFF24, 0xDFFF20, 0x83FF7C, 0x00008B, 0xFFEF00, 
         0xC70000, 0xFF0400, 0x0000DB, 0x00D7FF, 0x0050FF, 0xAFFF50, 0xE7FF18, 0x008FFF, 
         0xFF7000, 0xFF7400, 0xFF8700, 0x00D3FF, 0x0000B3, 0xFFDF00, 0x870000, 0x0000FF, 
         0x0000DF, 0xE30000, 0x4CFFB3, 0xFF2400, 0xFF6000, 0x0018FF, 0x0058FF, 0x00E7FF, 
         0xE3FF1C, 0xFF8B00, 0x7CFF83, 0x00009B, 0x8B0000, 0x68FF97, 0x00BFFF, 0x000CFF, 
         0xF3FF0C, 0xC3FF3C, 0xFFAF00, 0x0000F7, 0x00DFFF, 0x0000F3, 0xFF2800, 0x00C7FF, 
         0x006CFF, 0x000083, 0xFF6C00, 0x64FF9B, 0x000087, 0xFF0C00, 0xFFD300, 0xFFFF00, 
         0x0010FF, 0xABFF54, 0xAF0000, 0xD7FF28, 0xFF6400, 0x970000, 0xFB0000, 0x0038FF, 
         0x0000A3, 0x2CFFD3, 0x00008F, 0x44FFBB, 0xBB0000, 0x005CFF, 0x00FBFF, 0x20FFDF, 
         0xFF3C00, 0xFF4800, 0x34FFCB, 0x00F3FF, 0xFF8F00, 0xFFBB00, 0xFFC300, 0xC30000, 
         0x78FF87, 0xFF7C00, 0x30FFCF, 0xFF2C00, 0x00FFFF, 0xFFBF00, 0x9B0000, 0x8FFF70, 
         0x1CFFE3, 0x0034FF, 0x00ABFF, 0x0000D7, 0x00EFFF, 0x00B7FF, 0x00CBFF, 0xFF5000, 
         0x93FF6C, 0x8BFF74, 0xFF9300, 0x0000E7, 0xEFFF10, 0x00CFFF, 0x00C3FF, 0xBFFF40, 
         0xFF1800, 0x0020FF, 0xFFFB00, 0xEB0000, 0x0024FF, 0x008BFF, 0x5CFFA3, 0xF70000, 
         0x6CFF93, 0x0070FF, 0x97FF68, 0xBBFF44, 0x18FFE7, 0x000093, 0xFFEB00, 0x70FF8F, 
         0x00F7FF, 0x0000BB, 0x00E3FF, 0x0044FF, 0x002CFF, 0xFF5800, 0x04FFFB, 0x0000AB, 
         0x00EBFF, 0x0000AF, 0x0004FF, 0xE70000, 0x00A3FF, 0x00B3FF, 0x0030FF, 0xFF9B00, 
         0x0000C3, 0xBF0000, 0x0000FB, 0x007CFF, 0xFFF700, 0x0000EF, 0x80FF80, 0xFFD700, 
         0xD30000, 0xFFC700, 0xFFB300, 0x14FFEB, 0x004CFF, 0x87FF78, 0x0040FF, 0xFF3400, 
         0xFBFF04, 0xFF5400, 0xFF7800, 0x3CFFC3, 0x009BFF, 0xFFF300, 0x930000, 0xFF9700, 
         0xFF4000, 0xFFE700, 0xD3FF2C, 0xFFA700, 0x60FF9F, 0xFF1400, 0x0000BF, 0xA70000, 
         0x0000CF, 0xA7FF58, 0x10FFEF, 0x0000D3, 0xFFCF00, 0xEBFF14, 0xFF8300, 0xB7FF48, 
         0xFF5C00, 0xC7FF38, 0xCB0000, 0xFF0800, 0x74FF8B, 0xFF8000, 0x40FFBF, 0xFF4400, 
         0x00A7FF, 0xA30000, 0x0078FF, 0x0000C7, 0xFFAB00, 0xFF3800, 0xDB0000, 0x00AFFF, 
         0xFF6800, 0x9FFF60, 0x38FFC7, 0xFF4C00, 0xB70000, 0x0000CB, 0xFFE300, 0x0048FF};
    
}
