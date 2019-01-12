package pkg_placs;

public class PlaCS_App {

    PlaCS_Window window;
    public PlaCS_App(){

    }
    public void run(){
        PlaCS_Logic.init();
        window=new PlaCS_Window();

    }
}
