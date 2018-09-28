package sidmeyer.trainchecker.model.uz;

import lombok.*;

import java.time.LocalTime;
import java.time.ZonedDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UZTo {
    private String code;
    private String station;
    private String stationTrain;
    private String date;
    private LocalTime time;
    private ZonedDateTime sortTime;
}
