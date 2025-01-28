package models.books;

import lombok.Data;

import java.util.List;


@Data
public class AddBookRequestBodyModel {
    private final String userId;
    private final List<Isbn> CollectionOfIsbns;
}