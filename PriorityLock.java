import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class PriorityLock{
    private ReentrantReadWriteLock lock;
    private Semaphore semaphore;

    public PriorityLock(){
        lock = new ReentrantReadWriteLock();
        semaphore = new Semaphore(10);
    }

    public void lockWriter(){
        try{
            semaphore.acquire(1);
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            lock.writeLock().lock();
        }
    }

    public void lockReader(){
        try{
            semaphore.acquire(10);
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            lock.readLock().lock();
            semaphore.release(10);
        }
    }

    public void unlockWriter(){
        lock.writeLock().unlock();
        semaphore.release(1);
    }

    public void unlockReader(){
        lock.readLock().unlock();
    }
}
