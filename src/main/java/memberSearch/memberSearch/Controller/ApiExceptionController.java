package memberSearch.memberSearch.Controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import memberSearch.memberSearch.exception.BadRequestException;
import memberSearch.memberSearch.exception.UserException;
import memberSearch.memberSearch.exhandler.ErrorResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@Slf4j
public class ApiExceptionController {

    @GetMapping("/api/members/{id}")
    public MemberDto getMember(@PathVariable("id") String id) {
        if (id.equals("ex")) {
            throw new RuntimeException("잘못된 사용자");
        }

        if(id.equals("bad")){
            throw new IllegalArgumentException("잘못된 입력 값");
        }

        if(id.equals("user-ex")){
            throw new UserException("사용자 오류");
        }

        return new MemberDto(id, "hello " + id);
    }

    @GetMapping("/api/response-status-ex1")
    public String responseStatusEx1(){
        throw new BadRequestException();
    }

    @GetMapping("/api/response-status-ex2")
    public String responseStatusEx2(){
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "error.bad", new IllegalArgumentException());
    }

    @GetMapping("/api/default-handler-ex")
    public String defaultException(@RequestParam Integer data){
        return "ok";
    }

//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(IllegalArgumentException.class)
//    public ErrorResult illegalExHandle(IllegalArgumentException e){
//        log.error("[exceptionHandle] ex", e);
//        return new ErrorResult("BAD", e.getMessage());
//    }
//
//
//    @ExceptionHandler
//    public ResponseEntity<ErrorResult> userExHandle(UserException e){
//        log.error("[exceptionHandle] ex", e);
//        ErrorResult errorResult = new ErrorResult("USER-EX", e.getMessage());
//        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
//    }
//
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    @ExceptionHandler
//    public ErrorResult exHandle(Exception e){
//        log.error("[exceptionHandle] ex", e);
//        return new ErrorResult("EX", "내부 오류");
//    }



    @Data
    @AllArgsConstructor
    static class MemberDto {
        private String memberId;
        private String name;
    }

}
