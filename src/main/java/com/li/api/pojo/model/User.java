package com.li.api.pojo.model;

import com.li.api.util.MapAndJson;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Map;
import java.util.Objects;

/**
 * @author 黎源
 * @date 2020/12/22 16:01
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String openid;
    private String nickname;
    private Integer unifyUid;
    private String email;
    private String password;
    private String mobile;
    @Convert(converter = MapAndJson.class)
    private Map<String,Object> wxProfile;

}
