package org.tec.springbootswagger.model.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class PersonDto {

    @EqualsAndHashCode.Exclude
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String hash;
}
