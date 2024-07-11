/*
package com.example.utils;


import ch.qos.logback.core.util.ContextUtil;
import com.alibaba.fastjson.JSON;
import com.example.exception.ExceptionEnum;
import com.example.model.pojo.R;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.DependsOn;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.servlet.AsyncContext;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.function.Supplier;

*/
/**
 * 
 * @author HeJun
 * @date 2021-10-25
 * @Version V1.0
 * @Description 异步任务执行工具类
 *//*

@Slf4j
@Component
@DependsOn("contextUtil")	// 当前这个bean的初始化将依赖于名为contextUtil的bean
public class AsyncTaskUtil {
	
	public static final int PROCESSOR_NUM = Runtime.getRuntime().availableProcessors();
	private static final String EXECUTOR = "MY_EXECUTOR";
	private static final int KEEP_ALIVE_SECONDS = 60;
	public static final String SUCESS_RESP_STR = "执行成功";
	private static final RejectedExecutionHandler MY_REJECTED_EXECUTION_HANDLER = ContextUtil
			.getBean("myRejectedExecutionHandler", RejectedExecutionHandler.class);

	private static volatile Map<String, ThreadPoolExecutor> THREAD_POOL_EXECUTOR_MAP = new ConcurrentHashMap<>();
	private static volatile Map<String, AsyncContext> ASYNC_CONTEXT_MAP = new ConcurrentHashMap<>();
	private static final LinkedBlockingDeque<LongPollingEvent<?>> LONG_POLLING_EVENT_QUEUE = new LinkedBlockingDeque<>();
	private static final ScheduledThreadPoolExecutor TIME_OUT_CHECKER = new ScheduledThreadPoolExecutor(PROCESSOR_NUM,MY_REJECTED_EXECUTION_HANDLER);
	*/
/**
	 * 
	 * @author HeJun
	 * @date 2021-10-25
	 * @Version V1.0
	 * @Description Bean销毁前关闭线程池
	 *//*

	@PreDestroy
	private void destroyExecutor() {
		log.info("关闭线程池。。。。。。。");
		for (ThreadPoolExecutor executor : THREAD_POOL_EXECUTOR_MAP.values()) {
			executor.shutdown();
		}
		TIME_OUT_CHECKER.shutdown();
	}

	@PostConstruct
	private void init() {
		ThreadPoolTaskExecutor threadPoolTaskExecutor = ContextUtil.getBean(EXECUTOR, ThreadPoolTaskExecutor.class);
		if(threadPoolTaskExecutor == null){
			getOrAddThreadPoolExecutor(EXECUTOR,PROCESSOR_NUM,2*PROCESSOR_NUM,500,KEEP_ALIVE_SECONDS,true);
		}else {
			THREAD_POOL_EXECUTOR_MAP.put(EXECUTOR, threadPoolTaskExecutor.getThreadPoolExecutor());
		}
	}

	*/
/**
	 *
	 * @author HeJun
	 * @date 2022-05-06
	 * @Version V1.0
	 * @Description 返回单例定时器线程池
	 * @return
	 *//*

	public static ScheduledThreadPoolExecutor getTimeOutChecker(){
		return TIME_OUT_CHECKER;
	}

	*/
/**
	 * 
	 * @author HeJun
	 * @date 2021-12-18
	 * @Version V1.0
	 * @Description 使用默认线程池执行有返回值的任务，不阻塞当前线程，可抛出异常，队列满则拒绝并抛出异常
	 * @param <T>
	 * @param supplier
	 * @return
	 *//*

	public static <T> CompletableFuture<T> supplyAsync(Supplier<T> supplier) {		
		return supplyAsync(supplier, EXECUTOR, 0, 0, 0, 0,true);
	}
	
	*/
/**
	 * 
	 * @author HeJun
	 * @date 2021年4月28日
	 * @Version V1.0
	 * @Description 使用指定参数的线程池执行有返回值的任务，不阻塞当前线程，可抛出异常，用于隔离接口执行任务的线程池，避免单一接口拖垮整个系统，限制并发线程数提高执行效率
	 * @param <T>
	 * @param supplier
	 * @param poolName
	 * @param concurrency
	 * @param maxConcurrency
	 * @param queueSize
	 * @param isRejected 队列满是否拒绝任务，并抛出拒绝任务异常
	 * @return
	 *//*

	public static <T> CompletableFuture<T> supplyAsync(Supplier<T> supplier,String poolName,int concurrency,int maxConcurrency,int queueSize,int keepAliveSeconds,boolean isRejected) {
		if(poolName == null) {
			poolName = EXECUTOR;
		}
		ThreadPoolExecutor threadPoolExecutor = getOrAddThreadPoolExecutor(poolName, concurrency,maxConcurrency, queueSize,keepAliveSeconds,isRejected);

		return CompletableFuture.supplyAsync(supplier, threadPoolExecutor);
	}
	
	*/
