package edu.utdallas.taskExecutorImpl;

import edu.utdallas.blockingFIFO.BlockingFIFO;
import edu.utdallas.taskExecutor.Task;
import edu.utdallas.taskExecutor.TaskExecutor;
import edu.utdallas.taskRunner.TaskRunner;

public class TaskExecutorImpl implements TaskExecutor {

	BlockingFIFO blockingFIFO;
	private int threadPoolSize;

	public TaskExecutorImpl(int threadPoolSize) {
		this.threadPoolSize = threadPoolSize;
		blockingFIFO = new BlockingFIFO(100);  // blockFIFO queue max size
		for (int i = 0; i < threadPoolSize; i++) {
			String name = "TaskThread" + i;
			TaskRunner newTask = new TaskRunner(blockingFIFO);
			Thread thread = new Thread(newTask);
			thread.setName(name);
			thread.start();
			
			//Thread.yield();
		}
	}

	@Override
	public void addTask(Task task) {
		// TODO Complete the implementation
		try {
			blockingFIFO.append(task);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
