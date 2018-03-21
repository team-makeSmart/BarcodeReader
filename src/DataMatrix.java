class DataMatrix implements BarcodeIO
{
   public static final char BLACK_CHAR = '*';
   public static final char WHITE_CHAR = ' '; 
   private BarcodeImage image;
   private String text;
   private int actualWidth;
   private int actualHeight;
   
   DataMatrix()
   {
      
   }
   
   DataMatrix(BarcodeImage image)
   {
      
   }
   
   DataMatrix(String text)
   {
      
   }
   
   public int getActualWidth()
   {
      return actualWidth;
   }
   
   public int getActualHeight() 
   {
      return actualHeight;
   }
   
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
   
   private int computeSignalWidth()
   {
      return 0;
   }
   
   private int computeSignalHeight()
   {
      return 0;
   }
   
   private void cleanImage()
   {
      
   }
   
}
