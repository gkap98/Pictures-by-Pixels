/*
*
*   Written By: -> Gavin Kaepernick, Carthage College
*   Written In: -> Java
*   Description:
*       This class helps handle drawing pictures Pixel.java
*
*/

import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Icon {

    private ArrayList<ArrayList<Pixel>> pixels;
    private int filePadding;

// *****************
//   Constructors
// *****************
    public Icon() {                                  // Constructor for no given values.         Pre set to 40x40 icon
        pixels = new ArrayList<ArrayList<Pixel>>();
        for (int i = 0; i < 40; i++) {
            pixels.add(new ArrayList<Pixel>());
            for (int j = 0; j < 40; j++) {
                pixels.get(i).add(new Pixel());
            }
        }
    }

    public Icon(int rows, int columns) {             // Constructor FOR given values.            NO Presets
        pixels = new ArrayList<ArrayList<Pixel>>();
        for (int i = 0; i < columns; i++) {
            pixels.add(new ArrayList<Pixel>());
            for (int j = 0; j < rows; j++) {
                pixels.get(i).add(new Pixel());
            }
        }
    }

// *****************
//     Setters
// *****************
    public void setPixel(int row, int column, int red, int green, int blue) {   // Set pixel data at given coordinates.
        Pixel pixel = getPixel(column, row);
        pixel.setRed(red);
        pixel.setGreen(green);
        pixel.setBlue(blue);
    }

// *****************
//     Getters
// *****************
    public Pixel getPixel(int row, int column) {
        // Check user input for invalid data input
        if (column > pixels.size() || column < 0) {
            System.out.println("Add valid column size");
            column = 0;
        }
        // Check user input
        if (row > pixels.get(0).size() || row < 0) {
            System.out.println("Add valid column size");
            row = 0;
        }
        return pixels.get(column).get(row);
    }
    public void getPadding() {
        int size = pixels.get(0).size();
        // Check for padding size and if it is even needed.
        if ((size * 3) % 4 == 0) {
            filePadding = 0;
        }
        filePadding = (4 - ((size) * 3) % 4);
    }
    // Helper function for when it comes time to print hexidecimal values/\.
    public String toString() {
        String s = "";
        for (int i = 0; i < pixels.size(); i++) {
            for (int j = 0; j < pixels.get(i).size(); j++) {
                s += pixels.get(i).get(j).getHex() + " ";
            }
            s += "\n";
        }
        return s;
    }
// *****************
//     Methods
// *****************
    public void exportBitMap() {
        ArrayList<Byte> fileBytes = new ArrayList<Byte>();
        getPadding();
        try {
        // Set up File by Bytes
            FileOutputStream File = new FileOutputStream("File.bmp");

            int fileSize = 54 + (pixels.get(0).size() + filePadding) * pixels.size();
            // File Header
            fileBytes.add((byte)66);
            fileBytes.add((byte)77);
            // File Size
            fileBytes.add((byte)fileSize);
            fileBytes.add((byte)(fileSize >> 8));
            fileBytes.add((byte)(fileSize >> 16));
            fileBytes.add((byte)(fileSize >> 24));

            for (int i = 0; i < 4; i++) {
                fileBytes.add((byte)0);
            }
            // Number of Bytes from the Start
            fileBytes.add((byte)54);
            for (int i = 0; i < 3; i++) {
                fileBytes.add((byte)0);
            }
        // Set up Image and Info Header
            // Size of image header in bytes
            fileBytes.add((byte)40);
            for (int i = 0; i < 3; i++) {
                fileBytes.add((byte)0);
            }
            //  Width of image
            fileBytes.add((byte)pixels.get(0).size());
            for (int i = 0; i < 3; i++) {
                fileBytes.add((byte)0);
            }
            // Height of image
            fileBytes.add((byte)pixels.size());
            for (int i = 0; i < 3; i++) {
                fileBytes.add((byte)0);
            }
            // Number of image planes
            fileBytes.add((byte)1);
            fileBytes.add((byte)0);
            // Bit count
            fileBytes.add((byte)24);
            fileBytes.add((byte)0);
            // Uncompresed Bytes
            for (int i = 0; i < 4; i++) {
                fileBytes.add((byte)0);
            }
            // Size of pixel data
            fileBytes.add((byte)((pixels.get(0).size() + filePadding) * pixels.size()));
            // Unused Bytes
            for (int i = 0; i < 19; i++) {
                fileBytes.add((byte)0);
            }
            // Adding Pixels to fileBytes ArrayList
            for (int i = pixels.size() - 1; i >= 0; i--) {
                for (int j = 0; j < pixels.get(i).size(); j++) {
                    fileBytes.add((byte)pixels.get(i).get(j).getBlue());
                    fileBytes.add((byte)pixels.get(i).get(j).getGreen());
                    fileBytes.add((byte)pixels.get(i).get(j).getRed());
                }
                // Add File Padding
                for (int k = 0; k < filePadding; k++) {
                    fileBytes.add((byte)0);
                }
            }
            // Writing to the file
            for (int i = 0; i < fileBytes.size(); i++) {
                try {
                    File.write(fileBytes.get(i));
                } catch (IOException exception) {
                    System.out.println("Something went wrong adding Bytes to File");
                }
            }
            // Close the file
            try {
                File.close();
            } catch(IOException exception) {
                System.out.println("Something went wrong closing file");
            }
        } catch(FileNotFoundException exception) {
            System.out.println("Something went wrong finding file");
        }
    }
}