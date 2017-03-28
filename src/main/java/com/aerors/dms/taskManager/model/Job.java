package com.aerors.dms.taskManager.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.aerors.dms.enumerate.Constant.JOB_CMD;
import com.aerors.dms.enumerate.Constant.JOB_RESULT;
import com.aerors.dms.enumerate.Constant.JOB_RUN_TYPE;
import com.aerors.dms.enumerate.Constant.JOB_STATUS;
/**
 * @工程: dmsWeb
 * @包名: ccom.aerors.th.taskManager.model
 * @描述: 任务描述类
 * @作者: yd
 * @版本: V1.0
 * @时间: 2017/3/18 1
 */
public class Job {
	
	private int id;
	private String jobName;
	private int jobType;
	private int creatorId;
	private Timestamp createTime;
	private Timestamp startTime;
	private Timestamp endTime;
	private int flowId;
	private int priority;
	private Float step;
	private String currentModule;
	private Integer status;
	private Integer result;
	private String failReason;
	private String description;
	private Integer isPlan;
	private Timestamp planStartTime;
	private String readerIds;
	private String inDir;
	private String outDir;
	private Integer dataId;
	private String failDir;
	private String successDir;
	
	private String orderID;
	private String taskID;
	private String jobSource;
	

	public String getJobSource() {
		return jobSource;
	}

	public void setJobSource(String jobSource) {
		this.jobSource = jobSource;
	}

