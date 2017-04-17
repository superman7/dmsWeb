package com.aerors.dms.enumerate;

import java.util.HashMap;
/**
 * @工程: dmsWeb
 * @包名: ccom.aerors.th.common.model
 * @描述: 软件常用常量描述类
 * @作者: yd
 * @版本: V1.0
 * @时间: 2017/3/18 1
 */
public class Constant {
	
	/**
	 * 登陆用户
	 */
	public static final String SESSION_ADMIN = "session_admin";
	/**
	 * 登陆用户的权限
	 */
	public static final String SESSION_ADMIN_RIGHTS = "session_admin_rights";
	public static final String SESSION_ADMIN_ROLE = "session_admin_role";
	/** 是否有子节点:0表示无，1表示有 */
	public static final int MENU_IS_CHILD_NO = 0;
	public static final int MENU_IS_CHILD_YES = 1;
	/**数据核心存储区**/
	public static final String DATA_CORE_STORAGE="data_core_storage";
	/**浏览图拇指图推送区**/
	public static final String PUSH_FILE_STORAGE="push_file_storage";
	public static final String SESSION_ADMIN_isDel ="session_admin_isdel";
	//SystemConfigConstant 设置systemConfig中的常量
	public static final String MAX_JOB_NUMBER_KEY = "maxJobNumber";
	public static final String DEFAULT_DATA_IN_FLOW = "dataInFlow";
	public static final String DEFAULT_DATA_OUT_FLOW = "dataOutFlow";
	public static final String DEFAULT_DATA_QUERY_FLOW = "dataQueryFlow";
	public static final String DEFAULT_DATA_SEARCH_FLOW = "dataSearchFlow";
	
	/**
	 * job status<br/>
	 * <br/>
	 * <b>行为定义<b>
	 * <table>
	 * <tr>
	 * <th>&nbsp;\</th>
	 * <th>名称</th>
	 * <th>状态类型</th>
	 * <th>描述</th>
	 * </tr>
	 * <tr>
	 * <td>CREATED</td>
	 * <td>已创建</td>
	 * <td>持久状态</td>
	 * <td>刚创建完成的任务处于此状态</td>
	 * </tr>
	 * <tr>
	 * <td>READY</td>
	 * <td>就绪</td>
	 * <td>持久状态</td>
	 * <td>当某一任务被命令开始时并且当前系统已达到最大负载<br/>
	 * 则该任务被置于此状态</td>
	 * </tr>
	 * <tr>
	 * <td>RUNNING</td>
	 * <td>正在运行</td>
	 * <td>持久状态</td>
	 * <td>正在执行中的任务处于此状态</td>
	 * </tr>
	 * <tr>
	 * <td>PAUSED</td>
	 * <td>暂停</td>
	 * <td>持久状态</td>
	 * <td>正在执行中的任务处于此状态</td>
	 * </tr>
	 * <tr>
	 * <td>STOPPED</td>
	 * <td>停止</td>
	 * <td>持久状态</td>
	 * <td>运行结束或者被迫停止运行后处于此状态</td>
	 * </tr>
	 * <tr>
	 * <td>FINISH</td>
	 * <td>完成</td>
	 * <td>持久状态</td>
	 * <td>运行结束或者被迫停止运行后当任务进度完成时处于此状态</td>
	 * </tr>
	 * <tr>
	 * <td>PENDING</td>
	 * <td>状态转化中</td>
	 * <td>瞬时状态</td>
	 * <td>当调度系统尝试改变任务状态时，任务有可能处于此状态</td>
	 * </tr>
	 * <tr>
	 * <td>WAITING</td>
	 * <td>计划任务等待执行中</td>
	 * <td>持久状态</td>
	 * <td>只有计划任务应才会有的状态，表示当前该计划任务已经被执行开始操作<br/>
	 * 并且尚未到指定的开始时间且正在等待被执行的过程中</td>
	 * </tr>
	 * </table>
	 * <br>
	 * <b>直接转化规则<br>
	 * </b> &nbsp;&nbsp;&nbsp;&nbsp;列表示目的状态；行表示起始状态
	 * <table border>
	 * <tr>
	 * <th>\</th>
	 * <th>CREATED</th>
	 * <th>READY</th>
	 * <th>RUNNING</th>
	 * <th>PAUSED</th>
	 * <th>STOPPED</th>
	 * <th>FINISH</th>
	 * <th>PENDING</th>
	 * <th>WAITING</th>
	 * </tr>
	 * <tr>
	 * <td>CREATED</td>
	 * <td>\</td>
	 * <td>是</td>
	 * <td>是</td>
	 * <td>否</td>
	 * <td>否</td>
	 * <td>否</td>
	 * <td>是</td>
	 * <td>是</td>
	 * </tr>
	 * <tr>
	 * <td>READY</td>
	 * <td>否</td>
	 * <td>\</td>
	 * <td>是</td>
	 * <td>否</td>
	 * <td>否</td>
	 * <td>否</td>
	 * <td>是</td>
	 * <td>否</td>
	 * </tr>
	 * <tr>
	 * <td>RUNNING</td>
	 * <td>否</td>
	 * <td>否</td>
	 * <td>\</td>
	 * <td>是</td>
	 * <td>是</td>
	 * <td>是</td>
	 * <td>是</td>
	 * <td>否</td>
	 * </tr>
	 * <tr>
	 * <td>PAUSED</td>
	 * <td>否</td>
	 * <td>是</td>
	 * <td>是</td>
	 * <td>\</td>
	 * <td>是</td>
	 * <td>否</td>
	 * <td>是</td>
	 * <td>是</td>
	 * </tr>
	 * <tr>
	 * <td>STOPPED</td>
	 * <td>否</td>
	 * <td>是</td>
	 * <td>是</td>
	 * <td>否</td>
	 * <td>\</td>
	 * <td>否</td>
	 * <td>是</td>
	 * <td>否</td>
	 * </tr>
	 * <tr>
	 * <td>FINISH</td>
	 * <td>否</td>
	 * <td>是</td>
	 * <td>是</td>
	 * <td>否</td>
	 * <td>否</td>
	 * <td>\</td>
	 * <td>是</td>
	 * <td>是</td>
	 * </tr>
	 * <tr>
	 * <td>PENDING</td>
	 * <td>否</td>
	 * <td>是</td>
	 * <td>是</td>
	 * <td>是</td>
	 * <td>是</td>
	 * <td>是</td>
	 * <td>\</td>
	 * <td>是</td>
	 * </tr>
	 * <td>WAITING</td>
	 * <td>否</td>
	 * <td>是</td>
	 * <td>是</td>
	 * <td>否</td>
	 * <td>是</td>
	 * <td>否</td>
	 * <td>是</td>
	 * <td>\</td>
	 * </tr>
	 * </table>
	 * 
	 * @author XQ
	 * 
	 */
	public enum JOB_STATUS {

