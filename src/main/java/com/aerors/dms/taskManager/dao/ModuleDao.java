package com.aerors.dms.taskManager.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;













import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.jmx.export.assembler.InterfaceBasedMBeanInfoAssembler;
import org.springframework.stereotype.Repository;

import com.aerors.dms.dao.IBaseMongoDao;
import com.aerors.dms.dao.impl.ABaseMongoDao;
import com.aerors.dms.model.FieldConfig;
import com.aerors.dms.taskManager.model.Module;

/**
 **任务功能组件Dao层
 * @author yd
 **add yd 
 **2017/03/19
 */
@Repository
public class ModuleDao  extends ABaseMongoDao<Module> {
	
	/**
	 * 任务组件添加方法
	 * @author yd
	 * @param module
	 * @return
	 */
	public boolean addModule(Module module){
		return false;
	}
	
	/**
	 * 根据任务组件ID删除任务组件方法
	 * @author yd
	 * @param moduleId
	 * @return
	 */
	public boolean deleteModuleById(int moduleId){
		return false;
		
	}
	/**
	 * 根据任务组件ID批量删除任务组件方法
	 * @author yd
	 * @param moduleIds
	 * @return
	 */
	public boolean deleteModuleByIds(int[] moduleIds){
		return false;
	}
	/**
	 * 任务组件更新方法，默认组件ID不可更改
	 * @author yd
	 * @param module
	 * @return
	 */
	public boolean updateModule(Module module){
		return false;
	}
	/**
	 * 根据查询条件进行任务组件查询
	 * @param query
	 * @return
	 */
	public List<Module> searchModules(Query query){
		List<Module> list=null;
		Sort.Direction direction = Sort.Direction.ASC;
		
	    list=this.findAll();
	    
		return list;
	}

	/* (non-Javadoc)
	 * @see com.aerors.dms.dao.impl.ABaseMongoDao#getEntityClass()
	 */
	@Override
	protected Class<Module> getEntityClass() {
		// TODO Auto-generated method stub
		return Module.class;
	}

}
