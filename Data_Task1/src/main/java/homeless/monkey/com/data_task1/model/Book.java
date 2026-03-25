package homeless.monkey.com.data_task1.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Book {
    private Long id;
    private String title;
    private String author;
    private Integer publicationYear;
}
