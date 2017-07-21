package edu.utdallas.blockingFIFO;

import edu.utdallas.taskExecutor.Task;

public class BlockingFIFO {

	private int bufferSize;     	
	private  Task[] blockingFIFOqueue; 
	private int nextin; // nextin = rear of the queue, 
	private int nextout; // nextout = front of the queue
	private int count;
	private  Object notempty; 
	private  Object notfull;

	// Initialize blockingFIFO with maxBufferSize
	public BlockingFIFO(int maxBufferSize) {
		this.bufferSize = maxBufferSize;
		this.blockingFIFOqueue = new Task[maxBufferSize];
		
		notempty = new Object();
		notfull =  new Object();
		nextin = nextout = count = 0;
	}

	// append the task to rear of blockingFIFOqueue (enqueue the task)
	public void append(Task task) throws InterruptedException {

		while (true) {
			if (count == bufferSize){          
				synchronized (notfull) {
					notfull.wait();
				}
			}
			synchronized (this) {
				if (count == bufferSize) {
					continue;
				}
				blockingFIFOqueue[nextin] = task;
				nextin = (nextin + 1) % bufferSize;
				count++;
				synchronized (notempty) {
					notempty.notify();  
				}
				return;
			}

		}

	}

	// take the task from front of the blockingFIFOqueue (dequeue the task)
	public Task take() throws InterruptedException {
		
		while (true) {
			if (count == 0) {               
				synchronized (notempty) {	
					notempty.wait();
				}
			}
			synchronized (this) {
				if (count == 0) {
					continue;
				}
				Task task = blockingFIFOqueue[nextout];
				nextout = (nextout + 1) % bufferSize;
				count--; 					
				synchronized (notfull) {
					notfull.notify();       
				}
				return task;
			}

		}
	}

}

