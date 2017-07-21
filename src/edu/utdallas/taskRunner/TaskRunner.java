package edu.utdallas.taskRunner;

import edu.utdallas.blockingFIFO.BlockingFIFO;
import edu.utdallas.taskExecutor.Task;

public class TaskRunner extends Thread {
	BlockingFIFO blockingFifoQueue;

	public TaskRunner(BlockingFIFO fifoqueue) {
		super();
		blockingFifoQueue = fifoqueue;
	}
	
	@Override
	public void run() {
		while (true) {
			try {
				Task newTask = blockingFifoQueue.take();
				newTask.execute();
			}
			catch (Throwable th) {
			}
		}
	}

}
