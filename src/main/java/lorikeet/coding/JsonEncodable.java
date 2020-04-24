package lorikeet.coding;

import lorikeet.core.Fallible;
import lorikeet.lobe.EncodeAgent;
import lorikeet.lobe.ResourceInsignia;
import lorikeet.lobe.UsesCoding;

// @TODO return resourceInsignia
public interface JsonEncodable<R extends UsesCoding> extends EncodeAgent<R, Fallible<String>> {

    @Override
    default ResourceInsignia resourceInsignia() {
        return null;
    }

    @Override
    default Fallible<String> junction(R resources) {
        return resources.useCoding().encodeJsonString(this, this.getClass());
    }
}
