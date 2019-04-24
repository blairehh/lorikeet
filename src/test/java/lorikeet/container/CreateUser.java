package lorikeet.container;

public class CreateUser implements Edict2<User, String, String> {

    private ActionContainer action;

    public User createUser(String email, String password) {
        User user = new User();
        user.email = email;
        user.password = password;
        user.welcomeMessageSentAt = action.yield(new SendWelcomeMessage(), email);
        return user;
    }


    @Override
    public User invoke(String email, String password) {
        return this.createUser(email, password);
    }

    @Override
    public void inject(ActionContainer action) {
        this.action = action;
    }

}
