package memberSearch.memberSearch.validator;

import lombok.extern.slf4j.Slf4j;
import memberSearch.memberSearch.domain.Member;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Slf4j
@Component
public class MemberValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Member.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Member member = (Member) target;

        if(!StringUtils.hasText(member.getId())){
            errors.rejectValue("id", "required");
        }
        if(!StringUtils.hasText(member.getName())){
            errors.rejectValue("name", "required");
        }
        if(StringUtils.hasText(member.getName()) && member.getName().length() > 10){
            errors.rejectValue("name", "length", new Object[]{1,10}, null);
        }
        if(!StringUtils.hasText(member.getPassword())){
            errors.rejectValue("password", "required");
        }

        if(!StringUtils.hasText(member.getId()) &&
                !StringUtils.hasText(member.getName())&&
                !StringUtils.hasText(member.getPassword())
        ){
            errors.reject("required");
        }
    }
}
