package ui.models.converter;

import service.TestService;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
public class TestModel implements Serializable {

    @Inject
    private TestService testService;

    public void logInTestService() {
        testService.init();
    }
}
