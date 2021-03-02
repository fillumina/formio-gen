package com.fillumina.formio.gen;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class Panel extends Component<Panel> {
    
    public Panel(String key) {
        super("panel", key); // checkout panel also
    }
    
    public Panel title(String title) {
        json.put("title", title);
        return this;
    }
    
}