		/**
		 * 已创建
		 */
		CREATED(1),

		/**
		 * 就绪
		 */
		READY(2),

		/**
		 * 执行中
		 */
		RUNNING(3),

		/**
		 * 暂停
		 */
		PAUSED(4),

		/**
		 * 停止
		 */
		STOPPED(5),

		/**
		 * 完成
		 */
		FINISH(6),

		/**
		 * 状态转换中
		 */
		PENDING(7),

		/**
		 * 计划任务等待执行中
		 */
		WAITING(8);

		private int value;

		/**
		 * 获取枚举实际值
		 * 
		 * @return
		 */
		public int getEnumValue() {
			return this.value;
		}

		/**
		 * 通过值获取状态枚举
		 * 
		 * @param value
		 * @return
		 */
		public static JOB_STATUS getEnumByValue(int value) {
			for (JOB_STATUS status : JOB_STATUS.values()) {
				if (status.value == value)
					return status;
			}
			return null;
		}

		private JOB_STATUS(int value) {
			this.value = value;
		}

		@Override
		public String toString() {
			String str = null;
			switch (getEnumByValue(value)) {
			case CREATED:
				str = "已创建";
				break;
			case READY:
				str = "就绪";
				break;
			case RUNNING:
				str = "执行中";
				break;
			case PAUSED:
				str = "暂停";
				break;
			case STOPPED:
				str = "停止";
				break;
			case FINISH:
				str = "完成";
				break;
			case PENDING:
				str = "状态转换中";
				break;
			case WAITING:
				str = "等待执行中";
				break;
			default:
				str = "";
				break;
			}
			return str;
		}

		/**
		 * 获取状态字符串
		 * 
		 * @param value
		 * @return
		 */
		public static String getJobStatus(int value) {
			JOB_STATUS status = getEnumByValue(value);
			return status.toString();
		}

	}

