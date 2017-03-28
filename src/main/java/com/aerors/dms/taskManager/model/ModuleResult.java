package com.aerors.dms.taskManager.model;


/**
 * @工程: dmsWeb
 * @包名: ccom.aerors.th.taskManager.model
 * @描述: 任务组件执行结果描述类
 * @作者: yd
 * @版本: V1.0
 * @时间: 2017/3/18 1
 */
public class ModuleResult {
	
	public static final int result_success = 0;
	public static final int result_failure = 1;

	private int result;
	private boolean isSuccess;
	private String failureReason;

	/**
	 * construct<br>
	 * default not success and use input failure reason
	 * 
	 * @param failureReason
	 */
	public ModuleResult(String failureReason) {
		if (!"".equalsIgnoreCase(failureReason)) {
			this.failureReason = failureReason;
			this.isSuccess = false;
			this.result = result_failure;
		} else {
			this.failureReason = null;
			this.isSuccess = true;
			this.result = result_success;
		}
	}

	/**
	 * construct<br>
	 * default success; failureReason = null;
	 */
	public ModuleResult() {
		this.result = result_success;
		this.isSuccess = true;
		this.failureReason = null;
	}

	public int getResult() {
		return result;
	}

	/**
	 * result_success or result_failure
	 * 
	 * @param result
	 * @throws Exception
	 */
	public void setResult(int result) throws IllegalArgumentException {
		if (result != result_success && result != result_failure) {
			throw new IllegalArgumentException(
					"只接受result_success或者result_failure所代表的值");
		}
		this.result = result;
		if (this.result == result_success)
			this.isSuccess = true;
		else
			this.isSuccess = false;
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getFailureReason() {
		return failureReason == null ? "未知原因" : failureReason;
	}

	public void setFailureReason(String failureReason)
			throws IllegalArgumentException {
		if ("".equalsIgnoreCase(failureReason) || failureReason == null) {
			throw new IllegalArgumentException("failure reason can't not null");
		}
		this.failureReason = failureReason;
	}

}
