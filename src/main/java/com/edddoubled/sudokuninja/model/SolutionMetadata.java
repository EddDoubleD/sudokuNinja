package com.edddoubled.sudokuninja.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@Document
public class SolutionMetadata {
    /**
     * hash code from the input string
     */
    @Id
    private String hash;

    /**
     * decision matrix
     */
    private String solution;

    /**
     * decision operation history
     */
    private String log;

}