/**
	 * 
	 * @author HeJun
	 * @date 2021年9月24日
	 * @Version V1.0
	 * @Description 批量向线程池添加任务，并返回任务的异常信息
	 * @param poolName
	 * @param concurrency
	 * @param maxConcurrency
	 * @param queueSize
	 * @param isRejected
	 * @param sucessRespStr 任务正常运行没有抛出异常时返回的字符串
	 * @param task 返回任务集合的总任务
	 * @param timeoutSeconds 任务超时时间
	 * @return
	 *
	 *//*

	public static List<String> supplyAsyncAllWithExceptionMsgBatch(Supplier<List<Supplier<String>>> task,int timeoutSeconds,String poolName,int concurrency,int maxConcurrency,int queueSize,boolean isRejected,String sucessRespStr) {
		if(sucessRespStr == null) {
			throw new RuntimeException("正确回复模板不能为空");
		}
		List<CompletableFuture<String>> futureList = new ArrayList<>();
		List<String> exMsgList = new ArrayList<>();
		supplyAsync(task).thenAccept(supplierList -> {
			for (Supplier<String> supplier : supplierList) {
				CompletableFuture<String> supplyAsync = supplyAsyncWithExceptionMsg(supplier, poolName, concurrency, maxConcurrency, queueSize, isRejected);
				futureList.add(supplyAsync);
			}
		}).join();
		for (CompletableFuture<String> completableFuture : futureList) {
			String result = null;
			try {
				result = completableFuture.get(timeoutSeconds,TimeUnit.SECONDS);
			} catch (InterruptedException | ExecutionException | TimeoutException e) {
				result = "任务执行超时";
			}
			if(!sucessRespStr.equals(result)) {
				exMsgList.add(result);
			}
		}
		return exMsgList;
	}
	
	*/
/**
	 * 
	 * @author HeJun
	 * @date 2021年9月24日
	 * @Version V1.0
	 * @Description 批量向线程池添加任务，并返回任务的异常信息
	 * @param task
	 * @param sucessRespStr 任务正常运行没有抛出异常时返回的字符串
	 * @return
	 *
	 *//*

	public static List<String> supplyAsyncAllWithExceptionMsgBatch(Supplier<List<Supplier<String>>> task, String sucessRespStr) {
		return supplyAsyncAllWithExceptionMsgBatch(task, 10, EXECUTOR, 0, 0, 0, true, sucessRespStr);
	}
	
	*/
/**
	 * 
	 * @author HeJun
	 * @date 2021年9月24日
	 * @Version V1.0
	 * @Description 提交任务到线程池，并返回异常信息
	 * @param supplier
	 * @param poolName
	 * @param concurrency
	 * @param maxConcurrency
	 * @param queueSize
	 * @param isRejected
	 * @return
	 *
	 *//*

	public static CompletableFuture<String> supplyAsyncWithExceptionMsg(Supplier<String> supplier,String poolName,int concurrency,int maxConcurrency,int queueSize,boolean isRejected){
		return supplyAsync(supplier, poolName, concurrency, maxConcurrency, queueSize, KEEP_ALIVE_SECONDS,isRejected)
				.exceptionally(t -> t.getCause() == null ? t.getMessage() : t.getCause().getMessage());
	}
	
	*/
/**
	 * 
	 * @author HeJun
	 * @date 2020-12-18
	 * @Version V1.0
	 * @Description 使用指定参数的线程池执行无返回值的任务，不阻塞当前线程，不可抛出异常
	 * @param runnable
	 * @return
	 *//*

	public static CompletableFuture<Void> runAsync(Runnable runnable,String poolName,int concurrency,int maxConcurrency,int queueSize,int keepAliveSeconds,boolean isReject) {
		ThreadPoolExecutor threadPoolExecutor = getOrAddThreadPoolExecutor(poolName, concurrency,maxConcurrency, queueSize,keepAliveSeconds,isReject);
		return CompletableFuture.runAsync(runnable, threadPoolExecutor);
	}
	
	*/
/**
	 * 
	 * @author HeJun
	 * @date 2020-12-18
	 * @Version V1.0
	 * @Description 使用默认线程池执行无返回值的任务，不阻塞当前线程，不可抛出异常
	 * @param runnable
	 * @return
	 *//*

	public static CompletableFuture<Void> runAsync(Runnable runnable) {
		return runAsync(runnable,EXECUTOR,0,0,0,0,true);
	}

	*/
