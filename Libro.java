import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Libro {
    private final Object controlReader = new Object();
    private final Object controlTotalReader = new Object();
    private PriorityLock lock;
    private static Integer nBook = 0;
    private Boolean isFinalVersion;
    private Integer reviews;
    private Integer reads;
    private Integer totalReads;
    private Integer id;

    public Libro(){
        this.lock = new PriorityLock();
        this.isFinalVersion = false;
        this.reviews = 0;
        this.reads = 0;
        this.totalReads = 0;
        this.id = ++nBook;
    }

    public int getId(){
        return this.id;
    }

    public int getReads(){
        return reads;
    }

    public Boolean isFinal(){
        return isFinalVersion;
    }

    public void addRead(){
        synchronized (controlReader){
            reads++;
        }
    }

    public void addTotalRead(){
        synchronized (controlTotalReader){
            totalReads++;
        }
    }

    public String toString(){
        return "Libro ID(" + getId() + ")############( writes: " + this.reviews + " ; reads: " + getReads() + " ; totalReads: " + this.totalReads +  " ; finalVersion: " + this.isFinalVersion + " )";
    }

    public void write() {
        lock.getWriteLock().lock();
        try{
            new Log().addMessage("El " + Thread.currentThread().toString() + " esta ESCRIBIENDO a las: " + System.currentTimeMillis());
            TimeUnit.MILLISECONDS.sleep(new Random().nextInt(100));
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
            new Log().addMessage("El " + Thread.currentThread().toString() + " esta LEYENDO a las: " + System.currentTimeMillis());
            TimeUnit.MILLISECONDS.sleep(new Random().nextInt(100));
            if(isFinal()){addRead();}
            addTotalRead();
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            lock.getReadLock().unlock();
            return isFinal();
        }
    }

}
