import org.apache.commons.io.FilenameUtils;
import org.imgscalr.Scalr;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * ImageTweakerTool - v1.0
 * Simple tool to edit images.
 * The user uses JFileChooser to choose the image, transforms it to 256x256 pixels,
 * 1:1 ratio and centers it. Finally it saves it in a local directory.
 *
 * @author Xavi Pineda
 * @version v1.0
 */
public class ImageTweakerTool {

    public static void main(String[] args) {
        File file = importImage();
        transformImage(file);
    }

    /**
     * Centers the selected image and transforms it to 256x256px & 1:1 ratio.
     * @param file The selected file from the JFileChooser
     */
    private static void transformImage(File file) {

        if (file != null){
            try {
                String fileName = file.getName();
                BufferedImage originalImgage = ImageIO.read(file);
                BufferedImage subImage = null;
                if (originalImgage.getWidth() == originalImgage.getHeight()){
                    subImage = originalImgage;
                }else {
                    if (originalImgage.getWidth() > originalImgage.getHeight()){
                        int xPos = (originalImgage.getWidth() - originalImgage.getHeight()) / 2;
                        subImage = originalImgage.getSubimage(xPos, 0, originalImgage.getHeight(), originalImgage.getHeight());
                    }else {
                        int yPos = (originalImgage.getHeight() - originalImgage.getWidth()) / 2;
                        subImage = originalImgage.getSubimage(0, yPos, originalImgage.getWidth(), originalImgage.getWidth());
                    }
                }
                subImage = Scalr.resize(subImage, 256, 256);
                exportImage(subImage, fileName);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("[ERROR] - Failed to transform the selected image...");
            }
        }else
            System.out.println("[ERROR] - The selected file is not valid or it's null...");

    }

    /**
     * Transforms and exports the image to the output folder
     * EX: "output/itt_imageName.ext"
     * @param subImage Image to export to file
     * @param fileName Name of the imported File
     */
    private static void exportImage(BufferedImage subImage, String fileName) {

        try {
            File outputfile = new File("output/itt_" + fileName);
            ImageIO.write(subImage, FilenameUtils.getExtension(outputfile.getAbsolutePath()), outputfile);
            System.out.println("[INFO] - Success! Exported to: ["+outputfile.getAbsolutePath() + "]");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("[ERROR] - Failed to export the selected image...");
        }
    }

    /**
     * Instances a new JFileChooser and the users has to select the image to edit
     * @serialData JPEG, JPG and PNG extensions.
     * @return The selected File, if error returns null and the program stops.
     */
    private static File importImage(){
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filtroImagen=new FileNameExtensionFilter("JPG, JPEG & PNG","jpg", "jpeg","png");
        fileChooser.setFileFilter(filtroImagen);
        int seleccion = fileChooser.showOpenDialog(null);
        if (seleccion == JFileChooser.APPROVE_OPTION)
        {
            File file = fileChooser.getSelectedFile();
            return file;
        }

        return null;
    }


}
