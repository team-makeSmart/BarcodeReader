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
	   this();
	   scan(image);
   }
   
   /**
    * Constructor that reads in a String
    * @param text 
    */
   DataMatrix(String text)
   {
	   this();
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
	   for (int col = 0; col < BarcodeImage.MAX_WIDTH; col++) {
		   if (image.getPixel(BarcodeImage.MAX_HEIGHT-1, col)) {
			   signalWidth++;
		   }
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
	   for (int row = 0; row < BarcodeImage.MAX_HEIGHT; row++) {
		   if (image.getPixel(row, 0)) {
			   signalHeight++;
		   }
	   }
	   return signalHeight;
   }
   
   
   /**
    * Makes the BarcodeImage lower-left justified 
    */
   private void cleanImage() 
   {
	   // Make the image left justified
	   while (leftColumnEmpty()) {
		   shiftImageLeft(); 
	   }
	   
	   // Make the image bottom justified
	   while (bottomRowEmpty()) {
		   shiftImageDown();
	   }
   }
   
   /**
    * Helper method for cleanImage()
    * Shifts the image to the left by one column
    */
   private void shiftImageLeft() 
   {
	   // Scan the image - top to bottom, left to right
	   for (int row = 0; row < BarcodeImage.MAX_HEIGHT; row++) // Scan each column 
	   {
		  for (int col = 1; col < BarcodeImage.MAX_WIDTH; col++) // Scan each row
		  { 
			  // Scan each row
			  image.setPixel(row, col-1, image.getPixel(row, col)); // Shift each column to the left
		   }
	   }
   }
   
   /**
    * Helper method for cleanImage()
    * Shift the image down by one row
    */
   private void shiftImageDown() 
   {
	   // Scan the image - bottom to top, left to right
	   for (int row = BarcodeImage.MAX_HEIGHT; row > 0; row--) // Scan each column
	   {
		   for (int col = 0; col < BarcodeImage.MAX_WIDTH; col++) // Scan each row
			{
			   image.setPixel(row, col, image.getPixel(row-1, col)); // Shift each row down
			}
	   }
   }
   
   
   /**
    * Helper method for cleanImage()
    * @return true if the leftmost column in the BarcodeImage is empty
    */
   private boolean leftColumnEmpty() {
	  boolean isEmpty = true;
	  for (int row = 0; row < BarcodeImage.MAX_HEIGHT; row++) // Scan the leftmost column 
	  {
		  if (image.getPixel(row, 0)) isEmpty = false; // '*' found, column is not empty
	  }
	  return isEmpty;
   }
   
   /**
    * Helper method for cleanImage()
    * @return true if the leftmost column in the BarcodeImage is empty
    */
   private boolean bottomRowEmpty() {
	  boolean isEmpty = true;
	  for (int col = 0; col < BarcodeImage.MAX_WIDTH; col++) // Scan the bottom row
	  {
		  if (image.getPixel(BarcodeImage.MAX_HEIGHT-1, col)) isEmpty = false; // '*' found, row is not empty
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
		   cleanImage();
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
   
   /**
    * Generates a BarcodeImage based on a given text
    */
   public boolean generateImageFromText()
   {
	   if (!text.isEmpty()) 
	   {
		   this.image = new BarcodeImage();  // First, make sure the BarcodeImage is blank
		   generateBarcodeBorders(); // Generate the borders for the image
		   fillBarcodeFromText(); // Fill the inside of the barcode
	       return true;
	   } else 
	   {
		   return false;
	   }
	   
   }
   
   /**
    * Generates the borders (2 closed limitation lines + 2 open borderlines) for the image
    */
   private void generateBarcodeBorders() 
   {
	   final int BORDER_HEIGHT = 7; // One for each 2^(0 to 7)
	   final int BORDER_WIDTH = text.length()+2; // Plus 2 because of the extra left and right columns
	   
	   // Generate bottom closed limitation line
	   for (int col = 0; col < BORDER_WIDTH; col++) 
	   {
		   image.setPixel(BarcodeImage.MAX_HEIGHT-1, col, true);
	   }
	   
	   // Generate left closed limitation line
	   for (int row = BarcodeImage.MAX_HEIGHT- BORDER_HEIGHT-2; row <= BarcodeImage.MAX_HEIGHT-2; row++) 
	   {
		   image.setPixel(row, 0, true);
	   }
	   
	   // Generate top open borderline
	   for (int col = 0; col < BORDER_WIDTH; col+=2) 
	   {
		   image.setPixel(BarcodeImage.MAX_HEIGHT-BORDER_HEIGHT-3, col, true);
	   }
	   
	   // Geneate right open borderline
	   for (int row = BarcodeImage.MAX_HEIGHT-1; row > BarcodeImage.MAX_HEIGHT-BORDER_HEIGHT-3; row-=2) 
	   {
		   image.setPixel(row, BORDER_WIDTH-1, true);
	   }
	   
	   // Set actualHeight and actualWidth so that the image prints properly
	   this.actualHeight = computeSignalHeight();
	   this.actualWidth = computeSignalWidth();
   }
   
   /**
    * Fills in the barcode using the given text
    */
   private void fillBarcodeFromText() {
	   final int IMAGE_BEGINING_MARK = BarcodeImage.MAX_HEIGHT - 2;
	   int asciiValue;
	   
	   for (int charIndex = 0; charIndex < text.length(); charIndex++) // Scan the text string
	   { 
		   asciiValue = text.charAt(charIndex); // Get the ASCII value for each character
		   
		   for (int exponent = 7; exponent >= 0; exponent--) // Cycle through 2^7 to 2^0
		   {
			   if (asciiValue >= Math.pow(2, exponent)) 
			   {
				   image.setPixel(IMAGE_BEGINING_MARK-exponent, charIndex+1, true); // Set '*' in barcode
				   asciiValue -= Math.pow(2, exponent);
			   }
		   }
	   }
   }
   
   
   
   /**
    * Looks at the internal image stored in the implementing class,
    * and produces a companion text string
    */
   public boolean translateImageToText()
   {
	   System.out.println("\nDecoded Image");
	   int topRow=(BarcodeImage.MAX_HEIGHT-getActualHeight());
	 
	    char[][] c = new char[BarcodeImage.MAX_HEIGHT-1][ getActualWidth()];
	   
	    //Holds the decoded image representing binary numbers
	    //located on the vertical position of the picture
	    String imageToBinary = "";
	    
	      for (int row = 1; row < BarcodeImage.MAX_HEIGHT-1; row++)
	      {
	         for (int col = 1; col < getActualWidth(); col++)
	         {
	        	 //if reached the point after the empty
	        	 //space (remember the array is shifted down)
				if (row > topRow)
					//begin to decode after the Closed Limitation Line and Open Borderline
					if (image.getPixel(row, col)) {
						c[row][col]='1';
						imageToBinary+="1";
						
					} else {
						c[row][col]='0';
						imageToBinary+="0";	
					}
	         }
	         imageToBinary+="\n";
	      }
	      
	      printDecodedImage(imageToBinary.trim());
	      binaryToString(topRow, c);

      return true;
   }
   
   public void printDecodedImage(String imageToBinary)
	{
		System.out.println();
		System.out.println(imageToBinary);
	}
   
   	/** Loops through the decoded image and transposes
	 * rows with columns. Each column will be converted to row that will
	 * hold a binary number to be converted to a decimal number
	 * and finally to asccii code
	 * @param topRow the row number where the image is shifted 
	 * @param c the array that holds the binary numbers
	 */
	public void binaryToString(int topRow, char[][] c)
	{
		int decimalToAsccii = 0;//holds the sum of the binaries
		System.out.println();
		
		for (int j = 1; j < getActualWidth(); j++)
		{
			for (int i = 1; i < getActualHeight() - 1; i++)
			{
				char[] c1 = new char[getActualWidth()];
				c1[i] = (c[topRow + i][j]);
				//if ones, multiply by the power of two
				if (c1[i] == '1')
				{
					decimalToAsccii += Math.pow(2, Math.abs(i - 8));
				}
			}
			char convertion = ((char) decimalToAsccii);
			this.text += String.valueOf(convertion);
			decimalToAsccii = 0;//reset for the next binary number
			
		}
		System.out.println();
	}

  
   /**
    * Displays the text to the console
    */
   public void displayTextToConsole()
   {
	   System.out.println(text);
   }
   
   /**
    * Displays the BarcodeImage to the console 
    * with a border and with the extra whitespace trimmed 
    */
   public void displayImageToConsole()
   {
	   
	   // Print top border
	   System.out.print(" ");
	   for (int i = 0; i < actualWidth; i ++) {
		   System.out.print("-");
	   }
	   
	   // Go to new line
	   System.out.println();;
	   
	   for (int row = BarcodeImage.MAX_HEIGHT - actualHeight; row < BarcodeImage.MAX_HEIGHT; row++) // Scan through each row
	   {
		   // Print border
		   System.out.print("|");
		   for (int col = 0; col < actualWidth; col++) // Scan through each column
	       {
			   
			   // Print a '*' if index value is true
			   if (image.getPixel(row, col) == true) {
	            	System.out.print("*");
	            }
	            else {
	            	System.out.print(" ");
	            }
	         }
	         // Print border and go to next line on console
		     System.out.print("|\n");
	      }
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
      
      dm.displayImageToConsole();
      
      System.out.println("actualHeight "+ dm.actualHeight);
      System.out.println("actualWidth "+ dm.actualWidth);
      
      
      //translate two images to text
      dm.translateImageToText();
      dm.displayTextToConsole();
      
      bc = new BarcodeImage(sImageIn_2);
      dm = new DataMatrix(bc);
      dm.translateImageToText();
      dm.displayTextToConsole();
      
      dm.generateImageFromText();
      dm.displayImageToConsole();
     
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