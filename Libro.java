import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Libro {
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

    public void write() {
        lock.getWriteLock().lock();
        try{
            TimeUnit.MILLISECONDS.sleep(new Random().nextInt(1));
            this.reviews++;
            if (reviews >= 10){ isFinalVersion = true; }
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            lock.getWriteLock().unlock();
        }
    }

    public Boolean read() {
        lock.getReadLock().lock();
        try{
            TimeUnit.MILLISECONDS.sleep(new Random().nextInt(1));
            if(isFinal()){this.reads++;}
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            lock.getReadLock().unlock();
            return isFinal();
        }
    }

    public Boolean isFinal(){
        return isFinalVersion;
    }

    public String toString(){
        return "Libro ID(" + this.id + ")############( " + + this.reviews + " ; " + this.reads +  " ; " + this.isFinalVersion + " )";
    }

}
