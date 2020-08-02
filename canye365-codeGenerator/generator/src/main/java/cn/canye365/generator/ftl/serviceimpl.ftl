package cn.canye365.code.demo.service.impl;

import cn.canye365.code.demo.domain.${Domain};
import cn.canye365.code.demo.dto.${Domain}Dto;
import cn.canye365.code.demo.domain.${Domain}Example;
import cn.canye365.code.PageDto;
import cn.canye365.code.demo.mapper.${Domain}Mapper;
import cn.canye365.code.demo.service.${Domain}Service;
import cn.canye365.code.CopyUtil;
import cn.canye365.code.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
<#list typeSet as type>
    <#if type=='Date'>
import java.util.Date;
    </#if>
</#list>
/**
 *
 * @author CanYe
 */
@Service
public class ${Domain}ServiceImpl implements ${Domain}Service {

    @Autowired
    @SuppressWarnings("all")
    private ${Domain}Mapper ${domain}Mapper;

    /**
     * 列表查询
     */
    @Override
    public void list(PageDto<${Domain}Dto> pageDto) {
        PageHelper.startPage(pageDto.getCurrentPage(), pageDto.getPageSize());  //第一页就是第一页，没有0 => 相当于limit
        // startPage后，会向下找到第一个select语句并进行分页
        ${Domain}Example ${domain}Example = new ${Domain}Example();
        <#list fieldList as field>
            <#if field.nameHump=='sort'>
        ${domain}Example.setOrderByClause("sort asc");
            </#if>
        </#list>
        List<${Domain}> ${domain}List = ${domain}Mapper.selectByExample(${domain}Example);
        PageInfo<${Domain}> pageInfo = new PageInfo<>(${domain}List);
        pageDto.setTotal(pageInfo.getTotal());
        List<${Domain}Dto> ${domain}DtoList = new ArrayList<>();
        for (int i = 0 ; i < ${domain}List.size(); i++) {
            ${Domain} ${domain} = ${domain}List.get(i);
            ${Domain}Dto ${domain}Dto = new ${Domain}Dto();
            BeanUtils.copyProperties(${domain}, ${domain}Dto);
            ${domain}DtoList.add(${domain}Dto);
        }
        pageDto.setList(${domain}DtoList);
        // 这个地方不返回了，因为数据都存在了pageDto当中。
        //return ;
    }

    @Override
    public ${Domain}Dto findById(long id){
        ${Domain} ${domain} = ${domain}Mapper.selectByPrimaryKey(id);
        ${Domain}Dto ${domain}Dao = CopyUtil.copy(${domain}, ${Domain}Dto.class);
        return userDao;
    }

    /**
     * 保存，有id更新，无id插入
     * @param ${domain}Dto
     */
    @Override
    public void save(${Domain}Dto ${domain}Dto){
        ${Domain} ${domain} = CopyUtil.copy(${domain}Dto, ${Domain}.class);
        if (StringUtils.isEmpty(${domain}.getId())){
            this.insert(${domain});
        } else{
            this.update(${domain});
        }
    }

    private void insert(${Domain} ${domain}) {
        <#list typeSet as type>
            <#if type=='Date'>
        Date now = new Date();
            </#if>
        </#list>
        <#list fieldList as field>
            <#if field.nameHump=='createdAt'>
        ${domain}.setCreatedAt(now);
            </#if>
            <#if field.nameHump=='updatedAt'>
        ${domain}.setUpdatedAt(now);
            </#if>
        </#list>
        ${domain}.setId(null);
        ${domain}Mapper.insert(${domain});
    }

    private void update(${Domain} ${domain}) {
        <#list fieldList as field>
            <#if field.nameHump=='updatedAt'>
        ${domain}.setUpdatedAt(new Date());
            </#if>
        </#list>
        ${domain}Mapper.updateByPrimaryKey(${domain});
    }

    @Override
    public void delete(long id) {
        ${domain}Mapper.deleteByPrimaryKey(id);
    }

}
