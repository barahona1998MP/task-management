package com.mplata.task_management.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTO {
    private Long id;
    @NotBlank(message = "The title cannot be blank.")
    @NotNull(message = "The title cannot be null.")
    private String title;
    private String description;
    private boolean completed;
}
