package com.jalivv.mry.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Page {


    private int pageSize;

    private int pageNo;
}
