class DataMatrix implements BarcodeIO

{
   public static final char BLACK_CHAR = '*';
   public static final char WHITE_CHAR = ' '; 
   private BarcodeImage image;
   private String text;
   private int actualWidth;
   private int actualHeight;
   
   /**
    * Default constructor 
    */
   DataMatrix()
   {
      this.image = new BarcodeImage();
      this.text = "";
      this.actualWidth = 0;
      this.actualHeight = 0;
   }
   
   /**
    * Constructor that reads in a BarcodeImage
    * @param image
    */
   DataMatrix(BarcodeImage image)
   {
	   scan(image);
   }
   
   /**
    * Constructor that reads in a String
    * @param text 
    */
   DataMatrix(String text)
   {
	   readText(text);
   }
   
   
   /**
    * Getter method for actualWidth
    * @return actualWidth
    */
   public int getActualWidth()
   {
	   return actualWidth;
   }
   
   /**
    * Getter method for actualHeight
    * @return actualHeight
    */
   public int getActualHeight() 
   {
	   return actualHeight;
   }
   
   /**
    * Calculates the signal width from the BarcodeImage
    * @return signalWidth the width of the barcode
    */
   private int computeSignalWidth()
   {
	   int signalWidth = 0;
	   // Scans the last row of the 2D array, incrementing the signalHeight if a '*' is found
	   for (int i = 0; i < BarcodeImage.MAX_WIDTH; i++) {
		   if (image.getPixel(BarcodeImage.MAX_HEIGHT-1, i)) signalWidth++;
	   }
	   return signalWidth;
   }
   
   /**
    * Calculates the signal height from the BarcodeImage
    * @return signalHeight the height of the barcode 
    */
   private int computeSignalHeight()
   {
	   int signalHeight = 0;
	   // Scans each element in the first column the 2D array, incrementing the signalHeight if a '*' is found
	   for (int i = 0; i < BarcodeImage.MAX_HEIGHT; i++) {
		   if (image.getPixel(i, 0)) signalHeight++;
	   }
	   return signalHeight;
   }
   
   
   /**
    * Makes the BarcodeImage lower-left justified 
    */
   private void cleanImage() 
   {
	   while (leftColumnEmpty()) {
		   shiftImageLeft();
	   }
	   
	   while (bottomRowEmpty()) {
		   shiftImageDown();
	   }
   }
   
   /**
    * Helper method fo cleanImage()
    * Shifts the image to the left by one column
    */
   private void shiftImageLeft() 
   {
	   // Scan the image - left to right, top to bottom
	   for (int i = 0; i < BarcodeImage.MAX_HEIGHT; i++) 
	   {
		   // Scan each column 
		  for (int j = 1; j < BarcodeImage.MAX_WIDTH; j++) 
		  { 
			  // Scan each row
			  image.setPixel(i, j-1, image.getPixel(i, j)); // Shift each column to the left
			  
		   }
	   }
   }
   
   /**
    * Helper method fo cleanImage()
    * Shift the image down by one row
    */
   private void shiftImageDown() 
   {
	   // Scan the image - left to right, top to bottom
	   for (int i = 1; i < BarcodeImage.MAX_HEIGHT; i++) 
	   {
		   for (int j = 0; j < BarcodeImage.MAX_WIDTH; j++) // Scan each row
			{ 
				image.setPixel(i, j, image.getPixel(i-1, j)); // Shift each row down
			}
	   }	
   }
   
   
   /**
    * Helper method for cleanImage()
    * @return true if the leftmost column in the BarcodeImage is empty
    */
   private boolean leftColumnEmpty() {
	  boolean isEmpty = true;
	  for (int i = 0; i < BarcodeImage.MAX_HEIGHT; i++) 
	  {
		  if (image.getPixel(i, 0)) isEmpty = false;
	  }
	  return isEmpty;
   }
   