	/**
	 * 任务运行结果枚举
	 * 
	 * @author x.q.
	 * 
	 */
	public enum JOB_RESULT {
		/**
		 * 成功
		 */
		SUCCESS(1),

		/**
		 * 失败
		 */
		FAILED(2),

		/**
		 * 人为中断
		 */
		INTERRUPTED(3),

		/**
		 * 未知结果
		 */
		UNKNOW(4),

		/**
		 * 人为取消
		 */
		CANCEL(5),

		/**
		 * null
		 */
		NULL(6);

		/**
		 * 获取实际值
		 * 
		 * @return
		 */
		public int getEnumValue() {
			return this.value;
		}

		/**
		 * 根据值获取结果枚举
		 * 
		 * @param value
		 * @return
		 */
		public static JOB_RESULT getEnumByValue(int value) {
			for (JOB_RESULT result : JOB_RESULT.values()) {
				if (result.value == value)
					return result;
			}
			return null;
		}

		private int value;

		private JOB_RESULT(int value) {
			this.value = value;
		}

		@Override
		public String toString() {
			String str = null;
			switch (getEnumByValue(value)) {
			case SUCCESS:
				str = "执行成功";
				break;
			case FAILED:
				str = "执行失败";
				break;
			case INTERRUPTED:
				str = "人为中断";
				break;
			case UNKNOW:
				str = "未知结果";
				break;
			case CANCEL:
				str = "人为取消";
				break;
			case NULL:
				str = "空(正在运行)";
				break;
			default:
				str = "";
				break;
			}
			return str;
		}

		/**
		 * 获取结果字符串
		 * 
		 * @param value
		 * @return
		 */
		public static String getJobResult(int value) {
			JOB_RESULT result = getEnumByValue(value);
			return result.toString();
		}
	}

	/**
	 * task commands
	 * 
	 * @author x.q.
	 * 
	 */
	public enum JOB_CMD {

		/**
		 * 创建
		 */
		CREATEJOB(1),

		/**
		 * 修改
		 */
		MODIFYJOB(2),

		/**
		 * 停止运行
		 */
		STOPJOB(3),

		/**
		 * 查询
		 */
		GETJOBDATA(4),

		/**
		 * 开始
		 */
		STARTJOB(5),

		/**
		 * 重做
		 */
		RESTARTJOB(6),

		/**
		 * 取消/删除
		 */
		CANCELJOB(7),

		/**
		 * 暂停
		 */
		PAUSEJOB(8),

		/**
		 * 继续任务
		 */
		RESUMEJOB(9),

		/**
		 * 由优先级引起的中断
		 */
		RUNTOREADY(10);

		private int value;

		/**
		 * 获取枚举所代表的实际数值
		 * 
		 * @return
		 */
		public Integer getEnumValue() {
			return this.value;
		}

		/**
		 * 根据值获取枚举对象
		 * 
		 * @param value
		 * @return
		 */
		public static JOB_CMD getEnumByValue(int value) {
			for (JOB_CMD cmd : JOB_CMD.values()) {
				if (cmd.value == value)
					return cmd;
			}
			return null;
		}

		private JOB_CMD(int value) {
			this.value = value;
		}

		private static HashMap<String, JOB_CMD> map = new HashMap<String, Constant.JOB_CMD>();

		@Override
		public String toString() {
			String str = null;
			switch (getEnumByValue(value)) {
			case CREATEJOB:
				str = "创建任务";
				if (!map.containsValue(CREATEJOB))
					map.put(str, CREATEJOB);
				break;
			case MODIFYJOB:
				str = "修改任务";
				if (!map.containsValue(MODIFYJOB))
					map.put(str, MODIFYJOB);
				break;
			case STOPJOB:
				str = "停止任务";
				if (!map.containsValue(STOPJOB))
					map.put(str, STOPJOB);
				break;
			case GETJOBDATA:
				str = "查询任务";
				if (!map.containsValue(GETJOBDATA))
					map.put(str, GETJOBDATA);
				break;
			case STARTJOB:
				str = "开始任务";
				if (!map.containsValue(STARTJOB))
					map.put(str, STARTJOB);
				break;
			case RESTARTJOB:
				str = "重新开始";
				if (!map.containsValue(RESTARTJOB))
					map.put(str, RESTARTJOB);
				break;
			case CANCELJOB:
				str = "删除任务";
				if (!map.containsValue(CANCELJOB))
					map.put(str, CANCELJOB);
				break;
			case PAUSEJOB:
				str = "暂停任务";
				if (!map.containsValue(PAUSEJOB))
					map.put(str, PAUSEJOB);
				break;
			case RESUMEJOB:
				str = "继续任务";
				if (!map.containsValue(RESUMEJOB))
					map.put(str, RESUMEJOB);
				break;
			case RUNTOREADY:
				str = "由优先级引起的系统中断";
				if (!map.containsValue(RUNTOREADY))
					map.put(str, RUNTOREADY);
				break;
			default:
				str = "";
				break;
			}
			return str;
		}

