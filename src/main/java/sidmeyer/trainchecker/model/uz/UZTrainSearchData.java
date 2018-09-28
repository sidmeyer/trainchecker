package sidmeyer.trainchecker.model.uz;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UZTrainSearchData {
    private String warning;
    private List<UZTrainSearchResult> list;
}
