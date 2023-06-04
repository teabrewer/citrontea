package kr.sel.tb.citrontea.model.dto;

import kr.sel.tb.citrontea.model.entity.UserPoint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PointDto {

    private Integer id;
    private Integer userId;
    private String pointName;
    private Integer point;

    public static PointDto of(UserPoint userPoint) {
        return new PointDto(userPoint.getId(), userPoint.getUserId(), userPoint.getPointName(), userPoint.getPoint());
    }

}
