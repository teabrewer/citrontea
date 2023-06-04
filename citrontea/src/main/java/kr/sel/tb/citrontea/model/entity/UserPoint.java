package kr.sel.tb.citrontea.model.entity;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//@Entity
//@Table(name = "user_point")
//@DynamicInsert
//@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPoint {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id")
    private Integer id;

//    @Column(name = "user_id")
    private Integer userId;

//    @Column(name = "point_name")
    private String pointName;

//    @Column(name = "point")
    private Integer point;

//    @Column(name = "created_at")
    private LocalDateTime createdAt;

//    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
