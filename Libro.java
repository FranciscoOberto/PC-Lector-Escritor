import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Libro {
    private final Object controlRead = new Object();
    private PriorityLock lock;
    private static Integer nBook = 0;
    private Boolean isFinalVersion;
    private Integer reviews;
    private Integer reads;
    private Integer id;

    public Libro(){
        this.lock = new PriorityLock();
        this.isFinalVersion = false;
        this.reviews = 0;
        this.reads = 0;
        this.id = ++nBook;
    }

    public int getId(){
        return this.id;
    }

    public int getReviews(){ return this.reviews;}

    public int getReads(){ return this.reads;}

    public boolean isFinal(){
        return isFinalVersion;
    }

    public void addReads(){
        synchronized (controlRead){
            reads++;
        }
    }

    public String toString(){
        return "Libro ID(" + getId() + ")############( " + + this.reviews + " ; " + this.reads +  " ; " + this.isFinalVersion + " )";
    }


    public void write() {
        lock.writeLock();
        try{
            TimeUnit.MILLISECONDS.sleep(new Random().nextInt(1));
            this.reviews++;
            if (reviews >= 10){ isFinalVersion = true; }
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            lock.writeUnlock();
        }
    }

    public Boolean read() {
        lock.readLock();
        try{
            TimeUnit.MILLISECONDS.sleep(new Random().nextInt(1));
            if (isFinal()){
                addReads();
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            lock.readUnlock();
            return isFinal();
        }
    }

}
