import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Libro {
    private static Integer nBook = 0;
    public Boolean isFinalVersion;
    public Integer reviews;
    public Integer reads;
    private Integer id;

    public Libro(){
        this.isFinalVersion = false;
        this.reviews = 0;
        this.reads = 0;
        this.id = ++nBook;
    }

    public int getId(){
        return this.id;
    }

    public void write() {
        try{
            TimeUnit.MILLISECONDS.sleep(new Random().nextInt(1));
            this.reviews++;
            if (reviews >= 10){ isFinalVersion = true; }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public Boolean read() {
        try{
            TimeUnit.MILLISECONDS.sleep(new Random().nextInt(1));
            this.reads++;
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            if (isFinalVersion){return true;}
            return false;
        }
    }

    public String toString(){
        return "Libro " + this.id;
    }

}
