package com.geekster.InstagramClone.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostOutput {
    private String instagramName;

    private LocalDateTime createdDate;

    private String postData;

    private String postCaption;

    private String location;

}
