package com.joker.mms.model.dto.meeting;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

@Data
public class MeetingDeleteRequest implements Serializable {

    /**
     * id
     */
    private Long id;


    private static final long serialVersionUID = 1L;
}