   /**
    * Helper method for cleanImage()
    * @return true if the leftmost column in the BarcodeImage is empty
    */
   private boolean bottomRowEmpty() {
	  boolean isEmpty = true;
	  for (int j = 0; j < BarcodeImage.MAX_WIDTH; j++) 
	  {
		  if (image.getPixel(BarcodeImage.MAX_HEIGHT, j)) isEmpty = false;
	  }
	  return isEmpty;
   }
   
   
   // ------- Implementation of BarcodeIO interface methods -------
   
   /**
    * Accepts image represented as a BarcodeImage object
    * Stores a copy of this image
    * Sets the actualWidth and actualHeight
    * 
    * @param bc the BarcodeImage object 
    * @return true if no CloneNotSupportedException occurs
    */
   @Override
   public boolean scan(BarcodeImage bc)
   {
	   try
	   {
		   //Object clone method should be implemented in the BarcodeImage class
		   this.image = (BarcodeImage) bc.clone();
		   //cleanImage();
		   this.actualHeight = computeSignalHeight();
		   this.actualWidth = computeSignalWidth();
		   return true;
	
	   }
	   catch(CloneNotSupportedException e)
	   {
		   //this shouldn't happen
	   }
      return false;
   }
   
   /**
    * Accepts a text string to be eventually encoded in an image.
    * 
    * @param text the text to be encoded
    * @return true if text length < Max width of the image
    */
   public boolean readText(String text)
   {
	   if(text.length() < BarcodeImage.MAX_WIDTH)
	      {
	         this.text = text;
	         return true;
	      }
	      return false;
   }
   
   public boolean generateImageFromText()
   {
      return true;
   }
   
   public boolean translateImageToText()
   {
      return true;
   }
   
   public void displayTextToConsole()
   {
     
   }
   
   public void displayImageToConsole()
   {
      image.displayToConsole();
   }

   // ---- End of BarcodeIO interface methods ----
   
   public static void main(String[] args)
   {
      String[] sImageIn =
      {
         "                                               ",
         "                                               ",
         "                                               ",
         "     * * * * * * * * * * * * * * * * * * * * * ",
         "     *                                       * ",
         "     ****** **** ****** ******* ** *** *****   ",
         "     *     *    ****************************** ",
         "     * **    * *        **  *    * * *   *     ",
         "     *   *    *  *****    *   * *   *  **  *** ",
         "     *  **     * *** **   **  *    **  ***  *  ",
         "     ***  * **   **  *   ****    *  *  ** * ** ",
         "     *****  ***  *  * *   ** ** **  *   * *    ",
         "     ***************************************** ",  
         "                                               ",
         "                                               ",
         "                                               "

      };      
            
         
      
      String[] sImageIn_2 =
      {
            "                                          ",
            "                                          ",
            "* * * * * * * * * * * * * * * * * * *     ",
            "*                                    *    ",
            "**** *** **   ***** ****   *********      ",
            "* ************ ************ **********    ",
            "** *      *    *  * * *         * *       ",
            "***   *  *           * **    *      **    ",
            "* ** * *  *   * * * **  *   ***   ***     ",
            "* *           **    *****  *   **   **    ",
            "****  *  * *  * **  ** *   ** *  * *      ",
            "**************************************    ",
            "                                          ",
            "                                          ",
            "                                          ",
            "                                          "

      };
     
      BarcodeImage bc = new BarcodeImage(sImageIn);
      DataMatrix dm = new DataMatrix(bc);
      
      System.out.println("actualHeight "+ dm.actualHeight);
      System.out.println("actualWidth "+ dm.actualWidth);
//     
//      // First secret message
//      dm.translateImageToText();
//      dm.displayTextToConsole();
//      dm.displayImageToConsole();
//      
//      // second secret message
//      bc = new BarcodeImage(sImageIn_2);
//      dm.scan(bc);
//      dm.translateImageToText();
//      dm.displayTextToConsole();
//      dm.displayImageToConsole();
//      
//      // create your own message
//      dm.readText("What a great resume builder this is!");
//      dm.generateImageFromText();
//      dm.displayTextToConsole();
//      dm.displayImageToConsole();
   }   
   
   
   
}