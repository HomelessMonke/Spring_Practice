package homeless.monkey.com.data_task1.mapper;

import homeless.monkey.com.data_task1.dto.BookDto;
import homeless.monkey.com.data_task1.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookMapper {

    BookDto bookToDto(Book book);

    @Mapping(target = "id", ignore = true)
    Book dtoToBook(BookDto dto);
}
