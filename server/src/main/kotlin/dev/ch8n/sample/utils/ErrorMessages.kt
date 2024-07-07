package dev.ch8n.sample.utils

enum class ErrorMessages(val message: String) {
    SOMETHING_WENT_WRONG("Something went wrong"),
    UNAUTHORIZED("you are unauthorized to perform this task"),
    NOT_FOUND("resource you are trying to access isn't found"),
    MISSING_REQUEST_PARAMS("missing mandatory request fields")
}