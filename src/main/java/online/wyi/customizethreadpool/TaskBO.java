package online.wyi.customizethreadpool;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskBO {

    /**
     * 主键
     */
    private Long id;

    /**
     * 任务名称
     */
    private String name;

    /**
     * 任务code
     */
    private String code;

    /**
     * 任务类型
     */
    private String status;

    /**
     * 任务配置
     */
    private String config;

    /**
     * 扩展信息
     */
    private String extInfo;

    /**
     * 描述
     */
    private String description;

    /**
     * 内容类型
     */
    private String taskType;

}