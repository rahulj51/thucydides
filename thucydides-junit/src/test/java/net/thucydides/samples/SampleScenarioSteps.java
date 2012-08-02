package net.thucydides.samples;

import net.thucydides.core.Thucydides;
import net.thucydides.core.annotations.Pending;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.annotations.StepGroup;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.TestsRequirement;
import net.thucydides.core.annotations.TestsRequirements;
import net.thucydides.core.pages.Pages;
import net.thucydides.core.steps.ScenarioSteps;
import net.thucydides.junit.runners.ThucydidesRunner;
import org.junit.Ignore;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.NoSuchElementException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;

@SuppressWarnings("serial")
public class SampleScenarioSteps extends ScenarioSteps {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ThucydidesRunner.class);

    public SampleScenarioSteps(Pages pages) {
        super(pages);
    }

    @Steps
    public SampleScenarioNestedSteps nestedSteps;
    
    @Step
    public void stepThatSucceeds() {

    }

    @Step
    public void anotherStepThatSucceeds() {
    }

    @Step
    public void stepThatUsesABrowser() {
        IndexPage page = pages().get(IndexPage.class);
        page.getTitle();
    }

    @Step
    public void stepThatOpensWikipedia() {
        WikipediaPage page = pages().get(WikipediaPage.class);
        page.getTitle();
    }

    @Step
    public void anotherStepThatUsesABrowser() {
        IndexPage page = pages().get(IndexPage.class);
        page.enterValue("some value");
        page.enterValue("some value");
        page.enterValue("some other different value");
    }

    @Step
    public void aStepThatAlsoUsesABrowser() {
        IndexPage page = pages().get(IndexPage.class);
        page.enterValue("some other value");
        page.enterValue("some other value");
        page.enterValue("some other value");
    }

    @Step
    public void stepThree(String option) {
    }

    @Step
    public void stepWithParameters(String option1, Integer option2) {
    }

    @Step
    public void stepThatFails() {
        assertThat(1, is(2));
    }

    @Step
    public void stepFour(String option) {
    }
    
    @Step
    public void stepThatShouldBeSkipped() {
    }

    @Step("Nested group of steps")
    public void stepThatCallsNestedSteps() {
        nestedSteps.stepThatSucceeds();
        nestedSteps.anotherStepThatSucceeds();
    }

    @Step("A pending step")
    @Pending
    public void stepThatIsPending() {
    }

    @Step
    @Ignore
    public void stepThatIsIgnored() {
    }

    public void anUnannotatedMethod() {
    }

    @Step
    public void stepWithAParameter(String value) {
    }
    
    @Step
    public void stepWithTwoParameters(String value, int number) {
    }
    
    @Step
    public void groupOfStepsContainingAFailure() {
        stepThatSucceeds();
        stepThatFails();
        stepThatShouldBeSkipped();
        
    }

    @Step
    public void anotherGroupOfSteps() {
        stepThatSucceeds();
        anotherStepThatSucceeds();
        stepThatIsPending();
        
    }

    @Step
    public void groupOfStepsContainingAnError() {
        stepThatSucceeds();
        anotherStepThatSucceeds();
        String nullString = null;
        int thisShouldFail = nullString.length();
        
    }

    @Step
    public void failsToFindElement() {
        throw new NoSuchElementException("Could not find an element");
    }

    public String name;
    public String age;
    public String address;

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @StepGroup
    public void data_driven_test_step() {
        enter_name_and_age(name, age);
        enter_address(address);
    }

    @Step
    public void data_driven_test_step_that_fails() {
        assertThat(Integer.parseInt(age), is(lessThan(35)));
    }


    public void data_driven_test_step_that_breaks() {
        LOGGER.info("Testing with data name=" +name +", age=" + age);
        if (Integer.parseInt(age) > 35) {
            throw new ElementNotVisibleException("A webdriver issue");
        }
    }

    @Step
    public void enter_address(String address) {
    }

    @Step
    public void enter_name_and_age(String name, String age) {

    }

    @Pending
    @Step
    public void data_driven_test_step_that_is_skipped() {
    }
    
    @Step
    public void store_name(String value) {
        Thucydides.getCurrentSession().put("name", value);
    }

    @Step
    public String get_name() {
        return (String) Thucydides.getCurrentSession().get("name");
    }

    public Boolean hasName() {
        return Thucydides.getCurrentSession().containsKey("name");
    }
}
