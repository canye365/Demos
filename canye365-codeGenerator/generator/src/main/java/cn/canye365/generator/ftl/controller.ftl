package cn.canye365.code.demo.controller;

import cn.canye365.code.demo.domain.${Domain};
import cn.canye365.code.demo.dto.${Domain}Dto;
import cn.canye365.code.demo.dto.PageDto;
import cn.canye365.code.demo.dto.ResponseDto;
import cn.canye365.code.demo.exception.ValidatorException;
import cn.canye365.code.demo.service.${Domain}Service;
import cn.canye365.code.demo.utils.ValidatorUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 *
 * @author CanYe
 */
@RestController
@RequestMapping("${domain}")
public class ${Domain}Controller {

    /**
     * 日志系统利用反射获取业务名称
     */
    public static final String BUSINESS_NAME = "${tableNameCn}";

    @Autowired
    private ${Domain}Service ${domain}Service;

    /**
     * 分页查询
     * @param pageDto
     * @return
     */
    @PostMapping("/list")
    public ResponseDto<PageDto> list(@RequestBody PageDto<${Domain}Dto> pageDto){
        ResponseDto<PageDto> responseDto = new ResponseDto<>();
        ${domain}Service.list(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    /**
    * 根据id查用户
    * @param id
    * @return
    */
    @GetMapping("/find/{id}")
    public ResponseDto<${Domain}Dto> list(@PathVariable long id){
        ResponseDto<${Domain}Dto> responseDto = new ResponseDto<>();
        ${Domain}Dto ${domain}Dto = ${domain}Service.findById(id);
        responseDto.setContent(${domain}Dto);
        return responseDto;
    }

    /**
     * 保存，有id更新，无id插入
     */
    @PostMapping("/save")
    public ResponseDto<${Domain}Dto> save(@RequestBody ${Domain}Dto ${domain}Dto){

        // 保存校验
        <#list fieldList as field>
        <#if field.name!="id" && field.nameHump!="createdAt" && field.nameHump!="updatedAt" && field.nameHump!="sort">
            <#if !field.nullAble>
        ValidatorUtil.require(${domain}Dto.get${field.nameBigHump}(), "${field.nameCn}");
            </#if>
            <#if (field.length > 0)>
        ValidatorUtil.length(${domain}Dto.get${field.nameBigHump}(), "${field.nameCn}", 1, ${field.length?c});
            </#if>
        </#if>
        </#list>

        ResponseDto<${Domain}Dto> responseDto = new ResponseDto<>();
        ${domain}Service.save(${domain}Dto);
        responseDto.setContent(${domain}Dto);
        return responseDto;
    }

    /**
     * 根据id删除
     */
    @DeleteMapping("/delete/{id}")
    public ResponseDto<${Domain}Dto> delete(@PathVariable long id){
        ${domain}Service.delete(id);
        ResponseDto<${Domain}Dto> responseDto = new ResponseDto<>();
        return responseDto;
    }

}