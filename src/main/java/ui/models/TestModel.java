package ui.models;

import service.TestService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named("testModel")
@ApplicationScoped
public class TestModel implements Serializable {

    @Inject
    private TestService testService;

    public void init() {
        testService.init();
    }
}
