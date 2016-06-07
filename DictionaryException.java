

	public class DictionaryException extends RuntimeException
	{
	   /******************************************************************
	     Sets up this exception with an appropriate message.
	   ******************************************************************/
	   public DictionaryException (String collection)
	   {
	      super ("The target element is not in this " + collection);
	   }
	} 

