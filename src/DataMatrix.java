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
      this.BarcodeImage = new BarcodeImage();
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
	   signalWidth = 0;
	   // Scans the last row of the 2D array, incrementing the signalHeight if a '*' is found
	   for (int i = 0; i < BarcodeImage.MAX_HEIGHT, i++) {
		   if (image.getPixel[-1][i]) signalWidth++;
	   }
	   return signalWidth;
   }
   
   /**
    * Calculates the signal height from the BarcodeImage
    * @return signalHeight the height of the barcode 
    */
   private int computeSignalHeight()
   {
	   signalHeight = 0;
	   // Scans each element in the first column the 2D array, incrementing the signalHeight if a '*' is found
	   for (int i = 0; i < BarcodeImage.MAX_HEIGHT, i++) {
		   if (image.getPixel[i][0]) signalHeight++;
	   
	   }
	   return signalHeight;
   }
   
   
   /**
    * Makes the BarcodeImage lower-left justified 
    */
   private void cleanImage() 
   {
	   cleanImage = new BarcodeImage();
	   boolean barcodeFound = false;
	   int offsetX;
	   int offsetY;
		
	   // Scan the image - left to right, top to bottom
	   for (int i = 0; i < BarcodeImage.MAX_HEIGHT); i++) // Scan each column 
	   {
			for (int j = 0; j < BarcodeImage.MAX_WIDTH; j++) // Scan each row
			{ 
				
				if (image.getPixel(i, j) // If a '*' is found
				{
					if (!barcodeFound) // Barcode not found
					{ 
						// Get the position of the top-left corner
						offsetX = j;
						offsetY = i;
						barcodeFound = true;
					} else // Barcode found
					{ 
						//Copy the old barcode to the new one
						cleanImage.setPixel(i-offsetY, j-offsetX);
					}
				}
			}
		}
		// Set the old image to the new one
		image = cleanImage();
	}
   
   
   // ------- Implementation of BarcodeIO interface methods -------
   
   public boolean scan(BarcodeImage bc)
   {
	   return false;
   }
   
   public boolean readText(String text)
   {
      return false; 
   }
   
   public boolean generateImageFromText()
   {
      return false;
   }
   
   public boolean translateImageToText()
   {
      return false;
   }
   
   public void displayTextToConsole()
   {
      
   }
   
   public void displayImageToConsole()
   {
      
   }
   
   // ---- End of BarcodeIO interface methods ----
   
}

interface BarcodeIO
{
   public boolean scan(BarcodeImage bc);
   public boolean readText(String text);
   public boolean generateImageFromText();
   public boolean translateImageToText();
   public void displayTextToConsole();
   public void displayImageToConsole();
}
