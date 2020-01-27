import java.io.*;
import java.util.*;

public class MyPermutation{
    @SuppressWarnings("unchecked")							//used because of line 17, there is no way to remove warnings, they have to be suppressed
    public String[] permutations(ArrayList<ArrayList<Character> > mat){  // permutations function returns the string required for output, by taking input an ArrayList of an ArrayList of characters taken from the csv file
    	/* The algorithm uses recursion to calculate the output string, by attaching the 
    	string calculated using all lines except the last one, with every character of the last line*/

        if(mat.size()==1){			//since the function is recursive, a base case for the first line has been made. 
            String[] s = new String[mat.get(0).size()];
            int i = 0;
            for(Object obj:mat.get(0)) {
                if (i < s.length) {
                    s[i++] = obj.toString();
                }
            }
            return s;		//returns the characters of the first line of the input as an array of String
        }
        ArrayList<Character>[] arr = new ArrayList[mat.size()]; //this has been created to provide ease of accessibility which is a bit difficult with an ArrayList of an ArrayList
        int k = 0;
        for(ArrayList a:mat){
            if(k<mat.size()){
                arr[k] = a;
            }
            k++;
        }
        int product = 1;
        for(ArrayList a: mat){
            product*= a.size(); 	// product calculates the size of the String to be generated for the given input
        }
        int i = 0;
        String[] ret = new String[product];
        ArrayList<ArrayList<Character> > ret_arr_list = new ArrayList<>();
        for(int exception_index = 0;exception_index<arr.length-1;++exception_index){
            ret_arr_list.add(arr[exception_index]); //ret_arr_list is an ArrayList of an ArrayList except the last row of the original input
        }
        String[] s = permutations(ret_arr_list);  //gets the string as mentioned at the very beginning of the function
        int ret_index = 0;
        for(int i_index = 0;i_index < s.length;++i_index){
            for(int j_index = 0;j_index<mat.get(mat.size()-1).size();++j_index){
                ret[ret_index++] = s[i_index] + arr[mat.size()-1].get(j_index).toString(); //appends every character of the last line to 
                																		   //every String element calculated in String[] s
            }
        }
        return ret;
    }

    public ArrayList<ArrayList<Character> > reader(java.io.InputStream in) throws IOException{ //reads the input to generate an arraylist of an arraylist of characters
        String s = "";
        int data = in.read();
        while(data!=-1){
            s += String.valueOf((char)data);
            data = in.read();
        }								// s now has the input file stored as a string
        ArrayList<ArrayList<Character> > arr = new ArrayList<>();
        for(int i = 0;i<s.length();){
            ArrayList<Character> a = new ArrayList<>();
            while(s.charAt(i)!='\n'){
                if((s.charAt(i)-'a'>=0&&s.charAt(i)-'a'<=25)||(s.charAt(i)-'0'>=0&&s.charAt(i)-'0'<=9)){ //taking only alphanumerics
                    a.add(s.charAt(i));
                }
                i++;
            }
            arr.add(a);
            i++;
        }
        return arr;
    }

    public static void main(String[] args) throws Exception{
        try {/Users/sharvankumar/IdeaProjects/MyPermutation/src/file.csv
            
            InputStream in = new FileInputStream(args[0]);
            MyPermutation m = new MyPermutation();
            String[] s = m.permutations(m.reader(in));
            for(int i = 0;i<s.length;++i){
                if(i!=s.length-1){
                    System.out.print(s[i]+", ");  //comma separated output
                }
                else{
                    System.out.print(s[i]);
                }
            }
        }
        catch(FileNotFoundException e){
            System.out.println("File Not Found");
        }
    }
}