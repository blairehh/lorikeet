package lorikeet.ecosphere;


import lorikeet.Seq;
import lorikeet.ecosphere.meta.Tag;

public class CreateUser implements Edict3<User, String, String, Seq<Integer>> {

    private Plug plug;

    public User createUser(String email, String password, Seq<Integer> codes) {
        User user = new User();
        user.email = email;
        user.password = password;
        user.welcomeMessageSentAt = plug.yield(new SendWelcomeMessage(), email, "Hello");

        plug.yield(new ChargePayment(), "USD", 45.0);
        plug.yield(new OpenAccount(), email);

        return user;
    }


    @Override
    public User invoke(@Tag("email") String email, @Tag("password") String password, @Tag("codes") Seq<Integer> codes) {
        return this.createUser(email, password, codes);
    }

    @Override
    public void inject(Plug action) {
        this.plug = action;
    }

    @Override
    public Meta getMeta() {
        return Meta.parameters("email", "password", "codes");
    }

}
