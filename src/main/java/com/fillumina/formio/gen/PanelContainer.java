package com.fillumina.formio.gen;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class PanelContainer extends Component<PanelContainer> {
    
    public PanelContainer(String key) {
        super("panel", key); // checkout panel also
    }
    
    public PanelContainer title(String title) {
        json.put("title", title);
        return this;
    }
    
}