		/**
		 * 获取控制命令字符串
		 * 
		 * @param value
		 * @return
		 */
		public static String getCMD(int value) {
			JOB_CMD cmd = getEnumByValue(value);
			return cmd.toString();
		}

		/**
		 * 根据字符串获取控制命令枚举
		 * 
		 * @param cmdStr
		 * @return
		 */
		public static JOB_CMD getCMD(String cmdStr) {
			if (map.containsKey(cmdStr))
				return map.get(cmdStr);
			return null;
		}
	}

	/**
	 * 任务运行类型 0=非计划任务；1=计划任务
	 * 
	 * @author x.q.
	 * 
	 */
	public enum JOB_RUN_TYPE {

		/**
		 * 非计划任务
		 */
		NOT_PLAN(0),

		/**
		 * 计划任务
		 */
		PLAN(1);

		private int value;

		private JOB_RUN_TYPE(int value) {
			this.value = value;
		}

		/**
		 * 获取枚举实际值
		 * 
		 * @return
		 */
		public Integer getEnumValue() {
			return this.value;
		}

		/**
		 * 根据值获取枚举对象
		 * 
		 * @param value
		 * @return
		 */
		public static JOB_RUN_TYPE getEnumByValue(int value) {
			for (JOB_RUN_TYPE type : JOB_RUN_TYPE.values()) {
				if (type.value == value)
					return type;
			}
			return null;
		}

		@Override
		public String toString() {
			return this.value == 0 ? "实时任务" : "计划任务";
		}

		/**
		 * 获取运行类型字符串
		 * 
		 * @param value
		 * @return
		 */
		public static String getRunType(int value) {
			JOB_RUN_TYPE type = getEnumByValue(value);
			return type.toString();
		}
	}

	/**
	 * 预定义的5种任务类型
	 * 
	 * @author x.q.
	 * 
	 */
	public enum JOB_TYPE {

		/**
		 * 数据出库
		 */
		DATA_OUT(1),

		/**
		 * 数据入库
		 */
		DATA_IN(2),

		/**
		 * 数据迁移
		 */
		DATA_MIGRATE(3),

		/**
		 * 数据备份
		 */
		DATA_BACKUP(4),

		/**
		 * 数据恢复
		 */
		DATA_RECOVERY(5);

		private int value;

		private JOB_TYPE(int value) {
			this.value = value;
		}

		/**
		 * 获取枚举的实际值
		 * 
		 * @return
		 */
		public Integer getEnumValue() {
			return this.value;
		}

		/**
		 * 根据值获取枚举
		 * 
		 * @param value
		 * @return
		 */
		public static JOB_TYPE getEnumByValue(int value) {
			for (JOB_TYPE type : JOB_TYPE.values()) {
				if (type.value == value)
					return type;
			}
			return null;
		}

		public String toString() {
			String str = "";
			switch (getEnumByValue(value)) {
			case DATA_OUT:
				str = "数据出库";
				break;
			case DATA_IN:
				str = "数据入库";
				break;
			case DATA_MIGRATE:
				str = "数据迁移";
				break;
			case DATA_BACKUP:
				str = "数据备份";
				break;
			case DATA_RECOVERY:
				str = "数据还原";
				break;
			default:
				break;
			}
			return str;
		}

		/**
		 * 根据值获取枚举字符串
		 * 
		 * @param value
		 * @return
		 */
		public static String getJobType(int value) {
			return getEnumByValue(value).toString();
		}
	}

	/**
	 * 日志类型 0=用户操作日志；1=系统日志；2=任务日志
	 * 
	 * @author x.q.
	 * 
	 */
	public enum LOG_TYPE {

