class BarcodeImage implements Cloneable
{
   // Internal max dimensions of a two-dimensional array
   public static final int MAX_HEIGHT = 30;
   public static final int MAX_WIDTH = 65;

   // 2D array of pixel data for black & white images. true == black. false ==
   // white.
   private boolean[][] image_data = new boolean[MAX_HEIGHT][MAX_WIDTH];

   /**
    * Default Constructor : creates an object of class type BarcodeImage. Note:
    * Object holds all default false values values because instance variable
    * array image_data are are automatically initialized to false in the
    * declaration of the array.
    */
   BarcodeImage()
   {
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
      // Index for string argument
      int strIndex = str_data.length - 1;

      // On iterations of for-loop,width holds length of string arg at strIndex
      // Width will allow the for-loop to only access the values of the
      // str_data[] within bounds of each elements length and thus
      // ragged arrays are accounted for
      int width = 0;

      // Height ensures for-loops stay within bounds of str_data's array length
      int height = (MAX_HEIGHT - str_data.length);

      // Initialize image_date to have the values of the string argument
      // Starts initializing at Cartesian coordinate point (0,0)
      // Note: The outer for-loop is decremented to place image in bottom-left
      for (int row = MAX_HEIGHT - 1; row >= height; row--)
      {
         // Set the width to length of str_data element at the index value
         width = str_data[strIndex].length();

         //Iterates through the columns of the row
         for (int col = 0; col < width; col++)
         {
            // Finds if the char value at str_data index represents true, '*' 
            if (str_data[strIndex].charAt(col) == '*')
            {
               image_data[row][col] = true;
            }
            // else the default value is false for ' '
         }
         strIndex--; // Decrement the index before next loop iteration
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
    * 
    * Creates and returns a copy of this object
    * @throws CloneNotSupportedException 
    */
   public Object clone() throws CloneNotSupportedException
   {
	   return (BarcodeImage) super.clone();
   }


//// BELOW FOR TESTING PURPOSES ONLY //////////////////////
   
   
   /**
    * Testing method to help in the development of the class
    * Creates an object of BarcodeImage class that
    * is initialized with string values representing
    * an image 1d string array
    * */
   public static void testImage()
   {
      // Create an array string
      String[] strArray =
      { "* * * * * * * * * * * * * * * * * *",
         "*                                 *",
         "***** ** * **** ****** ** **** **  ",
         "* **************      *************",
         "**  *  *        *  *   *        *  ",
         "* **  *     **    * *   * ****   **",
         "**         ****   * ** ** ***   ** ",
         "*   *  *   ***  *       *  ***   **",
         "*  ** ** * ***  ***  *  *  *** *   ",
         "***********************************" };

      // Create an object of BarcodeImage type using string array
      BarcodeImage object = new BarcodeImage(strArray);
      
      object.displayToConsole();
   }
   
   /**
    * Testing Method to help in the development of class
    * Prints calling objects image to screen
    * Converts true values to '*' * ' ' to false
    * */
   public void displayToConsole() 
   {
      // go next line on console
      System.out.println();

      // Loop through 
      for (int row = 0; row < MAX_HEIGHT; row++)
      {
         for (int col = 0; col < MAX_WIDTH; col++)
         {
            if (image_data[row][col] == true)
            {
               System.out.print("*");
            } 
            else
            {
            System.out.print(" ");
            }
         }
         // go next line on console
         System.out.println();
      }
   }
   
}
