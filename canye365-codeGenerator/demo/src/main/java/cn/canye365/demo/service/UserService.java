package cn.canye365.demo.service;

import cn.canye365.demo.domain.User;
import cn.canye365.demo.dto.UserDto;
import cn.canye365.demo.dto.PageDto;

import java.util.List;

/**
 * @author CanYe
 */
public interface UserService {

    /**
     * 不返回了，数据都存在了pageDto当中
     * @param pageDto
     */
    void list(PageDto<UserDto> pageDto);

    UserDto findById(long id);

    void save(UserDto userDto);

    void delete(long id);
}
