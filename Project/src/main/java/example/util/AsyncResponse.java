package example.util;

import java.util.concurrent.*;

public class AsyncResponse<V> implements Future<V> {


    private static final long BLOCK_INDEFINITELY = 0;
    private V value;
    private Exception executionException;
    private boolean isCompletedExceptionally = false;
    private boolean isCancelled = false;
    private boolean isDone = false;
    private long checkCompletedInterval = 100;


    public AsyncResponse() {

    }

    public AsyncResponse(V val) {
        this.value = val;
        this.isDone = true;
    }

    public AsyncResponse(Throwable ex) {
        this.executionException = new ExecutionException(ex);
        this.isCompletedExceptionally = true;
        this.isDone = true;
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        this.isCancelled = true;
        this.isDone = true;

        return false;
    }

    @Override
    public boolean isCancelled() {
        return this.isCancelled;
    }

    public boolean isCompletedExceptionally() {
        return this.isCompletedExceptionally;
    }

    @Override
    public boolean isDone() {
        return this.isDone;
    }

    @Override
    public V get() throws InterruptedException, ExecutionException {

        block(BLOCK_INDEFINITELY);

        if (isCancelled()) {
            throw new CancellationException();
        }
        if (isCompletedExceptionally()) {
            throw new ExecutionException(this.executionException);
        }
        if (isDone()) {
            return this.value;
        }

        throw new InterruptedException();
    }

    @Override
    public V get(long timeout, TimeUnit unit)
            throws InterruptedException, ExecutionException, TimeoutException {

        long timeoutInMillis = unit.toMillis(timeout);
        block(timeoutInMillis);

        if (isCancelled()) {
            throw new CancellationException();
        }
        if (isCompletedExceptionally()) {
            throw new ExecutionException(this.executionException);
        }
        if (isDone()) {
            return this.value;
        }

        throw new InterruptedException();
    }

    public boolean complete(V val) {
        this.value = val;
        this.isDone = true;

        return true;
    }

    public boolean completeExceptionally(Throwable ex) {
        this.value = null;
        this.executionException = new ExecutionException(ex);
        this.isCompletedExceptionally = true;
        this.isDone = true;

        return true;
    }

    public void setCheckCompletedInterval(long millis) {
        this.checkCompletedInterval = millis;
    }

    private void block(long timeout) throws InterruptedException {
        long start = System.currentTimeMillis();

        // Block until done, cancelled, or the timeout is exceeded
        while (!isDone() && !isCancelled()) {
            if (timeout > BLOCK_INDEFINITELY) {
                long now = System.currentTimeMillis();
                if (now > start + timeout) {
                    break;
                }
            }
            Thread.sleep(checkCompletedInterval);
        }
    }
}