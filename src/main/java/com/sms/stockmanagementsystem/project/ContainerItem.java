package com.sms.stockmanagementsystem.project;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ContainerItem {

    private String id;
    private LocalDateTime time;
    private String user;
    private String data;

    public ContainerItem(String id, String user, String data) {
        this.id = id;
        this.user = user;
        this.data = data;
        this.time = LocalDateTime.now();
    }

}
