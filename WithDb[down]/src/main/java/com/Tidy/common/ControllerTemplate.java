package com.Tidy.common;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class ControllerTemplate<
        D extends BaseDto,
        C extends com.Tidy.common.BaseCriteria,
        S extends ServiceTemplate> {

    protected final S service;
}
