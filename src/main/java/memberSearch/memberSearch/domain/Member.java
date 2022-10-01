package memberSearch.memberSearch.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import memberSearch.memberSearch.validator.SaveCheck;
import memberSearch.memberSearch.validator.UpdateCheck;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Member {

//    @NotBlank
//    @NotBlank(groups = {SaveCheck.class, UpdateCheck.class})
    @NotEmpty
    private String id;

//    @NotNull
//    @NotNull(groups = {UpdateCheck.class})
//@NotBlank(groups = {UpdateCheck.class})
    @NotEmpty
    private String name;

//    @NotNull
//    @NotNull(groups = {UpdateCheck.class})
    @NotEmpty
    private String password;
}
