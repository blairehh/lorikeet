package lorikeet.ecosphere;

public class CreateUser implements Edict2<User, String, String> {

    private Plug plug;

    public User createUser(String email, String password) {
        User user = new User();
        user.email = email;
        user.password = password;
        user.welcomeMessageSentAt = plug.yield(new SendWelcomeMessage(), email);

        plug.yield(new ChargePayment(), "USD", 45.0);
        plug.yield(new OpenAccount(), email);

        return user;
    }


    @Override
    public User invoke(String email, String password) {
        return this.createUser(email, password);
    }

    @Override
    public void inject(Plug action) {
        this.plug = action;
    }

    @Override
    public Meta getMeta() {
        return Meta.parameters("email", "password");
    }

}
