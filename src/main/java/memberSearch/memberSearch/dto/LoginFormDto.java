package memberSearch.memberSearch.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class LoginFormDto {

    @NotEmpty
    private String id;

    @NotEmpty
    private String password;
}
