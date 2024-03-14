package com.github.tetrationk.tetrat.errors

class CommandError(message: String, cause: Throwable) : Error(message, cause)