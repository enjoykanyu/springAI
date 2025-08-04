package cn.kanyu.springai.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class MoreModelConfig implements Serializable {


    /**
     * 模型
     */
    private String model;

    /**
     *温度
     */
    private Double temperature;





}
