import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Libro {
    private ReentrantReadWriteLock lock;
    private static Integer nBook = 0;
    private Boolean isFinalVersion;
    private Integer reviews;
    private Integer reads;
    private Integer id;

    public Libro(){
        this.lock = new ReentrantReadWriteLock();
        this.isFinalVersion = false;
        this.reviews = 0;
        this.reads = 0;
        this.id = ++nBook;
    }

    public int getId(){
        return this.id;
    }

    public void write() {
        lock.writeLock().lock();
        try{
            TimeUnit.MILLISECONDS.sleep(new Random().nextInt(1));
            this.reviews++;
            if (reviews >= 10){ isFinalVersion = true; }
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            lock.writeLock().unlock();
        }
    }

    public Boolean read() {
        lock.readLock().lock();
        try{
            TimeUnit.MILLISECONDS.sleep(new Random().nextInt(1));
            this.reads++;
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            lock.readLock().unlock();
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
