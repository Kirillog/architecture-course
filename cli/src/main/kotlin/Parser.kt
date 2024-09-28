package cli.src.main.kotlin

class Parser(private val envReader: EnvironmentReader) {
    fun parseCommands(tokens: List<Token>): List<CommandDescription> {
        if (tokens.isEmpty()) {
            return emptyList()
        }
        val commands = mutableListOf<CommandDescription>()
        var currentCommandType: CommandType? = null
        val currentDescription = mutableListOf<String>()
        var currentString = ""
        for (token in tokens) {
            when (token.type) {
                TokenType.Text -> currentString += token.data
                TokenType.Variable -> currentString += envReader.getVariable(token.data)
                TokenType.Delimiter -> {
                    if (currentString != "") {
                        currentDescription.add(currentString)
                        currentString = ""
                    }
                }
                TokenType.Assign -> {
                    currentCommandType = CommandType.Assign
                    if (currentString != "") {
                        currentDescription.add(currentString)
                        currentString = ""
                    } else {
                        // TODO: handle unexpected format
                    }
                }
                // TODO: handle pipe
            }
        }
        if (currentString != "") {
            currentDescription.add(currentString)
        }
        if (currentCommandType == null && currentDescription.isNotEmpty()) {
            currentCommandType = when (currentDescription.first()) {
                "cat" -> CommandType.Cat
                "echo" -> CommandType.Echo
                "wc" -> CommandType.WC
                "pwd" -> CommandType.PWD
                "exit" -> CommandType.Exit
                else -> CommandType.External
            }
        }
        if (currentCommandType != null) {
            commands.add(CommandDescription(currentCommandType, currentDescription))
        }
        return commands
    }
}