		/**
		 * 用户操作日志
		 */
		user(0),

		/**
		 * 系统日志
		 */
		system(1),

		/**
		 * 任务日志
		 */
		task(2);

		private int value;

		private LOG_TYPE(int value) {
			this.value = value;
		}

		/**
		 * 获取枚举实际值
		 * 
		 * @return
		 */
		public Integer getEnumValue() {
			return this.value;
		}

		@Override
		public String toString() {
			String context = "";
			switch (this) {
			case user:
				context = "用户操作日志";
				break;
			case system:
				context = "系统日志日志";
				break;
			case task:
				context = "任务日志";
				break;

			default:
				break;
			}
			return context;
		}

		/**
		 * 根据值获取枚举
		 * 
		 * @param value
		 * @return
		 */
		public static LOG_TYPE getEnumByValue(int value) {
			for (LOG_TYPE vs : LOG_TYPE.values()) {
				if (value == vs.getEnumValue())
					return vs;
			}
			return null;
		}

		/**
		 * 获取枚举字符串
		 * 
		 * @param value
		 * @return
		 */
		public static String getLogType(int value) {
			return getEnumByValue(value).toString();
		}

	}

	/**
	 * 日志内容分类 0=正常日志；1=异常日志
	 * 
	 * @author x.q.
	 * 
	 */
	public enum LOG_CONTENT_TYPE {

		/**
		 * 正常日志
		 */
		NORMAL(0),

		/**
		 * 异常日志
		 */
		ERROR(1);

		private int value;

		private LOG_CONTENT_TYPE(int value) {
			this.value = value;
		}

		public Integer getEnumValue() {
			return this.value;
		}

		@Override
		public String toString() {
			String context = "";
			switch (this) {
			case NORMAL:
				context = "正常日志";
				break;
			case ERROR:
				context = "异常日志";
				break;

			default:
				break;
			}
			return super.toString() + context;
		}

		/**
		 * 根据值获取枚举值
		 * 
		 * @param value
		 * @return
		 */
		public static LOG_CONTENT_TYPE getEnumByValue(int value) {
			for (LOG_CONTENT_TYPE vs : LOG_CONTENT_TYPE.values()) {
				if (value == vs.getEnumValue())
					return vs;
			}
			return null;
		}

		/**
		 * 获取枚举字符串
		 * 
		 * @param value
		 * @return
		 */
		public static String getLogContentType(int value) {
			return getEnumByValue(value).toString();
		}
	}
	
	/**
	 * 用户日志操作结果 0=操作失败；1=操作成功
	 * 
	 * @author x.q.
	 * 
	 */
	public enum USER_LOG_RESULT {

		/**
		 * 操作失败
		 */
		input(0),

		/**
		 * 操作成功
		 */
		success(1);

		private int value;

		private USER_LOG_RESULT(int value) {
			this.value = value;
		}

		public Integer getEnumValue() {
			return this.value;
		}

		public String toString() {
			String context = "";
			switch (this) {
			case input:
				context = "操作失败";
				break;
			case success:
				context = "操作成功";
				break;

			default:
				break;
			}
			return context;
		}

		/**
		 * 根据值获取枚举
		 * 
		 * @param value
		 * @return
		 */
		public static USER_LOG_RESULT getEnumByValue(int value) {
			for (USER_LOG_RESULT ulr : USER_LOG_RESULT.values()) {
				if (value == ulr.getEnumValue())
					return ulr;
			}
			return null;
		}

		/**
		 * 获取枚举字符串
		 * 
		 * @param value
		 * @return
		 */
		public static String getUserLogResult(int value) {
			return getEnumByValue(value).toString();
		}
	}

	/**
	 * 数据属性验证类型
	 * 
	 * @author x.q.
	 * 
	 */
	public enum VALID_TYPE {

		/**
		 * 相等
		 */
		EQUAL(1),

		/**
		 * 范围
		 */
		SCOPE(2),

		/**
		 * 枚举
		 */
		ENUM(3);

		private int value;

		private VALID_TYPE(int value) {
			this.value = value;
		}

		/**
		 * 获取枚举实际值
		 * 
		 * @return
		 */
		public int getEnumValue() {
			return this.value;
		}

