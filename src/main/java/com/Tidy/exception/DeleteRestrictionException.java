package com.Tidy.exception;

import org.apache.commons.lang3.StringUtils;

public class DeleteRestrictionException extends RuntimeException {

    public DeleteRestrictionException(String className, Long id) {
        super(DeleteRestrictionException.generateMessage(className, id));
    }

    private static String generateMessage(String entity, Long id) {
        return StringUtils.capitalize(entity) +
                " with id " + id + " cannot be deleted because it has one ore more associated relations!";
    }

}
