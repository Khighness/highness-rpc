package top.parak.api;

import lombok.*;

import java.io.Serializable;

/**
 * @author KHighness
 * @since 2021-08-26
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Hello implements Serializable {

    private String message;

    private String description;

}
