package homeless.monkey.com.mvc_task2.mapper;

import homeless.monkey.com.mvc_task2.dto.BookDto;
import homeless.monkey.com.mvc_task2.model.BookEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookMapper {

    @Mapping(target = "authorName", source = "author.name")
    @Mapping(target = "bookName", source = "name")
    BookDto toBookDto(BookEntity bookEntity);
}
