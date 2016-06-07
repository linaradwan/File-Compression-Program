/**
 * @author Lina Radwan
 * CS2210 Assignment 2
 * This class implements a dictionary using a hashtable with separate chaining
 */
public class Dictionary implements DictionaryADT{
	//variables
	private int hashtableSize;	
	private Node[] st;
	private int count;


	/**A constructors that creates an empty dictionary using an array of class "node" 
	 * with a  specified size passed in the parameters
	 * 
	 * @param hashtableSize:the required size of the dictionary (hashtable)
	 */
	public Dictionary(int hashtableSize) {

		this.hashtableSize=hashtableSize;
		st= new Node[hashtableSize];
		count=0;
	}

	/**A method that will take a string (key) as its parameters and return an index that it will be stored in 
	 * or it is stored in the hashtable
	 * 
	 * @param key: takes the string key 
	 * @return index that the key will be stored in or is stored in in the hashtable
	 */

	private int hashfunction(String key) {

		int a=77;

		int hash= (int) key.charAt(key.length()-1);
		for(int i=key.length()-2;i>=0;i--){
			hash=((hash*a)+(int)key.charAt(i))%(hashtableSize);
		}


		return hash;

	}

	/** A method that searches through the hashtable to find the String key(the parameter given) 
	 * in the dictionary(hashtable) and returns the key and its value or its dictEntry
	 * @param String key that is needed to be searched in the dictionary
	 *@return DictEntry (key,value)
	 */ 
	public DictEntry find (String key){

		DictEntry found = null;
		// we first need to get the index of the key it is inserted in
		int possibleKey= hashfunction(key);

		// after that since we are using separate chaining we need to check search in this specific index of the array
		//in the node for the value so we keep going to the next value till either we are at the end of the list
		//or we have found the key
		Node x= st[possibleKey];
		while(x!=null && (x.getKey().equals(key)==false)){	
			x=x.getNext();

		}
		//if we didn't find the key we return null
		if( x==null){
			return null;
		}
		//otherwise, create and return a DictEntry of the key and Value of x we found 
		else{
			found= new DictEntry(key,x.getValue());
			return found;
		}
	}

	/**
	 * finds the number of dictEntry objects stored in the dictionary
	 * @return: count: the number of DictEntry object stored in the dictionary
	 */
	public int numElements(){ 
		return count;
	}

	/** A method that will search for the key in the dictionary and remove it and its associated value from the dictionary
	 * @throws DictionaryException if the key given the parameter is not in the dictionary
	 * @param String key that we want to remove
	 */
	public void remove(String key)throws DictionaryException{
		Node previous=null;
		//get the location of the key first in order to search for it in the list of nodes
		int hashKey= hashfunction(key);
		// if the key is not there throw a DictionaryException
		if (find(key)==null){
			throw new DictionaryException("The key is not in the Dictionary");
		}

		//search for the key in the the index we got if it's in the first element we put the next element as the first
		//if the key is somewhere in the middle or at the end we will have to search for it in the list and make it point
		//to the next value instead of the removed one
		for (Node current = st[hashKey]; current != null; current = current.getNext()) {
			if (key.equals(current.getKey())) {
				if (previous != null){

					previous= previous.getNext();
					current= current.getNext();
					previous=current;

				}
				else{
					st[hashKey] = current.getNext();
				}
				previous = current;
			}
		}
		count--;
	}






	/** A method that adds a dictEntry passed in the parameter to a specified index in the hashtable
	 * 
	 * @param DictEntry object that is needed to be inserted
	 * @return int the number of collision: 1 if there's a collision and 0 if there's no
	 * @throws DictionaryException: which throws an expection if the key of the dictEntry is already in the Dictionary
	 */
	public int insert(DictEntry pair) throws DictionaryException{
		int collision=0;
		//split the dictEntry to value & key in order to find a specific index for the dictEntry to be inserted
		String key= pair.getKey();
		int value= pair.getCode();
		int possibleIndex= hashfunction(key);

		Node n=st[possibleIndex];
		//if there's at least one element in the index given; collison is incremented to 1, otherwise it remains zero
		if(n!=null){
			collision++;
		}
		//we need to look for the key first to check if it's already inserted or not in order to not insert it again
		// we then return an exception if we it's already inserted before
		if(find(key)!=null){
			throw new DictionaryException("The key is already in there");
		}
		else{
			//insert the the key and value of the dictEntry into the first element in the index given 
			//set the the next to the original first
			//increment count so that we can return the number of elements stored in the dictionary using the method numElements
			st[possibleIndex]= new Node(key,value,st[possibleIndex]);
			count++;
			return collision;
		}
	}

}
