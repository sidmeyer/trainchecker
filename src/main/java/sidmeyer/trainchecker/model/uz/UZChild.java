package sidmeyer.trainchecker.model.uz;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UZChild {
    private LocalDate minDate;
    private LocalDate maxDate;
}
