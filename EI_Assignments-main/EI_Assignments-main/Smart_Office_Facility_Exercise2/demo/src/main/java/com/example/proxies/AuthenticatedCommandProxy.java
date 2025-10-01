package com.example.proxies;

import com.example.commands.ICommand;
import com.example.services.UserAuthenticator;
import com.example.utilities.CommandExecutionException;
import com.example.utilities.Logger;

/**
 * The Proxy Pattern (Structural Design Pattern) implementation.
 * Controls access to sensitive commands (e.g., config, booking) by checking
 * the session credentials before allowing the real command to execute.
 */
public class AuthenticatedCommandProxy implements ICommand {

    private final ICommand realCommand;
    private final UserAuthenticator authenticator;
    private final String username;
    private final String password;
    private final Logger logger = Logger.getInstance();

    public AuthenticatedCommandProxy(ICommand realCommand, String username, String password) {
        this.realCommand = realCommand;
        // In this implementation, the proxy uses the shared singleton authenticator.
        this.authenticator = new UserAuthenticator(); 
        this.username = username;
        this.password = password;
    }

    @Override
    public String execute() {
        // 1. Access Control Logic (Proxy's job)
        // Since the Main login only stores admin credentials in the session,
        // we only need to check against admin authentication logic here.
        if (authenticator.authenticateAdmin(username, password)) {
            
            logger.logInfo("Authentication successful for user: " + username + ". Executing secured command: " + realCommand.getClass().getSimpleName());
            
            // 2. Delegation to the Real Subject
            return realCommand.execute();
        } else {
            
            // The user failed the security check (Proxy Protection)
            String errorMsg = "Authentication failed for user: " + username + ". Access denied to execute secured command.";
            logger.logError(errorMsg);
            
            // Throw exception to be caught and displayed in Main.java
            throw new CommandExecutionException(errorMsg);
        }
    }
}