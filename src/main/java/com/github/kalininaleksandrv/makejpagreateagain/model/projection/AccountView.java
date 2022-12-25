package com.github.kalininaleksandrv.makejpagreateagain.model.projection;

public interface AccountView {
    Integer getId();

    boolean isBlocked();

    String getBlockingReason();
}
