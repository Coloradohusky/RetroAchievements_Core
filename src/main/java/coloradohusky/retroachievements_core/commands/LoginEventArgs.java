package coloradohusky.retroachievements_core.commands;

public class LoginEventArgs {
    public final String username;
    public final String password;

    public LoginEventArgs(String username, String password) {
        this.username = username;
        this.password = password;
    }
}