/**
	 * 
	 * @author HeJun
	 * @date 2021年4月28日
	 * @Version V1.0
	 * @Description 获得指定名字的线程池，不存在则创建，线程安全
	 * @param poolName
	 * @param concurrency
	 * @param queueSize
	 * @param isRecject 队列已满是否拒绝
	 * @return
	 *//*

	public static ThreadPoolExecutor getOrAddThreadPoolExecutor(String poolName,int concurrency,int maxConcurrency,int queueSize,int keepAliveSecsonds,boolean isRecject) {
		ThreadPoolExecutor threadPoolExecutor = THREAD_POOL_EXECUTOR_MAP.get(poolName);
		if(threadPoolExecutor == null) {
			synchronized (poolName.intern()){
				// 双检锁
				threadPoolExecutor = THREAD_POOL_EXECUTOR_MAP.get(poolName);
				if(threadPoolExecutor == null){
					// 生成一个指定参数表的线程池对象
					RejectedExecutionHandler rejectedExecutionHandler = isRecject ? MY_REJECTED_EXECUTION_HANDLER : new ThreadPoolExecutor.CallerRunsPolicy();
					keepAliveSecsonds = keepAliveSecsonds == 0 ? KEEP_ALIVE_SECONDS : keepAliveSecsonds;
					threadPoolExecutor = new ThreadPoolExecutor(concurrency, maxConcurrency, keepAliveSecsonds, TimeUnit.SECONDS, new LinkedBlockingDeque<>(queueSize), rejectedExecutionHandler);

					// 放入线程池单例map中
					THREAD_POOL_EXECUTOR_MAP.put(poolName, threadPoolExecutor);
				}
			}

		}
		return threadPoolExecutor;
	}

	*/
/**
	 * 
	 * @author HeJun
	 * @date 2020年5月14日
	 * @Version V1.0
	 * @Description 长轮询
	 * @param request
	 * @param requestId
	 * @param timeOutSeconds
	 *//*

	public static void longPolling(HttpServletRequest request, String requestId, long timeOutSeconds) {
		//请求异步域对象
		AsyncContext asyncContext = request.startAsync();
		//关闭长轮询自动超时处理
		asyncContext.setTimeout(0);
		//消息等待map添加异步域对象
		ASYNC_CONTEXT_MAP.put(requestId, asyncContext);		
		//注册长轮询超时未响应兜底任务
		TIME_OUT_CHECKER.schedule(() -> {
			try {
				writePollingResponse(requestId, R.error(ExceptionEnum.POLLING_TIME_OUT));
			} catch (IOException e) {
				log.error(e.getMessage(), e);
			}
		}, timeOutSeconds,TimeUnit.SECONDS);
	}

	*/
/**
	 * 添加长轮询立即响应触发事件
	 * @param <T>
	 * @param requestId
	 * @param task
	 * @return
	 *//*

	public static <T> boolean addLongPollingRespEvent(String requestId, LongPollingTask<T> task) {
		return LONG_POLLING_EVENT_QUEUE.add(new LongPollingEvent<T>(requestId, task));
	}

	*/
/**
	 * 长轮询触发响应事件类
	 * @param <T>
	 *//*

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	static class LongPollingEvent<T> {
		// AsyncContext对象id
		private String requestId;
		// 待处理内容的处理任务
		private LongPollingTask<T> task;
	}

	*/
/**
	 * 待处理内容的处理任务接口
	 * @param <T>
	 *//*

	public static interface LongPollingTask<T> {
		R<T> execute();
	}

	*/
/**
	 *
	 * @author HeJun
	 * @date 2021年4月28日
	 * @Version V1.0
	 * @Description 获得指定名字的线程池，不存在则创建，线程安全
	 * @param requestId
	 * @param resp
	 * @return
	 *//*

	private static void writePollingResponse(String requestId, R<?> resp) throws IOException {
		if (ASYNC_CONTEXT_MAP.get(requestId) != null) {
			synchronized (requestId.intern()){
				AsyncContext asyncContext = ASYNC_CONTEXT_MAP.get(requestId);
				if (asyncContext != null) {
					log.info("事件触发长轮询回复----,requestId={},resp={}",requestId,resp);
					ServletResponse response = asyncContext.getResponse();
					response.setContentType("application/json;charset=utf-8");
					response.setCharacterEncoding("utf-8");
					response.getWriter().append(JSON.toJSONString(resp));
					asyncContext.complete();
					ASYNC_CONTEXT_MAP.remove(requestId);
				}else {
					log.info("长轮询已回复，超时回复终止----,requestId={},resp={}",requestId,resp);
				}
			}
		}else {
			log.info("长轮询已回复，超时回复终止----,requestId={},resp={}",requestId,resp);
		}
	}

	*/
/**
	 * 长轮询事件触发后的处理守护线程，监听长轮询立即回复事件队列
	 *//*

	static {
		Thread thread = new Thread(() -> {		
			while (true) {
				try {
					LongPollingEvent pollingEvent = LONG_POLLING_EVENT_QUEUE.take();
					runAsync(() -> {
						try {
							R<?> resp = pollingEvent.getTask().execute();
							writePollingResponse(pollingEvent.getRequestId(), resp);
						} catch (Exception e) {
							log.error(e.getMessage(), e);
						}
					});
				} catch (InterruptedException e) {
					log.error(e.getMessage(), e);
				}
			}
		});
		thread.setDaemon(true);
		thread.start();
	}
}


*/
