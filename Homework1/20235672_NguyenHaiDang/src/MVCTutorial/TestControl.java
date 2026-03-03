package MVCTutorial;

public class TestControl {
    private Test model;
    private TestView view;

    public TestControl(Test model, TestView view) {
        this.model = model;
        this.view = view;
    }

    public void setTestName(String name) {
        model.setName(name);
    }

    public String getTestName() {
        return model.getName();
    }

    public void updateView() {
        view.printTestInfo(model.getName());
    }
}