	public String getOrderID() {
		return orderID;
	}

	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}

	public String getTaskID() {
		return taskID;
	}

	public void setTaskID(String taskID) {
		this.taskID = taskID;
	}

	// 冗余：任务类型名称
	private String jobTypeName;

	// 冗余：任务流程
	private Flow flow;

	// 冗余：创建时间字符串
	private String createTimeStr;

	// 冗余：结束时间字符串
	private String endTimeStr;

	// 冗余：开始时间字符串
	private String startTimeStr;

	// 冗余：进度字符串
	private String stepStr;
	
	// 冗余：状态字符串
	private String statusStr;

	// 冗余：根据当前状态可执行的操作码列表
	private ArrayList<JOB_CMD> opterateList;
	
	// 冗余：操作字符串列表
	private ArrayList<String> operateStrList;

	

	private SimpleDateFormat dsf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/**
	 * 根据状态获取可操作的操作码列表
	 * 
	 * @param status
	 * @return
	 */
	public static ArrayList<JOB_CMD> getArrivableOpterateCode(int status) {
		ArrayList<JOB_CMD> list = new ArrayList<JOB_CMD>();
		switch (JOB_STATUS.getEnumByValue(status)) {
		case CREATED:
			list.add(JOB_CMD.STARTJOB);
			list.add(JOB_CMD.CANCELJOB);
			break;
		case READY:
			list.add(JOB_CMD.PAUSEJOB);
			list.add(JOB_CMD.STOPJOB);
			list.add(JOB_CMD.CANCELJOB);
			break;
		case RUNNING:
			list.add(JOB_CMD.RESTARTJOB);
			list.add(JOB_CMD.PAUSEJOB);
			list.add(JOB_CMD.STOPJOB);
			list.add(JOB_CMD.CANCELJOB);
			break;
		case PAUSED:
			list.add(JOB_CMD.RESTARTJOB);
			list.add(JOB_CMD.RESUMEJOB);
			list.add(JOB_CMD.STOPJOB);
			list.add(JOB_CMD.CANCELJOB);
			break;
		case STOPPED:
			list.add(JOB_CMD.RESTARTJOB);
			list.add(JOB_CMD.CANCELJOB);
			break;
		case FINISH:
			list.add(JOB_CMD.RESTARTJOB);
			list.add(JOB_CMD.CANCELJOB);
			break;
		case WAITING:
			list.add(JOB_CMD.STOPJOB);
			list.add(JOB_CMD.CANCELJOB);
			break;
		default:
			break;
		}
		return list;
	}

	/**
	 * 获取计划任务STR
	 * 
	 * @param isPlan
	 * @return
	 */
	public static String getIsPlanStr(int isPlan) {
		JOB_RUN_TYPE typeEnum = JOB_RUN_TYPE.getEnumByValue(isPlan);
		return typeEnum.toString();
	}

	/**
	 * 获取任务结果STR
	 * 
	 * @param result
	 * @return
	 */
	public static String changeResult(Integer result) {
		JOB_RESULT resultEnum = JOB_RESULT.getEnumByValue(result);
		return resultEnum.toString();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public int getJobType() {
		return jobType;
	}

	public void setJobType(int jobType) {
		this.jobType = jobType;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
		if (createTime != null) {
			this.setCreateTimeStr(dsf.format(createTime));
		}
	}

	public Timestamp getStartTime() {
		return startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
		if (startTime != null) {
			this.setStartTimeStr(this.dsf.format(startTime));
		}
	}

	public Timestamp getEndTime() {
		return endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
		if (endTime != null) {
			this.setEndTimeStr(this.dsf.format(endTime));
		}
	}

	public int getFlowId() {
		return flowId;
	}

	public void setFlowId(int flowId) {
		this.flowId = flowId;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public Float getStep() {
		return step;
	}

	public void setStep(Float step) {
		this.step = step;
		if (step != null) {
			float f = step * 100;
			this.setStepStr(f + " %");
		}
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
		this.setOpterateList(getArrivableOpterateCode(status));
		this.setStatusStr(JOB_STATUS.getEnumByValue(this.status).toString());
	}

	public Integer getResult() {
		return result;
	}

	public void setResult(Integer result) {
		this.result = result;
		if(this.result == null){
		}
	}

	public String getFailReason() {
		return failReason;
	}

	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getIsPlan() {
		return isPlan;
	}

	public void setIsPlan(Integer isPlan) {
		this.isPlan = isPlan;
	}

	public String getJobTypeName() {
		return jobTypeName;
	}

	public void setJobTypeName(String jobTypeName) {
		this.jobTypeName = jobTypeName;
	}

	public Flow getFlow() {
		return flow;
	}

	public void setFlow(Flow flow) {
		this.flow = flow;
	}

	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

	public String getEndTimeStr() {
		return endTimeStr;
	}

	public void setEndTimeStr(String endTimeStr) {
		this.endTimeStr = endTimeStr;
	}

	public String getStartTimeStr() {
		return startTimeStr;
	}

	public void setStartTimeStr(String startTimeStr) {
		this.startTimeStr = startTimeStr;
	}

	public String getStepStr() {
		if (this.step == 0) {
			this.stepStr = "0%";
		} else if (this.step == 1.00) {
			this.stepStr = "100%";
		} else {
			this.stepStr = new BigDecimal(this.step)
					.setScale(2, RoundingMode.HALF_UP)
					.multiply(
							new BigDecimal(100).setScale(2,
									RoundingMode.HALF_UP)).intValue()
					+ "%";
		}
		return stepStr;
	}

	public void setStepStr(String stepStr) {
		this.stepStr = stepStr;
	}

	public ArrayList<JOB_CMD> getOpterateList() {
		return opterateList;
	}

	public void setOpterateList(ArrayList<JOB_CMD> opterateList) {
		this.opterateList = opterateList;
		ArrayList<String> operateStrList = new ArrayList<String>();
		for (JOB_CMD cmd : opterateList) {
			operateStrList.add(cmd.toString());
		}
		this.setOperateStrList(operateStrList);
	}

	public String getResultStr() {
		if (this.getResult() != null) {
			return changeResult(this.getResult());
		}
		return null;
	}

	public int getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(int creatorId) {
		this.creatorId = creatorId;
	}

	public String getCurrentModule() {
		return currentModule;
	}

	public void setCurrentModule(String currentModule) {
		this.currentModule = currentModule;
	}

	public Timestamp getPlanStartTime() {
		return planStartTime;
	}

	public void setPlanStartTime(Timestamp planStartTime) {
		this.planStartTime = planStartTime;
	}

	public String getReaderIds() {
		return readerIds;
	}

	public void setReaderIds(String readerIds) {
		this.readerIds = readerIds;
	}

	

	public String getInDir() {
		return inDir;
	}

	public void setInDir(String inDir) {
		this.inDir = inDir;
	}

	public String getOutDir() {
		return outDir;
	}

	public void setOutDir(String outDir) {
		this.outDir = outDir;
	}

	public String getStatusStr() {
		return statusStr;
	}

	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}

	public ArrayList<String> getOperateStrList() {
		return operateStrList;
	}

	public void setOperateStrList(ArrayList<String> operateStrList) {
		this.operateStrList = operateStrList;
	}

	public Integer getDataId() {
		return dataId;
	}

	public void setDataId(Integer dataId) {
		this.dataId = dataId;
	}

	public String getFailDir() {
		return failDir;
	}

	public void setFailDir(String failDir) {
		this.failDir = failDir;
	}

	public String getSuccessDir() {
		return successDir;
	}

	public void setSuccessDir(String successDir) {
		this.successDir = successDir;
	}

}
