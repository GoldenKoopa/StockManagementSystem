package com.sms.stockmanagementsystem.project.data;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ContainerDTO {
    private String id;
    private LocalDateTime time;
    private String username;
    private String data;

    public ContainerDTO(Container container) {
        this.id = container.getId();
        this.time = container.getTime();
        this.username = container.getUsername();
        this.data = container.getData();
    }
}
