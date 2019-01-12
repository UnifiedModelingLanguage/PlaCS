package pkg_rich;

import java.io.*;
import java.util.Random;

public class RichUtil {

    public static void write(String path, String message){
        BufferedWriter writer=null;
        try {
            writer = new BufferedWriter(new FileWriter(path));
            writer.write(message);
        }catch(IOException e){
            e.printStackTrace();
        }finally {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String read(String path){
        String res= "";
        BufferedReader reader=null;
        try{
            reader= new BufferedReader(new FileReader(path));
            String line;
            while((line=reader.readLine())!=null){
                res += line.trim();
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return res;
    }


    public static String punctuate(Random random){
        String res="";
        switch(random.nextInt(5)){
            case 1:
                res+=';';
                break;
            case 2:
                res+=':';
                break;
            case 3:
                res+='!';
                break;
            case 4:
                res+='?';
                break;
            default:
                res+='.';
                break;
        }
        return res+' ';
    }
}
