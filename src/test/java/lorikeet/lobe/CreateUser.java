package lorikeet.lobe;


import lorikeet.Seq;
import lorikeet.lobe.articletesting.meta.Dbg;

public class CreateUser implements Action3<User, String, String, Seq<Integer>> {

    private Tract tract;

    public User createUser(String email, String password, Seq<Integer> codes) {
        User user = new User();
        user.email = email;
        user.password = password;
        user.welcomeMessageSentAt = tract.yield(new SendWelcomeMessage(), email, "Hello");

        tract.yield(new ChargePayment(), "USD", 45.0);
        tract.yield(new OpenAccount(), email);

        return user;
    }


    @Override
    public User invoke(@Dbg("email") String email, @Dbg("password") String password, @Dbg("codes") Seq<Integer> codes) {
        return this.createUser(email, password, codes);
    }

    @Override
    public void connect(Tract action) {
        this.tract = action;
    }

}
