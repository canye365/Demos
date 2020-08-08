package cn.canye365.demo.controller;

import cn.canye365.demo.domain.User;
import cn.canye365.demo.dto.UserDto;
import cn.canye365.demo.dto.PageDto;
import cn.canye365.demo.dto.ResponseDto;
import cn.canye365.demo.exception.ValidatorException;
import cn.canye365.demo.service.UserService;
import cn.canye365.demo.utils.ValidatorUtil;
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
@RequestMapping("user")
public class UserController {

    /**
     * 日志系统利用反射获取业务名称
     */
    public static final String BUSINESS_NAME = "Users and global privileges";

    @Autowired
    private UserService userService;

    /**
     * 分页查询
     * @param pageDto
     * @return
     */
    @PostMapping("/list")
    public ResponseDto<PageDto> list(@RequestBody PageDto<UserDto> pageDto){
        ResponseDto<PageDto> responseDto = new ResponseDto<>();
        userService.list(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    /**
    * 根据id查用户
    * @param id
    * @return
    */
    @GetMapping("/find/{id}")
    public ResponseDto<UserDto> list(@PathVariable long id){
        ResponseDto<UserDto> responseDto = new ResponseDto<>();
        UserDto userDto = userService.findById(id);
        responseDto.setContent(userDto);
        return responseDto;
    }

    /**
     * 保存，有id更新，无id插入
     */
    @PostMapping("/save")
    public ResponseDto<UserDto> save(@RequestBody UserDto userDto){

        // 保存校验
        ValidatorUtil.require(userDto.getUsername(), "用户名");
        ValidatorUtil.length(userDto.getUsername(), "用户名", 1, 20);
        ValidatorUtil.length(userDto.getPassword(), "密码", 1, 20);
        ValidatorUtil.require(userDto.getAge(), "年龄");
        ValidatorUtil.length(userDto.getEmail(), "邮箱", 1, 30);

        ResponseDto<UserDto> responseDto = new ResponseDto<>();
        userService.save(userDto);
        responseDto.setContent(userDto);
        return responseDto;
    }

    /**
     * 根据id删除
     */
    @DeleteMapping("/delete/{id}")
    public ResponseDto<UserDto> delete(@PathVariable long id){
        userService.delete(id);
        ResponseDto<UserDto> responseDto = new ResponseDto<>();
        return responseDto;
    }

}
