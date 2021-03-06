package cn.canye365.demo.service;

import cn.canye365.demo.domain.${Domain};
import cn.canye365.demo.dto.${Domain}Dto;
import cn.canye365.demo.dto.PageDto;

import java.util.List;

/**
 * @author CanYe
 */
public interface ${Domain}Service {

    /**
     * 不返回了，数据都存在了pageDto当中
     * @param pageDto
     */
    void list(PageDto<${Domain}Dto> pageDto);

    ${Domain}Dto findById(long id);

    void save(${Domain}Dto ${domain}Dto);

    void delete(long id);
}
