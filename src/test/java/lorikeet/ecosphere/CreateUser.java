package lorikeet.ecosphere;


import lorikeet.Seq;
import lorikeet.ecosphere.meta.Dbg;

public class CreateUser implements Action3<User, String, String, Seq<Integer>> {

    private Axon axon;

    public User createUser(String email, String password, Seq<Integer> codes) {
        User user = new User();
        user.email = email;
        user.password = password;
        user.welcomeMessageSentAt = axon.yield(new SendWelcomeMessage(), email, "Hello");

        axon.yield(new ChargePayment(), "USD", 45.0);
        axon.yield(new OpenAccount(), email);

        return user;
    }


    @Override
    public User invoke(@Dbg("email") String email, @Dbg("password") String password, @Dbg("codes") Seq<Integer> codes) {
        return this.createUser(email, password, codes);
    }

    @Override
    public void inject(Axon action) {
        this.axon = action;
    }

}
