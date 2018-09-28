package sidmeyer.trainchecker.model.uz;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UZRoute {
    private String from;
    private String to;
    private LocalDate date;
    private LocalTime time;
}
