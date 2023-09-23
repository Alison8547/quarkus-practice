package org.com.entity;

import lombok.*;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "Movie", description = "Movie representation")
public class Movie {

    private Integer id;
    @Schema(required = true)
    private String title;
}
