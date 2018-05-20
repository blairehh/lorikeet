package lorikeet;

import lorikeet.parse.Parse;

import static org.assertj.core.api.Assertions.assertThat;

public class Expect {

    public static <A> A expect(Parse<A> parse) {
        assertThat(parse.succeded()).isTrue();
        return parse.getResult();
    }

    public static void expect(String value, String code) {
        assertThat(value).isEqualTo(code);
    }

}
