package extensionTools.search;

/**
 * A search algorithem factory. takes an {@link extensionTools.search.OptionsBase} It then looks at
 * those options and returns the correct search implemantation of
 * {@link AlgoInterface}
 *
 * @author raanan
 * - the options needed for any search algo.
 */
public class AlgoFactory {
    OptionsBase options = null;

    public AlgoFactory(OptionsBase optionbase) {
        this.options = optionbase;
    }

    public Object create() {
        // TODO when you add search options here is the selection between them
        // for now it's only the in string search

        AlgoInterface search = null;
        if (this.options instanceof OptionsInString) {
            search = new AlgoInString((OptionsInString) this.options);
        }

        if (this.options instanceof OptionsExectMatch) {
            search = new AlgoExectMatch((OptionsExectMatch) this.options);
        }

        if (this.options instanceof OptionsByEntryID) {
            search = new AlgoByEntryID((OptionsByEntryID) this.options);
        }

        if (this.options instanceof OptionsIsPointer) {
            search = new AlgoIsPointer((OptionsIsPointer) options);
        }

        if (this.options instanceof OptionsRegExp) {
            search = new AlgoRegEX((OptionsRegExp) options);
        }

        return search;
    }

}
