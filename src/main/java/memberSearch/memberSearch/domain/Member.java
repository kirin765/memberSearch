package memberSearch.memberSearch.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@AllArgsConstructor
public class Member {
    private String id;
    private String name;
    private String password;
}
