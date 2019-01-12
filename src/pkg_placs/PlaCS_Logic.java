package pkg_placs;

import pkg_rich.RichJob;
import pkg_rich.RichString;
import pkg_rich.RichUtil;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class PlaCS_Logic {

    private final static int number_of_adverbs=4474;
    private final static int number_of_verbs=11539;
    private final static int number_of_adjectives=21498;
    private final static int number_of_nouns=117952;
    private final static int dic_offset=35;
    private static String query_path="res/IO_buffer/query_gen";
    private static String result_path="res/IO_buffer/result_gen";
    private static String xslt_generic_path="res/PlaCS_gen";
    private final static int nb_threads=Math.max(1,Runtime.getRuntime().availableProcessors()-2);
    private static PlaCS_SentenceThread[] thread=new PlaCS_SentenceThread[nb_threads];
    private static int printer=0;
    private static Random random=new Random();
    private static Random seeded_random;
    private static int nb_normal=62;
    private static char[] char_array={
            'a',
            'b',
            'c',
            'd',
            'e',
            'f',
            'g',
            'h',
            'i',
            'j',
            'k',
            'l',
            'm',
            'n',
            'o',
            'p',
            'q',
            'r',
            's',
            't',
            'u',
            'v',
            'w',
            'x',
            'y',
            'z',
            'A',
            'B',
            'C',
            'D',
            'E',
            'F',
            'G',
            'H',
            'I',
            'J',
            'K',
            'L',
            'M',
            'N',
            'O',
            'P',
            'Q',
            'R',
            'S',
            'T',
            'U',
            'V',
            'W',
            'X',
            'Y',
            'Z',
            '1',
            '2',
            '3',
            '4',
            '5',
            '6',
            '7',
            '8',
            '9',
            '0',
            '!',
            '@',
            '#',
            '$',
            '%',
            '^',
            '&',
            '*',
            '(',
            ')',
            '-',
            '_',
            '`',
            '~',
            '{',
            '[',
            ']',
            '}',
            ';',
            ':',
            ',',
            '<',
            '.',
            '>',
            '/',
            '?',
    };

    public static void init(){
        for(int i=0;i<thread.length;i++){
            thread[i]=new PlaCS_SentenceThread();
        }
    }

    public static String generate_password(int length, boolean allow_special){
        return generate_password(-1,length,allow_special);
    }
    /**
     * @param seed==-1 means clock seed
     * */
    public static String generate_password(int seed, int length, boolean allow_special){
        if(seed!=-1)
            seeded_random=new Random(seed);
        String res="";
        for(int i=0;i<length;i++){
            res+=char_array[
                    seed==-1?
                    random.nextInt(allow_special?char_array.length:nb_normal)
                    :seeded_random.nextInt(allow_special?char_array.length:nb_normal)
                    ];
        }
        return res;
    }
    public static String generate_username(boolean allow_special){
        return generate_username(-1,allow_special);
    }

    /**
     * @param seed==-1 means clock seed
     * */
    public static String generate_username(int seed, boolean allow_special){
        if(seed!=-1)
            seeded_random=new Random(seed);
        return "Joh"+char_array[
                (seed==-1?
                random.nextInt(allow_special?char_array.length:nb_normal)
                :seeded_random.nextInt(allow_special?char_array.length:nb_normal))]
                +"Do"+char_array[
                (seed==-1?
                        random.nextInt(allow_special?char_array.length:nb_normal)
                :seeded_random.nextInt(allow_special?char_array.length:nb_normal))]
                +(seed==-1?random.nextInt(9):seeded_random.nextInt(9))
                +(seed==-1?random.nextInt(9):seeded_random.nextInt(9))
                +(seed==-1?random.nextInt(9):seeded_random.nextInt(9))
                +(seed==-1?random.nextInt(9):seeded_random.nextInt(9));
    }

    public static int roll_dice(int nb_face){
        return random.nextInt(nb_face)+1;
    }


    private static String generate_dic_line(int max){
        return ""+random.nextInt(max);
    }
    private static String query_generic(){

        String query="<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                +"<?xml-stylesheet type=\"text/xsl\" href=\"PlaCS_gen.xsl\"?><sentence>";
        switch(random.nextInt(4)){
            //ADJ NOUN VERB ADV
            case 1:
                query+="<a>"+ generate_dic_line(number_of_adjectives)+"</a>";
                query+="<n>"+ generate_dic_line(number_of_nouns)+"</n>";
                query+="<v>"+ generate_dic_line(number_of_verbs)+"</v>";
                query+="<r>"+ generate_dic_line(number_of_adverbs)+"</r>";
                break;
            //NOUN ADJ VERB ADV
            case 2:
                query+="<n>"+ generate_dic_line(number_of_nouns)+"</n>";
                query+="<a>"+ generate_dic_line(number_of_adjectives)+"</a>";
                query+="<v>"+ generate_dic_line(number_of_verbs)+"</v>";
                query+="<r>"+ generate_dic_line(number_of_adverbs)+"</r>";
                break;
            //NOUN ADJ ADV VERB
            case 3:
                query+="<n>"+ generate_dic_line(number_of_nouns)+"</n>";
                query+="<a>"+ generate_dic_line(number_of_adjectives)+"</a>";
                query+="<r>"+ generate_dic_line(number_of_adverbs)+"</r>";
                query+="<v>"+ generate_dic_line(number_of_verbs)+"</v>";
                break;
            //NOUN VERB NOUN
            default:
                query+="<n>"+ generate_dic_line(number_of_nouns)+"</n>";
                query+="<v>"+ generate_dic_line(number_of_verbs)+"</v>";
                query+="<n>"+ generate_dic_line(number_of_nouns)+"</n>";
                break;
        }
        query+="</sentence>";
        RichUtil.write(query_path+".xml",query);
        BasicXsl.xsl(query_path+".xml", result_path+".xml", xslt_generic_path+".xsl");
        return RichUtil.read(result_path+".xml");
    }
    public static String generate_scrambled_generic_message(){
        String query=query_generic();
        System.out.println(query);
        String res="";
        String[] words=query.split("\\|");
        for(int i=0;i<words.length;i++){
            RichString word=new RichString(words[i]);
            if(i==0){
                word.capitalize();
            }
            res+=word.get_word();
            if(i<words.length-1){
                res+=' ';
            }
        }
        res+=RichUtil.punctuate(random);
        return res;
    }

    public static String generate_scrambled_message(String original_message){
        return generate_scrambled_message(-1,original_message);
    }
    public static String generate_scrambled_message(int seed, String original_message){
        if(seed!=-1)
            seeded_random=new Random(seed);
        String res="";
        original_message=original_message.toLowerCase();
        String[] sentences=original_message.split("[ ]*(\\.|;|:|!|\\?|&#.*?;)[ ]*");
        ArrayList<RichJob> rich_jobs=new ArrayList<>();
        for(int i=0;i<sentences.length;i++){
            RichJob job=new RichJob(thread[i%nb_threads].get_id(),sentences[i]);
            rich_jobs.add(job);
            thread[i%nb_threads].append_job(job);
        }
        for(int i=0;i<thread.length;i++){
            thread[i].run();
        }

        boolean ongoing_job=true;
        while(ongoing_job){
            ongoing_job=false;
            for(RichJob job:rich_jobs){
                if(job.get_status()!=2){
                    ongoing_job=true;
                }
            }
            try {
                Thread.sleep(1000);
            }
            catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
        for(RichJob job:rich_jobs){
            res+=job.get_job_out();
        }

        return res;
    }

    public static BufferedImage make_color_image(int size){
        return make_color_image(size,size);
    }
    public static BufferedImage make_color_image(int size_x, int size_y){
        return make_color_image(-1,size_x,size_y);
    }
    public static BufferedImage make_color_image(int seed, int size_x, int size_y){
        if(seed!=-1)
            seeded_random=new Random(seed);
        BufferedImage img= new BufferedImage(size_x, size_y,BufferedImage.TYPE_INT_RGB);
        Color c;
        for (int x = 0; x < size_x; x++) {
            for (int y = 0; y < size_y; y++) {
                c=new Color(

                        (seed==-1?
                        random.nextInt(256)
                        :seeded_random.nextInt(256)
                        ),
                        (seed==-1?
                                random.nextInt(256)
                                :seeded_random.nextInt(256)
                        ),
                        (seed==-1?
                                random.nextInt(256)
                                :seeded_random.nextInt(256)
                        ));
                img.setRGB(x,y,c.getRGB());
            }
        }
        return img;
    }public static BufferedImage make_faulty_color_image(int size_x, int size_y){
        BufferedImage img= new BufferedImage(size_x, size_y,BufferedImage.TYPE_INT_RGB);
        Color black=new Color(0,0,0);
        Color white=new Color(255,255,255);
        for (int x = 0; x < size_x; x++) {
            for (int y = 0; y < size_y; y++) {
                if(within_question_mark(((float) x)/((float)size_x),((float)y)/((float)size_y)))
                    img.setRGB(x,y,black.getRGB());
                else
                    img.setRGB(x,y,white.getRGB());
            }
        }
        return img;
    }
    private static boolean within_question_mark(float rel_x, float rel_y){
        boolean res=false;
        rel_x=Math.round(rel_x*100f)/100f;
        rel_y=Math.round(rel_y*100f)/100f;
        float circle=(rel_x-0.5f)*(rel_x-0.5f)+(rel_y-0.3f)*(rel_y-0.3f);
        if(circle<=0.08f && circle>=0.05f){
            res=true;
        }
        if(rel_x>=0.48 && rel_x<0.52 && rel_y>=0.55 && rel_y<0.8){
            res=true;
        }
        if(rel_x>=0.48 && rel_x<0.52 && rel_y>=0.9 && rel_y<0.95){
            res=true;
        }
        if(rel_x<0.48f && rel_y>0.3f){
            res=false;
        }

        return res;
    }

    public static void print(BufferedImage img) {
        print(img,"jpg");
    }
    public static void print(BufferedImage img, String suffix) {
        print(img,suffix,"p_");
    }
    public static void print(BufferedImage img, String suffix, String name) {
        try {
            ImageIO.write(img, suffix, new File("res/out/" + name + String.format("%04d", (printer++)) + "." + suffix));
        } catch (Exception e) {
            System.out.println(e);
        }
    }


}
