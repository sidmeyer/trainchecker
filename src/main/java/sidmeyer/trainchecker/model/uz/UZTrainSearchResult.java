package sidmeyer.trainchecker.model.uz;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UZTrainSearchResult {
    private String num;
    private int category;
    private boolean isTransformer;
    private String travelTime;
    private UZFrom from;
    private UZTo to;
    private List<UZType> types;
    private UZChild child;
    private boolean allowStudent;
    private boolean allowBooking;
    private boolean isCis;
    private boolean isEurope;
    private boolean allowPrivilege;
}
