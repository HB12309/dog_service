package ebing.top.dog.service.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OperateLogThread implements Runnable {

	private final static Logger logger = LoggerFactory.getLogger(OperateLogThread.class);

	private int accountId;
	private int roleId;

	public OperateLogThread(int accountId,
							int roleId
	){
		this.accountId = accountId;
		this.roleId = roleId;
	}


	@Override
	public void run() {

		System.out.println("start run");
		try {
			System.out.println("start save");

		}catch (Exception e){
			logger.warn("登录日志异步记录失败："+e.getMessage());
			e.printStackTrace();
		}

	}
}
