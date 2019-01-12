package pkg_rich;

import java.util.ArrayList;

public class RichString {

    public enum WordType{NOUN,VERB,ADJ,ADV,ALL}
    private ArrayList<String> nouns;
    private ArrayList<String> adverbs;
    private ArrayList<String> verbs;
    private ArrayList<String> adjectives;
    private String original;
    private boolean poor_word;
    private String suffix;
    private String hold;
    private boolean capital;
    public RichString(String original){
        nouns=new ArrayList<>();
        verbs=new ArrayList<>();
        adverbs=new ArrayList<>();
        adjectives=new ArrayList<>();
        this.original=original;
        poor_word=true;
        suffix="";
        hold="";
        capital=false;
    }
    public boolean poor_word(){
        return poor_word;
    }
    public String get_original(){
        return original;
    }
    public String add_word(String word, WordType type){
        poor_word=false;
        String res="";
        switch(type){
            case NOUN:nouns.add(word);
                break;
            case VERB:verbs.add(word);
                break;
            case ADJ:adjectives.add(word);
                break;
            case ADV:adverbs.add(word);
                break;
        }
        return res;
    }
    public WordType get_most_likely_word_type(){
        int sn=nouns.size();
        int sv=verbs.size();
        int sr=adverbs.size();
        int sa=adjectives.size();
        WordType res=WordType.NOUN;
        if(sv>=sn && sv>=sr && sv>=sa){
            res=WordType.VERB;
        }
        else if(sr>=sn && sr>=sv && sr>=sa){
            res=WordType.ADV;
        }
        else if(sa>=sn && sa>=sv && sa>=sr){
            res=WordType.ADJ;
        }
        return res;
    }

    public void set_suffix(String s){
        suffix=s;
    }
    public String get_suffix(){
        return suffix;
    }
    public void set_hold(String s){
        hold=s;
    }
    public String get_hold(){
        return hold;
    }
    public String get_word(){
        String res=original;
        if(capital)
            res=res.substring(0, 1).toUpperCase() + res.substring(1);
        return res.replaceAll("_"," ").replaceAll("\\(.*?\\)","");
    }
    public void capitalize(){
        capital=true;
    }
    public String get_word(int pos, WordType type){
        String res="";
        switch(type){
            case NOUN:res=nouns.get(pos);
                break;
            case VERB:res=verbs.get(pos);
                break;
            case ADJ:res=adjectives.get(pos);
                break;
            case ADV:res=adverbs.get(pos);
                break;
            case ALL:
                if(pos<adverbs.size())res=adverbs.get(pos);
                else if(pos<adverbs.size()+adjectives.size())
                    res=adjectives.get(pos-adverbs.size());
                else if(pos<adverbs.size()+adjectives.size()+verbs.size())
                    res=verbs.get(pos-adverbs.size()-adjectives.size());
                else if(pos<adverbs.size()+adjectives.size()+verbs.size()+nouns.size())
                    res=nouns.get(pos-adverbs.size()-adjectives.size()-verbs.size());
                break;
        }
        if(capital)
            res=res.substring(0, 1).toUpperCase() + res.substring(1);
        return res.replaceAll("_"," ").replaceAll("\\(.*?\\)","");
    }
    public int get_size(WordType type){
        int res=0;
        switch(type){
            case NOUN:res=nouns.size();
                break;
            case VERB:res=verbs.size();
                break;
            case ADJ:res=adjectives.size();
                break;
            case ADV:res=adverbs.size();
                break;
            case ALL:res=adverbs.size()+adjectives.size()+verbs.size()+nouns.size();
                break;
        }
        return res;
    }
}
