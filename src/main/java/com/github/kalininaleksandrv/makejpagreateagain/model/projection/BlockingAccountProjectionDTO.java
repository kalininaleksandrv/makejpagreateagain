package com.github.kalininaleksandrv.makejpagreateagain.model.projection;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BlockingAccountProjectionDTO {
    private Integer id;
    private boolean blocked;
    private String blockingReason;
}
