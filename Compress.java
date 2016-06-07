import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author Lina Radwan
 * CS2210 Assignment 2
 * This class compresses the words in an input file to ASCII codes outputfile
 */
public class Compress {

	public static void main(String[] args) {
		//Store using the insert method of the dictionary each character and its value (ASII code) into a dictionary 
		Dictionary dict= new Dictionary(4093);
		for (int i=0; i<256; i++)
		{
			String s= Character.toString((char)i);
			DictEntry dictentry= new DictEntry(s,(char)i);
			dict.insert(dictentry);
		}

		String inputfilename= args[0];
		BufferedInputStream in = null;
		BufferedOutputStream out = null;
		MyOutput myoutput = new MyOutput();

		try {			
			in = new BufferedInputStream (new FileInputStream(inputfilename+".txt"));
			out = new BufferedOutputStream (new FileOutputStream(inputfilename+".zzz"));

			//STEP 1//
			//Read the first byte of the input file and write it in the output file,set the variable n to 256
			int n=256;
			int readbyte =in.read();
			myoutput.output(readbyte, out);				
			char c=0;
			String bytes=Character.toString((char)readbyte);
			int q = 0;
			int r=0;

			//STEP2//
			// while we haven't finished reading from the file 
			while((readbyte= in.read())!=-1 ){

				//set c to be the readbyte and concatenate it into the string bytes that has the previous read bytes
				c=(char)readbyte;
				String characterStringRead=Character.toString(c);
				bytes=bytes.concat(characterStringRead);

				//STEP3//
				DictEntry searchdict= dict.find(bytes);
				//try finding the string bytes in the dict if we found it 
				//we increment r and test whether r is 2,which means the the string of bytes have been read twice if it is,
				// this means that we will be repeating characters and we don't need that 
				//so, set the value of bytes to be the last char read this is 
				if(searchdict!=null){
					r++;
					q=0;
					if(r==2){
						bytes=Character.toString(c);
						r=0;


					}
					//if r!=0 we just write the value of searchdict into the output file
					else{
						int codeP= searchdict.getCode();
						myoutput.output(codeP, out);
					}


				}	
				//STEP 4//

				//if we didn't find the bytes in the dictionary 
				//we increment q by 1 and test whether q=2 if this happens that means that it skipped over a character 
				// so if this happens we insert the last character read
				//if not we just insert the bytes string into the dictionary and increment n
				//set bytes to be the last element read
				else{ 
					r=0;
					q++;
					if(q==2){
						myoutput.output(c, out);
					}

					DictEntry newEntry=new DictEntry(bytes,n);
					dict.insert(newEntry);
					n++;

					bytes=Character.toString(c);

				}

			}
			//after the while loop terminates the while loop doesn't insert the last element so just insert it after the loop
			myoutput.output(c,out);
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		catch (IOException e) {
			e.printStackTrace();
		}
		finally{
			try{
				if(out!=null && in!=null){
					myoutput.flush(out);

					out.close();
					in.close();
				}
			}
			catch (IOException e) {
				e.printStackTrace();
			}



		}
	}
}