		/**
		 * 根据值获取验证类型枚举
		 * 
		 * @param value
		 * @return
		 */
		public static VALID_TYPE getEnumByValue(int value) {
			for (VALID_TYPE type : VALID_TYPE.values()) {
				if (type.value == value)
					return type;
			}
			return null;
		}

		/**
		 * 根据字符串获取验证类型枚举
		 * 
		 * @param value
		 * @return
		 */
		public static VALID_TYPE getEnumByValue(String str) {
			if ("equal".equalsIgnoreCase(str))
				return EQUAL;
			else if ("scope".equalsIgnoreCase(str))
				return SCOPE;
			else if ("enum".equalsIgnoreCase(str))
				return ENUM;
			return null;
		}
		
		public String toString(){
			String str = null;
			switch (this) {
			case ENUM:
				str = "ENUM";
				break;
			case EQUAL:
				str = "EQUAL";
				break;
			case SCOPE:
				str = "SCOPE";
				break;

			default:
				break;
			}
			return str;
		}
	}

	// 修改
    /**
	 * 业务数据类型
	 * 
	 * @author x.q.
	 * 
	 */
	public enum BUSINESS_DATA_TYPE {
	
	    /**
	     * 1对N类型
	     */
	    ONTYPE(1),
	
	    /**
	     * N关联类型
	     */
	    NNTYPE(2),
	
	    /**
	     * 常规类型
	     */
	    NORMALTYPE(3),
	
	    /**
	     * 小数据
	     */
	    SMALLTYPE(4);
	
	    private int value;
	
	    private BUSINESS_DATA_TYPE(int value) {
	        this.value = value;
	    }
	
	    /**
	     * 获取枚举实际的值
	     * 
	     * @return
	     */
	    public int getEnumValue() {
	        return this.value;
	    }
	
	    /**
	     * 根据值获取业务数据类型枚举
	     * 
	     * @param value
	     * @return
	     */
	    public static BUSINESS_DATA_TYPE getEnumByValue(int value) {
	        for (BUSINESS_DATA_TYPE type : BUSINESS_DATA_TYPE.values()) {
	            if (type.value == value)
	                return type;
	        }
	        return null;
	    }
	    
	    public String toString(){
	        String str = null;
	        switch (getEnumByValue(value)) {
	//      case NNTYPE:
	//          str = "N关联类型";
	//          break;
	        case NORMALTYPE:
//	            str = "常规类型";
	            str = "Normal type";
	            break;
	//      case ONTYPE:
	//          str = "一对N类型";
	//          break;
	        case SMALLTYPE:
	            str = "小数据类型";
	            str = "Small data type";
	            break;
	        default:
	            str = "";
	            break;
	        }
	        return str;
	    }
	}
	
	
	
	/**
	 * @author zhangly
	 * 产品判定规则-修改-数据类型下拉框
	 */
	public enum BUSINESS_DATA_TYPE1 {
	
	    /**
	     * 常规类型
	     */
	    NORMALTYPE(3),
	
	    /**
	     * 小数据
	     */
	    SMALLTYPE(4);
	
	    private int value;
	
	    private BUSINESS_DATA_TYPE1(int value) {
	        this.value = value;
	    }
	
	    /**
	     * 获取枚举实际的值
	     * 
	     * @return
	     */
	    public int getEnumValue() {
	        return this.value;
	    }
	
	    /**
	     * 根据值获取业务数据类型枚举
	     * 
	     * @param value
	     * @return
	     */
	    public static BUSINESS_DATA_TYPE getEnumByValue(int value) {
	        for (BUSINESS_DATA_TYPE type : BUSINESS_DATA_TYPE.values()) {
	            if (type.value == value)
	                return type;
	        }
	        return null;
	    }
	    
	    public String toString(){
	        String str = null;
	        switch (getEnumByValue(value)) {

	        case NORMALTYPE:
	            str = "Normal type";
	            break;
	        case SMALLTYPE:
	            str = "Small data type";
	            break;
	        default:
	            str = "";
	            break;
	        }
	        return str;
	    }
	}
	
	

	/**
	 * 源数据文件类型
	 * 
	 * @author x.q.
	 * 
	 */
	public enum SOURCE_FILE_TYPE {

		/**
		 * 纯文本类型
		 */
		TEXT(1),

		/**
		 * XML格式类型
		 */
		XML(2);

		private int value;

		private SOURCE_FILE_TYPE(int value) {
			this.value = value;
		}

