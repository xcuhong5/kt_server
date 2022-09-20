package com.atguigu.ggkt.vo.vod;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * <p>
 * Dict
 * </p>
 *
 * @author qy
 */
/* excel  操作 对应的实体 */
@Data
public class SubjectEeVo {
	// excel 对应的 表头名字，index 是表头索引，0是第一个表头
	@ExcelProperty(value = "id" ,index = 0)
	private Long id;

	@ExcelProperty(value = "课程分类名称" ,index = 1)
	private String title;

	@ExcelProperty(value = "上级id" ,index = 2)
	private Long parentId;

	@ExcelProperty(value = "排序" ,index = 3)
	private Integer sort;


}

