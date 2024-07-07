package dev.ch8n.sample.utils

class BadRequestException(override val message: String) : Exception(message)
class UnauthorizedActivityException(override val message: String) : Exception(message)

// collections
class CollectionUpdateException(override val message: String) : Exception(message)

// products