		/**
		 * 获取枚举实际值
		 * 
		 * @return
		 */
		public int getEnumValue() {
			return this.value;
		}

		/**
		 * 根据值获取枚举
		 * 
		 * @param value
		 * @return
		 */
		public static SOURCE_FILE_TYPE getEnumByValue(int value) {
			for (SOURCE_FILE_TYPE type : SOURCE_FILE_TYPE.values()) {
				if (type.value == value)
					return type;
			}
			return null;
		}
		
		public String toString(){
			String str = null;
			switch (this) {
			case TEXT:
//				str = "纯文本类型";
				str = "pure-text format";
				break;
			case XML:
//				str = "XML格式类型";
				str = "XML format";
				break;

			default:
				break;
			}
			return str;
		}
	}
	// 新增
    /**
     * 数据关联枚举类型
     * 2015/02/07 ckj added
     */
    public enum CONNECT_DATA_TYPE {
        // N关联类型
        NNTYPE(1),
        
        // 1对N类型
        ONTYPE(2),
        
        // 关联类型，
        CCTYPE(3);
        
        private int value;

        private CONNECT_DATA_TYPE(int value) {
            this.value = value;
        }
        
        /**
         * 获取枚举实际的值
         * 
         * @return
         */
        public int getEnumValue() {
            return this.value;
        }

        /**
         * 根据值获取业务数据类型枚举
         * 
         * @param value
         * @return
         */
        public static CONNECT_DATA_TYPE getEnumByValue(int value) {
            for (CONNECT_DATA_TYPE type : CONNECT_DATA_TYPE.values()) {
                if (type.value == value)
                    return type;
            }
            return null;
        }
        
        public String toString(){
            String str = null;
            switch (getEnumByValue(value)) {
            case NNTYPE:
  //              str = "N关联类型";
                str = "N association type";
                break;
            case ONTYPE:            
    //            str = "一对N类型";
                str = "One to N type";
                break;
            case CCTYPE:
//                str = "关联类型";
                str = "Conventional type";
                break;
            default:
                str = "";
                break;
            }
            return str;
        }
    }
    
    /**
     * 运行状态说明枚举类型
     * 2016/10/11 zengnl added
     */ 
    public enum RUNSTATUS_TO_OMS{
    	/**
		 * 已收到采集订单审核结果
		 */
    	COLLECT_RECEIVED_AUDIT_RESULT(1),
    	/**
		 * 收到采集订单数据采集拆分结果
		 */
    	COLLECT_RECEIVED_PLAN_RESULT(2),
    	/**
		 * 收到归档通知，原始数据归档开始
		 */
    	COLLECT_RECEIVED_ARCHIVE_RAW(3),
    	/**
		 * 原始数据归档完成
		 */
    	COLLECT_ARCHIVE_RAW_SUCCESS(4),
    	/**
		 * 收到归档通知，0级数据归档开始
		 */
    	COLLECT_RECEIVED_ARCHIVE_L0DATA(5),
    	/**
		 * 0级数据归档完成
		 */
    	COLLECT_ARCHIVE_L0DATA_SUCCESS(6),
    	/**
		 * 收到外部产品订购单
		 */
    	ORDER_RECEIVED_PRODUCT_ORDER(7),
    	/**
		 * 开始推送
		 */
    	ORDER_START_PUSH(8),
    	/**
		 * 元数据数据推送完成通知
		 */
    	ORDER_PUSH_PRODUCT_SUCCESS(9),
    	/**
		 * 接收定制回执
		 */
    	CUSTOM_RECEIVED_RECEIPT(10),
    	/**
		 * 接收提取
		 */
    	CUSTOM_RECEIVED_EXTRACT(11),
    	/**
		 * 接收产品归档订单
		 */
    	CUSTOM_RECEIVED_ARCHIVE_PRODUCT(12),
    	/**
		 * 完成归档
		 */
    	CUSTOM_ARCHIVE_PRODUCT_SUCCESS(13),
    	/**
		 * 产品数据推送完成通知
		 */
    	CUSTOM_PUSH_PRODUCT_SUCCESS(14),
    	/**
		 * 0级、产品数据同步删除通知
		 */
    	DELETE_SEND_NOTICE(15),
    	/**
		 * DMS数据归档模块异常
		 */
    	DMS_ARCHIVE_EXCEPTION(16),
    	/**
		 * DMS数据迁移模块异常
		 */
    	DMS_MIGRATION_EXCEPTION(17),
    	/**
		 * DMS数据提取模块异常
		 */
    	DMS_EXTRACT_EXCEPTION(18),
    	/**
		 * DMS数据查询模块异常
		 */
    	DMS_QUERY_EXCEPTION(19),
    	/**
		 * DMS数据归档模块失败
		 */
    	DMS_ARCHIVE_FAULT(20),
    	/**
		 * DMS数据迁移模块失败
		 */
    	DMS_MIGRATION_FAULT(21),
    	/**
		 * DMS数据提取模块失败
		 */
    	DMS_EXTRACT_FAULT(22),
    	/**
		 * DMS数据查询模块失败
		 */
    	DMS_QUERY_FAULT(23);
    	
