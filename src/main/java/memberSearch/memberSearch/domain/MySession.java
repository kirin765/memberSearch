package memberSearch.memberSearch.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MySession {

    private String sessionId;

    private String id;

    private LocalDateTime createdBy;
}
