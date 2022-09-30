package memberSearch.memberSearch.validation;

import lombok.extern.slf4j.Slf4j;
import memberSearch.memberSearch.domain.Member;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

@Slf4j
public class BeanValidationTest {

    @Test
    void beanValidationTest(){
        Member member1 = new Member("", null, null);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<Member>> violations = validator.validate(member1);

        for(ConstraintViolation<Member> violation : violations){
            log.info("violation={}", violation);
        }
    }
}
