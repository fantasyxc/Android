package cn.eoe.wiki.utils;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadPoolUtil {
	public static final ExecutorService pool;
	static {
		pool = Executors.newCachedThreadPool();
	}
	
	public static void execute (Runnable command) {
		pool.execute(command);
	}
	
	public static <T> Future<T> submit(Callable<T> task) {
		return pool.submit(task);
	}
	
	public static Future<?> submit(Runnable task) {
		return pool.submit(task);
	}
	
	public static <T> Future<T> submit(Runnable task, T result) {
		return pool.submit(task, result);
	}
}
