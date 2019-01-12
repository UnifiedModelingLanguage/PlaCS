package pkg_placs;

import pkg_rich.RichJob;
import pkg_rich.RichString;
import pkg_rich.RichUtil;

import java.util.ArrayList;
import java.util.Random;

public class PlaCS_SentenceThread implements Runnable{

    private Random random=new Random();
    private int id;
    private static int base_id=0;
    private static String query_path="res/IO_buffer/query";
    private static String result_path="res/IO_buffer/result";
    private static String xslt_path="res/PlaCS";
    private ArrayList<RichJob> jobs=new ArrayList<>();
    private int cur_job=0;

    public PlaCS_SentenceThread(){
        id=base_id++;
    }

    @Override
    public void run() {

        while(jobs.size() > cur_job) {

            if (jobs.get(cur_job).get_status() == 0) {
                String result = "";

                jobs.get(cur_job).set_status(1);
                String[] words = jobs.get(cur_job).get_job_in().split("[ ]*( |,)[ ]*");
                RichString[] rich_sentence = new RichString[words.length];
                for (int j = 0; j < words.length; j++) {

                    String word = words[j].toLowerCase();
                    String original = word;
                    String[] hold = {"", "y", "", "", "y", "", "", ""};
                    String[] suffixes = {"ing", "ied", "ed", "d", "ies", "es", "s", "'s"};
                    rich_sentence[j] = make_rich_sentence(word, query(word));
                    for (int k = 0; k < suffixes.length; k++) {
                        if (rich_sentence[j].poor_word() && original.length() > 3) {
                            if (original.endsWith(suffixes[k])) {
                                word = original.substring(0, original.length() - suffixes[k].length()) + hold[k];
                                rich_sentence[j] = make_rich_sentence(word, query(word));
                                if (!rich_sentence[j].poor_word()) {
                                    rich_sentence[j].set_suffix(suffixes[k]);
                                    rich_sentence[j].set_hold(hold[k]);
                                }
                            }
                        } else {
                            break;
                        }
                    }
                    if (rich_sentence[j].poor_word()) {
                        rich_sentence[j] = new RichString(original);
                    }
                }
                boolean one_verb = true;//false;//a sentence should have a verb
                boolean one_noun = true;//false;//a sentence should have a noun
                for (int j = 0; j < rich_sentence.length; j++) {
                    if (j == 0) {
                        rich_sentence[j].capitalize();
                    }
                    String complete_word = "";
                    RichString.WordType type = rich_sentence[j].get_most_likely_word_type();
                    if (!one_verb && rich_sentence[j].get_size(RichString.WordType.VERB) > 0) {
                        type = RichString.WordType.VERB;
                        complete_word = rich_sentence[j].get_word(random.nextInt(rich_sentence[j].get_size(type)), type);
                    } else if (!one_noun && rich_sentence[j].get_size(RichString.WordType.NOUN) > 0) {
                        type = RichString.WordType.NOUN;
                        complete_word = rich_sentence[j].get_word(random.nextInt(rich_sentence[j].get_size(type)), type);

                    } else if (rich_sentence[j].poor_word()) {
                        complete_word = rich_sentence[j].get_original();
                    } else {
                        int pos = random.nextInt(rich_sentence[j].get_size(RichString.WordType.ALL));
                        complete_word = rich_sentence[j].get_word(pos, RichString.WordType.ALL);
                    }
                    if (type == RichString.WordType.NOUN) {
                        one_noun = true;
                    } else if (type == RichString.WordType.VERB) {
                        one_verb = true;
                    }
                    result += complete_word;
                    if (j < rich_sentence.length - 1) {
                        result += ' ';
                    }
                }
                result += RichUtil.punctuate(random);
                jobs.get(cur_job).set_job_out(result);
                jobs.get(cur_job).set_status(2);
                cur_job++;
            } else if (jobs.get(cur_job).get_status() == 1) {
                System.out.println("bad thread job=1");
            } else if (jobs.get(cur_job).get_status() == 2) {
                System.out.println("bad thread job=2");
            }
        }
    }
    public void append_job(RichJob job){
        jobs.add(job);
    }
    public void refresh(){
        jobs.clear();
        cur_job=0;
    }
    public int get_id(){
        return id;
    }

    private String query(String word){
        String query="<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                +"<?xml-stylesheet type=\"text/xsl\" href=\"PlaCS.xsl\"?>"
                +"<query>"+word+"</query>";
        RichUtil.write(query_path+id+".xml",query);
        BasicXsl.xsl(query_path+id+".xml", result_path+id+".xml", xslt_path+".xsl");
        return RichUtil.read(result_path+id+".xml");
    }

    private RichString make_rich_sentence(String word, String db_res){
        RichString s=new RichString(word);
        db_res = db_res.replaceAll("(\\r|\\n)", "");
        String[] types=db_res.split("\\*(adverb|verb|adjective|noun)\\|");
        for(int k=1;k<types.length;k++){
            RichString.WordType type;
            switch(k){
                case 1:
                    type=RichString.WordType.ADV;
                    break;
                case 2:
                    type=RichString.WordType.VERB;
                    break;
                case 3:
                    type=RichString.WordType.ADJ;
                    break;
                default:
                    type=RichString.WordType.NOUN;
                    break;
            }
            String[] type_res=types[k].split("\\|");
            for(int l=0;l<type_res.length;l++){
                if(type_res[l].trim().length()>0){
                    s.add_word(type_res[l].trim(),type);
                }
            }
        }
        return s;
    }


}
