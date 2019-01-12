package pkg_rich;

public class RichJob {

    private int id;
    private static int base_id=0;
    String job_in;
    String job_out;
    int status;// 0: pending
                //1: active
                //2: done
    int thread;
    public RichJob(int thread, String job){
        this.job_in=job;
        job_out="";
        this.thread=thread;
        status=0;
        id=base_id++;
    }

    public int get_id() {
        return id;
    }

    public String get_job_in() {
        return job_in;
    }


    public String get_job_out() {
        return job_out;
    }

    public void set_job_out(String job) {
        job_out=job;
    }


    public int get_status() {
        return status;
    }
    //0: pending
    //1: active
    //2: done
    public void set_status(int status) {
        this.status = status;
    }

    public int get_thread() {
        return thread;
    }

}
