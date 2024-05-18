package com.joker.mms.model.dto.meeting;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

@Data
public class MeetingUpdateRequest implements Serializable {


    /**
     * name
     */
    private String name;

    /**
     * 容纳人数
     */
    private Long capacity;

    /**
     * 会议室地点
     */
    private String location;

    /**
     * 会议室状态
     */
    private Integer state;

    /**
     * 附带设备
     */
    private String tags;


    private static final long serialVersionUID = 1L;
}
