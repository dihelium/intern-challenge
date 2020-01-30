import java.util.*;
import java.io.*;
import java.nio.CharBuffer;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

class Node{
    char data;
    Node left,right;

    public Node(char ch){
        data = ch;
        left = right = null;
    }
}

public class charTree {

    Node root;
    Node head;
    static Node prev = null;

    charTree(char ch){
        root = new Node(ch);
        root.left = null;
        root.right = null;
    }

    public Node addLeft(String s){
        charTree c = new charTree(s.charAt(0));
        c.root.left = new Node('0');
        int len = s.length();
        if(len>1){
            if(len==2){
                c.root.left = c.addLeft(s.substring(1,2));
            }
            else{
                c.root.left = c.addLeft(s.substring(1,len/2+1));
                c.root.right = c.addRight(s.substring(len/2+1,len));
            }
        }
        if(c.root.left.left!=null&&c.root.left.left.data=='0'&&c.root.right==null){
            c.root.right = new Node('0');
        }
        return c.root;
    }

    public Node addRight(String s){
        charTree c = new charTree(s.charAt(0));
        c.root.left = new Node('0');
        int len = s.length();
        if(len>1){
            if(len==2){
                c.root.left = c.addLeft(s.substring(1,2));
            }
            else{
                c.root.left = c.addLeft(s.substring(1,len/2+1));
                c.root.right = c.addRight(s.substring(len/2+1,len));
            }
        }
        if(c.root.left.left!=null&&c.root.left.left.data=='0'&&c.root.right==null){
            c.root.right = new Node('0');
        }
        return c.root;
    }

    public charTree createTree(String[] s){
        charTree c = new charTree('1');

        if(s.length==1){
            c.root.left = c.addLeft(s[0]);
        }
        else if(s.length==2){
            c.root.left = c.addLeft(s[0]);
            c.root.right = c.addRight(s[1]);
        }
        else {
            String[] sleft = new String[(s.length+1)/2];
            String[] sright = new String[s.length - (s.length+1)/2];
            for (int i = 0; i < (s.length + 1) / 2; ++i) {
                sleft[i] = s[i];
            }
            for(int i = (s.length + 1) / 2;i<s.length;++i){
                sright[i-(s.length + 1) / 2] = s[i];
            }
            c.root.left = createTree(sleft).root;
            if(sright.length==1){
                c.root.right = addRight(s[2]);
            }
            else {
                c.root.right = createTree(sright).root;
            }
        }

        return c;
    }

    public String traverse(Node n,String s){
        String str = s;
        if(n==null) return s;
        if(n.data=='0'){
            return "0";
        }
        else{
            str = str.concat(Character.toString(n.data));
            String newstr = "";
            newstr = traverse(n.left,newstr);
            str = str.concat(newstr);
            newstr = "";
            newstr = traverse(n.right,newstr);
            str = str.concat(newstr);
        }
        return str;
    }

    public char[] create(String[] s){
        String str = "";
        charTree c = createTree(s);
        String newstr = traverse(c.root,str);
        str = str.concat(newstr);
        char[] ch = new char[str.length()];
        for(int i = 0;i<str.length();++i){
            ch[i] = str.charAt(i);
        }
        return ch;
    }

    public static String csvReaderDelimiter(java.io.InputStream in){
        try {
            String s = "";
            int data = in.read();
            while (data != -1) {
                s += String.valueOf((char) data);
                data = in.read();
            }
            String del = ",";
            int flag = 0;
            for(int i=0;i<s.length();++i){
                if(i<s.length() && s.charAt(i)-'a'<=25 && s.charAt(i)-'a'>=0 && i+2<s.length() && s.charAt(i+2)-'a'<=25 && s.charAt(i+2)-'a'>=0){
                    flag = 1;
                    del = Character.toString(s.charAt(i+1));
                    break;
                }
            }
            return del;
        }
        catch(Exception e){
            System.out.println("Exception Caught");
            String ret = ",";
            return ret;
        }
    }

