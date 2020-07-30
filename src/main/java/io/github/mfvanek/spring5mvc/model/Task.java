package io.github.mfvanek.spring5mvc.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Task {

    private String title;
    private String description;
}