    	private int value;
    	
    	private RUNSTATUS_TO_OMS(int value) {
            this.value = value;
        }
    	
    	/**
         * 获取枚举实际的值
         * 
         * @return
         */
    	public int getEnumValue() {
            return this.value;
        }
    	
    	/**
         * 根据值获取运行状态说明枚举
         * 
         * @param value
         * @return
         */
    	public static RUNSTATUS_TO_OMS getEnumByValue(int value) {
            for (RUNSTATUS_TO_OMS type : RUNSTATUS_TO_OMS.values()) {
                if (type.value == value)
                    return type;
            }
            return null;
        }
    	
    	 public String toString(){
             String str = null;
             switch (getEnumByValue(value)) {
             case COLLECT_RECEIVED_AUDIT_RESULT:;
                 str = "checked collection order had been found";
                 break;
             case COLLECT_RECEIVED_PLAN_RESULT:       
                 str = "planned collection order had been found";
                 break;
             case COLLECT_RECEIVED_ARCHIVE_RAW:
                 str = "archived confirm had been received, RAWDATA is starting to archive";
                 break;
             case COLLECT_ARCHIVE_RAW_SUCCESS:
            	 str = "RAWDATA archivement had been finished";
            	 break;
             case COLLECT_RECEIVED_ARCHIVE_L0DATA:    
            	 str = "archived confirm had been received，L0DATA is starting to archive";
            	 break;
             case COLLECT_ARCHIVE_L0DATA_SUCCESS:
            	 str = "L0DATA archivement had been finished";
            	 break;    
             case ORDER_RECEIVED_PRODUCT_ORDER:;
             	 str = "external pruduct order had been received";
             	 break;
             case ORDER_START_PUSH:       
            	 str = "starting push";
            	 break;
             case ORDER_PUSH_PRODUCT_SUCCESS:
            	 str = "metadata push had been finished";
            	 break;
             case CUSTOM_RECEIVED_RECEIPT:
            	 str = "custom receipt had been found";
            	 break;
             case CUSTOM_RECEIVED_EXTRACT:    
            	 str = "extract order had been found";
            	 break;
             case CUSTOM_RECEIVED_ARCHIVE_PRODUCT:
            	 str = "PRODUCT archive order had been found";
            	 break;  
             case CUSTOM_ARCHIVE_PRODUCT_SUCCESS:       
                 str = "archivement had been finished";
                 break;
             case CUSTOM_PUSH_PRODUCT_SUCCESS:
                 str = "Product push had been finished";
                 break;
             case DELETE_SEND_NOTICE:
            	 str = "L0DATA or PRODUCT synchronization order had been send";
            	 break;
             case DMS_ARCHIVE_EXCEPTION:    
            	 str = "DMS archive module exception";
            	 break;
             case DMS_MIGRATION_EXCEPTION:
            	 str = "DMS migration module exception";
            	 break;    
             case DMS_EXTRACT_EXCEPTION:;
             	 str = "DMS extract module exception";
             	 break;
             case DMS_QUERY_EXCEPTION:       
            	 str = "DMS query module exception";
            	 break;
             case DMS_ARCHIVE_FAULT:
            	 str = "DMS archive module fault";
            	 break;
             case DMS_MIGRATION_FAULT:
            	 str = "DMS migration module fault";
            	 break;
             case DMS_EXTRACT_FAULT:    
            	 str = "DMS extract module fault";
            	 break;
             case DMS_QUERY_FAULT:
            	 str = "DMS query module fault";
            	 break;  
             default:
                 str = "";
                 break;
             }
             return str;
         }
     }

}