    public static String[] read(String csvFile,String delimiter) {
        try {
            File file = new File(csvFile);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            String line = "";
            String[] tempArr;
            String retArr = "";
            while((line = br.readLine()) != null) {
                tempArr = line.split(delimiter);
                for(String tempStr : tempArr) {
                    retArr += tempStr;
                }
                retArr += "\n";
            }
            br.close();
            int count = 0;
            for(int i = 0;i<retArr.length();++i){
                if(retArr.charAt(i)=='\n'){
                    if(retArr.charAt(i-1)!='\n')
                        count++;
                }
            }
            String[] str = new String[count];
            int j = 0;
            for(int i = 0;i<retArr.length();++i){
                if(i<retArr.length()&&retArr.charAt(i)=='\n'){
                    i++;
                }
                int index = i;
                while(i<retArr.length()&&retArr.charAt(i)!='\n'){
                    i++;
                }
                if(j<count)
                    str[j++] = retArr.substring(index,i) + "2";
                else
                    break;
            }
            return str;
        } catch(IOException ioe) {
            String[] str = new String[1];
            ioe.printStackTrace();
            return str;
        }
    }

    public byte[] toBytes(char[] chars) {
        CharBuffer charBuffer = CharBuffer.wrap(chars);
        ByteBuffer byteBuffer = StandardCharsets.UTF_8.encode(charBuffer);
        byte[] bytes = Arrays.copyOfRange(byteBuffer.array(),
                byteBuffer.position(), byteBuffer.limit());
        Arrays.fill(byteBuffer.array(), (byte) 0); // clear sensitive data
        return bytes;
    }

    public int numberOfWords(int ones){
        if(ones==0){
            return 0;
        }
        if(ones==1){
            return 2;
        }
        else if(ones==2){
            return 3;
        }
        else{
            int count = 0;
            ones--;
            count+= numberOfWords((ones+1)/2);
            count+= numberOfWords(ones - (ones+1)/2);
            return count;
        }
    }

    public String getWords(Node n,String s) {
        String str = s;
        if(n==null||n.data=='0'){
            return s;
        }
        else if(n.data=='2'){
            s = s.concat("\n");
            return s;
        }
        else{
            if(n.data!='1')str = str.concat(Character.toString(n.data));
            String newstr = "";
            newstr = getWords(n.left,newstr);
            str = str.concat(newstr);
            newstr = "";
            newstr = getWords(n.right,newstr);
            str = str.concat(newstr);
        }
        return str;

    }

    public ArrayList getTree(char[] ch, int index){
        @SuppressWarnings("unchecked")
        ArrayList a = new ArrayList(),b = new ArrayList();
        int i = index;
        charTree c = new charTree(ch[i]);
        if(ch[i]=='0'){
            c.root.left = null;
            c.root.right = null;
            a.add(c.root);
            a.add(i);
            return a;
        }
        b = getTree(ch,i+1);
        c.root.left = (Node)b.get(0);
        i = (int)b.get(1);
        if(c.root.left!=null&&c.root.left.data=='0'){
            c.root.right = null;
        }
        else{
            b = getTree(ch,i+1);
            c.root.right = (Node)b.get(0);
            i = (int)b.get(1);
        }

        a.add(c.root);
        a.add(i);
        return a;
    }

    public void load(char[] ch){
        String s = "";
        int count = 0;
        /*for(int i = 0;i<ch.length;++i){
            if(ch[i]=='1'){
                count++;
            }
        }*/
        int num_words = numberOfWords(count);
        String[] words = new String[num_words];
        ArrayList a = getTree(ch,0);
        Node start = (Node)a.get(0);
        s = getWords(start,s);
        System.out.println(s);
    }

    public static void main(String[] args) {
        try {
            charTree c = new charTree('1');
            if (args[0].equals("create")) {
            InputStream in = new FileInputStream(args[1]);
            //InputStream in = new FileInputStream("/Users/sharvankumar/Downloads/file-2.csv");
            String del = c.csvReaderDelimiter(in);
            String[] s = read(args[1],del);
            //String[] s = read("/Users/sharvankumar/Downloads/file-2.csv", del);
            byte[] bytes = c.toBytes(c.create(s));
            Path path = Paths.get(args[2]);
            //Path path = Paths.get("/Users/sharvankumar/Desktop/test.bin");
            Files.write(path, bytes);//*/
        }
            else if (args[0].equals("load")) {
                Path path = Paths.get(args[1]);
            //Path path = Paths.get("/Users/sharvankumar/Desktop/test.bin");
                byte[] bytes = Files.readAllBytes(path);
                String s = new String(bytes);
                char[] chars = s.toCharArray();
                c.load(chars);//*/
            }
        } catch (Exception e) {
            System.out.println("Exception Caught");
        }
    }
}
