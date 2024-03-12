package com.shoppify.dto.payload.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryResponse {
   private long id;
   private String name;
   private boolean isShow;
}
