import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class PriorityLock {
    private ReentrantReadWriteLock lock;
    private Integer writers;
    private Object controlWriters;

    public PriorityLock(){
        lock = new ReentrantReadWriteLock();
        writers = 0;
        controlWriters = new Object();
    }

    public void writeLock(){
        addWriter();
        lock.writeLock().lock();
    }

    public void readLock(){
        if (writersEmpty()){
            if (!lock.readLock().tryLock()){
                System.out.println("No se pudo lockear el reader");
                try{
                    TimeUnit.SECONDS.sleep(1);
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    readLock();
                }
            }
        }else{
            try{
                TimeUnit.SECONDS.sleep(1);
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                readLock();
            }
        }
    }

    public void writeUnlock(){
        lock.writeLock().unlock();
        removeWriter();
    }

    public void readUnlock(){
        lock.readLock().unlock();
    }

    public void addWriter(){
        synchronized (controlWriters){
            writers++;
        }
    }

    public void removeWriter(){
        synchronized (controlWriters){
            writers--;
        }
    }

    public boolean writersEmpty(){
        synchronized (controlWriters){
            if (writers == 0){
                return true;
            }else {
                return false;
            }
        }
    }


}
