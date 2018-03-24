class BarcodeImage
{
   // Internal max dimensions of a two-dimensional array
   public static final int MAX_HEIGHT = 30;
   public static final int MAX_WIDTH = 65;

   // 2D array of pixel data for black & white images. true == black. false ==
   // white.
   private boolean[][] image_data = new boolean[MAX_HEIGHT][MAX_WIDTH];

   /**
    * Default Constructor : Initiates a 2D array holding all white values, i.e.
    * false values.
    */
   BarcodeImage()
   {
      // Initialize 2D array to have all white values, i.e. false values
      for (int row = 0; row < MAX_HEIGHT; row++)
      {
         for (int col = 0; col < MAX_WIDTH; col++)
         {
            image_data[row][col] = false;
         }
      }
   }

   /**
    * Constructor : Initializes a 2D array that holds values based on string
    * array of 1D elements received as the argument. All characters in strings
    * shall be considered as true if char == '*', else will be left with default
    * initialized to false.
    * 
    * @param str_data[]
    *           array of strings that represents ' ' or '*'
    * @Override
    */
   BarcodeImage(String[] str_data)
   {
      // Initialize 2D array to have the values of the string argument
      // Starts initializing at cartesian coordianate point (0,0)
      for (int row = 0; row < str_data.length; row++)
      {
         // Get and store the length of the row at indexed value
         int rowLength = str_data[row].length();

         // Check if rowLength is too long for BarcodeImage object
         if (rowLength > MAX_WIDTH)
         {
            rowLength = MAX_WIDTH; // Set to MAX_WIDTH
         }

         //
         for (int col = (rowLength - 1); col > 0; col--)
         {
            if (str_data[row].charAt(col) == '*')
            {
               image_data[row][col] = true;
            }
            // else the default value is is false for ' '
         }
      }
   }

   /**
    * Accessor Method
    * 
    * @param row
    *           int value representing y-cartesian coordinate for image
    * @param col
    *           int value representing x-cartesian coordinate for image
    * @return boolean pixel value given at the coordinate location of the
    *         parameters. If true, return white. If black or error, return
    *         false.
    */
   public boolean getPixel(int row, int col)
   {
      // Check if row argument values are within valid range
      if (row < 0 || row > MAX_HEIGHT - 1)
      {
         return false; // Error out of range
      }

      // Check if col argument values are within valid range
      if (col < 0 || col > MAX_WIDTH - 1)
      {
         return false; // Error out of range
      }

      return image_data[row][col];
   }

   /**
    * Mutator Method
    * 
    * @param row
    *           int value representing y-cartesian coordinate for image
    * @param col
    *           int value representing x-cartesian coordinate for image
    * @param value
    *           boolean representing true for '*' or false for ' '
    * @return boolean true if successful, else returns false
    */
   boolean setPixel(int row, int col, boolean value)
   {
      // Check if row argument values are within valid range
      if (row < 0 || row > MAX_HEIGHT - 1)
      {
         return false; // Error out of range
      }

      // Check if col argument values are within valid range
      if (col < 0 || col > MAX_WIDTH - 1)
      {
         return false; // Error out of range
      }

      // Set the pixel with argument boolean value at the argument coordinates
      image_data[row][col] = value;

      // Return true because pixel was successfully changed
      return true;
   }

   /**
    * Clone Method. Allows for BarcodeImage object to be cloned
    * 
    * @return clone of BarcodeImage object or returns null if exception
    */
   @Override
   public BarcodeImage clone()
   {
      try
      {
         return (BarcodeImage) super.clone();
      } catch (CloneNotSupportedException exception)
      {
         return null; // Returns null if cannot clone object
      }
   }

}
