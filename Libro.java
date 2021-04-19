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
    private final Integer TIME = 200;

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

    public int getReviews(){
        return reviews;
    }

    public int getReads(){
        synchronized (controlReader){
            return reads;
        }
    }

    public int getTotalReads(){return totalReads;}

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
        return "Libro ID(" + getId() + ")############( writes: " + getReviews() + " ; reads: " + getReads() + " ; totalReads: " + this.totalReads +  " ; finalVersion: " + this.isFinalVersion + " )";
    }

    public void write() {
        lock.getWriteLock().lock();
        try{
            TimeUnit.MILLISECONDS.sleep(new Random().nextInt(TIME));
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            this.reviews++;
            if (reviews >= 10){ isFinalVersion = true; }
            lock.getWriteLock().unlock();
            Log.addMessage("El " + Thread.currentThread().toString() + " esta ESCRIBIENDO ");
        }
    }

    public Boolean read() {
        lock.getReadLock().lock();
        try{
            TimeUnit.MILLISECONDS.sleep(new Random().nextInt(TIME));
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            Boolean isFinal = isFinal();
            if(isFinal){addRead();}
            addTotalRead();
            lock.getReadLock().unlock();
            Log.addMessage("El " + Thread.currentThread().toString() + " esta LEYENDO");
            return isFinal;
        }
    }

}
