package cn.canye365.mybatisplus.demo.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@Builder
public class User {
    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;
    private String name;
    private Integer age;
    private String email;
}
