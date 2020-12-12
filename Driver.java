/*
*
*   Written By: -> Gavin Kaepernick, Carthage College
*   Written In: -> Java
*   Description:
*       Driver Class that runs Icon and Pixel Classes and allows us to see
*       the picture that we have drawn.
*
*/

public class Driver {
    public static void main(String args[]) {
        Icon icon = new Icon(5,5);
        icon.setPixel(0, 0, 255, 0, 0);
        icon.setPixel(0, 4, 0, 255, 0);
        icon.setPixel(4, 0, 0, 0, 255);
        icon.setPixel(4, 4, 0, 0, 0);
        System.out.println(icon.toString());
        icon.exportBitMap();
    }
}